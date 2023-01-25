package com.mrqinzh.core.security;

import com.mrqinzh.core.config.SwaggerConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SecurityProperties {

    public static final int DEFAULT_FILTER_ORDER = 1;
    public static final int DEFAULT_EXPIRE_TIME_SECONDS = 30 * 60;

    public static final String TOKEN_CACHE_PREFIX = "authenticated:";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String SSO_URL = "/sso";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "userPwd";
    public static final String COOKIE_NAME = "token";

    public static final Set<String> accessApisWithoutAuth = new HashSet<>();
    public static final String[] systemWhiteApis = {
            "/", LOGIN_URL, "/**"
    };
    static {
        accessApisWithoutAuth.addAll(Arrays.asList(systemWhiteApis));
        accessApisWithoutAuth.addAll(SwaggerConfig.swaggerUrls);
    }

}
