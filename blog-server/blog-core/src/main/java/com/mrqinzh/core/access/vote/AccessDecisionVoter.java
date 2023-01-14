package com.mrqinzh.core.access.vote;

import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.auth.token.AuthenticatedToken;

import java.util.Collection;

public interface AccessDecisionVoter {

    int GRANT = 1;
    int ABSTAIN = 0;
    int DENIED = -1;

    /**
     * @param token 认证后的token信息
     * @param configRoles 方法上的注解信息
     */
    int vote(AuthenticatedToken token, Collection<RoleType> configRoles);

    /**
     * 可扩展自定义投票策略
     */
    boolean support(AuthenticatedToken token, Collection<RoleType> configRoles);

}
