#!/bin/bash

set -x
set -e

# TODO 需要修改成现场的配置
# 192.168.0.71:9200
ELASTIC_SEARCH_HOST="${1}"
# 192.168.0.121:1521/orcl
ORACLE_CONNECT_STR="${2}"
# zxjt_control:123456
ORACLE_USER_PASS="${3}"

# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=$(dirname "$PRG")"/$link"
    fi
done
cd "$(dirname \"$PRG\")/" >/dev/null
APP_HOME="$(pwd -P)"
cd "${APP_HOME}"


# env
if [[ ! -e ../logs ]]; then
  mkdir -p ../logs
fi

# log
ORA2ES_OUT="../logs/$(date "+%Y%m%d%H%M%S").out"
exec > >(tee -a "${ORA2ES_OUT}") 2>&1

# create new index
INDEX_ALIAS="hongwang-dama"
INDEX_NAME="${INDEX_ALIAS}-$(date "+%Y%m%d%H%M%S")"

curl -X PUT "http://${ELASTIC_SEARCH_HOST}/${INDEX_NAME}?pretty" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "index.mapping.ignore_malformed": true
  }
}
'

ora_2_es() {
  sql=$1
  usql --json -c "${sql}" oracle://"${ORACLE_USER_PASS}"@"${ORACLE_CONNECT_STR}" > data.json
  lines=$(wc -l data.json | cut -f 1 -d ' ')
  if [[ "${lines}" -ne 2 ]]; then
    echo "Fail to fetch data from oracle"
    exit 1
  fi
  tail -n 1 data.json > oracle_json_list.json
  python jsonlist2jsons.py oracle_json_list.json es_ready.json
  # send data to es
  curl -H "Content-Type: application/json" -XPOST "http://${ELASTIC_SEARCH_HOST}/${INDEX_NAME}/${INDEX_NAME}/_bulk?pretty&refresh" --data-binary "@es_ready.json"
  # clean
  rm -rf data.json oracle_json_list.json es_ready.json
}

# real work
ora_2_es "SELECT T.*, TYPE AS ASSET_TYPE FROM V_TABLE_INFO T"
ora_2_es "SELECT T.*, 'procedure' AS ASSET_TYPE FROM V_PROCEDURE_INFO T"
ora_2_es "SELECT T.*, 'column' AS ASSET_TYPE FROM V_COLUMN_INFO T"


# relate alias to latest index
alias_size=$(curl -X GET "${ELASTIC_SEARCH_HOST}/*/_alias/${INDEX_ALIAS}?pretty" | grep -o -E "${INDEX_ALIAS}-[0-9]+" | wc -l)
if [[ "${alias_size}" -eq 0 ]]; then
	curl -X PUT "http://${ELASTIC_SEARCH_HOST}/${INDEX_NAME}/_alias/${INDEX_ALIAS}?pretty"
else
    # 只处理alias_size为1的情况
    if [[ "${alias_size}" -ne 1 ]]; then
    	echo "Num of indices to this alias not 1"
    	exit 1
    fi

    last_index=$(curl -X GET "${ELASTIC_SEARCH_HOST}/*/_alias/${INDEX_ALIAS}?pretty" | grep -o -E "${INDEX_ALIAS}-[0-9]+")
    # atom op to switch alias
    curl -X POST "${ELASTIC_SEARCH_HOST}/_aliases?pretty" -H 'Content-Type: application/json' -d'
{
    "actions": [
        { "remove": { "index": "'"${last_index}"'", "alias": "'"${INDEX_ALIAS}"'" }},
        { "add": { "index": "'"${INDEX_NAME}"'", "alias": "'"${INDEX_ALIAS}"'" }}
    ]
}'
fi

# 保留一个index副本

index_reserved=""
for index in $(curl -X GET "${ELASTIC_SEARCH_HOST}/_cat/indices?v" | grep -o -E "${INDEX_ALIAS}-[0-9]+");
do
	echo "current: ${index}, latest: ${INDEX_NAME}"
	if [[ "${INDEX_NAME}" -eq "${index}" ]]; then
		continue
	fi
	# delete
	if [[ -z "${index_reserved}" ]]; then
		index_reserved="${index}"
	else
		# delete the index
		curl -X DELETE "${ELASTIC_SEARCH_HOST}/${index}?pretty"
	fi
done

