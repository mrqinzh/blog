package com.mrqinzh.blog.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.model.resp.Resp;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 退出成功后执行的逻辑
 */
@Component
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(Resp.success()));
        out.flush();
        out.close();
    }

}
