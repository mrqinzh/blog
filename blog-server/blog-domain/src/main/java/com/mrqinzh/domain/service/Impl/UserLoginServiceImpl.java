package com.mrqinzh.domain.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.UserLoginService;
import com.mrqinzh.core.auth.token.AbstractAuthenticationToken;
import com.mrqinzh.core.entity.LoginLog;
import com.mrqinzh.core.entity.Role;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.core.security.SecurityProperties;
import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import com.mrqinzh.domain.mapper.RoleMapper;
import com.mrqinzh.domain.service.LoginLogService;
import com.mrqinzh.domain.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private LoginLogService loginLogService;

    public void cachePrincipal(AbstractAuthenticationToken<?> token) {
        User user = getUser(token);
        if (user == null) return;
        List<Role> roles = roleMapper.getRolesByUserId(user.getId());
        user.setRoles(roles);
        redisUtil.set(token.getUsername(), user, SecurityProperties.DEFAULT_EXPIRE_TIME_SECONDS);
    }

    @Override
    public void addLoginRecord(AbstractAuthenticationToken<?> token) {
        User user = getUser(token);
        if (user == null) return;
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setIp(token.getIp());
        loginLog.setLoginTime(new Date());
        loginLogService.save(loginLog);
    }

    @Override
    public boolean checkTokenExpired(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return false;
        // todo jwt
        String username = tokenId.substring(SecurityProperties.TOKEN_CACHE_PREFIX.length() +
                UUID.randomUUID().toString().replaceAll("-", "").length());
        SecurityUser user = securityService.loadSecurityUserFromDb(username);
        // 第二个参数 false，不抛出异常，如果结果有多个，取第一个值
        LoginLog loginLog = loginLogService.getOne(new LambdaQueryWrapper<LoginLog>()
                .eq(LoginLog::getUserId, user.getId())
                .orderByDesc(LoginLog::getLoginTime), false
        );
        if (loginLog == null || loginLog.getLoginTime() == null) {
            return false;
        }

        return !DateUtil.isIn(new Date(),
                loginLog.getLoginTime(),
                DateUtils.addSeconds(loginLog.getLoginTime(), SecurityProperties.DEFAULT_EXPIRE_TIME_SECONDS)
                );
    }

    private User getUser(AbstractAuthenticationToken<?> token) {
        Object principal = token.getPrincipal();
        if (!(principal instanceof User)) return null;
        return  (User) principal;
    }

}
