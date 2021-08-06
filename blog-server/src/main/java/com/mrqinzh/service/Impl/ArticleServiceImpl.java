package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.ArticleMapper;
import com.mrqinzh.mapper.CommentMapper;
import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Article;
import com.mrqinzh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Article findByArticleId(int id) {
        return articleMapper.findByArticleId(id);
    }

    @Override
    public List<Article> findByUserId(int id) {
        return articleMapper.findByUserId(id);
    }

    @Override
    public List<Article> orderArticles(PageDTO pageDTO) {
        return articleMapper.orderArticles(pageDTO);
    }

    @Override
    public int delArticle(int id) {
        commentMapper.delByArtId(id);
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
