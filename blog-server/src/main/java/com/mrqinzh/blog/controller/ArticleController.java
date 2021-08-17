package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.service.ArticleService;

import com.mrqinzh.blog.util.Page;
import com.mrqinzh.blog.util.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "文章接口")
@CrossOrigin
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
    public Page list(PageDTO pageDTO) {
        return articleService.list(pageDTO);
    }

    @ApiOperation(value = "添加文章")
    @PostMapping("/add")
    public Resp add(@RequestBody Article article, HttpServletRequest request) {
        return articleService.add(article, request);
    }

    @ApiOperation(value = "根据 articleId 更新文章")
    @PostMapping("/update/{articleId}")
    public Resp update(@PathVariable("articleId") Integer articleId , @RequestBody Article article){
        article.setId(articleId);
        return articleService.update(article);
    }

    @ApiOperation(value = "根据 articleId 删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        articleService.delete(articleId);
        return Resp.ok("删除成功");
    }

    @ApiOperation(value = "更新浏览量功能")
    @PostMapping("/views/{articleId}")
    public Resp updateArticleViews(@PathVariable Integer articleId) {
        return articleService.updateArticleViews(articleId);
    }

}