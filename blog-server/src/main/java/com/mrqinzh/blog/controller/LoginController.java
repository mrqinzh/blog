package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.util.Resp;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.service.UserService;
import com.mrqinzh.blog.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录接口")
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;



    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Resp login(@RequestBody User user) {
        return userService.getByUsernameOrEmail(user);
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public Resp exit(@RequestHeader("token") String token){

        if (redisUtil.hasKey(token)) {
            redisUtil.del(token);

        }
        return Resp.ok("退出成功");
    }
}
