# -*- coding: utf-8 -*-
import os

from antlr4 import InputStream, CommonTokenStream, ParseTreeWalker

from orax.OraxListener import OraxListener
from orax.PlSqlLexer import PlSqlLexer
from orax.PlSqlParser import PlSqlParser


def find_tables_and_fields(sql_clause):
    input_stream = InputStream(sql_clause.upper())

    lexer = PlSqlLexer(input_stream)
    stream = CommonTokenStream(lexer)

    stream.fill()

    parser = PlSqlParser(stream)
    raw_tokens = [sql_clause[e.start:e.stop + 1] for e in stream.tokens]
    my_listener = OraxListener(parser, raw_tokens)
    walker = ParseTreeWalker()
    walker.walk(my_listener, parser.unit_statement())
    return my_listener.get_tables(), my_listener.get_fields(), raw_tokens, my_listener.get_alias_table_kv(), \
           my_listener.get_bind(), my_listener.get_repair_info()


def print_tables_and_fields(result):
    print("table names:")
    print(str([result[2][e] for e in result[0]]))
    print("table fields:")
    print(str([result[2][e] for e in result[1]]))


def run_console_mode(s, table_pairs, fields_pairs, repair):
    if not table_pairs:
        table_pairs = []

    if not fields_pairs:
        fields_pairs = []

    if not s or len(s) == 0:
        print("SQL 不能为空!!!")
        exit(1)
    r = find_tables_and_fields(s)


    tokens = r[2]
    tables = r[0]
    all_tables = set([tokens[t].upper() for t in tables])
    # tables
    for t in tables:
        if tokens[t].upper() in table_pairs:
            tokens[t] = table_pairs[tokens[t].upper()]
    # fields
    fields = r[1]
    alias_table_kv = r[3]
    # 需要修复的情况下需要先确定修复的位置 <pos, val>
    repair_pos = {}
    select_fields = r[5]
    if repair:
        for f in fields:
            for sf in select_fields:
                if sf[0] <= f <= sf[1]:
                    repair_pos[sf[1]] = tokens[f]

    # 精确替代
    nice_match = []
    for k, v in r[4].items():
        for vi in v:
            f = tokens[vi].upper()
            if (k, f) in fields_pairs:
                tokens[vi] = fields_pairs[(k, f)]
                nice_match.append(vi)
    # 去掉精确匹配的字段
    for n in nice_match:
        fields.remove(n)

    # #准备简单字段映射
    easy_fields_mapping = {}
    for k, v in fields_pairs.items():
        if k[0] and k[0] in all_tables:
            easy_fields_mapping[k[1].upper()] = v
    for f in fields:
        # 尝试判断前面有没有 alias
        if f - 2 >= 0 and tokens[f - 1] == '.':
            alias = tokens[f - 2].upper()
            if alias in alias_table_kv:
                table_name = alias_table_kv[alias]
                if (table_name, tokens[f].upper()) in fields_pairs:
                    tokens[f] = fields_pairs[(table_name, tokens[f].upper())]
                    continue
        else:
            # TODO 从key里找可能对应的field,做转换, 可能会不太安全
            if tokens[f].upper() in easy_fields_mapping:
                tokens[f] = easy_fields_mapping[tokens[f].upper()]
    output_tokens = []
    if repair:
        for i in range(len(tokens)):
            output_tokens.append(tokens[i])
            if i in repair_pos:
                output_tokens.append(' ')
                output_tokens.append(repair_pos[i])
        tokens = output_tokens
    return ''.join(tokens)


def run_file_mode(f, table_pairs, fields_pairs, repair):
    return run_console_mode(f.read(), table_pairs, fields_pairs, repair)


def run_batch_mode(d, table_pairs, fields_pairs, repair):
    for f in os.listdir(d):
        if not f.endswith('.sql'):
            continue
        with open(os.path.join(d, f + ".out"), encoding="utf-8", mode="w+") as w:
            w.write(run_file_mode(open(os.path.join(d, f), encoding="utf-8"), table_pairs, fields_pairs, repair))
