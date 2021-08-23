package com.mrqinzh.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LoginLog {

    private Integer id;
    private Integer userId;
    private String token;
    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date loginTime;

}
