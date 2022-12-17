package com.mrqinzh.core.auth.token;

import com.mrqinzh.core.auth.LoginType;
import com.mrqinzh.core.security.SecurityUser;
import lombok.Data;

@Data
public class AuthenticatedToken extends AbstractAuthenticationToken<SecurityUser> {

    private String tokenId;
    private String username;
    private SecurityUser securityUser;
    private LoginType loginType;

    public AuthenticatedToken(String username, SecurityUser securityUser) {
        super();
        this.username = username;
        this.securityUser = securityUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public LoginType getLoginType() {
        return loginType;
    }

    @Override
    public SecurityUser getPrinciple() {
        return securityUser;
    }
}
