package com.mrqinzh.config;

import com.mrqinzh.realm.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean subject = new ShiroFilterFactoryBean();
        Map<String,String> filterMap = new LinkedHashMap<>();
        //设置安全管理器
        subject.setSecurityManager(defaultWebSecurityManager);

        /*
        anon : 无需认证，就可以访问
        authc : 必须认证，才能访问
        user : 必须拥有 “记住我”功能才能用
        perms : 拥有对某个资源的权限才能访问
        role : 拥有某个角色权限才能访问
        */

//        filterMap.put("/login/*", "anon");
//        subject.setFilterChainDefinitionMap(filterMap);
        return subject;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置算法名字
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }


}
