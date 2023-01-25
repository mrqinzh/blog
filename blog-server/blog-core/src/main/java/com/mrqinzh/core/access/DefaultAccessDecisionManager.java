package com.mrqinzh.core.access;

import com.mrqinzh.core.access.vote.AccessDecisionVoter;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 赞成 >= 拒绝
 */
@Component
public class DefaultAccessDecisionManager implements AccessDecisionManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAccessDecisionManager.class);
    @Autowired
    private List<AccessDecisionVoter> voters;

    @Override
    public void decide(AuthenticatedToken token, Collection<RoleType> configRoles) throws AccessDenyException {
        int grant = 0;
        int denied = 0;
        for (AccessDecisionVoter voter : voters) {
            if (voter.support(token, configRoles)) {
                int result = voter.vote(token, configRoles); // 核心
                logger.info("{} vote {} !", voter.getClass().getName(), result);
                switch (result) {
                    case AccessDecisionVoter.GRANT:
                        grant++;
                        break;
                    case AccessDecisionVoter.DENIED:
                        denied++;
                        break;
                    default:
                        break;
                }
            }
        }
        // 赞成 >= 拒绝
        if (grant > denied || grant == denied) {
            return;
        }
        throw new AccessDenyException("access denied !");

    }
}
