package com.mrqinzh.blog.mapper;

import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.entity.Article;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleMapper {

    List<Article> list(PageDTO pageDTO); // 排序全部文章、以及分页

    Article getById(Integer articleId); // 根据文章id展示当前文章

    Boolean add(Article article); // 添加一篇文章

    Boolean update(Article article); // 修改文章内容

    @Update("update article set status = 1 where id = #{articleId}")
    Boolean delete(Integer articleId); // 删除一篇文章

}