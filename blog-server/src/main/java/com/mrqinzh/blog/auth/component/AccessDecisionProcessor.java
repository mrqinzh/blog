package com.mrqinzh.blog.auth.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义投票器
 */
@Slf4j
@Component
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * @param authentication 当前登录的主体
     * @param object
     * @param attributes 配置文件中配置的资源访问权限集合
     * @return ACCESS_GRANTED: 1 （赞成）
     *         ACCESS_DENIED: -1 （反对）
     *         ACCESS_ABSTAIN: 0 （弃权）
     */
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        String requestUrl = object.getRequestUrl();
        String method = object.getRequest().getMethod();
        log.info("请求进入自定义投票器，URI：{}   method：{}", requestUrl, method);

        if (authentication == null) {
            return ACCESS_ABSTAIN;
        }

        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }

        // 如果当前userDetails == null，则证明当前访问为游客，直接弃票
        if (userDetails == null) {
            return ACCESS_ABSTAIN;
        }

        // Todo 当前只要认证过，就能通过
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
//            System.out.println("authority.getAuthority() = " + authority.getAuthority());
        }

        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

}
