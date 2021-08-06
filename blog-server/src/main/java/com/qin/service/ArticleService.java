package com.qin.service;

import com.qin.pojo.Article;
import com.qin.pojo.Page;

import java.util.List;

public interface ArticleService {

    List<Article> orderArticles(Page page); // 排序全部文章、以及分页

    Article findByArticleId(int id); // 根据文章id展示当前文章

    List<Article> findByUserId(int id); // 根据UserId寻找当前User发表的文章

    int addNewArticle(Article article);

    int updateArticle(Article article); // 修改文章内容

    int delArticle(int id); // 删除一篇文章

}