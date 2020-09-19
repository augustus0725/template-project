package com.ht.auto.hive;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ht.auto.SqlAutoComplete;
import com.ht.auto.dfa.Predictor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

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
        return bestMatchTokenByRuleName(parser, collection);
    }

    private List<String> bestMatchTokenByRuleName(HplsqlParser parser, Predictor.CandidatesCollection collection) {
        Set<Integer> rules = Sets.newHashSet(collection.tokenRule.values());
        Set<Integer> matches = Sets.newHashSet(Arrays.asList(
                HplsqlParser.RULE_insert_stmt,
                HplsqlParser.RULE_expr,
                HplsqlParser.RULE_expr_func,
                HplsqlParser.RULE_create_table_stmt
        ));
        boolean hasExpectRules = false;
        List<String> resultTokenName = Lists.newArrayList();
        Set<Integer> resultTokenIndex = collection.tokens.keySet();

        for (Integer toMatch : matches) {
            if (rules.contains(toMatch)) {
                hasExpectRules = true;
                break;
            }
        }
        if (hasExpectRules) {
            resultTokenIndex = collection.tokenRule.keySet();
        }
        for (Integer id : resultTokenIndex) {
            resultTokenName.add(parser.getVocabulary().getDisplayName(id));
        }
        return resultTokenName;
    }
}
