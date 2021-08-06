package com.qin.service;

import com.qin.pojo.User;
import java.util.List;

public interface UserService {

    List<User> findAllUser(); // 查询所有用户

    User logByUidEmailTel(String uid); // 登录方法

    int regUser(User user); // 用户注册

    int updateUserInfo(User user); // 更新用户的信息
}