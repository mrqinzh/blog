package com.mrqinzh.core.auth.event;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.event.AbstractAuthenticationEvent;
import com.mrqinzh.core.auth.token.Token;

public class AuthFailureEvent extends AbstractAuthenticationEvent {

    private AuthException exception;

    public AuthFailureEvent(AuthException exception, Token source) {
        super(source);
        BizAssert.notNull(exception, "exception must be not null");
        this.exception = exception;
    }

    public AuthException getException() {
        return exception;
    }

}
