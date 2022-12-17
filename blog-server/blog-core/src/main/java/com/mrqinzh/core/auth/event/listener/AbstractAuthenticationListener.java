package com.mrqinzh.core.auth.event.listener;

import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.event.AbstractAuthenticationEvent;
import com.mrqinzh.core.auth.token.Token;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractAuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Async
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthSuccessEvent) {
            Token token = event.getToken();
            BizAssert.isTrue(token.isAuthenticated(), "token must be authenticated when auth success !");
            doAuthSuccessEvent((AuthSuccessEvent) event);
        } else doAuthFailureEvent((AuthFailureEvent) event);
    }

    protected abstract void doAuthSuccessEvent(AuthSuccessEvent event);

    protected abstract void doAuthFailureEvent(AuthFailureEvent event);

}
