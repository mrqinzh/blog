package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.dto.resp.Resp;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Resp update(User user, String token);

    Resp add(User user, String token);

    Resp loginByUsernameOrEmail(User user); // 登录方法

    Resp logout(String token);

    Resp info(String token, HttpServletRequest request);

    Resp list(PageDTO pageDTO);
}