package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.util.Resp;

public interface UserService {

    Resp getByUsernameOrEmail(User user); // 登录方法

}