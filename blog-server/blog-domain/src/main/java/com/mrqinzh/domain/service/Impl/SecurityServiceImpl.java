package com.mrqinzh.domain.service.Impl;

import com.mrqinzh.core.entity.User;
import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import com.mrqinzh.domain.mapper.RoleMapper;
import com.mrqinzh.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public SecurityUser loadSecurityUserFromDb(String username) {
        User user = userMapper.getByUsernameOrEmail(username);
        return user;
    }
}
