package com.mrqinzh.model.entity;

/**
 * 统一响应的格式
 * @param <E>
 */
public class Resp<E> {

    private String code;
    private String message;
    private E body;

    public static <E> Resp<E> success(E body){
        return new Resp<E>("200","success",body);
    }

    public static <E> Resp<E> error(String code, String message){
        return new Resp<E>(code,message,null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getBody() {
        return body;
    }

    public void setBody(E body) {
        this.body = body;
    }

    public Resp(String code, String message, E body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Resp() {
    }

    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}
