package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.MyFile;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper {

    boolean add(MyFile myFile);

    @Update("update file set status = 1 where file_name = #{fileName}")
    boolean delete(String fileName);

    @Select("select * from file where file_name = #{fileName}")
    MyFile getByFileName(String fileName);

}
