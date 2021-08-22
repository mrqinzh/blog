package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.dto.PageDTO;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("添加一个用户")
    @PostMapping("add")
    public Resp add(@RequestBody User user, @RequestHeader("token") String token) {
        return userService.add(user, token);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public Resp info(@RequestParam String token) {
        return userService.info(token);
    }

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Resp login(@RequestBody User user) {
        return userService.getByUsernameOrEmail(user);
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public Resp exit(@RequestHeader("token") String token){
        // 这里在拦截器中已经判断了 token 是否存在
        redisUtil.del(token);
        return Resp.ok("退出成功");
    }

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("list")
    public Resp list(PageDTO pageDTO) {
        return userService.list(pageDTO);
    }
}
