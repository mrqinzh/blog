package com.mrqinzh.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.core.entity.Role;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.core.mapper.RoleMapper;
import com.mrqinzh.server.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Role::getStatus, 0);
        List<Role> roles = roleMapper.selectList(queryWrapper);
        return roles;
    }

    @Override
    public List<Role> findPage(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().lambda().eq(Role::getStatus, 0));
        return roles;
    }

    @Override
    public Role getById(Integer id) {
        Role role = roleMapper.selectById(id);
        return role;
    }

    @Override
    public void add(Role role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            throw new BizException(AppStatus.BAD_REQUEST, "请输入角色名称");
        }
        role.setCreateTime(new Date()).setUpdateTime(new Date()).setStatus(0);
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        if (role.getId() == null || StringUtils.isBlank(role.getRoleName())) {
            throw new BizException(AppStatus.BAD_REQUEST, "请输入角色名称");
        }
        role.setUpdateTime(new Date());
        roleMapper.updateById(role);
    }

    @Override
    public void delete(Integer id) {
        roleMapper.deleteById(id);
    }

}
