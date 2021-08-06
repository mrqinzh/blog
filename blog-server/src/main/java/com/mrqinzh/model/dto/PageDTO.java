package com.mrqinzh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDTO implements Serializable {

    private int currentPage;
    private int pageSize;
    // 条件属性
    private String condition;
}
