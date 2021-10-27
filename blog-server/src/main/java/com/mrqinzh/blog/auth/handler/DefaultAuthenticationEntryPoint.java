package com.mrqinzh.blog.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.model.enums.AppStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(AppStatus.AUTH_FAILED));
        out.flush();
        out.close();
    }

}
