package com.mrqinzh.blog.model.enums;

public enum AppStatus {
    /**
     * 请求成功
     */
    SUCCESS(0, "success"),
    /**
     * 内部错误
     */
    INTERNAL_SERVER_ERROR(1, "internal server error"),
    /**
     * 重复提交错误
     */
    RESUBMIT_ERROR(2, "resubmit error"),
    /**
     * 未鉴权
     */
    UNAUTHORIZED(401, "unauthorized"),

    /**
     * 鉴权失败
     */
    AUTH_FAILED(403, "auth failed"),

    /**
     *登录失败，账号密码不正确
     */
    AUTH_LOGIN_BAD_CREDENTIALS(101, "登录失败，账号密码不正确"),

    /**
     *登录失败，未知原因
     */
    AUTH_LOGIN_FAIL_UNKNOWN(102, "登录失败"),

    /**
     *登录失败，无此用户
     */
    AUTH_LOGIN_FAIL_NO_USER(103, "登录失败"),

    /**
     * 下载失败
     */
    DOWNLOAD_FAILED(1001, "download file failed"),

    /**
     * 新增失败
     */
    INSERT_FAILED(1002, "insert failed"),

    /**
     * 请求错误
     */
    BAD_REQUEST(2001, "request error"),

    /**
     * 缺少必要参数
     */
    LACK_NECESSARY_PARAMETER(2002, "lack necessary parameter"),

    /**
     * 参数校验错误
     */
    PARAMETER_ERROR(2003, "parameter error"),

    /**
     * 无对应实体
     */
    NO_ENTITY(2004, "no such entity is existed"),

    /**
     * jsCode error
     */
    JS_CODE_ERROR(3001, "js code error"),
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