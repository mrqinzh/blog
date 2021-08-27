package com.mrqinzh.blog.service.Impl;

import com.mrqinzh.blog.mapper.CommentMapper;
import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.service.CommentService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.dto.resp.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp list() {
        List<Comment> comments = commentMapper.list();
        return Resp.ok(comments);
    }

    @Override
    @Transactional
    public Resp add(Comment comment, String token) {
        User user = (User) redisUtil.get(token);
        comment.setUserId(user.getId());
        commentMapper.add(comment);

        return Resp.sendSuccessMsg("评论成功");
    }

    @Override
    public Resp getById(String idType, Integer id) {

        List<Comment> comments = commentMapper.getById(idType, id);

        for (Comment comment : comments) {
            if (comment.getParentId() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParentId().equals(comment.getId())).collect(Collectors.toList()));
            }
        }

        List<Comment> list = comments.stream().filter(c -> c.getParentId() == 0).collect(Collectors.toList());
        return Resp.ok(list);
    }

    /**
     * 根据id 删除评论
     * @param idType userId、articleId、commentId 分别对应不同的id类型
     * @param id id 值
     * @return
     */
    @Override
    @Transactional
    public Resp deleteById(String idType, Integer id) {

        commentMapper.deleteById(idType, id);
        return Resp.sendSuccessMsg("操作成功");
    }


}
