package com.mrqinzh.server.service.Impl;

import com.mrqinzh.blog.auth.SecurityUser;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.RoleMapper;
import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.entity.Role;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.enums.AppStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            throw new BizException(AppStatus.BAD_REQUEST, "用户名不能为空");
        }

        User user = userMapper.getByUsernameOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s用户不存在", username));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Todo 查询权限集合
        List<Role> userRoles = roleMapper.getRolesByUserId(user.getId());
        for (Role role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        // 返回UserDetails
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(user, securityUser);
        securityUser.setAuthorities(authorities); // 设置权限集

        return securityUser;
    }

}
