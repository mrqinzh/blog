package com.mrqinzh.core.auth.session;

import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SessionManager {

    @Autowired
    private RedisUtil redisUtil;

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public void start(HttpServletRequest request, HttpServletResponse response, AuthenticatedToken token) {
        Cookie cookie = new Cookie(SecurityProperties.COOKIE_NAME, token.getTokenId());
        response.addCookie(cookie);

        redisUtil.set(token.getTokenId(), token, SecurityProperties.DEFAULT_EXPIRE_TIME_SECONDS);
    }

    public AuthenticatedToken getToken(String sessionId) {
        return (AuthenticatedToken) redisUtil.get(sessionId);
    }

    public String getSessionId(HttpServletRequest request) {
        String token = request.getParameter(SecurityProperties.COOKIE_NAME);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(SecurityProperties.COOKIE_NAME);
        }
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (SecurityProperties.COOKIE_NAME.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return token;
    }

    public void expire(Token token) {
        AuthenticatedToken authenticated = (AuthenticatedToken) token;
        if (authenticated != null) {
            String username = authenticated.getUsername();
            redisUtil.expire(username, 0);
            redisUtil.expire(authenticated.getTokenId(), 0);
        }
    }
}
