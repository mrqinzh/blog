package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.model.dto.Resp;

/**
 * @author mrqinzh
 */
public interface CommentService {

    Resp list();

    Resp add(Comment comment, String token);

    Resp getById(String idType, Integer id);

    /**
     * 根据id 删除评论
     * @param idType userId、articleId、commentId 分别对应不同的id类型
     * @param id id 值
     * @return
     */
    Resp deleteById(String idType, Integer id);

}
