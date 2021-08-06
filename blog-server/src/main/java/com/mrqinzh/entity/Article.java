package com.mrqinzh.entity;

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

    private int article_id;
    private String article_author;
    private String article_title;
    private String article_body;
    private String article_html;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date article_time;
    private String article_url;

    private String article_md;

    private String article_tag;

    private int user_id;

    // 外键对象
    private User user;
}
