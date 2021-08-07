package com.mrqinzh.mapper;

import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Article;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ArticleMapper {

    List<Article> list(PageDTO pageDTO); // 排序全部文章、以及分页

    Article getById(Integer articleId); // 根据文章id展示当前文章

    int add(Article article); // 添加一篇文章

    int update(Article article); // 修改文章内容

    @Delete("delete from article where id = #{id}")
    int delete(Integer articleId); // 删除一篇文章

}