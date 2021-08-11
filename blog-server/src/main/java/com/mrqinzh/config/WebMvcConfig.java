package com.mrqinzh.config;

import com.mrqinzh.interceptor.CorsInterceptor;
import com.mrqinzh.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/files/**").addResourceLocations("file:/files/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 跨域拦截器
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");

        // token 验证拦截器
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/file/**")
//                .addPathPatterns("/article/add")
                .addPathPatterns("/comment/add")
                .addPathPatterns("/logout");
    }
}
