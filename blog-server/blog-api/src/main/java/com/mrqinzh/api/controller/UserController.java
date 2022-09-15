package com.mrqinzh.api.controller;

import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.vo.user.UserVO;
import com.mrqinzh.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户接口")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("list")
    public Resp list(PageVO pageVO) {
        return userService.list(pageVO);
    }

    @ApiOperation(value = "根据id获取指定用户")
    @GetMapping("{id}")
    public Resp getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return DataResp.ok(user);
    }

    @ApiOperation(value = "添加一个用户")
    @PostMapping("add")
    public Resp add(@RequestBody UserVO userVO) {
        return userService.add(userVO);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    public Resp update(@RequestBody UserVO userVO) {
        return userService.update(userVO);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public Resp info(@RequestParam String token) {
        return userService.info(token);
    }

}
