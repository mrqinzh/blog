package com.mrqinzh.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("comment")
@Data
public class Comment implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String avatar;

    private String nickname;

    private String commentContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentTime;

    private String ip;

    private Integer status;

    private Integer articleId;
    @TableField(exist = false)
    private Article article;

    private Integer parentId;

    @TableField(exist = false)
    private List<Comment> comments;

}
