package com.mrqinzh.blog.config;

import com.mrqinzh.blog.auth.handler.DefaultAccessDeniedHandler;
import com.mrqinzh.blog.auth.handler.DefaultAuthenticationEntryPoint;
import com.mrqinzh.blog.auth.handler.DefaultAuthenticationFailureHandler;
import com.mrqinzh.blog.auth.handler.DefaultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

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
                .anyRequest().permitAll()
                ;

        // 处理异常情况：认证失败/权限不足
        http.exceptionHandling()
                .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                .accessDeniedHandler(defaultAccessDeniedHandler);

        // 设置登录成功处理器
        http.formLogin()
                .usernameParameter("userName")
                .passwordParameter("userPwd")
                .successHandler(defaultAuthenticationSuccessHandler)
                .failureHandler(defaultAuthenticationFailureHandler);
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

}
