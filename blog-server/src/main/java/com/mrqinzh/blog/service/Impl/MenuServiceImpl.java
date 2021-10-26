package com.mrqinzh.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.mapper.MenuMapper;
import com.mrqinzh.blog.model.entity.Menu;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.vo.menu.MenuVO;
import com.mrqinzh.blog.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> findAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Menu::getStatus, 0);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        menus.stream().forEach(menu -> {
            if (menu.getParentId() == 0) {
                menu.setMenuChildren(menus.stream().filter(m -> m.getParentId() == menu.getId()).collect(Collectors.toList()));
            }
        });
        List<Menu> menuList = menus.stream().filter(m -> m.getParentId() == 0).collect(Collectors.toList());
        return menuList;
    }

    @Override
    public List<Menu> findPage(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getStatus, 0));
        return menus;
    }

    @Override
    public Menu findById(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void add(MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);
        menu.setCreateTime(new Date()).setUpdateTime(new Date()).setStatus(0);

        menuMapper.insert(menu);
    }

    @Override
    public void update(MenuVO menuVO) {
        Menu menu = menuMapper.selectById(menuVO.getId());
        BeanUtils.copyProperties(menuVO, menu);

        menu.setUpdateTime(new Date());

        menuMapper.updateById(menu);
    }

    @Override
    public void delete(Integer id) {
        menuMapper.deleteById(id);
    }

}
