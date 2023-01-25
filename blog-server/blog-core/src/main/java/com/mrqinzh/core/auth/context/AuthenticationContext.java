package com.mrqinzh.core.auth.context;

import com.mrqinzh.core.auth.token.Token;

public class AuthenticationContext {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
