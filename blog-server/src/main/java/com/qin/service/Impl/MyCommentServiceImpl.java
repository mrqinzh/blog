package com.qin.service.Impl;

import com.qin.mapper.MyCommentMapper;
import com.qin.pojo.MyComment;
import com.qin.service.MyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyCommentServiceImpl implements MyCommentService {

    @Autowired
    private MyCommentMapper commentMapper;

    @Override
    public List<MyComment> all(int articleId) {
        List<MyComment> comments = commentMapper.all(articleId);

        for (MyComment comment : comments) {
            if (comment.getParent_id() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParent_id() == comment.getId()).collect(Collectors.toList()));
            }
        }

        return comments.stream().filter(c -> c.getParent_id() == 0).collect(Collectors.toList());
    }

    @Override
    public void addComment(MyComment myComment) {
        commentMapper.addComment(myComment);
    }

    @Override
    public List<MyComment> preOrder() {
        List<MyComment> myComments = commentMapper.preOrder();
        return myComments;
    }
}
