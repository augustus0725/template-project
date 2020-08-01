package com.sabo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Example {
    static DruidDataSource createDataSourceFromResource(String resource) throws IOException {
        Properties properties = new Properties();

        InputStream configStream = null;
        try {
            configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            properties.load(configStream);
        } finally {
            JdbcUtils.close(configStream);
        }

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.configFromPropety(properties);
        return dataSource;
    }




    public static void main(String[] args) throws IOException, SQLException {
        DruidDataSource dataSource = createDataSourceFromResource("pool.properties");

        Connection conn = dataSource.getConnection();
        List<String> tables = JdbcUtils.showTables(conn, JdbcConstants.ORACLE);
        // System.out.println(tables);
//        List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, "select * from dam_db_statistic");
//        List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, "select * from dam_db_statistic");
        List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, "select * from dam_indicators");
        boolean head = false;
        System.out.println("<table>");
        for (Map<String, Object> record : result) {
            if (!head) {
                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    System.out.print("<td>"+ entry.getKey() + "</td>");
                }
                head = true;
            }
            System.out.print("<tr>");
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                System.out.print("<td>"+ entry.getValue() + "</td>");
            }
            System.out.println("</tr>");
        }
        System.out.println("</table>");

        // https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html#package.description
        // auto java.sql.Driver discovery -- no longer need to load a java.sql.Driver class via Class.forName

        // register JDBC driver, optional since java 1.6
        /*try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        // Oracle SID = orcl , find yours in tnsname.ora

    }
}