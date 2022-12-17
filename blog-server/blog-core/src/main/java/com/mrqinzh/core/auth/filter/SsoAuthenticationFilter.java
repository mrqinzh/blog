package com.mrqinzh.core.auth.filter;

import com.mrqinzh.core.auth.AbsAuthenticationFilter;
import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.UsernamePasswordToken;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SsoAuthenticationFilter extends AbsAuthenticationFilter {

    @Override
    protected AbstractAuthenticationToken<?> createCredentialToken(HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(null,  false);
        token.setLoginType(LoginType.SSO);
        return token;
    }

    @Override
    protected boolean requiredAuthenticationRequest(HttpServletRequest request) {
        return request.getRequestURI().equals(SecurityProperties.SSO_URL);
    }
}
