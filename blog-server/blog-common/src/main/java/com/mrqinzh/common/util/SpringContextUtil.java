package com.mrqinzh.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    public static Class<? extends Object> getBeanType(String beanName) {
        return applicationContext.getType(beanName);
    }

}
