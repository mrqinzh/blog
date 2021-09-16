package com.mrqinzh.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "留言信息实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyMessage {

    private Integer id;

    private String nickname;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String ip;

    private Integer status;

}
