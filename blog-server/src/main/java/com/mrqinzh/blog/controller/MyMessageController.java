package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.entity.MyMessage;
import com.mrqinzh.blog.model.vo.resp.Resp;
import com.mrqinzh.blog.service.MyMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "留言接口")
@RestController
@RequestMapping("message")
public class MyMessageController {

    @Autowired
    private MyMessageService messageService;

    @ApiOperation(value = "获取所有留言")
    @GetMapping("list")
    public Resp list() {
        return messageService.list();
    }

    @ApiOperation(value = "增加留言")
    @PostMapping("add")
    public Resp add(@RequestBody MyMessage message) {
        return messageService.add(message);
    }

}
