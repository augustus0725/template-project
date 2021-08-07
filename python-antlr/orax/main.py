# -*- coding: utf-8 -*-
import click
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


@click.command()
@click.option('-m', default='console', required=True, type=click.Choice(['console', 'file', 'batch']),
              help="三种运行模式, console表示从命令行传入sql, file表示从文件, batch表示批量也是从文件,要求一行一条sql")
@click.option('-s', default=None, help='输入需要转换的SQL, console模式')
@click.option('-f', default=None, type=click.File('r', 'utf-8'), help='sql文件')
@click.option('--tables', default=None, help='替换的表名, 格式： source_t1:target_t1,source_t2:target_t2')
@click.option('--fields', default=None, help='替换的字段, 格式： source_f1:target_f1,source_f2:target_f2')
def main(m, s, f, tables, fields):
    table_names = dict(table.split(':') for table in tables.split(','))
    print(str(table_names))


if __name__ == '__main__':
    main()
