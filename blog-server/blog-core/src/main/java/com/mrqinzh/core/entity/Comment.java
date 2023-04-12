package com.mrqinzh.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@TableName("comment")
@Data
public class Comment extends BaseEntity {

    private String avatar;

    private String nickname;

    private String commentContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentTime;

    private String ip;

    /**
     * 类型：
     * 1： 评论    2：留言
     */
    private Integer type;

    private Integer status;

    private Integer articleId;
    @TableField(exist = false)
    private Article article;

    private Integer parentId;

    @TableField(exist = false)
    private List<Comment> comments;

}
