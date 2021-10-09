package com.mrqinzh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrqinzh.blog.model.entity.MyMessage;

public interface MyMessageService extends IService<MyMessage> {

    void add(MyMessage message);

}
