package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Select("select * from comment limit 5")
    List<Comment> list();

    int add(Comment comment);

    /**
     * 根据id 查询评论信息
     * @param idType id类型： userId、articleId
     * @param id id值
     * @return java.util.List
     */
    List<Comment> getById(String idType, Integer id);
    /**
     * 根据 id 删除评论信息
     * @param idType id类型： userId、articleId、commentId
     * @param id
     * @return
     */
    int deleteById(String idType, Integer id);



}
