package com.mrqinzh.blog.exception;

import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.dto.resp.Resp;
import lombok.Data;

/**
 * @author mrqinzh
 */
@Data
public class BizException extends RuntimeException {

    private AppStatus status;

    public BizException(AppStatus status) {
        this.status = status;
    }

    public Resp sendExceptionMsg() {
        return Resp.sendMsg(status);
    }

}
