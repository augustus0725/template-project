package com.sabo.dubbo;

import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class HelloProviderBootstrap {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.in.read();
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.sabo.dubbo.service")
    @PropertySource("classpath:/spring/dubbo-provider.properties")
    static public class ProviderConfiguration {
        @Bean
        public ProviderConfig providerConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            providerConfig.setTimeout(1000);
            return providerConfig;
        }
    }
}
