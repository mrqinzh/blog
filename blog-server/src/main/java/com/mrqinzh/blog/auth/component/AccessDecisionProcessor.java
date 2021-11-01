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
        return false;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        String requestUrl = object.getRequestUrl();
        String method = object.getRequest().getMethod();
        log.info("进入自定义投票器，URI：{}，method：{}", requestUrl, method);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println("authority.getAuthority() = " + authority.getAuthority());
        }

        return 0;
    }

    @Override
    public boolean supports(Class clazz) {
        return false;
    }

}
