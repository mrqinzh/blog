package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper {

    @Select("select * from tag")
    List<Tag> page();

    @Select("select * from tag limit 20")
    List<Tag> list();

    @Insert("insert into tag(tag_name) values (#{tagName})")
    Boolean add(Tag tag);

    @Delete("delete from tag where id = #{id}")
    Boolean delete(Integer id);

}
