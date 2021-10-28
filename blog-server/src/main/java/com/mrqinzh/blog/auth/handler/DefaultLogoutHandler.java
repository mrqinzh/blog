package com.mrqinzh.blog.auth.handler;

import com.mrqinzh.blog.auth.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DefaultLogoutHandler implements LogoutHandler {

    private static Logger logger = LoggerFactory.getLogger(DefaultLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

//        SecurityUser principal = (SecurityUser) authentication.getPrincipal();

//        logger.info("用户 {} 执行推出操作", principal.getUsername());

    }

}
