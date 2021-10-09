package com.mrqinzh.blog.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    private Integer id;

    private String roleName;

    /**
     * 用户最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date loginLastTime;

    private String userAvatar;

    private String userNickname;

    private String userEmail;

    private String userName;

    private String userPwd;

    /**
     * 新密码，用于更新密码
     */
    private String newPass;

}
