package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/9 15:16
 */
@RestController
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }
}
