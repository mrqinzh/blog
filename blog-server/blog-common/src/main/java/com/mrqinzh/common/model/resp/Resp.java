package com.mrqinzh.common.model.resp;

import com.mrqinzh.common.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回数据类型
 */
@ApiModel(value = "返回消息类")
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

    public static Resp success() {
        return Resp.sendMsg(AppStatus.SUCCESS);
    }

    public static Resp sendMsg(AppStatus status, String msg) {
        Resp resp = new Resp();
        resp.setCode(status.getCode());
        resp.setSuccess(status.getSuccess());

        resp.setMsg(msg);

        return resp;
    }

    public static Resp sendMsg(AppStatus status) {
        Resp resp = new Resp();
        resp.setCode(status.getCode());
        resp.setSuccess(status.getSuccess());
        resp.setMsg(status.getMsg());
        return resp;
    }

}