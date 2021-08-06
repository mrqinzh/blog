package com.mrqinzh.mapper;

import com.mrqinzh.model.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> all(int articleId);

    int addComment(Comment comment);

    @Delete("delete from mycomment where article_id = #{article_id}")
    int delByArtId(int articleId);

    /**
     * 最新评论
     * @return
     */
    List<Comment> preOrder();
}
