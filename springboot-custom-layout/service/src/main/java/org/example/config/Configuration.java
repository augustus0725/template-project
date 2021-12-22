package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.api.Hello;
import org.springframework.context.annotation.Bean;

@Slf4j
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public Hello hello() {
        return new org.example.Hello();
    }
}
