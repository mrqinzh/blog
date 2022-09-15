package com.mrqinzh.server.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 */
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(Resp.sendMsg(AppStatus.USERNAME_PASSWORD_ERROR)));
    }

}
