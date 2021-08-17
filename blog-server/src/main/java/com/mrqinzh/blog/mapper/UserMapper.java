package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user")
    List<User> findAllUser(); // 查询所有用户

    @Select("select * from user where user_name = #{usernameOrEmail} or user_email = #{usernameOrEmail}")
    User getByUsernameOrEmail(String usernameOrEmail); // 登录方法

}
