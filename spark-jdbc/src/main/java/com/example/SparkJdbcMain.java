package com.example;

import avro.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SparkJdbcMain {
    private static void query01(int limit) {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection("jdbc:hive2://192.168.30.248:10000");
             Statement stmt = con.createStatement();
        ) {
            stmt.execute("create temporary view myview\n" +
                    "using org.apache.spark.sql.jdbc\n" +
                    "options (\n" +
                    "  url \"jdbc:mysql://192.168.31.252:3306/mydb\",\n" +
//                    "  dbtable \"select * from dxp_t001\",\n" +
                    "  query \"select * from dxp_t001 limit " + limit + "\",\n" +
                    "  driver \"com.mysql.cj.jdbc.Driver\",\n" +
                    "  user \"myadmin\",\n" +
                    "  password \"*******\",\n" +
                    "  fetchsize \"2000\"\n" +
                    ");");

            rs = stmt.executeQuery("select * from myview limit " + limit);
            while (rs.next()) {
                // the example query returns one String column
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void loadBig700w(int limit) {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection("jdbc:hive2://192.168.0.5:10001");
             Statement stmt = con.createStatement();
        ) {
            stmt.execute("create temporary view big700w\n" +
                    "using org.apache.spark.sql.jdbc\n" +
                    "options (\n" +
                    "  url \"jdbc:mysql://192.168.31.252:3306/mydb\",\n" +
                    "  dbtable \"dxp_t001\",\n" +
                    "  driver \"com.mysql.cj.jdbc.Driver\",\n" +
                    "  user \"myadmin\",\n" +
                    "  password \"*******\",\n" +
                    "  fetchsize \"2000\"\n" +
                    ");");

            rs = stmt.executeQuery("select * from big700w limit " + limit);
            while (rs.next()) {
                // the example query returns one String column
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void createHiveTable() {
        try (Connection con = DriverManager.getConnection("jdbc:hive2://192.168.0.234:10000");
             Statement stmt = con.createStatement();
        ) {
            stmt.execute("create table if not exists p01(\n" +
                    "name string\n" +
                    ")\n" +
                    "stored as parquet");

            stmt.execute("insert into p01 select  * from t01");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void queryWithYarn() {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection("jdbc:hive2://192.168.0.234:10000");
             Statement stmt = con.createStatement();
        ) {
            rs = stmt.executeQuery("select max(a) from t01 ");
            while (rs.next()) {
                // the example query returns one String column
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void concurrent() throws ExecutionException, InterruptedException {
        List<Future<?>> futures = new ArrayList<>();
        ExecutorService executors = executors();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Future<?> future = executors.submit(new Runnable() {
                @Override
                public void run() {
                    query01(finalI + 1);
                }
            });

            futures.add(future);
        }
        for (Future<?> future : futures) {
            future.get();
        }
        System.out.println("over");
    }

    public static ExecutorService executors() {
        final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("find-metric-%d")
                .build();
        return new ThreadPoolExecutor(8, 8, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(256), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        loadBig700w(100);
//        queryWithYarn();
//        createHiveTable();
    }

}
