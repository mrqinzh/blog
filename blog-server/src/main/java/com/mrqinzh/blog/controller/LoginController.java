package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录接口")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Resp login(@RequestBody User user) {
        return userService.loginByUsernameOrEmail(user);
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public Resp exit(@RequestHeader("token") String token){
        return userService.logout(token);
    }

}
