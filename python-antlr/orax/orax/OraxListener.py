# -*- coding: utf-8 -*-
from orax.PlSqlParser import PlSqlParser
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
        # table field bind
        self.bind = {}
        self.insert_into_clause_on = False
        self.insert_bind_table = None
        self.enter_column_list = False

    # Enter a parse tree produced by PlSqlParser#insert_into_clause.
    def enterInsert_into_clause(self, ctx: PlSqlParser.Insert_into_clauseContext):
        self.insert_into_clause_on = True

    # Exit a parse tree produced by PlSqlParser#insert_into_clause.
    def exitInsert_into_clause(self, ctx: PlSqlParser.Insert_into_clauseContext):
        self.insert_into_clause_on = False
        self.insert_bind_table = None

    # Enter a parse tree produced by PlSqlParser#tableview_name.
    def enterTableview_name(self, ctx: PlSqlParser.Tableview_nameContext):
        if self.insert_into_clause_on:
            self.insert_bind_table = ctx.getText().upper()
            self.bind[self.insert_bind_table] = []

    # Enter a parse tree produced by PlSqlParser#column_list.
    def enterColumn_list(self, ctx: PlSqlParser.Column_listContext):
        self.enter_column_list = True

    # Exit a parse tree produced by PlSqlParser#column_list.
    def exitColumn_list(self, ctx: PlSqlParser.Column_listContext):
        self.enter_column_list = False

    # Enter a parse tree produced by PlSqlParser#column_name.
    def enterColumn_name(self, ctx: PlSqlParser.Column_nameContext):
        if self.insert_into_clause_on and self.insert_bind_table and self.enter_column_list:
            self.bind[self.insert_bind_table].append(ctx.getSourceInterval()[0])

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

    def get_bind(self):
        return self.bind

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
