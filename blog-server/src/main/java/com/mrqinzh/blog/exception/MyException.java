package com.mrqinzh.blog.exception;

import com.mrqinzh.blog.model.enums.ExceptionEnums;
import com.mrqinzh.blog.model.enums.ExceptionInfo;
import com.mrqinzh.blog.util.Resp;

/**
 * @author mrqinzh
 */
public class MyException extends RuntimeException {

    private final ExceptionInfo exceptionInfo;

    public MyException(ExceptionEnums exceptionEnums) {
        this.exceptionInfo = exceptionEnums;
    }

    public Resp toResp() {
        return Resp.fromErrorInfo(exceptionInfo);
    }

}
