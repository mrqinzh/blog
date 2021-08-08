package com.mrqinzh.controller;

import com.mrqinzh.util.Resp;
import com.mrqinzh.model.entity.User;
import com.mrqinzh.service.UserService;
import com.mrqinzh.util.RedisUtil;
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

    // token到期时间
    private static final Long TOKEN_EXPIRE_TIME = 12*60*60L;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Resp login(@RequestBody User user) {
        return userService.getByUsernameOrEmail(user);
    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public Resp exit(@RequestHeader("token") String token){

        if (redisUtil.hasKey(token)) {
            redisUtil.del(token);

        }
        return Resp.ok("退出成功");
    }
}
