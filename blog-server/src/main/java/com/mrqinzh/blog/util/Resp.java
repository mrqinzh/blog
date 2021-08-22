package com.mrqinzh.blog.util;

import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.model.enums.ExceptionInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回数据类型
 * @param <E> 传入数据类型
 */
@Data
public class Resp<E> {


    private Integer code;
    private boolean success;
    private E data;

    private String message;

    public static <E> Resp<E> ok(E data){
        return new Resp<>(200, true, data);
    }

    public static <E> Resp<E> error(Integer code, E data){
        return new Resp<>(code, false, data);
    }

    public static Resp sendSuccessMsg(String message) {
        Resp resp = new Resp();
        resp.setCode(200);
        resp.setSuccess(true);
        resp.setMessage(message);
        return resp;
    }

    public static Resp fromErrorInfo(ExceptionInfo exceptionInfo) {
        return new Resp(exceptionInfo.getCode(), false, exceptionInfo.getMsg());
    }

    public static <E> Resp<Map<String, Object>> sendPageData(PageInfo<E> pageInfo) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("currentPage", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("totalCount", pageInfo.getTotal());
        map.put("totalPage", pageInfo.getPages());
        map.put("rows", pageInfo.getList());
        return Resp.ok(map);
    }

    public Resp(Integer code, boolean success, E data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public Resp() {

    }

}