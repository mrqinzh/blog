package com.mrqinzh.blog.model.enums;

public enum AppStatus {
    /**
     * 请求成功
     */
    SUCCESS(200, "success"),

    INSERT_SUCCESS(20001, "添加成功"),
    UPDATE_SUCCESS(20001, "更新成功"),
    DELETE_SUCCESS(20001, "删除成功"),
    EMAIL_SEND_SUCCESS(20001, "邮件发送成功"),

    BAD_REQUEST(400, "参数校验失败"),

    USERNAME_PASSWORD_ERROR(4003 , "账号或密码错误！"),

    /**
     * 鉴权失败
     */
    AUTH_FAILED(403, "对不起，你的权限不足，请充值。。.>_>"),

    /**
     * 内部错误
     */
    UNKNOWN_SERVER_ERROR(500, "不好意思，服务端出现了未知的错误，通知管理员吧。。。-_-"),

    INTERNET_ERROR(50000, "网络好像出现了问题，稍后再试试吧。。。"),
    ;

    private int code;
    private String msg;

    AppStatus(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}