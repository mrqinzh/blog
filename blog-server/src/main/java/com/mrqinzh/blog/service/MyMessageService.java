package com.mrqinzh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.resp.Resp;

public interface MyMessageService extends IService<MyMessage> {

    Resp add(MyMessage message);

}
