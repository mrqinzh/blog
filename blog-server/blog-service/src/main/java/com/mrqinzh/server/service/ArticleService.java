package com.mrqinzh.server.service;

import com.mrqinzh.common.model.entity.Article;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.article.ArticleVo;

public interface ArticleService {

    Resp list(PageVO pageVO); // 排序全部文章、以及分页

    Article getById(Integer articleId); // 根据文章id展示当前文章

    void add(ArticleVo articleVo);

    void update(ArticleVo articleVo); // 修改文章内容

    void delete(Integer articleId); // 删除一篇文章

}