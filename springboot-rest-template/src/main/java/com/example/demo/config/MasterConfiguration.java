package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@PropertySource(value = "master.properties")
@PropertySource(value = "file:./master.properties", ignoreResourceNotFound = true) // 覆盖上面的配置
public class MasterConfiguration {
    @Value("${master.exec.threads:100}")
    private int masterExecThreads;

    @Value("#{'${master.ip.prefix.excludes:unset}'.split(',')}")
    private Set<String> ipPrefixExcludes;

    @PostConstruct
    public void run() {
        System.out.println(masterExecThreads);
    }
}
