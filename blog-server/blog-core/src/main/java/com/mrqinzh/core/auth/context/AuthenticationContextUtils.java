package com.mrqinzh.core.auth.context;

import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityUser;

public class AuthenticationContextUtils {

    public static AuthenticatedToken getAuthenticatedToken() {
        Token token = AuthenticationContextHolder.getContext().getToken();
        return token != null ? (AuthenticatedToken) token : null;
    }

    public static String getAuthUsername() {
        Token token = AuthenticationContextHolder.getContext().getToken();
        return token.getUsername();
    }

    public static SecurityUser getSecurityUser() {
        Token token = AuthenticationContextHolder.getContext().getToken();
        return token != null ? (SecurityUser) token.getPrincipal() : null;
    }

}
