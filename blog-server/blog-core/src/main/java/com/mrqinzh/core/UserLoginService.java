package com.mrqinzh.core;

import com.mrqinzh.core.auth.token.Token;

public interface UserLoginService {

    void cachePrincipal(Token token);

}
