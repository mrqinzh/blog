package com.mrqinzh.service;

import com.mrqinzh.model.entity.User;
import com.mrqinzh.util.Resp;

public interface UserService {

    Resp getByUsernameOrEmail(User user); // 登录方法

}