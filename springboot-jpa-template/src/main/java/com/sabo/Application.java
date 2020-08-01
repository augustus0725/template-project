package com.sabo;

import com.google.common.base.Preconditions;
import com.sabo.providers.RdbProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        DataSource dataSource = null;

        try {
            dataSource = RdbProvider.createDataSource("pg", properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Preconditions.checkArgument(dataSource != null);
        return dataSource;
    }
}
