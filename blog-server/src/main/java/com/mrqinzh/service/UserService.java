package com.mrqinzh.service;

import com.mrqinzh.model.entity.User;
import com.mrqinzh.util.Resp;

public interface UserService {

    Resp findByUidEmailTel(User user); // 登录方法

    int updateUserInfo(User user); // 更新用户的信息
}