package com.mrqinzh.server.service;

import com.mrqinzh.core.entity.Comment;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.comment.CommentPageVo;
import com.mrqinzh.common.model.vo.comment.CommentVo;

import java.util.List;

/**
 * @author mrqinzh
 */
public interface CommentService {

    List<Comment> list(CommentPageVo commentPageVo);

    List<Comment> getMessageList();

    void add(CommentVo commentVo);

    Resp getById(String idType, Integer id);

    /**
     * 根据id 删除评论
     * @param idType userId、articleId、commentId 分别对应不同的id类型
     * @param id id 值
     * @return
     */
    Resp deleteById(String idType, Integer id);

}
