package com.mrqinzh.blog.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "分页信息类", description = "前端传入的分页信息")
public class PageDTO implements Serializable {

    private Integer currentPage;
    private Integer pageSize;
    /**
     * 条件属性
     */
    private String condition;
    /**
     * 排序
     */
    private String orderBy;
}
