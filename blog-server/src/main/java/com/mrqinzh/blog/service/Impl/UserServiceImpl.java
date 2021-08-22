package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.exception.MyException;
import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrqinzh.blog.model.enums.ExceptionEnums;
import com.mrqinzh.blog.service.UserService;
import com.mrqinzh.blog.util.Constant;
import com.mrqinzh.blog.util.JwtUtil;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.util.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mrqinzh
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getSimpleName());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp add(User user, String token) {

        // Todo 添加用户，先验证当前操作人的权限是否足够。。。
        User sysUser = (User) redisUtil.get(token);
        if (!sysUser.getRoleName().equals("super-admin")) {
            throw new MyException(ExceptionEnums.NO_AUTHORITY);
        }

        // Todo 这里可以对用户密码 进行加密 再入库
        userMapper.add(user);

        return Resp.sendSuccessMsg("添加用户成功。..^_^");
    }

    @Override
    public Resp getByUsernameOrEmail(User user) {
        logger.info("有用户进行了登录操作。。。user => " + user);
        User dbUser = userMapper.getByUsernameOrEmail(user.getUserName());
        // 数据库进行了md5加密加盐
        if (null == dbUser || !dbUser.getUserPwd().equals(user.getUserPwd())){
            throw new MyException(ExceptionEnums.USERNAME_PASSWORD_ERROR);
        }

        // 添加到 jwt 的 body 中
        Map<String,String> claim = new HashMap<>(8);
        claim.put("username", dbUser.getUserName());
        claim.put("password", dbUser.getUserPwd());
        claim.put("avatar", dbUser.getUserAvatar());
        String token = JwtUtil.getTokenWithClaim(claim);

        redisUtil.set(token, dbUser, Constant.TOKEN_EXPIRE_TIME);

        HashMap<Object, Object> resultMap = new HashMap<>(1);
        resultMap.put("token", token);
        return Resp.ok(resultMap);
    }

    @Override
    public Resp info(String token) {

        User user = (User) redisUtil.get(token);

        Map<String, Object> map = new HashMap<>(3);
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());
        map.put("role", user.getRoleName());

        return Resp.ok(map);
    }

    @Override
    public Resp list(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<User> users = userMapper.list();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return Resp.sendPageData(pageInfo);
    }

}
