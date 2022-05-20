package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
        basePackages = {RepositoryConfig.RW.REPOSITORY_PACKAGE},
        entityManagerFactoryRef = "rwEntityManagerFactory",
        transactionManagerRef = "rwTransactionManager",
        includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {RepositoryConfig.RW.class})}
)
@EnableJpaAuditing
public class ReadWriteJpaConfiguration {
    @Primary
    @Bean(name = "rwDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db0")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean(name = "rwEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("rwDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(
                        RepositoryConfig.JPA_ENTITY_HOME
                        )
                .persistenceUnit("rw-emf")
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                .build();
    }

    @Primary
    @Bean(name = "rwTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("rwEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
