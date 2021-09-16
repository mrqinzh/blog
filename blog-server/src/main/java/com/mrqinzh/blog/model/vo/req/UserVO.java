package com.mrqinzh.blog.model.vo.req;

import lombok.Data;

@Data
public class UserVO {

    private String userNickname;

    private String userAvatar;

    private String userName;

    private String userPwd;

    /**
     * 新密码，用于更新密码
     */
    private String newPass;

}
