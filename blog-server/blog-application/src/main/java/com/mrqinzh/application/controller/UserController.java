package com.mrqinzh.application.controller;

import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.core.access.AccessPermission;
import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.auth.context.AuthenticationContextUtils;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.model.vo.user.UserVO;
import com.mrqinzh.domain.service.MenuService;
import com.mrqinzh.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("list")
    public Resp list(PageVO pageVO) {
        return userService.list(pageVO);
    }

    @ApiOperation(value = "根据id获取指定用户")
    @GetMapping("{id}")
    public Resp getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return DataResp.ok(user);
    }

    @ApiOperation(value = "添加一个用户")
    @PostMapping("add")
    @AccessPermission(RoleType.SUPER_ADMIN)
    public Resp add(@RequestBody UserVO userVO) {
        return userService.add(userVO);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    @AccessPermission(RoleType.SUPER_ADMIN)
    public Resp update(@RequestBody UserVO userVO) {
        return userService.update(userVO);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public Resp info() {
        User user = AuthenticationContextUtils.getUser();
        if (user == null) {
            return new Resp(AppStatus.TOKEN_EXPIRED);
        }
        // 返回用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("name", user.getUserNickname());
        map.put("avatar", user.getUserAvatar());

        map.put("roles", user.getRoles());

        // Todo 暂时使用全部，用于前端调试
        map.put("menus", menuService.findAll());
//        map.put("menus", menuMapper.getByRoleId(user.getRole().getId()));
        return DataResp.ok(map);
    }

}
