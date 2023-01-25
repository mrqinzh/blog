package com.mrqinzh.core.exception;

import cn.hutool.core.util.StrUtil;
import com.mrqinzh.core.access.AccessDenyException;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author mrqinzh
 * @Description 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务发生的异常
     * @param e
     */
    @ExceptionHandler(value = BizException.class)
    public Resp bizExceptionHandler(BizException e) {
        e.printStackTrace();
        return e.sendExceptionMsg();
    }

    @ExceptionHandler(value = AccessDenyException.class)
    public Resp accessDenyException(AccessDenyException e) {
        e.printStackTrace();
        return new Resp(AppStatus.NO_PERMISSION);
    }

    /**
     * 处理参数 valid 校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Resp handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrorList) {
            stringBuilder.append(error.getField()).append("(").append(error.getDefaultMessage()).append("); ");
        }
        String errMsg = StrUtil.removeSuffix(stringBuilder.toString(), ";");
        e.printStackTrace();
        return Resp.sendMsg(AppStatus.BAD_REQUEST, errMsg);
    }

    /**
     * 处理空指针异常
     * @param e
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Resp exceptionHandler(NullPointerException e) {
        e.printStackTrace();
        return Resp.sendMsg(AppStatus.NULL_PRINTER_EXCEPTION);
    }

    /**
     * 处理其他异常情况
     */
    @ExceptionHandler(value = Exception.class)
    public Resp exceptionHandler(Exception e) {
        e.printStackTrace();
        return Resp.sendMsg(AppStatus.UNKNOWN_SERVER_ERROR);
    }

}
