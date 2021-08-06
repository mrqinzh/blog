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

        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/file/**")
                .addPathPatterns("/article/add", "/article/myblog/**")
                .addPathPatterns("/comment/add", "/comment/addMsg");
    }
}
