package com.mrqinzh.core.auth.filter;

import com.mrqinzh.core.auth.security.SecurityContextHolder;
import com.mrqinzh.core.auth.session.SessionManager;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.security.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 50)
@Component
public class SecurityContextFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String sessionId = sessionManager.getSessionId(request);
        if (StringUtils.isNotBlank(sessionId)) {
            AuthenticatedToken token = sessionManager.getToken(sessionId);
            if (token != null) {
                SecurityContextHolder.getContext().setToken(token);
                filterChain.doFilter(request, response);
                return;
            }
        }
        // todo token为空处理
        filterChain.doFilter(request, response);
    }

}
