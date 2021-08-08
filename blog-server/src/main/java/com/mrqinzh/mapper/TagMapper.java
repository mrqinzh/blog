package com.mrqinzh.mapper;

import com.mrqinzh.model.entity.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper {

    @Select("select * from tag")
    List<Tag> page();

    @Select("select * from tag limit 20")
    List<Tag> list();

    @Insert("insert into tag(tag_name) values (#{tagName})")
    int add(Tag tag);

    @Delete("delete from tag where id = #{id}")
    int delete(Integer id);

}
