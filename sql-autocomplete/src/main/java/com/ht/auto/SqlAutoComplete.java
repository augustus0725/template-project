package com.ht.auto;

import java.util.List;

/**
 * @author canbin.zhang@qq.com
 * date: 2020/9/13
 */
public interface SqlAutoComplete {
    /**
     * To find the tokens that mqy appear.
     *
     * @param sql is part of sql string that we will do analysis
     * @param pos where we try to find the next token.
     * @return tokens that may appear next to the pos or empty list that can not find one.
     */
    List<String> suggest(String sql, int pos);
}
