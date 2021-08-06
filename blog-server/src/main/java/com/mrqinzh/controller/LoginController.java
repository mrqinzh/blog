package com.mrqinzh.controller;

import com.mrqinzh.util.Resp;
import com.mrqinzh.entity.User;
import com.mrqinzh.service.UserService;
import com.mrqinzh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    private static final Long TOKEN_EXPIRE_TIME = 12*60*60L;    // token到期时间

    /**
     * 登录请求判断
     * @param user  封装用户信息
     * @return  封装返回的信息
     */
    @PostMapping("/login")
    public Resp toMain(@RequestBody User user) {
        return userService.findByUidEmailTel(user);
    }

    /**
     *  退出登录操作
     * @param token  获取用户令牌
     * @return  返回 ==> 退出成功
     */
    @RequestMapping("/logout")
    public Resp exit(@RequestHeader("token") String token){

        if (redisUtil.hasKey(token)) {
            redisUtil.del(token);    // 删除令牌
        }
        return Resp.ok("推出成功");
    }
}
