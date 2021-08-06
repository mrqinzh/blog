package com.mrqinzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 秦志宏
 */
@SpringBootApplication
@MapperScan("com.mrqinzh.mapper")
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }

}
