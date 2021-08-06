package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.CommentMapper;
import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> all(int articleId) {
        List<Comment> comments = commentMapper.all(articleId);

        for (Comment comment : comments) {
            if (comment.getParent_id() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParent_id() == comment.getId()).collect(Collectors.toList()));
            }
        }

        return comments.stream().filter(c -> c.getParent_id() == 0).collect(Collectors.toList());
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> preOrder() {
        List<Comment> comments = commentMapper.preOrder();
        return comments;
    }
}
