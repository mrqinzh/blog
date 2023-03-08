package com.mrqinzh.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.core.entity.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    @Select("select * from t_login_log")
    List<LoginLog> list();

    @Insert("insert into t_login_log(user_id, token, ip, login_time) values (#{userId}, #{token}, #{ip}, #{loginTime})")
    Boolean add(LoginLog loginLog);

}
