package com.mrqinzh.core.auth.session;

import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.cache.SessionCache;
import com.mrqinzh.core.security.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SessionManager {

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public void start(HttpServletRequest request, HttpServletResponse response, AuthenticatedToken token) {
        Cookie cookie = new Cookie(SecurityProperties.COOKIE_NAME, token.getTokenId());
        response.addCookie(cookie);

        SessionCache.set(token.getTokenId(), token);
    }

    public Token getToken(String sessionId) {
        return SessionCache.get(sessionId);
    }

    public String getSessionId(HttpServletRequest request) {
        String token = request.getParameter(SecurityProperties.COOKIE_NAME);
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

}
