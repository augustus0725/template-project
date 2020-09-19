package com.ht.auto.hive;

import com.ht.auto.SqlAutoComplete;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author crazy
 * date: 2020/9/13
 */
public class HiveSqlAutoCompleteTest {
    @Test
    public void test001() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create index idx_001";

        assertEquals("[T_ON]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test002() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create table a ";

        assertEquals("[T_ROW, T_LIKE, T_STORED, '(', T_WITH, ',', T_AS, T_SEL, T_SELECT]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test003() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "insert into t";

        assertEquals("[T_VALUES, '(', T_WITH, T_SEL, T_SELECT]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test004() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "insert into ";

        assertEquals("[T_TABLE, '-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test005() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "SELECT * FROM";

        assertEquals("['(', T_TABLE, '-', L_ID]", ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test006() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select * from t left";

        assertEquals("[T_JOIN, T_OUTER]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test007() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "begin";

        assertEquals("[T_TRANSACTION]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test008() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "insert into student";

        assertEquals("[T_VALUES, '(', T_WITH, T_SEL, T_SELECT]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test009() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql  = "select id, name from student t";

        assertEquals("[T_HAVING, T_LEFT, T_LIMIT, T_EXCEPT, '(', T_UNION, T_ORDER, T_INNER, T_RIGHT, T_INTERSECT, T_FULL, T_WHERE, ',', T_WITH, T_JOIN, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test010() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create table if";

        assertEquals("[T_NOT]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test011() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create table if not";

        assertEquals("[T_EXISTS]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test012() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create table if not exists";

        assertEquals("['-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test013() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select ";

        assertEquals("[T_ALL, T_AVG, T_INTERVAL, T_SUBSTRING, T_SUM, T_TIMESTAMP, T_CASE, T_TOP, T_CAST, T_TRUE, T_MAX, T_VAR, T_MIN, T_COUNT, T_COUNT_BIG, T_CURRENT, T_ACTIVITY_COUNT, T_CUME_DIST, T_CURRENT_DATE, T_CURRENT_TIMESTAMP, T_NULL, T_CURRENT_USER, T_DATE, T_DENSE_RANK, T_FIRST_VALUE, T_LAG, T_LAST_VALUE, T_LEAD, T_MAX_PART_STRING, T_MIN_PART_STRING, T_MAX_PART_INT, T_MIN_PART_INT, T_MAX_PART_DATE, T_MIN_PART_DATE, T_PART_COUNT, T_PART_LOC, T_RANK, T_ROW_NUMBER, T_STDEV, T_SYSDATE, T_VARIANCE, T_USER, T_DISTINCT, '+', '*', '(', T_FALSE, '-', L_ID, L_S_STRING, L_D_STRING, L_INT, L_DEC, T_TRIM]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test014() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select *";

        assertEquals("[T_HAVING, T_LIMIT, T_EXCEPT, T_UNION, T_ORDER, T_INTERSECT, T_FROM, T_INTO, T_WHERE, ',', T_WITH, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test015() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "with";

        assertEquals("['-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test016() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "with a";

        assertEquals("['(', T_AS]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test017() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "DROP TABLE";

        assertEquals("[T_IF, '-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test018() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select a, b from ";

        assertEquals("['(', T_TABLE, '-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test019() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select a, b from stu";

        assertEquals("[T_HAVING, T_LEFT, T_LIMIT, T_EXCEPT, T_UNION, T_ORDER, T_INNER, T_RIGHT, T_AS, '-', L_ID, T_INTERSECT, T_FULL, T_WHERE, ',', T_WITH, T_JOIN, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test020() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select a, b from stu t";

        assertEquals("[T_HAVING, T_LEFT, T_LIMIT, T_EXCEPT, '(', T_UNION, T_ORDER, T_INNER, T_RIGHT, T_INTERSECT, T_FULL, T_WHERE, ',', T_WITH, T_JOIN, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test021() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t.";

        assertEquals("[T_FIELDS_t]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test022() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t. from student t";

        assertEquals("[T_FIELDS_student]",
                ac.suggest(sql, "select t.".length() - 1).toString());
    }

    @Test
    public void test023() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "create";

        assertEquals("[T_SET, T_DATABASE, T_LOCAL, T_OR, T_INDEX, T_UNIQUE, T_PACKAGE, T_VOLATILE, T_MULTISET, T_FUNCTION, T_SCHEMA, T_TABLE, T_PROC, T_PROCEDURE]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test024() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t2. from student t1, teacher t2";

        assertEquals("[T_FIELDS_teacher]",
                ac.suggest(sql, "select t2.".length() - 1).toString());
    }

    @Test
    public void test025() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.* from student t1, teacher t2";

        assertEquals("[T_HAVING, T_LEFT, T_LIMIT, T_EXCEPT, '(', T_UNION, T_ORDER, T_INNER, T_RIGHT, T_INTERSECT, T_FULL, T_WHERE, ',', T_WITH, T_JOIN, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test026() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.* from student t1, teacher";

        assertEquals("[T_HAVING, T_LEFT, T_LIMIT, T_EXCEPT, T_UNION, T_ORDER, T_INNER, T_RIGHT, T_AS, '-', L_ID, T_INTERSECT, T_FULL, T_WHERE, ',', T_WITH, T_JOIN, T_QUALIFY, T_GROUP]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test027() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.* from student t1, ";

        assertEquals("['(', T_TABLE, '-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test028() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.* from default.";

        assertEquals("['(', T_TABLE, '-', L_ID]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test029() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.* from stu t1 where t1.";

        assertEquals("[T_FIELDS_stu]",
                ac.suggest(sql, sql.length() - 1).toString());
    }

    @Test
    public void test030() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1. from student t1, teacher t2 where t2";

        assertEquals("[T_FIELDS_student]",
                ac.suggest(sql, "select t1.".length() - 1).toString());
    }

    @Test
    public void test031() {
        SqlAutoComplete ac = new HiveSqlAutoComplete();
        String sql = "select t1.a, t2. from student t1, teacher t2 where t2";

        assertEquals("[T_FIELDS_teacher]",
                ac.suggest(sql, "select t1.a, t2.".length() - 1).toString());
    }
}