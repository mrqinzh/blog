package com.mrqinzh.core.access;

import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.RedirectStrategy;
import com.mrqinzh.core.auth.context.AuthenticationContextHolder;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 99)
@Component
public class AccessControlFilter extends OncePerRequestFilter {

    @Autowired
    private RedirectStrategy redirectStrategy;
    @Autowired
    private PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 是否过滤请求
        if (!withoutAuthApi(request)) {
            Token token = AuthenticationContextHolder.getContext().getToken();
            if (token == null || !token.isAuthenticated()) {
                redirectStrategy.redirect(request, response, new Resp(AppStatus.SESSION_EXPIRE));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean withoutAuthApi(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String allowURL : SecurityProperties.accessApisWithoutAuth) {
            if (pathMatcher.match(allowURL, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
