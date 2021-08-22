package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.util.Resp;

public interface UserService {

    Resp add(User user, String token);

    Resp getByUsernameOrEmail(User user); // 登录方法

    Resp info(String token);

    Resp list(PageDTO pageDTO);
}