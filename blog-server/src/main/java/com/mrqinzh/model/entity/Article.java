package com.mrqinzh.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article implements Serializable {

    private Integer id;
    private String articleAuthor;
    private String articleTitle;
    private String articleSummary;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date articleCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date articleUpdateTime;

    private String articleContentMd;

    private String articleTag;

    private String articleType;

    private Integer articleViews;

    private Integer userId;

    // 外键对象
    private User user;
}
