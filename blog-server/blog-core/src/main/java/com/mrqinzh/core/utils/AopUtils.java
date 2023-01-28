package com.mrqinzh.core.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

public class AopUtils {

    public static String getTargetMethodName(JoinPoint jp) {
        return jp.getStaticPart().getSignature().getName(); // 目标方法名
    }

    public static String[] getTargetMethodArgsNames(JoinPoint jp) {
        return ((CodeSignature) jp.getStaticPart().getSignature()).getParameterNames();
    }

    public static Class<?>[] getTargetMethodArgsTypes(JoinPoint jp) {
        return ((CodeSignature) jp.getStaticPart().getSignature()).getParameterTypes();
    }

    public static Class<?> getTargetClassType(JoinPoint jp) {
        return jp.getSignature().getDeclaringType(); // 目标方法名
    }


}
