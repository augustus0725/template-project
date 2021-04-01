# -*- coding: utf-8 -*-
import codecs
import json
import sys


# sys.argv[1]: raw json list
# sys.argv[2]: bulk ready json file

def main():
    content = codecs.open(sys.argv[1], 'r', 'utf-8').read()
    # remove bad characters
    content_clean = content.replace('\\x', '')
    records = json.loads(content_clean)
    packed_records = []
    action = {"index": {}}
    part = 0
    for record in records:
        all = []
        for k in record:
            if u"CATALOG" == k:
                continue
            v = record[k]
            if v is None or "" == v:
                record[k] = u"null"
            if not (type(v) is str or type(v) is unicode):
                record[k] = unicode(v)
            all.append(record[k])
        # prepare _text field
        record["_text"] = ' '.join(all)
        if u"CATALOG" in record:
            v = record[u"CATALOG"]
            if v and len(v) > 0:
                record[u"CATALOG"] = list(v.split(','))
        packed_records.append(action)
        packed_records.append(record)
        # 分片
        if 5000 == len(packed_records):
            codecs.open(sys.argv[2] + "-part." + str(part), 'w', 'utf-8').write(
                '\n'.join(map(lambda x: json.dumps(x, ensure_ascii=False),
                              packed_records)) + '\n')
            packed_records = []
            part += 1
            pass

    # rest
    codecs.open(sys.argv[2] + "-part." + str(part), 'w', 'utf-8').write(
            '\n'.join(map(lambda x: json.dumps(x, ensure_ascii=False),
                          packed_records)) + '\n')


if __name__ == '__main__':
    main()
