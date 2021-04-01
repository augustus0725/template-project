package com.sabo.spring.provider;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class JdbcProvider {
    public static DataSource dataSource(String config) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(config)) {
            properties.load(inputStream);
        }
        properties.setProperty("druid.testWhileIdle", "false");
        dataSource.configFromPropety(properties);
        return dataSource;
    }
}
