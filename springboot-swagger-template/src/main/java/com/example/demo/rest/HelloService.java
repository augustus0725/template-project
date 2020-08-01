package com.example.demo.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {
    @RequestMapping(value = "/api/v2/hello", method = RequestMethod.POST)
    public String hello(@RequestBody HelloParams params) {
        return "Hello world! " + params.getName() + " : " + params.getAge();
    }
}
