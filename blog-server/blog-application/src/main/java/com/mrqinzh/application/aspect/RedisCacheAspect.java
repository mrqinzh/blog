package com.mrqinzh.application.aspect;

import com.mrqinzh.common.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisCacheAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.mrqinzh.application.aspect.RedisCache)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        String targetMethod = jp.getSignature().getName();
        return jp.proceed();
    }

}
