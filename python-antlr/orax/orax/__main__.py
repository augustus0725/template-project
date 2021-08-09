# -*- coding: utf-8 -*-
import click

from orax.OraxImp import run_console_mode, run_file_mode, run_batch_mode


def parse_mapping_from_excel(excel):
    import xlrd
    book = xlrd.open_workbook(excel)
    sheet1 = book.sheets()[0]
    # to find table mapping till empty value
    tables_mapping = {}
    line = 1
    while True:
        try:
            table_from = sheet1.cell(line, 0).value
            table_to = sheet1.cell(line, 3).value
        except Exception as e:
            break
        line = 1 + line
        tables_mapping[table_from] = table_to
    # find fields mapping
    line = 1
    fields_mapping = {}
    while True:
        try:
            field_from = sheet1.cell(line, 1).value
            field_to = sheet1.cell(line, 4).value
        except Exception as e:
            break
        line = 1 + line
        fields_mapping[field_from] = field_to

    return tables_mapping, fields_mapping


@click.command()
@click.option('-m', default='console', type=click.Choice(['console', 'file', 'batch']),
              help="三种运行模式, console表示从命令行传入sql, file表示从文件, batch表示批量也是从文件,要求一行一条sql")
@click.option('-s', default=None, help='输入需要转换的SQL, console模式')
@click.option('-f', default=None, type=click.File('r', 'utf-8'), help='sql文件')
@click.option('--excel', default=None, help='excel文件,里面有映射规则')
@click.option('--tables', default=None, help='替换的表名, 格式： source_t1:target_t1,source_t2:target_t2')
@click.option('--fields', default=None, help='替换的字段, 格式： source_f1:target_f1,source_f2:target_f2')
def main(m, s, f, excel, tables, fields):
    if not tables and not excel:
        print("缺少映射规则, 可以用--excel指定excel文件, 或者用--tables 和 --fields指定")
    table_pairs = {}
    fields_pairs = {}
    if tables and fields:
        table_pairs = dict(table.split(':') for table in tables.split(','))
        fields_pairs = dict(field.split(':') for field in fields.split(','))
    elif excel:
        table_pairs, fields_pairs = parse_mapping_from_excel(excel)
    else:
        print("检查命令行, 缺少映射规则")
        exit(1)

    if 'console' == m:
        if not s:
            print("console模式,SQL不能为空, -s 'select * from t'")
            exit(1)
        print(run_console_mode(s, table_pairs, fields_pairs))
    elif 'file' == m:
        print(run_file_mode(f, table_pairs, fields_pairs))
    elif 'batch' == m:
        print(run_batch_mode(f, table_pairs, fields_pairs))
    else:
        print("检查命令行, 出现不支持的模式")
        exit(1)


if __name__ == '__main__':
    main()
