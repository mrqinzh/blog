package com.mrqinzh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.mapper.ArticleMapper;
import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Article;
import com.mrqinzh.service.ArticleService;
import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Page list(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Article> articles = articleMapper.list(pageDTO);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        return Page.getPageData(pageInfo);
    }

    @Override
    public Resp getById(Integer articleId) {
        Article article = articleMapper.getById(articleId);
        return Resp.ok(article);
    }

    @Override
    public void add(Article article) {
        String articleSummary = stripHtml(article.getArticleSummary());
        if (articleSummary.length() > 100) {
            article.setArticleSummary(articleSummary.substring(0, 100));
        } else {
            article.setArticleSummary(articleSummary);
        }

        articleMapper.add(article);
    }

    @Override
    public Resp update(Article article) {

        article.setArticleUpdateTime(new Date());
        System.out.println(article);

//        articleMapper.update(article); //
        return Resp.ok(null);
    }

    @Override
    public void delete(Integer articleId) {
        articleMapper.delete(articleId);
    }


    /**
     * 将 content 中的 HTML 标签过滤
     * @param content HTML
     * @return java.lang.String
     */
    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }


}
