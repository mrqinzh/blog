package com.mrqinzh.core.access;

import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;

import java.util.Collection;

/**
 * spring-security：
 * 先拿到方法上的权限注解获取访问资源需要的权限，判断是否需要访问权限，如果方法上面没有注解，则判断是否开启公共资源允许访问。
 * 拿到token中保存的用户信息，获取authorities属性。
 * 然后鉴权：通过 VoteManager 进行投票判断是否允许访问。
 * AffirmativeBased（肯定）： 任意一个vote通过，就允许
 * ConsensusBased（共识）： 赞成数 > 拒接数 如果相等 allowIfEqualGrantedDeniedDecisions（默认true）
 * UnanimousBased（一致）：没有拒绝票
 */
public interface AccessDecisionManager {

    void decide(AuthenticatedToken token, Collection<RoleType> configRoles) throws AccessDenyException;

}
