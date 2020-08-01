package com.sabo.dubbo.service;

import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloImp implements Hello {
    public String hello(String name) {
        return "Hello, " + name;
    }
}
