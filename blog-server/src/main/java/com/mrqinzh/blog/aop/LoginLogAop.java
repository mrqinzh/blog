package com.mrqinzh.blog.aop;

import com.mrqinzh.blog.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginLogAop {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogAop.class);

    @Pointcut("execution(* com.mrqinzh.blog.controller.UserController.login(..))")
    public void loginLog() {

    }

    @Around("loginLog()")
    public Object loginAround(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = WebUtil.getRequest();
        String ip = WebUtil.getClientIp(request);

        logger.info("login ==> start ==> 请求ip地址：{}", ip);
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        logger.info("login ==> end ==> login() 耗时：{}ms", System.currentTimeMillis() - beginTime);

        return result;
    }

}
