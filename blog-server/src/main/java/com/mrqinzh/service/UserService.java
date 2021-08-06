package com.mrqinzh.service;

import com.mrqinzh.entity.User;
import com.mrqinzh.util.Resp;

import java.util.List;

public interface UserService {

    Resp findByUidEmailTel(User user); // 登录方法

    int updateUserInfo(User user); // 更新用户的信息
}