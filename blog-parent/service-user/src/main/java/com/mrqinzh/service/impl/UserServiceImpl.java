package com.mrqinzh.service.impl;

import com.mrqinzh.apis.user.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public String test() {
        return "success";
    }
}
