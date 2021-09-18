package com.mrqinzh.blog.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 表示接受任意域名的请求,也可以指定域名
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));

        // 该字段可选，是个布尔值，表示是否可以携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 允许跨域的请求方法
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        // 请求头信息
        response.setHeader("Access-Control-Allow-Headers", "*");

        // 判断前端简单请求 非简单请求（请求为json类型为非简单请求）
        // 遇简放行
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        return true;
    }

}
