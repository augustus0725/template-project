# -*- coding: utf-8 -*-
from orax.PlSqlParserListener import PlSqlParserListener, ParserRuleContext


class OraxListener(PlSqlParserListener):
    def __init__(self, parser, tokens):
        self.parser = parser
        self.tokens = tokens

        self.table_alias = []
        self.alias_table_kv = {}
        self.fields_like = []
        self.tables = []
        self.ignores = []

    def __is_table_alias(self, f):
        field_text = self.tokens[f].upper()
        return field_text in self.table_alias and f + 1 < len(self.tokens) and '.' == self.tokens[f + 1]

    def enterEveryRule(self, ctx: ParserRuleContext):
        # print("{}: {}: ({}, {})".format(self.parser.ruleNames[ctx.getRuleIndex()], ctx.getText(),
        #                                 ctx.getSourceInterval()[0], ctx.getSourceInterval()[1]))

        if "table_alias" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            self.table_alias.append(ctx.getText().upper())
            self.ignores.append(ctx.getSourceInterval()[0])
            self.alias_table_kv[ctx.getText().upper()] = self.tokens[ctx.getSourceInterval()[0] - 2].upper()
        if "function_argument" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            self.ignores.append(ctx.getSourceInterval()[0] - 1)
        if "regular_id" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            self.fields_like.append(ctx.getSourceInterval()[0])
        if self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()] in ("query_name", "tableview_name"):
            if ctx.getSourceInterval()[0] != ctx.getSourceInterval()[1]:
                self.ignores.append(ctx.getSourceInterval()[0])
                self.tables.append(ctx.getSourceInterval()[1])
            else:
                self.ignores.append(ctx.getSourceInterval()[0])
                self.tables.append(ctx.getSourceInterval()[0])

    def get_alias_table_kv(self):
        return self.alias_table_kv

    def get_tables(self):
        tables = list(set(self.tables))
        tables.sort()
        # 去掉alias里不是表名的关联
        table_names = set([self.tokens[p].upper() for p in tables])
        keys_to_remove = []
        for k, v in self.alias_table_kv.items():
            if v not in table_names:
                keys_to_remove.append(k)
        for k in keys_to_remove:
            self.alias_table_kv.pop(k)
        return tables

    def get_fields(self):
        fields = list(set(self.fields_like).difference(set(self.ignores)).difference(set(self.tables)))
        fields_to_remove = []
        for f in fields:
            if self.__is_table_alias(f):
                fields_to_remove.append(f)
        fields = list(set(fields).difference(set(fields_to_remove)))
        fields.sort()
        return fields
