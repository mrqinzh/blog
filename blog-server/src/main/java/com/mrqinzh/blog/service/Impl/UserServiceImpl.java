package com.mrqinzh.blog.service.Impl;

import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mrqinzh.blog.service.UserService;
import com.mrqinzh.blog.util.JwtUtil;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.util.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getSimpleName());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp getByUsernameOrEmail(User user) {
        logger.info("有用户进行了登录操作。。。user => " + user);
        User realUser = userMapper.getByUsernameOrEmail(user.getUserName());
        // 数据库进行了md5加密加盐
        if (null == realUser || !realUser.getUserPwd().equals(user.getUserPwd())){
            return Resp.error(200, "用户名或密码错误!");
        }

        Map<String,String> claim = new HashMap<>(); // 添加到 jwt 的 body 中
        claim.put("username", realUser.getUserName());
        claim.put("password", realUser.getUserPwd());
        claim.put("avatar", realUser.getUserAvatar());
        String token = JwtUtil.getTokenWithClaim(claim);
        redisUtil.set(token, realUser);
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return Resp.ok(resultMap);
    }

}
