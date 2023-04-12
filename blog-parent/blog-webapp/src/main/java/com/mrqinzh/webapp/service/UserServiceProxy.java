package com.mrqinzh.webapp.service;

import com.mrqinzh.apis.user.UserService;
import com.mrqinzh.common.entity.User;
import com.mrqinzh.common.resp.Resp;
import com.mrqinzh.common.vo.PageVO;
import com.mrqinzh.common.vo.user.UserVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceProxy implements UserService {

    @DubboReference(timeout = 2000)
    private UserService userService;

    @Override
    public List<User> test() {
        return userService.test();
    }

    @Override
    public Resp update(UserVO userVO) {
        return null;
    }

    @Override
    public Resp add(UserVO userVO) {
        return null;
    }

    @Override
    public Map<String, Object> info(String token) {
        return null;
    }

    @Override
    public Resp list(PageVO pageVO) {
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }


}
