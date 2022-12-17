package com.mrqinzh.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.core.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getByRoleId(Integer roleId);

}
