package com.mrqinzh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.common.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

//    Boolean update(User user);

    Boolean add(User user);

    List<User> list(); // 查询所有用户

    User getByUsernameOrEmail(String usernameOrEmail); // 登录方法

}
