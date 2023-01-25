package com.mrqinzh.core.auth.event;

import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.token.Token;

public class AuthSuccessEvent extends AbstractAuthenticationEvent {

    public AuthSuccessEvent(Token source) {
        super(source);
        BizAssert.isTrue(source != null && source.isAuthenticated(), "token must be authenticated when auth success !");
    }

}
