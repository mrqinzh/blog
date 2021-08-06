package com.mrqinzh.mapper;

import com.mrqinzh.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    List<User> findAllUser(); // 查询所有用户

    User logByUidEmailTel(String uid); // 登录方法

    int regUser(User user); // 用户注册

    int updateUserInfo(User user); // 更新用户的信息

}
