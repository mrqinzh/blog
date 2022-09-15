package com.mrqinzh.api.controller;

import com.mrqinzh.blog.model.entity.Role;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.resp.PageResp;
import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取所有角色列表")
    @GetMapping("list")
    public Resp findAll() {
        List<Role> roles = roleService.findAll();
        return DataResp.ok(roles);
    }

    @ApiOperation(value = "分页获取所有角色列表")
    @GetMapping("page")
    public Resp findPage(PageVO pageVO) {
        List<Role> roles = roleService.findPage(pageVO);
        return PageResp.ok(roles);
    }

    @ApiOperation(value = "根据id获取指定角色信息")
    @GetMapping("{id}")
    public Resp findById(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        return DataResp.ok(role);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("add")
    public Resp add(@RequestBody Role role) {
        roleService.add(role);
        return Resp.success();
    }

    @ApiOperation(value = "根据id更新角色信息")
    @PostMapping("update")
    public Resp update(@RequestBody Role role) {
        roleService.update(role);
        return Resp.success();
    }

    @ApiOperation(value = "根据id删除角色")
    @DeleteMapping("{id}")
    public Resp delete(@PathVariable Integer id) {
        roleService.delete(id);
        return Resp.success();
    }




}
