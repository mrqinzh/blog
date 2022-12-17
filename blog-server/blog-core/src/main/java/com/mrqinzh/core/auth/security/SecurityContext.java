package com.mrqinzh.core.auth.security;

import com.mrqinzh.core.auth.token.Token;

public class SecurityContext {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
