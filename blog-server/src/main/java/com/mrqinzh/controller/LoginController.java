package com.mrqinzh.controller;

import com.mrqinzh.entity.Resp;
import com.mrqinzh.entity.User;
import com.mrqinzh.service.UserService;
import com.mrqinzh.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/login")
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
    @PostMapping({"/isLogin"})
    public Resp<String> toMain(@RequestBody User user) {
        // 获取当前这个用户
        Subject subject = SecurityUtils.getSubject();
        // 封装登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUid(), user.getPassword());
        try {
            subject.login(usernamePasswordToken);

            String token = UUID.randomUUID().toString();
            redisUtil.set(token,subject.getPrincipal());

            return Resp.success(token);

        } catch (UnknownAccountException usernameError){
            //如果用户名不存在
            return Resp.error("404","该账号不存在哦！");
        } catch (IncorrectCredentialsException pwdError) {
            //如果密码错误
            return Resp.error("404","密码错误！");
        }
    }

    /**
     * 用户注册方法
     * @param user  封装用户信息
     * @return  success： 注册成功   error： 注册失败
     */
    @PostMapping("/reg")
    public String add(@RequestBody User user) {
        try {
            user.setUid(user.getTel());
            user.setHead_img("http://47.108.209.62:9090/files/20210426/969e7905-e905-436b-b755-c14688e0640d_%E5%8E%9F%E7%A5%9E3.jpg");
            String substring = UUID.randomUUID().toString().substring(0, 4);
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), substring, 1024);
            user.setPassword(md5Hash.toString());
            user.setSalt(substring);

            int i = userService.regUser(user);
            return i==1 ? "success" : "error";
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  退出登录操作
     * @param auth  获取用户令牌
     * @return  返回 ==> 退出成功
     */
    @RequestMapping("/logout")
    public Resp<String> exit(@RequestHeader("Authorization") String auth){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        if (redisUtil.hasKey(auth)) {
            redisUtil.del(auth);    // 删除令牌
        }
        return Resp.success("退出成功");
    }
}
