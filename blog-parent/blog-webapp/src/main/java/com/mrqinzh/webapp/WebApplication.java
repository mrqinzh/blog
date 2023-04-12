package com.mrqinzh.webapp;

import com.mrqinzh.framework.annotation.BlogServerApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;

@BlogServerApplication
@EnableDubbo
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
