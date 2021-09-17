package com.mrqinzh.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true) // 开启链式编程
public class Article implements Serializable {

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
    private User user;
}
