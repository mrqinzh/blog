package com.mrqinzh.common.auth.filter;

import com.mrqinzh.common.security.SecurityUser;
import com.mrqinzh.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    public static final String header = "token";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(JwtAuthenticationTokenFilter.header);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(header);
        }
        log.info("token: {}", token);
        if (StringUtils.isNotBlank(token)) {
            boolean check = false;
            // 判断token是否过期
            try {
                check = jwtTokenUtil.isTokenExpired(token);
            } catch (Exception e) {
                throw new RuntimeException("令牌已过期，请重新登录。" + e.getMessage());
            }
            if (!check) {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                log.info("username: {}", username);
                // 拿到用户名之后,重新通过userService的loadUserByUsername方法重新得到一个用户
                // 放到securityContext的上下文就行了,判断用户名不为空,且SecurityContextHolder授权信息是空的
                if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);
                    boolean validate = false;
                    // 验证token是否有效
                    try {
                        validate = jwtTokenUtil.validateToken(token, userDetails);
                    } catch (Exception e) {
                        throw new RuntimeException("验证token无效:" + e.getMessage());
                    }
                    if (validate) {
                        // 将用户信息存入authentication，方便后续校验
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将authentication存入threadLocal，方便后续获取用户信息。。。续期
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("===通过JwtAuthenticationTokenFilter，并将authentication存入threadLocal===");

                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

}
