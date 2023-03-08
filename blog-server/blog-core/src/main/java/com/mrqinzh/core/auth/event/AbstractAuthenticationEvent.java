package com.mrqinzh.core.auth.event;

import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.Token;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractAuthenticationEvent extends ApplicationEvent {

    public AbstractAuthenticationEvent(AbstractAuthenticationToken<?> source) {
        super(source);
    }

    public AbstractAuthenticationToken<?> getToken() {
        return (AbstractAuthenticationToken<?>) super.getSource();
    }

}
