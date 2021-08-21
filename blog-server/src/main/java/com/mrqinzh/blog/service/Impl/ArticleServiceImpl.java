package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.mapper.ArticleMapper;
import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.service.ArticleService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.util.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class.getSimpleName());

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp list(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Article> articles = articleMapper.list(pageDTO);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        return Resp.sendPageData(pageInfo);
    }

    @Override
    public Resp getById(Integer articleId) {
        Article article = articleMapper.getById(articleId);
        return Resp.ok(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp add(Article article, HttpServletRequest request) {
        try {
            // request 添加 文章userId & articleAuthor信息
            // 目前处于写死的状态，后续通过 request获取
            article.setUserId(1).setArticleAuthor("秦志宏");

            // 获取文章摘要，截取内容的前100
            String articleSummary = stripHtml(article.getArticleSummary());
            if (articleSummary.length() > 100) {
                article.setArticleSummary(articleSummary.substring(0, 100));
            } else {
                article.setArticleSummary(articleSummary);
            }

            // 初始化文章的固定信息
            article.setArticleCreateTime(new Date()).setArticleUpdateTime(new Date()).setArticleViews(0);

            articleMapper.add(article);

            logger.info("新增文章了。。。 => ");
        } catch (Exception e) {
            e.printStackTrace();
            return Resp.error(500, e.getMessage());
        }
        return Resp.ok(article.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp update(Article article) {

        article.setArticleUpdateTime(new Date());
        System.out.println(article);

        if (!articleMapper.update(article)) {
            return Resp.error(500, "更新失败");
        }
        return Resp.ok(null);
    }

    /**
     * 此处执行删除文章时，需要删除文章对应的评论
     * @param articleId 文章ID
     */
    @Override
    @Transactional
    public void delete(Integer articleId) {
//        articleMapper.delete(articleId);
    }

    @Override
    public Resp updateArticleViews(Integer articleId) {
        Boolean result = articleMapper.updateArticleViews(articleId);
        return Resp.ok(result);
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
