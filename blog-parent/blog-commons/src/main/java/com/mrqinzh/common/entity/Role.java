package com.mrqinzh.common.entity;

import com.mrqinzh.common.enums.RoleType;
import com.mrqinzh.commons.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Role extends BaseEntity {

    private RoleType roleName;

    private Date createTime;
    private Date updateTime;

    private Integer status;

}
