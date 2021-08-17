package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.MyFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {

    boolean add(MyFile myFile);

    @Delete("delete from file where file_name = #{condition}")
    boolean delete(String condition);

    @Select("select * from file where file_name = #{condition}")
    MyFile getOne(String condition);

}
