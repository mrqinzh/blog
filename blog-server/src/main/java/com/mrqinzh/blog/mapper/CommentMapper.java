package com.mrqinzh.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.blog.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    Boolean add(Comment comment);

    @Select("select * from comment where ip = #{ip} or nickname = #{nickname}")
    List<Comment> getByIpOrNickname(String ip, String nickname);

    /**
     * 根据id 查询评论信息
     * @param idType id类型： userId、articleId、commentId
     * @param id id值
     */
    List<Comment> getById(String idType, Integer id);

    /**
     * 根据 id 删除评论信息
     * @param idType id类型： userId、articleId、commentId
     */
    Boolean deleteById(String idType, Integer id);

}
