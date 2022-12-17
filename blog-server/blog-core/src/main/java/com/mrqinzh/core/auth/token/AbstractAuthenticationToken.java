package com.mrqinzh.core.auth.token;

/**
 * 认证完成的token
 */
public abstract class AbstractAuthenticationToken<T> implements Token {

    private boolean authenticated;

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public abstract T getPrinciple();

}
