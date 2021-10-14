package com.mrqinzh.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrqinzh.blog.mapper.CommentMapper;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.vo.CommentVo;
import com.mrqinzh.blog.service.CommentService;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getMessageList() {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 2);
        queryWrapper.eq("status", 0);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return comments;
    }

    @Override
    public void add(CommentVo commentVo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);

        String ip = WebUtil.getClientIp(WebUtil.getRequest());
        // 先根据 ip/昵称 查询当前用户是否已经进行过评论
        List<Comment> commentsByIp = commentMapper.getByIpOrNickname(ip, commentVo.getNickname());
        String avatar;
        if (commentsByIp.size() > 0 && StringUtils.isNotBlank(commentsByIp.get(0).getAvatar())) {
            avatar = commentsByIp.get(0).getAvatar();
        } else {
            avatar = "http://mrqinzh.info:9090/img/random-avatars/" + "avatar" + (int)Math.floor((Math.random() * 10) + 1) + ".png";
        }

        comment.setAvatar(avatar);
        comment.setIp(ip);
        comment.setCommentTime(new Date());

        commentMapper.insert(comment);
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
        commentMapper.deleteByTypeId(idType, id);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}
