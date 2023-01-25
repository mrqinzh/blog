package com.mrqinzh.core.auth;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.core.auth.handler.AbstractPrePostAuthHandler;
import com.mrqinzh.core.auth.handler.DefaultLoginHandler;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public abstract class AbsAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private DefaultLoginHandler defaultLoginHandler;
    @Autowired
    private AuthenticationProcessor processor;
    @Autowired
    private List<AbstractPrePostAuthHandler> prePostAuthHandlers;

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
        AbstractAuthenticationToken<?> credential = null;
        try {
            credential = createCredentialToken(request);

            for (AbstractPrePostAuthHandler handler : prePostAuthHandlers) {
                if (!handler.preAuth(request, response, credential)) {
                    return false;
                }
            }

            AuthenticatedToken token = processor.auth(credential);

            for (AbstractPrePostAuthHandler handler : prePostAuthHandlers) {
                if (!handler.postAuth(request, response, token)) {
                    return false;
                }
            }

            defaultLoginHandler.onLoginSuccess(request, response, token);
        } catch (AuthException e) {
            defaultLoginHandler.onLoginFailure(request, response, credential, e);
        }
        return false;
    }

    protected abstract boolean requiredAuthenticationRequest(HttpServletRequest request);

    protected abstract AbstractAuthenticationToken<?> createCredentialToken(HttpServletRequest request);

}
