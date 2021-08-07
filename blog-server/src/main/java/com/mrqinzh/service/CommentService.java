package com.mrqinzh.service;

import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.util.Resp;

import java.util.List;

public interface CommentService {

    Resp getListByArticleId(Integer articleId);

    void deleteByArticleId(Integer articleId);

    void add(Comment comment);

    Resp getById(String type, Integer id);



}
