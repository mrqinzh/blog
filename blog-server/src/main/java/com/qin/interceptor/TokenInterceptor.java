package com.qin.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qin.pojo.Resp;
import com.qin.pojo.User;
import com.qin.util.RedisUtil;
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
        String token = req.getHeader("Authorization");
        System.out.println(token);

        if (redisUtil.hasKey(token)) {
            User user = (User) redisUtil.get(token);
            req.setAttribute("user", user);
            return true;
        }
        resp.setContentType("text/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new Resp<String>("403", "权限不足", token));
        resp.getWriter().write(json);

        return false;
    }


}
