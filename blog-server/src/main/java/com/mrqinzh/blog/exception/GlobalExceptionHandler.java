package com.mrqinzh.blog.exception;

import com.mrqinzh.blog.model.enums.ExceptionEnums;
import com.mrqinzh.blog.model.dto.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mrqinzh
 * @Description 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务发生的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Resp bizExceptionHandler(BizException e) {
        e.printStackTrace();
        return e.toResp();
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
        return Resp.sendExceptionInfo(ExceptionEnums.UNKNOWN_ERROR);
    }

    /**
     * 处理其他异常情况
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Resp exceptionHandler(Exception e) {
        e.printStackTrace();
        return Resp.sendExceptionInfo(ExceptionEnums.UNKNOWN_ERROR);
    }

}
