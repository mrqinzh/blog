package com.mrqinzh.server.service;

import com.mrqinzh.common.model.entity.User;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.user.UserVO;

public interface UserService {

    Resp update(UserVO userVO);

    Resp add(UserVO userVO);

    Resp info(String token);

    Resp list(PageVO pageVO);

    User getById(Integer id);
}