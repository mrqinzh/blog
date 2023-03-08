package com.mrqinzh.core;

import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;

public interface UserLoginService {

    void cachePrincipal(AbstractAuthenticationToken<?> token);

    void addLoginRecord(AbstractAuthenticationToken<?> token);

    boolean checkTokenExpired(String tokenId);

}
