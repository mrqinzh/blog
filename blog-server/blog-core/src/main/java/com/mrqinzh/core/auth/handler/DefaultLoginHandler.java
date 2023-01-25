package com.mrqinzh.core.auth.handler;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.RedirectStrategy;
import com.mrqinzh.core.auth.event.AuthFailureEvent;
import com.mrqinzh.core.auth.event.AuthSuccessEvent;
import com.mrqinzh.core.auth.session.SessionManager;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * login处理器
 */
@Component
public class DefaultLoginHandler implements LoginSuccessHandler, LoginFailureHandler {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private RedirectStrategy redirectStrategy;

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, AuthenticatedToken token) {
        publisher.publishEvent(new AuthSuccessEvent(token));
        sessionManager.start(request, response, token);
        redirectStrategy.redirect(request, response, DataResp.ok(token.getTokenId()));
    }

    @Override
    public void onLoginFailure(HttpServletRequest request,
                               HttpServletResponse response,
                               AbstractAuthenticationToken<?> credential,
                               AuthException exception) {
        publisher.publishEvent(new AuthFailureEvent(exception, credential));
        redirectStrategy.redirect(request, response, new Resp(AppStatus.LOGIN_FAILURE));
    }

}
