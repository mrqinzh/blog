package com.mrqinzh.commons.entity;

import com.mrqinzh.commons.mapper.BlogTypeAlias;

import java.io.Serializable;

public class BaseEntity implements BlogTypeAlias, Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
