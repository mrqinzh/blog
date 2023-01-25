package com.mrqinzh.core.auth.token;

import com.mrqinzh.core.auth.LoginType;

public interface Token {

    String getUsername(); // 登录账号

    LoginType getLoginType();

    Object getPrincipal();

    boolean isAuthenticated();

    void setAuthenticated(boolean authenticated);

}
