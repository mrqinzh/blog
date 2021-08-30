package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.ArticleMapper;
import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.dto.resp.DataResp;
import com.mrqinzh.blog.model.dto.resp.PageResp;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.ArticleService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.dto.resp.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return PageResp.ok(articles);
    }

    @Override
    public Resp getById(Integer articleId) {
        Article article = articleMapper.getById(articleId);
        // 更新浏览量
        article.setArticleViews(article.getArticleViews() + 1);
        articleMapper.update(article);
        return DataResp.ok(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp add(Article article, String token) {

        User user = (User) redisUtil.get(token);

        article.setUserId(user.getId()).setArticleAuthor("秦志宏");

        // 获取文章摘要，截取内容的前100
        String articleSummary = stripHtml(article.getArticleSummary());
        if (articleSummary.length() > 100) {
            article.setArticleSummary(articleSummary.substring(0, 100));
        } else {
            article.setArticleSummary(articleSummary);
        }

        // 初始化文章的固定信息
        article.setArticleCreateTime(new Date()).setArticleUpdateTime(new Date()).setArticleViews(0);

        if (!articleMapper.add(article)) {
            throw new BizException(AppStatus.INSERT_FAILED);
        }

        logger.info("新增文章了。。。 => ");
        return DataResp.ok(article.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp update(Article article) {

        // 判断传入文章的Id是否存在
        if (article.getId() == null) {
            throw new BizException(AppStatus.BAD_REQUEST);
        }

        // 设置文章的最后更新时间
        article.setArticleUpdateTime(new Date());

        articleMapper.update(article);

        if (!articleMapper.update(article)) {
            throw new BizException(AppStatus.UPDATE_FAILED);
        }
        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    /**
     * 此处执行删除文章时(逻辑删除)，需要删除文章对应的评论
     * @param articleId 文章ID
     */
    @Override
    public Resp delete(Integer articleId) {
        articleMapper.delete(articleId);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
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
