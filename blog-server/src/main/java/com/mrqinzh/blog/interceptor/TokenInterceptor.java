package com.mrqinzh.blog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.util.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class.getSimpleName());

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
        String json = objectMapper.writeValueAsString(Resp.error(403, "权限不足"));
        resp.getWriter().write(json);

        logger.error("token验证失败 => " + token);

        return false;
    }


}
