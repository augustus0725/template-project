package org.example;

import org.example.proxy.ExecutorProxy;
import org.example.proxy.JdbcProxy;
import org.example.proxy.StandardExecutorClassLoader;

import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        new ExecutorProxy("mvvj-app01/target", "org.example.MvvjApp").execute("sabo");
        new ExecutorProxy("mvvj-app02/target", "org.example.MvvjApp").execute("sabo");
        JdbcProxy jdbcProxy = new JdbcProxy(new StandardExecutorClassLoader("./drivers"));

        for (int i = 0; i < 10; i++) {
            Connection connection = jdbcProxy.getConnection("org.postgresql.Driver",
                    "jdbc:postgresql://192.168.0.xxx:5432/dxp", "xxxxx", "******");

            System.out.println(connection.getMetaData().getDriverName());
            connection.close();
        }
    }
}
