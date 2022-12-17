package com.mrqinzh.core.auth.token;

import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.auth.security.Principle;

public interface Token {

    String getUsername();

    LoginType getLoginType();

    Object getPrinciple();

    boolean isAuthenticated();

    void setAuthenticated(boolean authenticated);

}
