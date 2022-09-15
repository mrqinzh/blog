package com.mrqinzh.common.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class DefaultLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        log.info("用户 {} 执行退出操作", principal.getUsername());

        SecurityContextHolder.clearContext();

    }

}
