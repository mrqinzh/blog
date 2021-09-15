package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.MyFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {

    Boolean add(MyFile myFile);

    @Update("update file set status = 1 where file_name = #{fileName}")
    Boolean delete(String fileName);

    @Select("select * from file where file_name = #{fileName}")
    MyFile getByFileName(String fileName);

}
