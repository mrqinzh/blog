package com.mrqinzh.common.model.resp;

import cn.hutool.json.JSONUtil;
import com.mrqinzh.common.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 统一返回数据类型
 */
@ApiModel(value = "返回消息类")
@Data
@NoArgsConstructor
public class Resp implements Serializable {

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

    public static Resp sendErrorMsg(Integer code, String msg) {
        Resp resp = new Resp();
        resp.setCode(code);
        resp.setSuccess(false);
        resp.setMsg(msg);
        return resp;
    }

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }

}