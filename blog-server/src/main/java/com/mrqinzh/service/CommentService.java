package com.mrqinzh.service;

import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.util.Resp;

import java.util.List;

/**
 * @author mrqinzh
 */
public interface CommentService {

    void add(Comment comment);

    Resp getById(String type, Integer id);

}
