package com.mrqinzh.core.mapper;

import com.mrqinzh.core.entity.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LoginLogMapper {

    @Select("select * from t_login_log")
    List<LoginLog> list();

    @Insert("insert into t_login_log(user_id, token, ip, login_time) values (#{userId}, #{token}, #{ip}, #{loginTime})")
    Boolean add(LoginLog loginLog);

}
