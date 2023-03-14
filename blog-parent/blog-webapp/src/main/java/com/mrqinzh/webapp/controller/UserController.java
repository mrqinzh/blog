package com.mrqinzh.webapp.controller;

import com.mrqinzh.webapp.service.UserServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceProxy userServiceProxy;

    @RequestMapping("user")
    public String testDubbo() {
        return userServiceProxy.test();
    }

}
