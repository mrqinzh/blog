package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.config.WebSocketServer;
import com.mrqinzh.blog.model.vo.req.PageVO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.service.ArticleService;

import com.mrqinzh.blog.model.vo.resp.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "根据 articleId 查询文章具体信息")
    @GetMapping("/{articleId}")
    public Resp getById(@PathVariable("articleId") Integer articleId){
        return articleService.getById(articleId);
    }

    @ApiOperation(value = "分页加载文章列表")
    @GetMapping("/list")
    public Resp list(PageVO pageVO) {
        try {
            WebSocketServer.sendInfo("有人访问了 ===> /article/list", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleService.list(pageVO);
    }

    @ApiOperation(value = "添加文章")
    @PostMapping("/add")
    public Resp add(@RequestBody Article article, @RequestHeader("token") String token) {
        return articleService.add(article, token);
    }

    @ApiOperation(value = "根据 articleId 更新文章")
    @PostMapping("/update")
    public Resp update(@RequestBody Article article){
        return articleService.update(article);
    }

    @ApiOperation(value = "根据 articleId 删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        return articleService.delete(articleId);
    }

}