package com.mrqinzh.server.service;

import com.mrqinzh.core.entity.Role;
import com.mrqinzh.common.model.vo.PageVO;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    List<Role> findPage(PageVO pageVO);

    Role getById(Integer id);

    void add(Role role);

    void update(Role role);

    void delete(Integer id);

}
