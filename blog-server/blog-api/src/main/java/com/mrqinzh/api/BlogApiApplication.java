package com.mrqinzh.api;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mrqinzh")
@MapperScan("com.mrqinzh.server.mapper")
public class BlogApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(BlogApiApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(BlogApiApplication.class, args);
            logger.info("============== BlogApiApplication start success ==============");
        } catch (Exception e) {
            logger.error("============== BlogApiApplication start failed ==============", e);
            throw e;
        }
    }
}
