package com.mrqinzh.domain.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.core.entity.Article;
import com.mrqinzh.core.entity.Tag;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.PageResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.article.ArticleVo;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.common.util.MyUtil;
import com.mrqinzh.core.auth.context.AuthenticationContextUtils;
import com.mrqinzh.domain.mapper.ArticleMapper;
import com.mrqinzh.domain.mapper.CommentMapper;
import com.mrqinzh.domain.mapper.TagMapper;
import com.mrqinzh.domain.mapper.UserMapper;
import com.mrqinzh.domain.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Resp list(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Article> articles = articleMapper.list(pageVO);

        return PageResp.ok(articles);
    }

    @Override
    public Article getById(Integer articleId) {
        BizAssert.notNull(articleId, "文章查询失败，查询id为空");
        Article article = articleMapper.getById(articleId);
        // 更新浏览量
        article.setArticleViews(article.getArticleViews() + 1);
        articleMapper.updateById(article);
        return article;
    }

    @Override
    @Transactional
    public void add(ArticleVo articleVo) {
        BizAssert.notNull(articleVo, "文章添加失败，文章信息为空");
        articleVo.setArticleSummary(MyUtil.stripHtml(articleVo.getArticleSummary()));

        User user = (User) AuthenticationContextUtils.getSecurityUser();
        BizAssert.notNull(user, "用户异常。！");
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);

        // 设置文章作者，优先为realName
        article.setArticleAuthor(StringUtils.isNotBlank(user.getUserRealName()) ? user.getUserRealName() : user.getUserNickname());

        // 如果添加文章时，没有上传文章的封面图，系统将从选择的标签中，随机选择一个标签所对应的图片为其设置为封面。
        if (StringUtils.isBlank(article.getArticleCoverImg())) {
            article.setArticleCoverImg(getArticleCoverImgByTag(article.getArticleTag()));
        }

        Date now = new Date();
        // 初始化文章的固定信息
        article.setArticleViews(0)
                .setUserId(user.getId())
                .setArticleCreateTime(now)
                .setArticleUpdateTime(now)
                .setStatus(0);

        articleMapper.insert(article);

        logger.info("新增文章了。。。 => 时间：{}", DateUtil.format(now, "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void update(ArticleVo articleVo) {
        // 判断传入文章的Id是否存在
        if (articleVo == null || articleVo.getId() == null) {
            throw new BizException(AppStatus.BAD_PARAMETER_REQUEST);
        }

        Article origin = articleMapper.getById(articleVo.getId());
        if (origin == null) {
            throw new BizException(AppStatus.BAD_PARAMETER_REQUEST, "当前文章错误。");
        }

        origin.setArticleTitle(articleVo.getArticleTitle());
        // 获取文章摘要，截取内容的前100
        origin.setArticleSummary(MyUtil.stripHtml(articleVo.getArticleSummary()));
        origin.setArticleCoverImg(articleVo.getArticleCoverImg());
        origin.setArticleContentMd(articleVo.getArticleContentMd());
        origin.setArticleTag(articleVo.getArticleTag());
        origin.setArticleType(articleVo.getArticleType());
        // 设置更新时间
        origin.setArticleUpdateTime(new Date());
        articleMapper.updateById(origin);
    }

    /**
     * 此处执行删除文章时(逻辑删除)，删除文章对应的评论
     */
    @Override
    @Transactional
    public void delete(Integer articleId) {
        articleMapper.deleteStatus(articleId);
        commentMapper.deleteByTypeId("articleId", articleId);
    }

    private String getArticleCoverImgByTag(String articleTag) {
        if (StringUtils.isBlank(articleTag)) return null;
        String[] tags = articleTag.split(",");
        int threshold = 0; // 定义随机阈值
        while (true) {
            int i = MyUtil.randomInt(tags.length);
            String currTag = tags[i];
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Tag::getTagName, currTag);
            List<Tag> tagList = tagMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(tagList) && StringUtils.isNotBlank(tagList.get(0).getTagImg())) {
                return tagList.get(0).getTagImg();
            }
            if (threshold++ > 4) {
                throw new BizException(AppStatus.BAD_PARAMETER_REQUEST, "对不起，系统里好像没有选择标签的相关图片，请重新选择标签，或者上传自己的封面图！！！");
            }
        }
    }






}
