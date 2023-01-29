package com.mrqinzh.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static List<String> swaggerUrls = new ArrayList<>();
    public static String[] swaggerApis = {
            "/swagger-ui.html", "/v2/api-docs", "/swagger-resources/configuration/ui",
            "/swagger-resources", "/swagger-resources/configuration/security", "/webjars/**"
    };
    static {
      swaggerUrls.addAll(Arrays.asList(swaggerApis));
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mrqinzh.blog.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("个人博客网站")
                        .description("对项目代码进行了重构")
                        .version("2.0")
                        .contact(new Contact("我的网站", "http://mrqinzh.com", "1552589784@qq.com"))
                        .license("The Apache License2.0")
                        .licenseUrl("https://gitee.com/mrqinzh/blog")
                        .build()
                );
    }
}
