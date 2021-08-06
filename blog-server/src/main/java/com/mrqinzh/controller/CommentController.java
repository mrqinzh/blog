package com.mrqinzh.controller;

import com.mrqinzh.model.entity.*;
import com.mrqinzh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/list")
    public Resp<List<Comment>> list() {
        List<Comment> comments = commentService.preOrder();
        return Resp.success(comments);
    }

    @GetMapping("/id")
    public Resp<List<Comment>> loadComment(@PathVariable("id") int articleId) {
        return Resp.success(commentService.all(articleId));
    }

    @PostMapping("/add")
    public Resp<String> add(@RequestBody Comment comment, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        comment.setUser_id(user.getId());
        commentService.addComment(comment);

        return Resp.success("success");
    }

}
