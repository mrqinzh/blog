package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.model.vo.comment.CommentVo;
import com.mrqinzh.blog.model.vo.PageVO;

import java.util.List;

/**
 * @author mrqinzh
 */
public interface CommentService {

    List<Comment> list(PageVO pageVO);

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
