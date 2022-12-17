package com.mrqinzh.core.auth.handler;

import com.mrqinzh.core.auth.token.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证前后置处理
 */
public abstract class AbstractAuthHandler {

    public boolean preAuth(HttpServletRequest request, HttpServletResponse response, Token credentialToken) {
        return true;
    }

    public boolean postAuth(HttpServletRequest request, HttpServletResponse response, Token authenticatedToken) {
        return true;
    }
}
