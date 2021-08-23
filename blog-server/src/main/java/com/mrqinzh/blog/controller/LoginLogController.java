package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.service.LoginLogService;
import com.mrqinzh.blog.util.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录日志接口")
@RestController
@RequestMapping("login-log")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @ApiOperation(value = "登录日志列表")
    @GetMapping("list")
    public Resp list(PageDTO pageDTO) {
        return loginLogService.list(pageDTO);
    }

}
