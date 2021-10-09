package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.vo.UserVO;
import com.mrqinzh.blog.model.resp.Resp;

public interface UserService {

    Resp update(UserVO userVO, String token);

    Resp add(User user, String token);

    Resp loginByUsernameOrEmail(User user); // 登录方法

    Resp logout(String token);

    Resp info(String token);

    Resp list(PageVO pageVO);
}