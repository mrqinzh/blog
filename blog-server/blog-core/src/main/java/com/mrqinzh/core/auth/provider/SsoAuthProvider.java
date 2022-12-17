package com.mrqinzh.core.auth.provider;

import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.auth.token.UsernamePasswordToken;
import org.springframework.stereotype.Component;

@Component
public class SsoAuthProvider implements AuthProvider {

    @Override
    public AuthenticatedToken doAuth(AbstractAuthenticationToken credentialToken) {
        return new AuthenticatedToken(null, null);
    }

    @Override
    public boolean support(Token credentialToken) {
        return LoginType.SSO == credentialToken.getLoginType();
    }

}
