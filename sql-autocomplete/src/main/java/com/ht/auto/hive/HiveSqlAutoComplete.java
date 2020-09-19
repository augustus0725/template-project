package com.ht.auto.hive;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ht.auto.SqlAutoComplete;
import com.ht.auto.dfa.Predictor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author canbin.zhang@qq.com
 * date: 2020/9/13
 */
public class HiveSqlAutoComplete implements SqlAutoComplete {
    @Override
    public List<String> suggest(String sql, int pos) {
        if (Strings.isNullOrEmpty(sql) || pos <= 0) {
            return Collections.emptyList();
        }

        String partOfSql = sql.substring(0, pos + 1);
        HplsqlLexer lexer = new HplsqlLexer(CharStreams.fromString(partOfSql));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        HplsqlParser parser = new HplsqlParser(tokenStream);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        HplsqlParser.StmtContext startState = parser.stmt();

        Predictor predictor = new Predictor(parser, null, Sets.newHashSet(1, 2, 3, 4, 5));
        Predictor.CandidatesCollection collection = predictor.collectCandidates(tokenStream.size() - 1, startState);
        List<String> expectedTokens = Lists.newArrayList();
        for (Integer id : collection.tokens.keySet()) {
            expectedTokens.add(parser.getVocabulary().getDisplayName(id));
        }
        // 特殊处理一下, 当发现是需要获取表字段的情况下尝试返回 T_FIELDS_表名, 如果表名不存在还是返回 L_ID 表示这个是用户定义的标识符
        expectedTokens = checkIfTablesFields(sql, pos, expectedTokens, tokenStream);
        return expectedTokens;
    }

    private List<String> checkIfTablesFields(String sql, int pos, List<String> expectedTokens,
                                             CommonTokenStream tokenStream) {
        if (tokenStream.size() < 2 || !".".equals(tokenStream.get(tokenStream.size() - 2).getText())) {
            return expectedTokens;
        }
        // select clause
        if (isSelectClause(expectedTokens, tokenStream)
                || isConditionClause(expectedTokens, tokenStream)) {
            // look right and find 1st match
            String token = tokenStream.get(tokenStream.size() - 3).getText();
            if (Strings.isNullOrEmpty(token)) {
                return expectedTokens;
            }
            HplsqlLexer lexer = new HplsqlLexer(CharStreams.fromString(getNewPartString(sql, pos)));
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 语法解析, 获取表名和引用相关信息
            ParseTreeWalker walker = new ParseTreeWalker();
            TableNameListener listener = new TableNameListener();
            HplsqlParser.StmtContext tree = new HplsqlParser(tokens).stmt();
            walker.walk(listener, tree);

            // 获取表名和引用的映射关系
            Map<String, String> tableAilaMap = listener.getTableNameAilasPair();

            // 尝试根据 token 来翻译成真正的表名
            String real = token;
            for (Map.Entry<String, String> entry : tableAilaMap.entrySet()) {
                if (token.equals(entry.getValue())) {
                    real = entry.getKey();
                    break;
                }
            }

            // where clause 判断 real 是不是表名, 不是表名直接退出
            if (expectedTokens.contains("T_TABLE")) {
                if (!tableAilaMap.containsKey(real)) {
                    return expectedTokens;
                }
            }

            return Collections.singletonList("T_FIELDS_" + real);
        }

        return expectedTokens;
    }

    private boolean isConditionClause(List<String> expectedTokens, CommonTokenStream tokenStream) {
        return expectedTokens.size() == 1 && expectedTokens.contains("L_ID") && tokenStream.size() > 3;
    }

    private boolean isSelectClause(List<String> expectedTokens, CommonTokenStream tokenStream) {
        return expectedTokens.contains("'*'") && expectedTokens.contains("L_ID") && tokenStream.size() > 3;
    }

    private String getNewPartString(String sql, int pos) {
        String sql2 = sql.substring(0, pos);
        if (sql.length() > pos) {
            sql2 += sql.substring(pos + 1);
        }
        return sql2;
    }
}
