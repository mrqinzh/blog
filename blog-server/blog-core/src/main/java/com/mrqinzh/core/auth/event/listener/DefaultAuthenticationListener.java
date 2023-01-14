package com.mrqinzh.core.auth.event.listener;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.UserLoginService;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationListener extends AbstractAuthenticationListener {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void doAuthSuccessEvent(AuthSuccessEvent event) {
        System.out.println(this.getClass().getName() + " listener onApplicationEvent !");
        Token source = event.getToken();
        BizAssert.isTrue(source.isAuthenticated(), "token is not authenticated !");
        // todo 执行业务
        // 缓存用户信息
        userLoginService.cachePrinciple(source);
    }

    @Override
    protected void doAuthFailureEvent(AuthFailureEvent event) {
        System.out.println(this.getClass().getName() + " listener onApplicationEvent !");
        // todo 执行业务
        AuthException exception = ((AuthFailureEvent) event).getException();
    }
}
