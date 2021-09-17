package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.MyMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MyMessageMapper {

    @Select("select count(*) from t_message")
    Integer messageCount();

    @Select("select * from t_message")
    List<MyMessage> list();

    Boolean add(MyMessage message);

}
