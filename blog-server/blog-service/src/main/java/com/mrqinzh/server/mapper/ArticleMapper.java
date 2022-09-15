package com.mrqinzh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.common.model.entity.Article;
import com.mrqinzh.common.model.vo.PageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> list(PageVO pageVO); // 排序全部文章、以及分页

    Article getById(Integer articleId); // 根据文章id展示当前文章

    @Update("update article set status = 1 where id = #{articleId}")
    Boolean deleteStatus(Integer articleId); // 删除一篇文章

}