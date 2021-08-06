package com.qin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装分页请请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page implements Serializable {

    private int currentPage;
    private int pageSize;
    // 条件属性
    private String condition;

}