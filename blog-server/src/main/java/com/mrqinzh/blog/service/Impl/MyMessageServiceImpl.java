package com.mrqinzh.blog.service.Impl;

import com.mrqinzh.blog.mapper.MyMessageMapper;
import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.vo.resp.DataResp;
import com.mrqinzh.blog.model.vo.resp.Resp;
import com.mrqinzh.blog.service.MyMessageService;
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
}
