package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.api.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @Autowired
    private Hello hello;

    @GetMapping("/hello")
    public String hello() {
        return hello.say();
    }
}
