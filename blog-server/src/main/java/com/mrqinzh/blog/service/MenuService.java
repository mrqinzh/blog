package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.entity.Menu;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.vo.menu.MenuVO;

import java.util.List;

public interface MenuService {

    List<Menu> findAll();

    List<Menu> findPage(PageVO pageVO);

    Menu findById(Integer id);

    void add(MenuVO menuVO);

    void update(MenuVO menuVO);

    void delete(Integer id);

}
