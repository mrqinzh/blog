package com.mrqinzh.service;

import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Article;
import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;

public interface ArticleService {

    Page list(PageDTO pageDTO); // 排序全部文章、以及分页

    Resp getById(Integer articleId); // 根据文章id展示当前文章

    void add(Article article);

    Resp update(Article article); // 修改文章内容

    void delete(Integer articleId); // 删除一篇文章

}