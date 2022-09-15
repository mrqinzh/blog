package com.mrqinzh.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mrqinzh.blog.mapper")
public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }
}
