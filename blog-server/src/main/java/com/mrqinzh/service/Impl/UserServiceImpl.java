package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.UserMapper;
import com.mrqinzh.entity.User;
import java.util.List;

import com.mrqinzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return this.userMapper.findAllUser();
    }

    @Override
    public User logByUidEmailTel(String uid) {
        return userMapper.logByUidEmailTel(uid);
    }

    @Override
    public int regUser(User user) {
        return userMapper.regUser(user);
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }
}
