package com.sabo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@RestController
public class Hello {
    @Autowired private com.sabo.apis.Hello hello;

    @RequestMapping("/ping")
    public String ping() {
        try {
            hello.m10(100);
        } catch (Exception e) {
            System.out.println("===========");
        }

        return "pong";
    }
}
