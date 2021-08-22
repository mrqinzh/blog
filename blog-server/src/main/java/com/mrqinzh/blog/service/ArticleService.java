package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.util.Resp;

import javax.servlet.http.HttpServletRequest;

public interface ArticleService {

    Resp list(PageDTO pageDTO); // 排序全部文章、以及分页

    Resp getById(Integer articleId); // 根据文章id展示当前文章

    Resp add(Article article, HttpServletRequest request);

    Resp update(Article article); // 修改文章内容

    void delete(Integer articleId); // 删除一篇文章

}