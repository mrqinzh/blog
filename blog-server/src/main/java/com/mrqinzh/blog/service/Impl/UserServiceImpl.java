package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.constant.JwtConstant;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.LoginLogMapper;
import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.dto.resp.DataResp;
import com.mrqinzh.blog.model.dto.resp.PageResp;
import com.mrqinzh.blog.model.entity.LoginLog;
import com.mrqinzh.blog.model.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.UserService;
import com.mrqinzh.blog.util.JwtUtil;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.dto.resp.Resp;
import com.mrqinzh.blog.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mrqinzh
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Resp update(User user) {

        // Todo ......
//        userMapper.update(user);

        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @Override
    public Resp add(User user, String token) {

        // Todo 添加用户，先验证当前操作人的权限是否足够。。。
        User sysUser = (User) redisUtil.get(token);
        if (!sysUser.getRoleName().equals("super-admin")) {
            throw new BizException(AppStatus.AUTH_FAILED);
        }

        // Todo 这里可以对用户密码 进行加密 再入库
        userMapper.add(user);

        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @Override
    public Resp loginByUsernameOrEmail(User user) {
        User dbUser = userMapper.getByUsernameOrEmail(user.getUserName());

        if (null == dbUser || !dbUser.getUserPwd().equals(user.getUserPwd())){
            throw new BizException(AppStatus.USERNAME_PASSWORD_ERROR);
        }

        // 添加到 jwt 的 body 中
        Map<String,String> claim = new HashMap<>(8);
        claim.put("username", dbUser.getUserName());
        claim.put("password", dbUser.getUserPwd());
        claim.put("avatar", dbUser.getUserAvatar());
        String token = JwtUtil.getTokenWithClaim(claim);

        redisUtil.set(token, dbUser, JwtConstant.TOKEN_EXPIRE_TIME);

        HashMap<Object, Object> resultMap = new HashMap<>(2);
        resultMap.put("token", token);
        return DataResp.ok(resultMap);
    }

    @Override
    public Resp logout(String token) {
        User user = (User) redisUtil.get(token);
        // 修改用户的最后登录时间
        user.setLoginLastTime(new Date());
        userMapper.update(user);
        redisUtil.del(token);
        return Resp.sendMsg(AppStatus.SUCCESS);
    }

    @Override
    public Resp info(String token) {

        User user = (User) redisUtil.get(token);
        String clientIp = WebUtil.getClientIp(WebUtil.getRequest());

        // 向 t_login_log 表中添加一条登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(clientIp);
        loginLog.setLoginTime(new Date());
        loginLog.setToken(token);
        loginLog.setUserId(user.getId());
        // Todo 存在问题： 页面每次刷新时，也会添加
//        loginLogMapper.add(loginLog);

        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", user.getId());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());
        map.put("role", user.getRoleName());

        return DataResp.ok(map);
    }

    @Override
    public Resp list(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<User> users = userMapper.list();
        return PageResp.ok(users);
    }

}
