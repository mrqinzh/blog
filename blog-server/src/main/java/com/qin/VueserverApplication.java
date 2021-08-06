package com.qin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 秦志宏
 */
@SpringBootApplication
@MapperScan("com.qin.mapper")
public class VueserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueserverApplication.class, args);
    }

}
