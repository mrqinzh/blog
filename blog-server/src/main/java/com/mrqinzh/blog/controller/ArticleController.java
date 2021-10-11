package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.config.WebSocketServer;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.vo.ArticleVo;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.service.ArticleService;

import com.mrqinzh.blog.model.resp.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        Article article = articleService.getById(articleId);
        return DataResp.ok(article);

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
    public Resp add(@RequestBody @Valid ArticleVo articleVo, @RequestHeader("token") String token) {
        articleService.add(articleVo, token);
        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @ApiOperation(value = "根据 articleId 更新文章")
    @PostMapping("/update")
    public Resp update(@RequestBody Article article){
        articleService.update(article);
        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @ApiOperation(value = "根据 id 删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        articleService.delete(articleId);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}