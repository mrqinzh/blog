package com.mrqinzh.blog.model.enums;

/**
 * @author mrqinzh
 * @Description 返回错误信息及状态码
 */
public enum ExceptionEnums implements ExceptionInfo {

    TOKEN_EXPIRED(4001, "您的token已过期"),

    NOT_LOGIN(4002, "请先登录再进行访问"),

    NO_AUTHORITY(403, "对不起，你的权限不足，请充值。。.>_>"),

    TOKEN_INVALID(4004, "无效的token"),

    USERNAME_PASSWORD_ERROR(4009, "用户名或密码错误"),

    UNKNOWN_ERROR(500, "不好意思，服务端出现了未知的错误，通知管理员吧。。。-_-"),

    IMAGE_UPLOAD_ERROR(5001, "图片上传失败");

    private Integer code;
    private String msg;

    ExceptionEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
