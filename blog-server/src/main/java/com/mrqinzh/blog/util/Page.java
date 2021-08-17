package com.mrqinzh.blog.util;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page<T> implements Serializable {

    private Integer page;
    private Integer size;
    private Integer total;
    private List<T> rows;

    public static <T> Page<T> getPageData(PageInfo<T> pageInfo) {

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getList());
    }

}
