package com.mrqinzh.controller;

import com.mrqinzh.model.entity.Comment;
import com.mrqinzh.model.entity.User;
import com.mrqinzh.service.CommentService;
import com.mrqinzh.util.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "评论接口")
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加一条评论")
    @PostMapping("/add")
    public Resp add(@RequestBody Comment comment, HttpServletRequest req) {

        User user = (User) req.getAttribute("user");
        comment.setUserId(user.getId());
        commentService.add(comment);

        return Resp.ok("添加成功");
    }

    @GetMapping("/{type}/{id}")
    public Resp getById(@PathVariable String type, @PathVariable Integer id) {
        return commentService.getById(type, id);
    }


}
