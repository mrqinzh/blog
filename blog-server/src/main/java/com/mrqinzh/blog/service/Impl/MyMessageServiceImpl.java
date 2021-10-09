package com.mrqinzh.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.MyMessageMapper;
import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.service.MyMessageService;
import com.mrqinzh.blog.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyMessageServiceImpl extends ServiceImpl<MyMessageMapper, MyMessage> implements MyMessageService {

    @Autowired
    private MyMessageMapper messageMapper;

    @Override
    public void add(MyMessage message) {

        // 5000
        if (messageMapper.messageCount() > 5000) {
            throw new BizException(AppStatus.INSERT_FAILED, "留言数量好像超出上限了，快联系管理员处理吧。。。");
        }

        String ip = WebUtil.getClientIp(WebUtil.getRequest());

        // 查询从当前 ip 中的留言
        List<MyMessage> messagesByIp = messageMapper.getByIp(ip);
        if (messagesByIp.size() > 1000) {
            throw new BizException(AppStatus.INSERT_FAILED, "hxd，你留言数量好像有点多啊，需要联系管理员充值一下了。。。-_-");
        }

        message.setIp(ip);

        messageMapper.insert(message);

    }


}
