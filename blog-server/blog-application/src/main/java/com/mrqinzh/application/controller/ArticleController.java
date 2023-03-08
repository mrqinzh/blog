package com.mrqinzh.application.controller;

import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.model.bean.WebSocketBean;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.access.AccessPermission;
import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.auth.context.AuthenticationContextUtils;
import com.mrqinzh.core.entity.Article;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.article.ArticleVo;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.core.message.GlobalMessageProducer;
import com.mrqinzh.core.message.WebSocketMessage;
import com.mrqinzh.core.security.SecurityProperties;
import com.mrqinzh.domain.service.ArticleService;
import com.mrqinzh.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private GlobalMessageProducer producer;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据 articleId 查询文章具体信息")
    @GetMapping("/{articleId}")
    public Resp getById(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getById(articleId);
        return DataResp.ok(article);
    }

    @ApiOperation(value = "分页加载文章列表")
    @GetMapping("/list")
    public Resp list(PageVO pageVO) {
        User user = AuthenticationContextUtils.getUser();
        if (user != null) {
            String message = user.getName() + "刚刚浏览了文章列表，请注意查收。";
            WebSocketBean webSocketBean = new WebSocketBean(false, message);
            producer.produce(new WebSocketMessage(webSocketBean, SecurityProperties.PROJECT_DEVELOPER_ID));
        }
        return articleService.list(pageVO);
    }

    @AccessPermission(RoleType.SUPER_ADMIN)
    @ApiOperation(value = "添加文章")
    @PostMapping("/add")
    public Resp add(@RequestBody @Valid ArticleVo articleVo) {
        articleService.add(articleVo);
        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @AccessPermission(RoleType.SUPER_ADMIN)
    @ApiOperation(value = "根据 articleId 更新文章")
    @PostMapping("/update")
    public Resp update(@RequestBody ArticleVo articleVo){
        if (articleVo.getId() == null) {
            throw new BizException(AppStatus.BAD_PARAMETER_REQUEST);
        }
        articleService.update(articleVo);
        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @AccessPermission(RoleType.SUPER_ADMIN)
    @ApiOperation(value = "根据 id 删除文章")
    @DeleteMapping("/{articleId}")
    public Resp delete(@PathVariable("articleId") Integer articleId){
        articleService.delete(articleId);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}