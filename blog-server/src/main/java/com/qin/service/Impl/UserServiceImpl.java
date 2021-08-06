package com.qin.service.Impl;

import com.qin.mapper.UserMapper;
import com.qin.pojo.User;
import java.util.List;

import com.qin.service.UserService;
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
