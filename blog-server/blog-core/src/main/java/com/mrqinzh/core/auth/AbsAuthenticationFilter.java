package com.mrqinzh.core.auth;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.core.auth.handler.AbstractAuthHandler;
import com.mrqinzh.core.auth.handler.DefaultLoginHandler;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
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
import java.util.List;

@Component
public abstract class AbsAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private static final int DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER;

    @Autowired
    private DefaultLoginHandler defaultLoginHandler;
    @Autowired
    private AuthenticationProcessor processor;
    @Autowired
    private List<AbstractAuthHandler> authHandlers;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!requiredAuthenticationRequest(request)) {
            chain.doFilter(request, response);
            return;
        }
        boolean flag = executeLogin(request, response);
        if (flag) {
            chain.doFilter(request, response);
        }
    }

    protected boolean executeLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            AbstractAuthenticationToken<?> credential = createCredentialToken(request);

            for (AbstractAuthHandler handler : authHandlers) {
                if (!handler.preAuth(request, response, credential)) {
                    return false;
                }
            }

            AuthenticatedToken token = processor.auth(credential);

            for (AbstractAuthHandler handler : authHandlers) {
                if (!handler.postAuth(request, response, token)) {
                    return false;
                }
            }

            defaultLoginHandler.onLoginSuccess(request, response, token);
            return false;
        } catch (AuthException e) {
            defaultLoginHandler.onLoginFailure(request, response, e);
            return false;
        }
    }

    protected abstract boolean requiredAuthenticationRequest(HttpServletRequest request);

    protected abstract AbstractAuthenticationToken<?> createCredentialToken(HttpServletRequest request);

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
