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
    List<Comment> getListByArticleId(Integer articleId);

    @Delete("delete from `comment` where article_id = #{articleId}")
    int deleteByArticleId(Integer articleId);

    int add(Comment comment);

    List<Comment> getById(String type, Integer id);



}
