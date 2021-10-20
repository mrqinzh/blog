package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.Role;
import com.mrqinzh.blog.model.vo.PageVO;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    List<Role> findPage(PageVO pageVO);

    Role getById(Integer id);

    void add(Role role);

    void update(Role role);

    void delete(Integer id);

}
