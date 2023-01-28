package com.mrqinzh.common.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@ApiModel(value = "分页信息类", description = "前端传入的分页信息")
public class PageVO<T> implements IPage<T>, Serializable {

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

    // ====================== 以下兼容mp的分页 ======================
    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();
    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();
    /**
     * 总数
     */
    private long total = 0;

    @Override
    public List<OrderItem> orders() {
        return getOrders();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return pageSize;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.pageSize = (int) size;
        return this;
    }

    @Override
    public long getCurrent() {
        return currentPage;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.currentPage = (int) current;
        return this;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }
}
