package com.qin.pojo;

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
public class MyComment implements Serializable {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date comment_time;
    private String comment_content;

    private int user_id;
    private User user;

    private int article_id;
    private Article article;

    private int parent_id;
    private List<MyComment> comments;

}
