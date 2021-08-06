package com.mrqinzh.mapper;

import com.mrqinzh.model.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Select("select * from comment where article_id = #{articleId}")
    List<Comment> list(Integer articleId);

    int add(Comment comment);

    int delete(Integer articleId);
}
