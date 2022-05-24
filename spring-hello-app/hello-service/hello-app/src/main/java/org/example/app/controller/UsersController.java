package org.example.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.app.api.Users;
import org.example.commons.annotation.Loggable;
import org.example.commons.standard.RestResponse;
import org.example.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor__ = {@Autowired})
@Api(tags = "实例controller, 提供管理用户的api")
public class UsersController {
    private final Users users;


    @PostMapping({"/users"})
    @ApiOperation("添加用户")
    @Loggable
    public RestResponse<User> hello(@RequestBody User user) {
        return users.save(user);
    }

}
