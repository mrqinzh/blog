package com.mrqinzh.core.auth.event;

import com.mrqinzh.core.auth.token.Token;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractAuthenticationEvent extends ApplicationEvent {

    public AbstractAuthenticationEvent(Token source) {
        super(source);
    }

    public Token getToken() {
        return (Token) super.getSource();
    }

}
