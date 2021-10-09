package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.model.resp.Resp;

public interface ArticleService {

    Resp list(PageVO pageVO); // 排序全部文章、以及分页

    Resp getById(Integer articleId); // 根据文章id展示当前文章

    Resp add(Article article, String token);

    Resp update(Article article); // 修改文章内容

    Resp delete(Integer articleId); // 删除一篇文章

}