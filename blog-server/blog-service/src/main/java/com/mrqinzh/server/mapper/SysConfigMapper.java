package com.mrqinzh.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.common.model.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}
