package com.mrqinzh.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.model.entity.Comment;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.comment.CommentPageVo;
import com.mrqinzh.common.model.vo.comment.CommentVo;
import com.mrqinzh.common.util.MyUtil;
import com.mrqinzh.common.util.WebUtil;
import com.mrqinzh.common.config.WebSocketServer;
import com.mrqinzh.server.mapper.CommentMapper;
import com.mrqinzh.server.mapper.UserMapper;
import com.mrqinzh.server.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Comment> list(CommentPageVo commentPageVo) {
        PageHelper.startPage(commentPageVo.getCurrentPage(), commentPageVo.getPageSize(), commentPageVo.getOrderBy());
        List<Comment> comments = commentMapper.list(commentPageVo);
        return comments;
    }

    @Override
    public List<Comment> getMessageList() {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Comment::getType, 2)
                .eq(Comment::getStatus, 0);
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
            avatar = MyUtil.getRandomAvatarUrl();
        }

        Date now = new Date();
        comment.setAvatar(avatar);
        comment.setIp(ip);
        comment.setCommentTime(now);

        commentMapper.insert(comment);

        // Todo 通过webSocket向super-admin发送信息通知
        String message = "ip为" + ip + "的用户，留下了他的足迹。";
        try {
            WebSocketServer.sendInfo(message, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public Resp deleteById(String idType, Integer id) {
        commentMapper.deleteByTypeId(idType, id);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}
