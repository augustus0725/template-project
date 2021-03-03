#!/usr/bin/env bash

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

# parames
GIT_URL="$1"
PROJECT_NAME="$2"

# create temp dir for building
compile_date=`date "+%Y%m%d%H%M%S"`
build_target="/opt/release/${PROJECT_NAME}/${compile_date}"

rm -rf ${build_target}
mkdir -p ${build_target}

# remove old releases elder than 7 days = 24 * 7.
tmpwatch -af 168 /opt/release

# output log
exec > ${build_target}/release.log 2>&1

#
echo "Date: ${compile_date}"
echo "build_target: ${build_target}"
echo "PROJECT_NAME: ${PROJECT_NAME}"
echo "GIT_URL: ${GIT_URL}"

set -e
set -x
# get new version code
cd ${build_target}
git clone ${GIT_URL}
rm -rf ${PROJECT_NAME}/.git
tar -zcf ${PROJECT_NAME}-src.tgz ${PROJECT_NAME}
cd ${PROJECT_NAME}

# do building
/bin/bash ./gradlew

# copy release output
cp build/distributions/*.tgz ..
#
cd ..
rm -rf ${PROJECT_NAME}

# md5
md5sum *.tgz > release.md5

# if ftp dir exist, copy it to ftp
if [ -e /opt/ftp ]; then
  [ ! -e /opt/ftp/${PROJECT_NAME} ] && mkdir -p /opt/ftp/${PROJECT_NAME}
  mv ${build_target} /opt/ftp/${PROJECT_NAME}
  #
  chown sabo:sabo -R /opt/ftp/${PROJECT_NAME}
  tmpwatch -af 168 /opt/ftp/
fi


