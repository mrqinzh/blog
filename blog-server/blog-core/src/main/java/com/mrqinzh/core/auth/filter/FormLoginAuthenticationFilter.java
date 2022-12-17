package com.mrqinzh.core.auth.filter;

import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.auth.AbsAuthenticationFilter;
import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.auth.token.UsernamePasswordToken;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class FormLoginAuthenticationFilter extends AbsAuthenticationFilter {

    @Override
    protected AbstractAuthenticationToken<?> createCredentialToken(HttpServletRequest request) {
        String username = request.getParameter(SecurityProperties.USERNAME);
        String password = request.getParameter(SecurityProperties.PASSWORD);
        BizAssert.isNotBlank("parameter error !", username, password);

        UsernamePasswordToken token = new UsernamePasswordToken(username, false);
        token.setPassword(password);
        token.setLoginType(LoginType.FORM);
        return token;
    }

    @Override
    protected boolean requiredAuthenticationRequest(HttpServletRequest request) {
        return request.getRequestURI().equals(SecurityProperties.LOGIN_URL);
    }

}
