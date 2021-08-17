package com.mrqinzh.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDTO implements Serializable {

    private Integer currentPage;
    private Integer pageSize;
    /**
     * 条件属性
      */
    private String condition;
}
