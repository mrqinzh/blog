package com.mrqinzh.core.access;

import com.mrqinzh.core.auth.context.AuthenticationContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthorityAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityAspect.class);
    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @Pointcut("@annotation(com.mrqinzh.core.access.AccessPermission)")
    public void authorityPoint(){}

    @Before("authorityPoint()")
    public void checkAuthority(JoinPoint joinPoint) throws Exception {
        String targetMethodName = joinPoint.getStaticPart().getSignature().getName(); // 目标方法名
        Class<?> targetMethodClassType = joinPoint.getStaticPart().getSignature().getDeclaringType(); // 目标方法类
        Class<?>[] types = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterTypes(); // 目标方法的参数类型

        Method declaredMethod = targetMethodClassType.getDeclaredMethod(targetMethodName, types);
        AccessPermission annotation = declaredMethod.getAnnotation(AccessPermission.class);
        if (annotation == null || annotation.value() == null) {
            return;
        }
        List<RoleType> configAttributes = Arrays.asList(annotation.value());

        accessDecisionManager.decide(AuthenticationContextUtils.getAuthenticatedToken(), configAttributes);
    }

}
