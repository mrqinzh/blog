package com.mrqinzh.blog.config;

import com.mrqinzh.blog.auth.component.AccessDecisionProcessor;
import com.mrqinzh.blog.auth.filter.CustomFrontAuthorizationFilter;
import com.mrqinzh.blog.auth.filter.JwtAuthenticationTokenFilter;
import com.mrqinzh.blog.auth.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private CustomFrontAuthorizationFilter customFrontAuthorizationFilter;

    /**
     * 登录认证成功处理器
     */
    @Autowired
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    /**
     * 登录认证失败处理器
     */
    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

    /**
     * 登出处理器
     */
    @Autowired
    private DefaultLogoutHandler defaultLogoutHandler;

    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    @Autowired
    private DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Autowired
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 解决跨域问题，cors预检查请求放行，让spring security放行所有cors预检请求
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        http.authorizeRequests()
                .antMatchers("/*/list").permitAll()
                .antMatchers("/comment/**").permitAll()

                .anyRequest().permitAll()
                ;

        // 鉴权
//        http.addFilter(customFrontAuthorizationFilter);

        // 4.拦截token,并检测,在UsernamePasswordAuthenticationFilter之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 5.处理异常情况：认证失败/权限不足
        http.exceptionHandling()
                .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                .accessDeniedHandler(defaultAccessDeniedHandler);

        // 6.设置登录成功处理器
        http.formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("userName")
                .passwordParameter("userPwd")
                .successHandler(defaultAuthenticationSuccessHandler)
                .failureHandler(defaultAuthenticationFailureHandler);

        // last.退出
        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(defaultLogoutHandler)
                .logoutSuccessHandler(defaultLogoutSuccessHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**", "/static/**");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 设置加密算法
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

    @Bean
    public AccessDecisionVoter<FilterInvocation> accessDecisionVoter() {
        return new AccessDecisionProcessor();
    }

}
