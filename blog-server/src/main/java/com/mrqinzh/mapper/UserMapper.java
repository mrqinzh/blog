package com.mrqinzh.mapper;

import com.mrqinzh.model.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user_table")
    List<User> findAllUser(); // 查询所有用户

    @Select("select * from user_table where uid = #{uid} or tel=#{uid} or email=#{uid}")
    User logByUidEmailTel(String uid); // 登录方法

    @Update("update user_table set head_img = #{head_img} where uid = #{uid}")
    int updateUserInfo(User user); // 更新用户的信息

}
