package com.mrqinzh.blog.config;

import com.mrqinzh.blog.auth.component.AccessDecisionProcessor;
import com.mrqinzh.blog.auth.filter.CustomFrontAuthorizationFilter;
import com.mrqinzh.blog.auth.filter.JwtAuthenticationTokenFilter;
import com.mrqinzh.blog.auth.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;
import java.util.List;

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

        // 添加时，需要考虑先后顺序问题
        http.authorizeRequests()

                .antMatchers(HttpMethod.GET, "/article/{articleId}").permitAll()
                .antMatchers("/*/list").permitAll()
                .antMatchers("/comment/**").permitAll()
                .antMatchers("/article/**").hasAuthority("admin")
                .antMatchers("/role/**").hasAuthority("super-admin")
                .antMatchers("/menu/**").hasAuthority("super-admin")

                .anyRequest().authenticated()

                .accessDecisionManager(accessDecisionManager()) // 添加投票管理器
                ;

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
        // 放行swagger
        web.ignoring().antMatchers(
                "/swagger-ui.html",
                "/v2/api-docs", // swagger api json
                "/swagger-resources/configuration/ui", // 用来获取支持的动作
                "/swagger-resources", // 用来获取api-docs的URI
                "/swagger-resources/configuration/security", // 安全选项
                "/swagger-resources/**",
                "/webjars/**"
        );
    }


    /**
     * 投票决策器：3种
     * AffirmativeBased: 只要有一票同意，即可通过
     * ConsensusBased: 同意 > 反对，即可通过；
     *                 <，抛AccessDeniedException；
     *                 =，具体看allowIfEqualGrantedDeniedDecisions的参数设置，true通过，false抛异常
     * UnanimousBased: 所有投票器均同意才能通过
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // 将 自定义的投票器 添加到投票管理器中
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = Arrays.asList(new AccessDecisionProcessor(), new WebExpressionVoter());
        return new AffirmativeBased(accessDecisionVoters);
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
