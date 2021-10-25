package com.mrqinzh.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("user")
@Data
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private String telephone;

    /**
     * 用户最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginLastTime;

    private String userAvatar;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * email
     */
    private String userEmail;

    private String userRealName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPwd;

    @TableField(exist = false)
    private Role role;

}
