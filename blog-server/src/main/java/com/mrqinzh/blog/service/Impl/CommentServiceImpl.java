package com.mrqinzh.blog.service.Impl;

import com.mrqinzh.blog.mapper.CommentMapper;
import com.mrqinzh.blog.model.vo.resp.DataResp;
import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.CommentService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.vo.resp.Resp;
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
        return DataResp.ok(comments);
    }

    @Override
    @Transactional
    public Resp add(Comment comment, String token) {
        // Todo userId从redis中获取，articleId通过前端传入，parentId前端传入 0：父评论      !=0：表示对应commentId的值
        User user = (User) redisUtil.get(token);
        comment.setUserId(user.getId());

        System.out.println("comment = " + comment);
        commentMapper.add(comment);
        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @Override
    public Resp getById(String idType, Integer id) {
        List<Comment> comments = commentMapper.getById(idType, id);
        // 遍历comments，将子评论添加到对应的父评论下面
        comments.stream().forEach(comment -> {
            if (comment.getParentId() == 0) {
                comment.setComments(comments.stream().filter(c -> c.getParentId().equals(comment.getId())).collect(Collectors.toList()));
            }
        });
        List<Comment> list = comments.stream().filter(c -> c.getParentId() == 0).collect(Collectors.toList());
        return DataResp.ok(list);
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
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }


}
