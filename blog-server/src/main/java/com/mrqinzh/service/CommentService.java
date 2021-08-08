package com.mrqinzh.service;

import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.util.Resp;

import java.util.List;

/**
 * @author mrqinzh
 */
public interface CommentService {

    Resp list();

    void add(Comment comment);

    Resp getById(String idType, Integer id);

    Resp deleteById(String idType, Integer id);

}
