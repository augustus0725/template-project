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
        List<String> expectedTokens = Lists.newArrayList();
        for (Integer id : collection.tokens.keySet()) {
            expectedTokens.add(parser.getVocabulary().getDisplayName(id));
        }
        return expectedTokens;
    }
}
