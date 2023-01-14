package com.mrqinzh.core.auth.handler;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.RedirectStrategy;
import com.mrqinzh.core.auth.event.AuthenticationEventPublisher;
import com.mrqinzh.core.auth.security.SecurityContextHolder;
import com.mrqinzh.core.auth.session.SessionManager;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * login处理器
 */
@Component
public class DefaultLoginHandler implements LoginSuccessHandler, LoginFailureHandler {

    @Autowired
    private AuthenticationEventPublisher publisher;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private RedirectStrategy redirectStrategy;

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, AuthenticatedToken token) {
        String sessionId = sessionManager.generateSessionId();
        token.setTokenId(sessionId);
        sessionManager.start(request, response, token);
        SecurityContextHolder.getContext().setToken(token);

        publisher.publishAuthenticationSuccess(token);

        Resp resp = DataResp.ok(sessionId);
        redirectStrategy.redirect(request, response, resp, "/");

    }

    @Override
    public void onLoginFailure(HttpServletRequest request, HttpServletResponse response, AuthException exception) {
        Resp resp = Resp.sendErrorMsg(403, "authentication failure !");
        publisher.publishAuthenticationFailure(exception, null);
        redirectStrategy.redirect(request, response, resp, null);
    }

}
