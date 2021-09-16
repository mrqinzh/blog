package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.vo.resp.Resp;
import com.mrqinzh.blog.service.MyMessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "留言接口")
@RestController
@RequestMapping("message")
public class MyMessageController {

    @Autowired
    private MyMessageService messageService;

    @GetMapping("list")
    public Resp list() {
        return messageService.list();
    }

}
