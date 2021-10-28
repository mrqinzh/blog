package com.mrqinzh.blog.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 认证成功处理
 */
@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(DefaultAuthenticationSuccessHandler.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        Object o = auth.getPrincipal();
        UserDetails userDetails = null;
        if (o instanceof UserDetails) {
            userDetails = (UserDetails) o;
        }
        logger.info("登录用户名：{} 认证通过", userDetails.getUsername());

        // Todo 生成 token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(DataResp.ok(result)));

        out.flush();
        out.close();
    }

}
