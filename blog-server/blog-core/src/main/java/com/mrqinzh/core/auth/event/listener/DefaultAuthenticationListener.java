package com.mrqinzh.core.auth.event.listener;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.token.Token;

public class DefaultAuthenticationListener extends AbstractAuthenticationListener{

    @Override
    protected void doAuthSuccessEvent(AuthSuccessEvent event) {
        System.out.println(this.getClass().getName() + " listener onApplicationEvent !");
        Token source = event.getToken();
        BizAssert.isTrue("token is not authenticated !", source.isAuthenticated());
        // todo 执行业务
    }

    @Override
    protected void doAuthFailureEvent(AuthFailureEvent event) {
        System.out.println(this.getClass().getName() + " listener onApplicationEvent !");
        // todo 执行业务
        AuthException exception = ((AuthFailureEvent) event).getException();
    }
}
