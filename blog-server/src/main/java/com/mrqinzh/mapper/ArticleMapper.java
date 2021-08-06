package com.mrqinzh.mapper;

import com.mrqinzh.entity.Article;
import java.util.List;

import com.mrqinzh.entity.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper {

    List<Article> orderArticles(Page page); // 排序全部文章、以及分页

    @Select("select * from article_table where article_id = #{article_id}")
    Article findByArticleId(int id); // 根据文章id展示当前文章

    List<Article> findByUserId(int id); // 根据UserId寻找当前User发表的文章

    int addArticle(Article article); // 添加一篇文章

    int updateArticle(Article article); // 修改文章内容

    @Delete("delete from article_table where article_id = #{article_id}")
    int delArticle(int id); // 删除一篇文章

}