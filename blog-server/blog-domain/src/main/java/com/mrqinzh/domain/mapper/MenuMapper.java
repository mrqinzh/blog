package com.mrqinzh.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.core.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getByRoleId(Integer roleId);

}
