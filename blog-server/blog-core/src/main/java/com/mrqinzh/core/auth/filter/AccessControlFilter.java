package com.mrqinzh.core.auth.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.RedirectStrategy;
import com.mrqinzh.core.auth.security.SecurityContextHolder;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class AccessControlFilter extends OncePerRequestFilter implements Ordered {

    private static final int DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER + 99;
    @Autowired
    private RedirectStrategy redirectStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (allowed(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        Token token = SecurityContextHolder.getContext().getToken();
        if (token == null || !token.isAuthenticated()) {
            Resp resp = new Resp(AppStatus.AUTH_FAILED);
            redirectStrategy.redirect(request, response, resp, null);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean allowed(HttpServletRequest request) {
        return true;
        // todo 校验权限
//        String requestURI = request.getRequestURI();
//        for (String allowURL : SecurityProperties.allowUrlsUnLogin) {
//            if (requestURI.matches(allowURL)) {
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
