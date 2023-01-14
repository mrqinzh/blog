package com.mrqinzh.core.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.security.SecurityUser;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@TableName("user")
@Data
public class User extends BaseEntity implements SecurityUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
    private List<Role> roles;

    @Override
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return userRealName;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public List<RoleType> authorities() {
        if (CollectionUtil.isEmpty(roles)) return null;
        return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
    }
}
