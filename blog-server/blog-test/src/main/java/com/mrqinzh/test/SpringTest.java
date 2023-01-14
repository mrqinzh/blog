package com.mrqinzh.test;

import com.mrqinzh.bean.SpringTestService;
import com.mrqinzh.bean.TestConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        SpringTestService bean = context.getBean(SpringTestService.class);
        System.out.println(bean);
    }

}
