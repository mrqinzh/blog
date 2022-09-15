package com.mrqinzh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.blog.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByUserId(Integer userId);

}
