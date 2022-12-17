package com.mrqinzh.core.security;

import com.mrqinzh.core.config.SwaggerConfig;

import java.util.HashSet;
import java.util.Set;

public class SecurityProperties {

    public static final int DEFAULT_FILTER_ORDER = 1;

    public static final String LOGIN_URL = "/login";
    public static final String SSO_URL = "/sso";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "userPwd";

    public static final String COOKIE_NAME = "token";

    public static final Set<String> allowUrlsUnLogin = new HashSet<>();
    static {
        allowUrlsUnLogin.add("/");
        allowUrlsUnLogin.add("/**");
        allowUrlsUnLogin.addAll(SwaggerConfig.swaggerUrls);
    }

}
