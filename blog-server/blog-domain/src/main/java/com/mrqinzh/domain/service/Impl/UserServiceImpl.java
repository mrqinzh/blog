package com.mrqinzh.domain.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.PageResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.user.UserVO;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.core.auth.context.AuthenticationContextHolder;
import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import com.mrqinzh.domain.mapper.MenuMapper;
import com.mrqinzh.domain.mapper.RoleMapper;
import com.mrqinzh.domain.mapper.UserMapper;
import com.mrqinzh.domain.service.MenuService;
import com.mrqinzh.domain.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mrqinzh
 */
@Service
public class UserServiceImpl implements UserService, SecurityService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Resp update(UserVO userVO) {

        SecurityUser securityUser = (SecurityUser) AuthenticationContextHolder.getContext().getToken().getPrincipal(); // 当前登录的用户

        User user = new User();

        if (userVO.getUserPwd() != null && userVO.getNewPass() != null) {
            // 修改密码操作
            if (!userVO.getUserPwd().equals(securityUser.getPassword())) {
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
        userMapper.insert(user);

        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @Override
    public Map<String, Object> info(String token) {

        User securityUser = (User) AuthenticationContextHolder.getContext().getToken().getPrincipal();
        User user = userMapper.selectById(securityUser.getId());

        // 返回用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());

        map.put("roles", user.getRoles());

        // Todo 暂时使用全部，用于前端调试
        map.put("menus", menuService.findAll());
//        map.put("menus", menuMapper.getByRoleId(user.getRole().getId()));
        return map;
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public SecurityUser loadSecurityUserFromDb(String username) {
        return userMapper.getByUsernameOrEmail(username);
    }
}
