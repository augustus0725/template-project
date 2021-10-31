# -*- coding: utf-8 -*-
import codecs
import json
import re
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
    # re part
    mo = re.compile(u"[\u4e00-\u9fa5]")
    for record in records:
        # all = []
        for k in record:
            v = record[k]
            if v is None or "" == v:
                record[k] = u"null"
            if type(v) is not unicode:
                record[k] = unicode(v)
            if "SCORE" == k:
                record[k] = int(v)
            # all.append(record[k])
        # prepare _text field
        # record["_text"] = ' '.join(all)
        record["TABLE_HAS_DATA"] = "no"
        record["TABLE_HAS_TITLE"] = "no"
        record["COLUMN_HAS_CHINESE"] = "no"
        if "TABLE_COUNT" in record:
            v = record["TABLE_COUNT"]
            try:
                if v:
                    record["TABLE_HAS_DATA"] = "yes" if int(v) > 0 else "no"
            except:
                pass

        if "TABLE_TITLE" in record:
            v = record["TABLE_TITLE"]
            try:
                if v:
                    if type(v) is str:
                        v = unicode(v)
                record["TABLE_HAS_TITLE"] = "yes" if mo.search(v) is not None else "no"
            except:
                pass

        if "COLUMNS" in record:
            v = record["COLUMNS"]
            try:
                if v:
                    if type(v) is str:
                        v = unicode(v)
                record["COLUMN_HAS_CHINESE"] = "yes" if mo.search(v) is not None else "no"
            except:
                pass
        packed_records.append(action)
        packed_records.append(record)
        # 数据量太大文件会切割
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
