package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.CommentMapper;
import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.service.CommentService;
import com.mrqinzh.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Resp getListByArticleId(Integer articleId) {
        List<Comment> comments = commentMapper.getListByArticleId(articleId);

        for (Comment comment : comments) {
            if (comment.getParentId() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParentId() == comment.getId()).collect(Collectors.toList()));
            }
        }

        List<Comment> list = comments.stream().filter(c -> c.getParentId() == 0).collect(Collectors.toList());
        return Resp.ok(list);
    }

    @Override
    public void deleteByArticleId(Integer articleId) {
        commentMapper.deleteByArticleId(articleId);
    }

    @Override
    public void add(Comment comment) {
        commentMapper.add(comment);
    }

    @Override
    public Resp getById(String type, Integer id) {

        List<Comment> comments = commentMapper.getById(type, id);

        for (Comment comment : comments) {
            if (comment.getParentId() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParentId() == comment.getId()).collect(Collectors.toList()));
            }
        }

        List<Comment> list = comments.stream().filter(c -> c.getParentId() == 0).collect(Collectors.toList());
        return Resp.ok(list);
    }


}
