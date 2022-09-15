package com.mrqinzh.api.controller;

import com.mrqinzh.common.model.entity.Article;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.article.ArticleVo;
import com.mrqinzh.common.config.WebSocketServer;
import com.mrqinzh.server.service.ArticleService;
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
    public Resp add(@RequestBody @Valid ArticleVo articleVo) {
        articleService.add(articleVo);
        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @ApiOperation(value = "根据 articleId 更新文章")
    @PostMapping("/update")
    public Resp update(@RequestBody ArticleVo articleVo){
        articleService.update(articleVo);
        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @ApiOperation(value = "根据 id 删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        articleService.delete(articleId);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}