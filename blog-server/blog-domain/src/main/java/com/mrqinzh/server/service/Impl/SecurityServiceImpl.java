package com.mrqinzh.server.service.Impl;

import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import com.mrqinzh.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SecurityUser loadSecurityUserFromDb(String username) {
        return userMapper.getByUsernameOrEmail(username);
    }
}
