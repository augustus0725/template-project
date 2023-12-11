package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SparkJdbcMain {
    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection con = DriverManager.getConnection("jdbc:hive2://192.168.30.237:10000");

        Statement stmt = con.createStatement();
        stmt.execute("create temporary view myview\n" +
                "using org.apache.spark.sql.jdbc\n" +
                "options (\n" +
                "  url \"jdbc:mysql://192.168.31.7:3306/mydb\",\n" +
                "  dbtable \"poc_test\",\n" +
                "  driver \"com.mysql.cj.jdbc.Driver\",\n" +
                "  user \"myadmin\",\n" +
                "  password \"Windows_8\"\n" +
                ");");

        ResultSet rs = stmt.executeQuery("select * from myview");
        while (rs.next()) {
            // the example query returns one String column
            System.out.println(rs.getString(1));
        }

        System.out.println();

    }
}
