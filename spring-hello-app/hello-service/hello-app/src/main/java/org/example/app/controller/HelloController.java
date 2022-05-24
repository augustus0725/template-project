package org.example.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.app.api.Hello;
import org.example.app.api.vo.i.HelloVoIn;
import org.example.app.api.vo.o.HelloVoOut;
import org.example.commons.annotation.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor__ = {@Autowired})
@Api(tags = "这是个测试api")
public class HelloController {
    private final Hello hello;

    @PostMapping({"/hello"})
    @ApiOperation("测试")
    @Loggable
    public HelloVoOut hello(@RequestBody HelloVoIn hvi) {
        return hello.say(hvi);
    }
}
