package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.vo.resp.Resp;

public interface MyMessageService {

    Resp list();

    Resp add(MyMessage message);

}
