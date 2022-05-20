package org.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {RepositoryConfig.R.REPOSITORY_PACKAGE},
        entityManagerFactoryRef = "rEntityManagerFactory",
        transactionManagerRef = "rTransactionManager",
        includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {RepositoryConfig.R.class})}
)
public class ReadJpaConfiguration {
    @Bean(name = "rDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "rEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("rDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(
                        RepositoryConfig.JPA_ENTITY_HOME
                )
                .persistenceUnit("r-emf")
                .build();
    }

    @Bean(name = "rTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("rEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
