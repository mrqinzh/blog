package com.mrqinzh.blog.exception;

import com.mrqinzh.blog.util.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author mrqinzh
 * @Description 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp blogExceptionHandler(MyException exception) {
        logger.error(exception.getMessage());
        return exception.toResp();
    }

}
