package com.sabo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
@RestController
public class Hello {
    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }
}
