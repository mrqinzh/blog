package com.mrqinzh.core.auth.event;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.event.listener.AuthFailureEvent;
import com.mrqinzh.core.auth.event.listener.AuthSuccessEvent;
import com.mrqinzh.core.auth.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationEventPublisher implements AuthenticationEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishAuthenticationSuccess(Token token) {
        BizAssert.notNull(token, "token can not be null when auth success");
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(new AuthSuccessEvent(token));
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthException exception, Token token) {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(new AuthFailureEvent(exception, token));
        }
    }
}
