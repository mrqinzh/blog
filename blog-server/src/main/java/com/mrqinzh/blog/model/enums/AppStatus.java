package com.mrqinzh.blog.model.enums;

public enum AppStatus {

    /**
     * 请求成功
     */
    SUCCESS(200, true, "success"),

    INSERT_SUCCESS(20001, true, "添加成功"),
    UPDATE_SUCCESS(20001, true,"更新成功"),
    DELETE_SUCCESS(20001, true,"删除成功"),
    EMAIL_SEND_SUCCESS(20001, true,"邮件发送成功"),


    BAD_REQUEST(400, false, "参数校验失败"),
    USERNAME_PASSWORD_ERROR(4003 , false, "账号或密码错误！"),
    AUTH_FAILED(403, false, "对不起，你的权限不足，请充值。。.>_>"),
    /**
     * 内部错误
     */
    UNKNOWN_SERVER_ERROR(500, false, "不好意思，服务端出现了未知的错误，赶快通知管理员修改BUG吧。。。-_-"),
    SERVICE_ERROR(50000, false, "业务出现异常了，赶紧去通知管理员吧。。。-_-"),

    IMAGE_UPLOAD_ERROR(50001, false, "图片上传失败了"),
    INSERT_FAILED(50001, true, "添加失败"),
    UPDATE_FAILED(50001, true,"更新失败"),
    DELETE_FAILED(50001, true,"删除失败"),

    INTERNET_ERROR(50002, false, "网络好像出现了问题，稍后再试试吧。。。"),
    ;

    private int code;
    private Boolean success;
    private String msg;

    AppStatus(int code, Boolean success, String msg) {
        this.msg = msg;
        this.success = success;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }
}