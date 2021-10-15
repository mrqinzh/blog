package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.constant.JwtConstant;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.LoginLogMapper;
import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.vo.UserVO;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.resp.PageResp;
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
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.util.WebUtil;
import org.springframework.beans.BeanUtils;
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
    public Resp update(UserVO userVO, String token) {

        User sysUser = (User) redisUtil.get(token); // 当前登录的用户

        User user = new User();

        if (userVO.getUserPwd() != null && userVO.getNewPass() != null) {
            // 修改密码操作
            if (!userVO.getUserPwd().equals(sysUser.getUserPwd())) {
                throw new BizException(AppStatus.BAD_REQUEST, "原密码发生了错误。。。");
            }
            // Todo 此处可以对密码进行加密。。。
            user.setUserPwd(userVO.getNewPass()); // 设置新密码
        } else {
            // 避免前端传入密码脏数据，导致BeanUtils.copyProperties 复制脏密码，导致修改了原密码
            userVO.setUserPwd(null);
            BeanUtils.copyProperties(userVO, user); // 更改基础信息
        }

        userMapper.updateById(user);

        // 删除缓存，需要前端重新登录
        redisUtil.del(token);

        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @Override
    public Resp add(User user, String token) {

        // 添加用户，先验证当前操作人的权限是否足够。。。
        User sysUser = (User) redisUtil.get(token);
        if (!sysUser.getRoleName().equals("super-admin")) {
            throw new BizException(AppStatus.AUTH_FAILED);
        }

        // Todo 这里可以对用户密码 进行加密 再入库
        userMapper.insert(user);

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
        userMapper.updateById(user);
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
        // 存在问题： 页面每次刷新时，也会添加。等到正式环境在使用
//        loginLogMapper.add(loginLog);

        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", user.getId());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());
        map.put("role", user.getRoleName());

        return DataResp.ok(map);
    }

    @Override
    public Resp list(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<User> users = userMapper.list();
        return PageResp.ok(users);
    }

}
