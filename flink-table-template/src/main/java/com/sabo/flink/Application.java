package com.sabo.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) throws Exception {

        // set up execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        DataStream<Order> orderA = env.fromCollection(Arrays.asList(
                new Order(1L, "beer", 3),
                new Order(1L, "diaper", 4),
                new Order(3L, "rubber", 2)));

        DataStream<Order> orderB = env.fromCollection(Arrays.asList(
                new Order(2L, "pen", 3),
                new Order(2L, "rubber", 3),
                new Order(4L, "beer", 1)));



        // convert DataStream to Table
        Table tableA = tEnv.fromDataStream(orderA, "user, product, amount");
        // register DataStream as Table
        tEnv.registerDataStream("OrderB", orderB, "user, product, amount");

        // union the two tables
        System.out.println("SELECT * FROM " + tableA + " WHERE amount > 2 UNION ALL " +
                "SELECT * FROM OrderB WHERE amount < 2");
        Table result = tEnv.sqlQuery("SELECT user,product FROM " + tableA + " WHERE amount > 2 UNION ALL " +
                "SELECT user,product FROM OrderB WHERE amount < 2");
        System.out.println("=================================");
        System.out.println(result.getSchema());
        System.out.println("=================================");

        tEnv.toAppendStream(result, NOrder.class).print();

        env.execute();
    }

    // *************************************************************************
    //     USER DATA TYPES
    // *************************************************************************

    /**
     * Simple POJO.
     */
    public static class Order {
        public Long user;
        public String product;
        public int amount;

        public Order() {
        }

        public Order(Long user, String product, int amount) {
            this.user = user;
            this.product = product;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "user=" + user +
                    ", product='" + product + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

    public static class NOrder {
        public Long user;
        public String product;

        public NOrder() {
        }

        public NOrder(Long user, String product) {
            this.user = user;
            this.product = product;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "user=" + user +
                    ", product='" + product + '\'' +
                    '}';
        }
    }
}
