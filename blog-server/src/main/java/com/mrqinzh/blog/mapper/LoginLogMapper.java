package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogMapper {

    @Select("select * from t_login_log")
    List<LoginLog> list();

    @Insert("insert into t_login_log(user_id, token, ip, login_time) values (#{userId}, #{token}, #{ip}, #{loginTime})")
    Boolean add(LoginLog loginLog);

}
