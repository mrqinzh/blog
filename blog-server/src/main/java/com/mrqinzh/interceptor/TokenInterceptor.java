package com.mrqinzh.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.model.entity.User;
import com.mrqinzh.util.RedisUtil;
import com.mrqinzh.util.Resp;
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

            // 将信息存入request对象
            req.setAttribute("user", user);
            return true;
        }
        resp.setContentType("text/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Resp.error("403", "权限不足"));
        resp.getWriter().write(json);

        return false;
    }


}
