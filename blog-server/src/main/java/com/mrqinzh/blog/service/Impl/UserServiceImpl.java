package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.auth.SecurityUser;
import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.mapper.LoginLogMapper;
import com.mrqinzh.blog.mapper.MenuMapper;
import com.mrqinzh.blog.mapper.UserMapper;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.vo.user.UserVO;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.resp.PageResp;
import com.mrqinzh.blog.model.entity.User;

import java.util.*;

import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.MenuService;
import com.mrqinzh.blog.service.UserService;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.util.WebUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author mrqinzh
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Resp update(UserVO userVO) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 当前登录的用户

        User user = new User();

        if (userVO.getUserPwd() != null && userVO.getNewPass() != null) {
            // 修改密码操作
            if (!userVO.getUserPwd().equals(securityUser.getUserPwd())) {
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

        return Resp.sendMsg(AppStatus.UPDATE_SUCCESS);
    }

    @Override
    public Resp add(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        // Todo 这里可以对用户密码 进行加密 再入库
        userMapper.insert(user);

        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @Override
    public Resp info(String token) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userMapper.selectById(securityUser.getId());

        // 返回用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());

        map.put("roles", user.getRoleName());
//        map.put("roles", user.getRole().getRoleName());

        // Todo 暂时使用全部，用于前端调试
        map.put("menus", menuService.findAll());
//        map.put("menus", menuMapper.getByRoleId(user.getRole().getId()));
        return DataResp.ok(map);
    }

    @Override
    public Resp list(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<User> users = userMapper.list();
        return PageResp.ok(users);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

}
