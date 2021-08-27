package com.mrqinzh.blog.exception;

import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.enums.ExceptionEnums;
import com.mrqinzh.blog.model.enums.ExceptionInfo;
import com.mrqinzh.blog.model.dto.resp.Resp;

/**
 * @author mrqinzh
 */
public class BizException extends RuntimeException {

    private final ExceptionInfo exceptionInfo;

    public BizException(ExceptionEnums exceptionEnums) {
        this.exceptionInfo = exceptionEnums;
    }

    public Resp toResp() {
        return Resp.sendMsg(AppStatus.UNKNOWN_SERVER_ERROR);
    }

}
