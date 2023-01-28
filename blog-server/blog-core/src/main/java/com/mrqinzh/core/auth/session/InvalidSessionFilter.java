package com.mrqinzh.core.auth.session;

import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.RedirectStrategy;
import com.mrqinzh.core.auth.context.AuthenticationContextHolder;
import com.mrqinzh.core.auth.token.Token;
import com.mrqinzh.core.security.SecurityProperties;
import com.mrqinzh.core.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需在 SecurityContextFilter 后执行。
 */
@Component
@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 51)
public class InvalidSessionFilter extends OncePerRequestFilter {

    @Autowired
    private RedirectStrategy redirectStrategy;
    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityUtils.withoutAuthApi(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenId = sessionManager.getTokenId(request);
        if (!sessionManager.checkTokenId(tokenId)) {
            redirectStrategy.redirect(request, response, new Resp(AppStatus.TOKEN_ILLEGAL));
            return;
        }

        Token token = AuthenticationContextHolder.getContext().getToken();
        if (token == null || !token.isAuthenticated()) {
            redirectStrategy.redirect(request, response, new Resp(AppStatus.TOKEN_EXPIRED));
        }


        filterChain.doFilter(request, response);
    }

}
