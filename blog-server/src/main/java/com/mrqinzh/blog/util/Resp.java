package com.mrqinzh.blog.util;

import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.model.enums.ExceptionInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
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

    public static Resp<String> sendSuccessMsg(String message) {
        Resp resp = new Resp();
        resp.setCode(200);
        resp.setSuccess(true);
        resp.setMessage(message);
        return resp;
    }

    /**
     * 返回异常信息
     * @param exceptionInfo
     */
    public static Resp sendExceptionInfo(ExceptionInfo exceptionInfo) {
        return new Resp(exceptionInfo.getCode(), false, exceptionInfo.getMsg());
    }

    /**
     * 返回分页请求相关数据
     * 注：**原数据不能进行操作。。。否则 PageInfo 分页失效**
     * @param listData 需要进行分页的list集合
     * @return
     */
    public static <T> Resp<Map<String, Object>> sendPageData(List<T> listData) {
        PageInfo<T> pageInfo = new PageInfo<>(listData);
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