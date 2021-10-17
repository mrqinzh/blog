package com.mrqinzh.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.blog.model.entity.User;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

//    Boolean update(User user);

    Boolean add(User user);

    List<User> list(); // 查询所有用户

    @Select("select * from user where user_name = #{usernameOrEmail} or user_email = #{usernameOrEmail} limit 1")
    User getByUsernameOrEmail(String usernameOrEmail); // 登录方法

}
