package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.config.WebSocketServer;
import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.dto.Resp;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "用户接口")
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("修改用户信息")
    @PostMapping("update")
    public Resp update(@RequestBody User user) {
        return userService.update(user);
    }

    @ApiOperation("添加一个用户")
    @PostMapping("add")
    public Resp add(@RequestBody User user, @RequestHeader("token") String token) {
        return userService.add(user, token);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public Resp info(@RequestParam String token, HttpServletRequest request) {
        return userService.info(token, request);
    }

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

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("list")
    public Resp list(PageDTO pageDTO) {
        return userService.list(pageDTO);
    }
}
