package com.mrqinzh.blog.util;

import lombok.Data;

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

    public static <E> Resp<E> ok(E data){
        return new Resp<>(200, true, data);
    }

    public static <E> Resp<E> error(Integer code, E data){
        return new Resp<>(code, false, data);
    }

    public Resp(Integer code, boolean success, E data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public Resp() {

    }

}