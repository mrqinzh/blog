package com.mrqinzh.blog.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(DefaultAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        Object o = auth.getPrincipal();
        UserDetails userDetails = null;
        if (o instanceof UserDetails) {
            userDetails = (UserDetails) o;
        }
        logger.info("登录用户名：", userDetails.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtUtil.getToken());

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(map));

        out.flush();
        out.close();
    }

}
