package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.vo.EmailVO;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "邮件接口")
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "发送一份简单邮件")
    @PostMapping("simple")
    public Resp sendSimpleEmail(@RequestBody EmailVO emailVO) {
        return emailService.sendSimpleMail(emailVO);
    }

    @ApiOperation(value = "发送一份带附件的邮件")
    @PostMapping("file")
    public Resp sendFileEmail(@RequestBody EmailVO emailVO) {
        return emailService.sendFileMail(emailVO);
    }

}
