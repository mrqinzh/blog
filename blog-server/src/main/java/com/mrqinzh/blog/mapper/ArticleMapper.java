package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Article;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

public interface ArticleMapper {

    List<Article> list(PageDTO pageDTO); // 排序全部文章、以及分页

    Article getById(Integer articleId); // 根据文章id展示当前文章

    int add(Article article); // 添加一篇文章

    Boolean update(Article article); // 修改文章内容

    @Update("update article set article_views = article_views + 1 where id = #{articleId}")
    Boolean updateArticleViews(Integer articleId);

    @Delete("delete from article where id = #{id}")
    int delete(Integer articleId); // 删除一篇文章

}