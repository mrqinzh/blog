package com.mrqinzh.core.auth.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

public abstract class AbstractAuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Async
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthSuccessEvent) {
            doAuthSuccessEvent((AuthSuccessEvent) event);
        } else doAuthFailureEvent((AuthFailureEvent) event);
    }

    protected abstract void doAuthSuccessEvent(AuthSuccessEvent event);

    protected abstract void doAuthFailureEvent(AuthFailureEvent event);

}
