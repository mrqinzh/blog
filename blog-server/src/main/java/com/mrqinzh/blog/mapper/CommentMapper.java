package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Select("select * from comment limit 5")
    List<Comment> list();

    Boolean add(Comment comment);

    /**
     * 根据id 查询评论信息
     * @param idType id类型： userId、articleId
     * @param id id值
     */
    List<Comment> getById(String idType, Integer id);

    /**
     * 根据 id 删除评论信息
     * @param idType id类型： userId、articleId、commentId
     */
    Boolean deleteById(String idType, Integer id);

}
