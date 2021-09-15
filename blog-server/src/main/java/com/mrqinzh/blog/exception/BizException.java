package com.mrqinzh.blog.exception;

import com.google.common.base.Strings;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.dto.resp.Resp;
import lombok.Data;

/**
 * @author mrqinzh
 */
@Data
public class BizException extends RuntimeException {

    private AppStatus status;

    private String msg;

    public BizException(AppStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BizException(AppStatus status) {
        this.status = status;
    }

    public Resp sendExceptionMsg() {
        return Strings.isNullOrEmpty(this.msg) ? Resp.sendMsg(status) : Resp.sendMsg(status, msg);
    }

}
