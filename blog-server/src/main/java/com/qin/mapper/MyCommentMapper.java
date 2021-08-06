package com.qin.mapper;

import com.qin.pojo.MyComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyCommentMapper {

    List<MyComment> all(int articleId);

    int addComment(MyComment myComment);

    int delByArtId(int articleId);

    /**
     * 最新评论
     * @return
     */
    List<MyComment> preOrder();
}
