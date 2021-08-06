package com.mrqinzh.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date comment_time;
    private String comment_content;
    private String comment_type;
    private Integer status;

    private Integer user_id;
    private User user;

    private Integer article_id;
    private Article article;

    private Integer parent_id;
    private List<Comment> comments;

}
