package com.mrqinzh.core.auth.event;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.core.auth.token.Token;

public interface AuthenticationEventPublisher {

    void publishAuthenticationSuccess(Token token);

    void publishAuthenticationFailure(AuthException exception, Token token);

}
