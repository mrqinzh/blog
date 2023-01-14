package com.mrqinzh.core.access.vote;

import cn.hutool.core.collection.CollectionUtil;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 角色
 */
@Component
public class RoleVoter implements AccessDecisionVoter {

    private static final Logger logger = LoggerFactory.getLogger(RoleVoter.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int vote(AuthenticatedToken token, Collection<RoleType> configRoles) {
        int result = ABSTAIN;
        if (token == null || CollectionUtil.isEmpty(configRoles)) return result;
        try {
            User user = (User) redisUtil.get(token.getUsername());
            // 角色信息能匹配上
            boolean hasPermission = configRoles.stream().anyMatch(e -> user.authorities().contains(e));
            result = hasPermission ? GRANT : DENIED;
        } catch (Exception e) {
            logger.error("RoleVoter vote failure", e);
        }
        return result;

    }

    @Override
    public boolean support(AuthenticatedToken token, Collection<RoleType> configRoles) {
        return true;
    }

}
