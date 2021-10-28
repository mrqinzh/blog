package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.vo.user.UserVO;
import com.mrqinzh.blog.model.resp.Resp;

public interface UserService {

    Resp update(UserVO userVO, String token);

    Resp add(User user, String token);

    Resp info(String token);

    Resp list(PageVO pageVO);

    User getById(Integer id);
}