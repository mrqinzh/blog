package com.mrqinzh.core.auth.token;

import com.mrqinzh.core.security.SecurityUser;
import lombok.Data;

/**
 * 认证完成的token
 */
@Data
public class AuthenticatedToken extends AbstractAuthenticationToken<SecurityUser> {

    private String tokenId;
    private String username;
    private SecurityUser principal;

    public AuthenticatedToken(){}

    public AuthenticatedToken(String username, SecurityUser securityUser) {
        super();
        this.username = username;
        this.principal = securityUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public SecurityUser getPrincipal() {
        return principal;
    }
}
