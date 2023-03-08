package com.mrqinzh.core.auth.event;

import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;

public class PreAuthEvent extends AbstractAuthenticationEvent {

    public PreAuthEvent(AbstractAuthenticationToken<?> source) {
        super(source);
    }

}
