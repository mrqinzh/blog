package com.mrqinzh.core.access;

import com.mrqinzh.core.auth.security.SecurityContextHolder;
import com.mrqinzh.core.security.SecurityContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthorityAspect {

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @Pointcut("@annotation(com.mrqinzh.core.access.AccessPermission)")
    public void authorityAspect(){}

    @Before("authorityAspect()")
    public void checkAuthority(JoinPoint joinPoint) throws Throwable {
        String targetMethodName = joinPoint.getStaticPart().getSignature().getName();
        Class<?>[] types = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterTypes();
        Class<?> declaringType = joinPoint.getStaticPart().getSignature().getDeclaringType();

        Method declaredMethod = declaringType.getDeclaredMethod(targetMethodName, types);
        AccessPermission annotation = declaredMethod.getAnnotation(AccessPermission.class);
        if (annotation == null || annotation.value() == null) {
            return;
        }
        List<RoleType> configAttributes = Arrays.asList(annotation.value());
        try {
            accessDecisionManager.decide(SecurityContextUtil.getAuthenticatedToken(), configAttributes);
        } catch (AccessDenyException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
