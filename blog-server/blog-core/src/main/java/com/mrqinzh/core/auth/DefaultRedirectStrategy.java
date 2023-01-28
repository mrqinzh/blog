package com.mrqinzh.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DefaultRedirectStrategy implements RedirectStrategy {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void redirect(HttpServletRequest request, HttpServletResponse response, Resp resp) {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        try {
            String jsonResp = objectMapper.writeValueAsString(resp);
            byte[] bytes = jsonResp.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = response.getOutputStream();
            stream.write(bytes);
            stream.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
