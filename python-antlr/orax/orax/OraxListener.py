# -*- coding: utf-8 -*-
from orax.PlSqlParserListener import PlSqlParserListener, ParserRuleContext


class OraxListener(PlSqlParserListener):
    def __init__(self, parser, tokens):
        self.parser = parser
        self.tokens = tokens

        self.table_alias = []
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
        if "function_argument" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            self.ignores.append(ctx.getSourceInterval()[0] - 1)
        if "regular_id" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            self.fields_like.append(ctx.getSourceInterval()[0])
        if "tableview_name" == self.parser.ruleNames[ctx.getRuleContext().getRuleIndex()]:
            if ctx.getSourceInterval()[0] != ctx.getSourceInterval()[1]:
                self.ignores.append(ctx.getSourceInterval()[0])
                self.tables.append(ctx.getSourceInterval()[1])
            else:
                self.tables.append(ctx.getSourceInterval()[0])

    def get_tables(self):
        tables = list(set(self.tables))
        tables.sort()
        return tables

    def get_fields(self):
        fields = list(set(self.fields_like).difference(set(self.ignores)).difference(set(self.tables)))
        for f in fields:
            if self.__is_table_alias(f):
                fields.remove(f)
        fields.sort()
        return fields
