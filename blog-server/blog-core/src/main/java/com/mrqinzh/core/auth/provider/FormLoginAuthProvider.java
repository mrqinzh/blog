package com.mrqinzh.core.auth.provider;

import com.mrqinzh.common.exception.AuthException;
import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.auth.security.Principle;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.auth.token.UsernamePasswordToken;
import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthProvider implements AuthProvider {

    @Autowired
    private SecurityService securityService;

    @Override
    public AuthenticatedToken doAuth(AbstractAuthenticationToken<?> credentialToken) {
        UsernamePasswordToken token;
        if (credentialToken instanceof UsernamePasswordToken) {
            token = (UsernamePasswordToken) credentialToken;
        } else throw new AuthException("token error !");

        String username = token.getUsername();
        String pwd = token.getPassword();
        SecurityUser securityUser = securityService.loadSecurityUserFromDb(username);
        if (pwd.equals(securityUser.getPassword())) {
            return new AuthenticatedToken(username, securityUser);
        }
        throw new AuthException("username or password error !");
    }

    @Override
    public boolean support(Token credentialToken) {
        return LoginType.FORM == credentialToken.getLoginType();
    }

}
