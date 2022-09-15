package com.mrqinzh.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.model.entity.Article;
import com.mrqinzh.common.model.entity.Tag;
import com.mrqinzh.common.model.entity.User;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.PageResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.article.ArticleVo;
import com.mrqinzh.common.util.MyUtil;
import com.mrqinzh.server.mapper.ArticleMapper;
import com.mrqinzh.server.mapper.CommentMapper;
import com.mrqinzh.server.mapper.TagMapper;
import com.mrqinzh.server.mapper.UserMapper;
import com.mrqinzh.server.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

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
        Article article = articleMapper.getById(articleId);
        // 更新浏览量
        article.setArticleViews(article.getArticleViews() + 1);
        articleMapper.updateById(article);

        return article;
    }

    @Override
    public void add(ArticleVo articleVo) {
        articleVo.setArticleSummary(MyUtil.stripHtml(articleVo.getArticleSummary()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userMapper.getByUsernameOrEmail(userDetails.getUsername());

        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);

        // 设置文章作者，优先为realName
        if (StringUtils.isNotBlank(user.getUserRealName())) {
            article.setArticleAuthor(user.getUserRealName());
        } else {
            article.setArticleAuthor(user.getUserNickname());
        }

        // 如果添加文章时，没有上传文章的封面图，系统将从选择的标签中，随机选择一个标签所对应的图片为其设置为封面。
        if (StringUtils.isBlank(article.getArticleCoverImg())) {
            String[] tags = article.getArticleTag().split(",");
            int threshold = 0; // 定义随机阈值
            while (true) {
                int i = MyUtil.randomInt(tags.length);
                String currTag = tags[i];
                QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Tag::getTagName, currTag);
                List<Tag> tagList = tagMapper.selectList(queryWrapper);
                if (tagList.size() > 0 && StringUtils.isNotBlank(tagList.get(0).getTagImg())) {
                    article.setArticleCoverImg(tagList.get(0).getTagImg());
                    break;
                }
                if (threshold++ > 4) {
                    throw new BizException(AppStatus.BAD_REQUEST, "对不起，系统里好像没有选择标签的相关图片，请重新选择标签，或者上传自己的封面图！！！");
                }
            }
        }

        Date now = new Date();
        // 初始化文章的固定信息
        article.setArticleViews(0)
                .setUserId(user.getId())
                .setArticleCreateTime(now)
                .setArticleUpdateTime(now)
                .setStatus(0);

        articleMapper.insert(article);

        logger.info("新增文章了。。。 => ");
    }

    @Override
    public void update(ArticleVo articleVo) {

        // 判断传入文章的Id是否存在
        if (articleVo.getId() == null) {
            throw new BizException(AppStatus.BAD_REQUEST);
        }

        // 判断数据库中是否 存在
        Article article = articleMapper.getById(articleVo.getId());
        if (article == null) {
            throw new BizException(AppStatus.BAD_REQUEST, "当前文章不存在数据库中");
        }

        // 获取文章摘要，截取内容的前100
        article.setArticleSummary(MyUtil.stripHtml(article.getArticleSummary()));

        // 设置更新时间
        article.setArticleUpdateTime(new Date());

        articleMapper.updateById(article);
    }

    /**
     * 此处执行删除文章时(逻辑删除)，需要删除文章对应的评论
     * @param articleId 文章ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer articleId) {
        articleMapper.deleteStatus(articleId);
        commentMapper.deleteByTypeId("articleId", articleId);
    }






}
