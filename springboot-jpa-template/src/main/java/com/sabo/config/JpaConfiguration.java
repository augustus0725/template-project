package com.sabo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class JpaConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db0")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
