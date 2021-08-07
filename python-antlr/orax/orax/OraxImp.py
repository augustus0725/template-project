# -*- coding: utf-8 -*-
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
    return my_listener.get_tables(), my_listener.get_fields(), raw_tokens


def print_tables_and_fields(result):
    print("table names:")
    print(str([result[2][e] for e in result[0]]))
    print("table fields:")
    print(str([result[2][e] for e in result[1]]))


def run_console_mode(s, table_pairs, fields_pairs):
    r = find_tables_and_fields(s)
    tokens = r[2]
    tables = r[0]
    # tables
    for t in tables:
        if tokens[t] in table_pairs:
            tokens[t] = table_pairs[tokens[t]]
    # fields
    fields = r[1]
    for f in fields:
        if tokens[f] in fields_pairs:
            tokens[f] = fields_pairs[tokens[f]]

    return ' '.join(tokens)


def run_file_mode(f, table_pairs, fields_pairs):
    return run_console_mode(f.read(), table_pairs, fields_pairs)


def run_batch_mode(f, table_pairs, fields_pairs):
    return '\n'.join([run_console_mode(sql, table_pairs, fields_pairs) for sql in f.readlines()])
