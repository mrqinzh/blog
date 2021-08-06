package com.mrqinzh.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.entity.Resp;
import com.mrqinzh.entity.User;
import com.mrqinzh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        String token = req.getHeader("token");
        System.out.println(token);

        if (redisUtil.hasKey(token)) {
            User user = (User) redisUtil.get(token);

            req.setAttribute("user", user); // 将信息存入request对象
            return true;
        }
        resp.setContentType("text/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new Resp<>("403", "权限不足", token));
        resp.getWriter().write(json);

        return false;
    }


}
