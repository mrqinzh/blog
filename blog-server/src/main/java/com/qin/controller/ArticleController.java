package com.qin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qin.pojo.Article;
import com.qin.pojo.Page;
import com.qin.pojo.Resp;
import com.qin.pojo.User;
import com.qin.service.ArticleService;

import java.util.*;

import com.qin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 根据请求文章ID，返回文章的html内容
     * @param blog_id 文章ID
     * @return 返回文章信息
     */
    @GetMapping("/blog/{blog_id}")
    public Article findOneArticle(@PathVariable("blog_id") int blog_id){
        return articleService.findByArticleId(blog_id);
    }

    /**
     * 分页请求文章信息
     * @param page  封装请求信息 页数 页大小 查询条件
     * @return  返回数据
     */
    @RequestMapping("/orderArticle")
    public Map<String,Object> someArticles(@RequestBody Page page) {
        Map<String,Object> map = new HashMap<>();

        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        List<Article> articles = articleService.orderArticles(page);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        map.put("count",pageInfo.getTotal());
        map.put("list",pageInfo.getList());

        return map;
    }

    /**
     * 查询当前用户发表的文章
     * @param currentPage 当前页数
     * @param pageSize 每页多少
     * @return 封装返回信息
     */
    @GetMapping("/myblog/{currentPage}/{pageSize}")
    public Map<String,Object> findOwnArticle(@PathVariable("currentPage")int currentPage,
                                             @PathVariable("pageSize")int pageSize,
                                             HttpServletRequest req){
        Map<String,Object> map = new HashMap<>();
        User user = (User) req.getAttribute("user");
        try {
            PageHelper.startPage(currentPage,pageSize);
            List<Article> articles = articleService.findByUserId(user.getId());
            PageInfo<Article> pageInfo = new PageInfo<>(articles);
            map.put("count",pageInfo.getTotal());
            map.put("list",pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","登录信息好像过期了，试着重新登录下吧");
        }
        return map;
    }

    /**
     * 写文章
     * @param article   要保存的信息
     * @return 1： 成功    ！= 1： 添加失败
     */
    @PostMapping("/add")
    public int add(@RequestBody Article article, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        article.setUser_id(user.getId());   // 设置作者的id号
        return this.articleService.addNewArticle(article);
    }

    /**
     * 更新文章内容
     * @param article 封装文章信息
     * @return 1： 成功    ！= 1： 添加失败
     */
    @PostMapping("/change/{id}")
    public int updateArticle(@PathVariable("id") int id , @RequestBody Article article){
        article.setArticle_id(id);
        return articleService.updateArticle(article);

    }

    /**
     * 删除一篇文章
     * @param id    删除的文章id
     * @return 封装返回信息
     */
    @DeleteMapping("/del/{articleId}")
    public Resp<String> delArticle(@PathVariable("articleId") int id){
        return articleService.delArticle(id) == 1 ? Resp.success("删除成功") : Resp.error("500","删除失败");
    }

}