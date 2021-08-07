package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.UserMapper;
import com.mrqinzh.model.entity.User;

import java.util.UUID;

import com.mrqinzh.service.UserService;
import com.mrqinzh.util.RedisUtil;
import com.mrqinzh.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp getByUsernameOrEmail(User user) {
        User realUser = userMapper.getByUsernameOrEmail(user.getUserName());
        // 数据库进行了md5加密加盐
        if (null == realUser || !realUser.getUserPwd().equals(user.getUserPwd())){
            return Resp.error("200", "用户名或密码错误!");
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisUtil.set(token, realUser);
        return Resp.ok(token);
    }

}
