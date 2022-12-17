package com.mrqinzh.core.auth.token;

import com.mrqinzh.core.auth.LoginType;

public class UsernamePasswordToken extends AbstractAuthenticationToken<String> {

    private String username;
    private String password;
    private LoginType loginType;

    public UsernamePasswordToken(String username, boolean authenticated) {
        this.username = username;
        super.setAuthenticated(authenticated);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    @Override
    public LoginType getLoginType() {
        return loginType;
    }

    @Override
    public String getPrinciple() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

}
