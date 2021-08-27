package com.mrqinzh.blog.model.dto.resp;

import com.mrqinzh.blog.model.enums.AppStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回数据类型
 */
@Data
@NoArgsConstructor
public class Resp {

    private Integer code;
    private Boolean success;
    private String msg;

    public Resp(AppStatus status) {
        this.code = status.getCode();
        this.success = status.getSuccess();
        this.msg = status.getMsg();
    }

    public static Resp sendMsg(AppStatus status) {
        Resp resp = new Resp();
        resp.setCode(status.getCode());
        resp.setSuccess(status.getSuccess());
        resp.setMsg(status.getMsg());
        return resp;
    }

}