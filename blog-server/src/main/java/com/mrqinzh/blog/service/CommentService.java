package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.util.Resp;

/**
 * @author mrqinzh
 */
public interface CommentService {

    Resp list();

    void add(Comment comment);

    Resp getById(String idType, Integer id);

    Resp deleteById(String idType, Integer id);

}
