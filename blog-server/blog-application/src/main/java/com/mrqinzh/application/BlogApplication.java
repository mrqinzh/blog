package com.mrqinzh.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.mrqinzh", exclude = { DataSourceAutoConfiguration.class })
public class BlogApplication {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(BlogApplication.class, args);
            logger.info("============== BlogApiApplication start success ==============");
        } catch (Exception e) {
            logger.error("============== BlogApiApplication start failed ==============", e);
            throw e;
        }
    }
}
