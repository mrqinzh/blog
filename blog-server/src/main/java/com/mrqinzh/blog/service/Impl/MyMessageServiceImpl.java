package com.mrqinzh.blog.service.Impl;

import com.mrqinzh.blog.mapper.MyMessageMapper;
import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.vo.resp.DataResp;
import com.mrqinzh.blog.model.vo.resp.Resp;
import com.mrqinzh.blog.service.MyMessageService;
import com.mrqinzh.blog.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyMessageServiceImpl implements MyMessageService {

    @Autowired
    private MyMessageMapper messageMapper;

    @Override
    public Resp list() {
        List<MyMessage> messages = messageMapper.list();
        return DataResp.ok(messages);
    }

    @Override
    public Resp add(MyMessage message) {

//        Integer messageCount = messageMapper.messageCount();

        String ip = WebUtil.getClientIp(WebUtil.getRequest());
        message.setIp(ip);

        messageMapper.add(message);

        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }


}
