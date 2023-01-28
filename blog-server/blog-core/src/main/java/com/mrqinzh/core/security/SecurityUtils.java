package com.mrqinzh.core.security;

import com.mrqinzh.common.util.SpringContextUtil;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    /**
     * 是否不需要登录请求
     * @return true 是；false 否
     */
    public static boolean withoutAuthApi(HttpServletRequest request) {
        PathMatcher pathMatcher = SpringContextUtil.getBean(PathMatcher.class);
        String requestURI = request.getRequestURI();
        for (String allowURL : SecurityProperties.accessApisWithoutAuth) {
            if (pathMatcher.match(allowURL, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
