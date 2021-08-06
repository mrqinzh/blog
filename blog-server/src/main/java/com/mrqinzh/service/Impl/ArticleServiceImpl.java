package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.ArticleMapper;
import com.mrqinzh.mapper.MyCommentMapper;
import com.mrqinzh.entity.Article;
import com.mrqinzh.entity.Page;
import com.mrqinzh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private MyCommentMapper myCommentMapper;


    @Override
    public Article findByArticleId(int id) {
        return articleMapper.findByArticleId(id);
    }

    @Override
    public List<Article> findByUserId(int id) {
        return articleMapper.findByUserId(id);
    }

    @Override
    public List<Article> orderArticles(Page page) {
        return articleMapper.orderArticles(page);
    }

    @Override
    public int delArticle(int id) {
        myCommentMapper.delByArtId(id);
        return articleMapper.delArticle(id);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    public int addNewArticle(Article article) {
        String md = stripHtml(article.getArticle_html());
        if (md.length() > 100){
            article.setArticle_body(md.substring(0, 100));
        } else {
            article.setArticle_body(md);
        }
        return articleMapper.addArticle(article);
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }


}
