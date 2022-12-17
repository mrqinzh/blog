package com.mrqinzh.core.auth.provider;

import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityUser;

public interface AuthProvider {

    AuthenticatedToken doAuth(AbstractAuthenticationToken<?> credentialToken);

    boolean support(Token credentialToken);

}
