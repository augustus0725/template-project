package org.example;

import org.example.proxy.ExecutorProxy;
import org.example.proxy.JdbcProxy;
import org.example.proxy.StandardExecutorClassLoader;

import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        new ExecutorProxy("mvvj-app01\\target", "org.example.MvvjApp").execute("sabo");
        new ExecutorProxy("mvvj-app02\\target", "org.example.MvvjApp").execute("sabo");
        Connection connection = new JdbcProxy(new StandardExecutorClassLoader("D:\\one")).getConnection("org.postgresql.Driver",
                "jdbc:postgresql://xxx.xxx.0.xxx:5432/xxxx", "xxxxx", "xxxx");
        assert connection != null;
    }
}
