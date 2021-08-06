package com.mrqinzh.service;

import com.mrqinzh.model.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> all(int articleId);

    void addComment(Comment comment);

    /**
     * 最新评论
     * @return
     */
    List<Comment> preOrder();
}
