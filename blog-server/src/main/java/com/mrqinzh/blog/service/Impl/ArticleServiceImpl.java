package com.mrqinzh.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.ArticleMapper;
import com.mrqinzh.blog.mapper.CommentMapper;
import com.mrqinzh.blog.model.vo.ArticleVo;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.resp.PageResp;
import com.mrqinzh.blog.model.entity.Article;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.ArticleService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.resp.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp list(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Article> articles = articleMapper.list(pageVO);

        return PageResp.ok(articles);
    }

    @Override
    public Article getById(Integer articleId) {
        Article article = articleMapper.getById(articleId);
        // 更新浏览量
        article.setArticleViews(article.getArticleViews() + 1);
        articleMapper.updateById(article);

        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ArticleVo articleVo, String token) {

        User user = (User) redisUtil.get(token);

        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);

        Date now = new Date();
        // 初始化文章的固定信息
        article.setArticleViews(0)
                .setUserId(user.getId())
                .setArticleAuthor("秦志宏")
                .setArticleCreateTime(now)
                .setArticleUpdateTime(now)
                .setStatus(0);

        articleMapper.insert(article);

        logger.info("新增文章了。。。 => ");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Article article) {

        // 判断传入文章的Id是否存在
        if (article.getId() == null) {
            throw new BizException(AppStatus.BAD_REQUEST);
        }

        // 判断数据库中是否 存在
        if (articleMapper.getById(article.getId()) == null) {
            throw new BizException(AppStatus.BAD_REQUEST, "当前文章不存在数据库中");
        }

        // 设置文章的最后更新时间
        article.setArticleUpdateTime(new Date());

        // 获取文章摘要，截取内容的前100
        article.setArticleSummary(subSummary(article.getArticleSummary()));

        articleMapper.updateById(article);
    }

    /**
     * 此处执行删除文章时(逻辑删除)，需要删除文章对应的评论
     * @param articleId 文章ID
     */
    @Override
    @Transactional
    public void delete(Integer articleId) {
        articleMapper.delete(articleId);
        commentMapper.deleteById("articleId", articleId);
    }



    /**
     * 截取文章摘要
     * @param articleSummary
     * @return
     */
    public String subSummary(String articleSummary) {
        String summary = stripHtml(articleSummary);
        return summary.length() > 100 ? summary.substring(0, 100) : summary;
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
