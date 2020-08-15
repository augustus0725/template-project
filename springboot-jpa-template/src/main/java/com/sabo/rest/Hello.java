package com.sabo.rest;

import com.sabo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@RestController
public class Hello {
    @Autowired private HelloService helloService;

    @RequestMapping("/ping")
    public String ping() {
        try {
            helloService.m10(100);
        } catch (Exception e) {
            System.out.println("===========");
        }

        return "pong";
    }
}
