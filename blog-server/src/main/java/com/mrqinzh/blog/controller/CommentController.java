package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.entity.Comment;
import com.mrqinzh.blog.service.CommentService;
import com.mrqinzh.blog.model.vo.resp.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "评论接口")
@CrossOrigin
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加一条评论，任何人均可添加")
    @PostMapping("add")
    public Resp add(@RequestBody Comment comment) {
        return commentService.add(comment);
    }

    /**
     * 根据对用的 id 类型， 对评论信息进行筛选
     * @param idType id类型： userId、articleId
     * @param id id值
     */
    @ApiOperation(value = "根据 userId、articleId 查询评论")
    @GetMapping("{idType}/{id}")
    public Resp getById(@PathVariable String idType, @PathVariable Integer id) {
        return commentService.getById(idType, id);
    }

    @ApiOperation(value = "根据(*id)删除评论")
    @DeleteMapping("{idType}/{id}")
    public Resp deleteById(@PathVariable String idType, @PathVariable Integer id) {
        return commentService.deleteById(idType, id);
    }

    @ApiOperation(value = "查询 list limit 5")
    @GetMapping("list")
    public Resp list() {
        return commentService.list();
    }


}
