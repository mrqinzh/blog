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

    int add(Comment comment);

    /**
     * 根据id 查询评论信息
     * @param type id类型： userId、articleId
     * @param id id值
     * @return java.util.List
     */
    List<Comment> getById(String type, Integer id);



}
