package com.mrqinzh.blog.exception;

import cn.hutool.core.util.StrUtil;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.model.resp.Resp;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mrqinzh
 * @Description 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务发生的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Resp bizExceptionHandler(BizException e) {
        e.printStackTrace();
        return e.sendExceptionMsg();
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
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Resp exceptionHandler(NullPointerException e) {
        e.printStackTrace();
        return Resp.sendMsg(AppStatus.NULL_PRINTER_EXCEPTION);
    }

    /**
     * 处理其他异常情况
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Resp exceptionHandler(Exception e) {
        e.printStackTrace();
        return Resp.sendMsg(AppStatus.UNKNOWN_SERVER_ERROR);
    }

}
