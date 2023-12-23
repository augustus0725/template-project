package com.example;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

public class SparkApp{
    private static void words() {
        String logFile = "README.md"; // Should be some file on your system
        SparkSession spark = SparkSession.builder().appName("Simple Application").master("local[2]").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();


        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();
    }

    private static void sql() {
        System.setProperty("java.security.auth.login.config", "keytabs/jaas.conf");
        System.setProperty("java.security.krb5.conf", "keytabs/krb5.conf");
        SparkSession spark = SparkSession
                .builder()
                .master("local[4]")
                .appName("Java Spark Hive Example")
                .enableHiveSupport()
                .getOrCreate();

        spark.sql("show tables").show();


    }


    public static void main(String[] args) {
//        words();
        sql();
    }
}
