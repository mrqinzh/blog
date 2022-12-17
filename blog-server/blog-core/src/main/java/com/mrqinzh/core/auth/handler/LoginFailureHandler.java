package com.mrqinzh.core.auth.handler;

import com.mrqinzh.common.exception.AuthException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginFailureHandler {

    void onLoginFailure(HttpServletRequest request, HttpServletResponse response, AuthException exception);

}
