package com.mrqinzh.core.access.vote;

import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限
 */
@Component
public class PermissionVoter implements AccessDecisionVoter {

    @Override
    public int vote(AuthenticatedToken token, Collection<RoleType> configRoles) {
        return ABSTAIN;
    }

    @Override
    public boolean support(AuthenticatedToken token, Collection<RoleType> configRoles) {
        return true;
    }

}
