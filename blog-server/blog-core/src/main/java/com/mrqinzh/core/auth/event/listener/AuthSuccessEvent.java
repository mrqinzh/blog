package com.mrqinzh.core.auth.event.listener;

import com.mrqinzh.core.auth.event.AbstractAuthenticationEvent;
import com.mrqinzh.core.auth.token.Token;

public class AuthSuccessEvent extends AbstractAuthenticationEvent {

    public AuthSuccessEvent(Token source) {
        super(source);
    }

}
