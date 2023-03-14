package com.mrqinzh.webapp.service;

import com.mrqinzh.apis.user.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProxy implements UserService {

    @DubboReference
    private UserService userService;

    @Override
    public String test() {
        return userService.test();
    }

}
