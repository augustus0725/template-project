# -*- coding: utf-8 -*-
from antlr4 import InputStream, CommonTokenStream, ParseTreeWalker

from PlSqlLexer import PlSqlLexer
from PlSqlParser import PlSqlParser
from OraxListener import OraxListener


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
    return my_listener.get_tables(), my_listener.get_fields(), raw_tokens


def print_tables_and_fields(result):
    print("table names:")
    print(str([result[2][e] for e in result[0]]))
    print("table fields:")
    print(str([result[2][e] for e in result[1]]))


if __name__ == '__main__':
    r = find_tables_and_fields(
        "select INITCAP('hi  there'),TO_CHAR(ts_col, 'DD-MON-YYYY HH24:MI:SSxFF'),CONCAT('A','BC'),"
        "'abc',max(t.a),t.a,t.b from ods.my_table t left join your_table p on t.id = p.id where a100 > 2")
    print_tables_and_fields(r)
    # main("SELECT 'xxx',INITCAP('hi  there') FROM MY_TABLE WHERE '100'='100'".upper())
    # main("SELECT 'MAX(T.A)',t.a, t.b from my_table t left join your_table p on t.id = p.id".upper())
    # main("SELECT MAX(T.A) FROM my_table T LEFT JOIN your_table p ON T.ID = P.ID")
    # main("select t.a, t.b from my_table t left join your_table p on t.id = p.id".upper())
    # main("insert into my_table select * from my_table".upper())
    # main("select a,b,c from my_table".upper())
