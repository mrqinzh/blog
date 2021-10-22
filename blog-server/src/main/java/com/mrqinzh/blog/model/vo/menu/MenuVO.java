package com.mrqinzh.blog.model.vo.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuVO {

    private Integer id;

    @ApiModelProperty(value = "父菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单名")
    @NotNull
    private String menuTitle;

    @ApiModelProperty(value = "菜单路径")
    @NotNull
    private String menuPath;

    @ApiModelProperty(value = "组件名")
    @NotNull
    private String componentName;

    @ApiModelProperty(value = "组件路径")
    @NotNull
    private String componentPath;

    @ApiModelProperty(value = "是否缓存，默认0，缓存")
    private Integer cache;

    @ApiModelProperty(value = "是否隐藏，默认1，不隐藏")
    private Integer hidden;

}
