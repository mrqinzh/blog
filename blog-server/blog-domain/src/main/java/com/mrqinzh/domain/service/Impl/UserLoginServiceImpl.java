package com.mrqinzh.domain.service.Impl;

import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.UserLoginService;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.entity.Role;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.core.security.SecurityProperties;
import com.mrqinzh.domain.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RoleMapper roleMapper;

    public void cachePrincipal(Token token) {
        Object principal = token.getPrincipal();
        if (!(principal instanceof User)) return;
        User user = (User) principal;

        List<Role> roles = roleMapper.getRolesByUserId(user.getId());
        user.setRoles(roles);

        redisUtil.set(token.getUsername(), user, SecurityProperties.DEFAULT_EXPIRE_TIME_SECONDS);
    }

}
