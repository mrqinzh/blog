package com.mrqinzh.core.security;

import com.mrqinzh.core.auth.security.SecurityContextHolder;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;

public class SecurityContextUtil {

    public static AuthenticatedToken getAuthenticatedToken() {
        Token token = SecurityContextHolder.getContext().getToken();
        return token != null ? (AuthenticatedToken) token : null;
    }

    public static String getAuthUsername() {
        Token token = SecurityContextHolder.getContext().getToken();
        return token.getUsername();
    }

    public static SecurityUser getPrinciple() {
        Token token = SecurityContextHolder.getContext().getToken();
        return token != null ? (SecurityUser) token.getPrinciple() : null;
    }

}
