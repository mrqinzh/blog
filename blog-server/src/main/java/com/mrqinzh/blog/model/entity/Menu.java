package com.mrqinzh.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_menu")
@Accessors(chain = true)
public class Menu implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    /**
     * 菜单名
     */
    private String menuTitle;

    /**
     * 组件name
     */
    private String componentName;

    /**
     * 组件位置
     */
    private String componentPath;

    private String icon;

    private String menuPath;

    private Integer cache;

    private Integer hidden;

    private Integer menuSort;

    private Integer type;

    private String permission;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableLogic
    private Integer status;



}
