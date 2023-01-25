package com.mrqinzh.core.auth.filter;

import com.mrqinzh.core.auth.context.AuthenticationContextHolder;
import com.mrqinzh.core.auth.session.SessionManager;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.security.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        String tokenId = sessionManager.getTokenId(request);
        if (StringUtils.isBlank(tokenId)) {
            filterChain.doFilter(request, response);
            return;
        }
        AuthenticatedToken token = sessionManager.getToken(tokenId);
        if (token != null) {
            AuthenticationContextHolder.getContext().setToken(token);
            try {
                filterChain.doFilter(request, response);
            } finally {
                AuthenticationContextHolder.clearContext();
            }
        }

    }
}
