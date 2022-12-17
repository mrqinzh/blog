package com.mrqinzh.core.auth.handler;

import com.mrqinzh.core.auth.token.Token;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DefaultAuthHandler extends AbstractAuthHandler {

    @Override
    public boolean preAuth(HttpServletRequest request, HttpServletResponse response, Token credentialToken) {
        return true;
    }

}
