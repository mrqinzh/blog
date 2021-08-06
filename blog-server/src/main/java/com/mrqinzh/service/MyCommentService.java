package com.mrqinzh.service;

import com.mrqinzh.entity.MyComment;

import java.util.List;

public interface MyCommentService {

    List<MyComment> all(int articleId);

    void addComment(MyComment myComment);

    /**
     * 最新评论
     * @return
     */
    List<MyComment> preOrder();
}
