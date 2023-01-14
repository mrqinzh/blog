package com.mrqinzh.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.resp.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Component
public class DefaultRedirectStrategy implements RedirectStrategy {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void redirect(HttpServletRequest request, HttpServletResponse response, Resp resp, String url) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        sendRedirect(request, response, resp, url);
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, Resp resp, String url) {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        try {
            String jsonResp = objectMapper.writeValueAsString(resp);
            byte[] bytes = jsonResp.getBytes("UTF-8");
            OutputStream stream = response.getOutputStream();
            stream.write(bytes);
            stream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
