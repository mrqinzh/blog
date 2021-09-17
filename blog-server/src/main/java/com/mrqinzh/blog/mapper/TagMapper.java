package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.model.vo.req.PageVO;
import com.mrqinzh.blog.model.vo.resp.Resp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    List<Tag> page(PageVO pageVO);

    @Select("select * from tag limit 20")
    List<Tag> list();

    @Insert("insert into tag(tag_name, tag_img) values (#{tagName}, #{tagImg})")
    Boolean add(Tag tag);

    @Delete("delete from tag where id = #{id}")
    Boolean delete(Integer id);

    @Select("select * from tag where id = #{id}")
    Tag getById(Integer id);
}
