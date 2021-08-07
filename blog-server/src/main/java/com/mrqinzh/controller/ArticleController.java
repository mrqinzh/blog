package com.mrqinzh.controller;

import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Article;
import com.mrqinzh.model.entity.User;
import com.mrqinzh.service.ArticleService;

import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;
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

    @ApiOperation(value = "根据文章id查询文章具体信息")
    @GetMapping("/{articleId}")
    public Resp getById(@PathVariable("articleId") Integer articleId){
        return Resp.ok(articleService.getById(articleId));
    }

    @ApiOperation(value = "分页加载文章列表")
    @GetMapping("/list")
    public Page list(PageDTO pageDTO) {
        return articleService.list(pageDTO);
    }

    @ApiOperation(value = "添加一篇文章")
    @PostMapping("/add")
    public Resp add(@RequestBody Article article, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        article.setUserId(user.getId());
        articleService.add(article);
        return Resp.ok("添加成功");
    }

    @ApiOperation(value = "根据文章id更新文章")
    @PostMapping("/update/{articleId}")
    public Resp update(@PathVariable("articleId") Integer articleId , @RequestBody Article article){
        article.setId(articleId);
        articleService.update(article);
        return Resp.ok("修改成功");
    }

    @ApiOperation(value = "根据文章id删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        articleService.delete(articleId);
        return Resp.ok("删除成功");
    }

}