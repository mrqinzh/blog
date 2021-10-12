package com.mrqinzh.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@TableName("article")
@Data
@Accessors(chain = true) // 开启链式编程
public class Article implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String articleAuthor;
    private String articleTitle;
    private String articleSummary;

    private String articleCoverImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date articleCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date articleUpdateTime;

    private String articleContentMd;

    private String articleTag;

    private String articleType;

    private Integer articleViews;

    /**
     * 状态值
     */
    private Integer status;

    private Integer userId;
    @TableField(exist = false)
    private User user;
}
