package com.mrqinzh.application.controller;

import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping
    public Resp testRedisKeyExpire() {
        redisUtil.set("test_expire", 1, 5);
        return Resp.success();
    }

}
