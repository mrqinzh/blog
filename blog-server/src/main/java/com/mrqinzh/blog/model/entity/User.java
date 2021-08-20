package com.mrqinzh.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private Integer id;

    private String roleName;

    /**
     * 用户最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date loginLastTime;

    private String userAvatar;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * email
     */
    private String userEmail;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPwd;

}
