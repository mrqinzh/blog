package com.mrqinzh.util;

/**
 * 统一返回数据类型
 * @param <E> 传入数据类型
 */
public class Resp<E> {

    private String code;
    private boolean success;
    private E data;

    public static <E> Resp<E> ok(E data){
        return new Resp<>("200",true, data);
    }

    public static <E> Resp<E> error(String code, E data){
        return new Resp<>(code, false, data);
    }

    public Resp(String code, boolean success, E body) {
        this.code = code;
        this.success = success;
        this.data = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Resp() {

    }

}