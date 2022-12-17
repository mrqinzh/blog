package com.mrqinzh.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mrqinzh.core.mapper")
public class SpringMvcConfig {
}
