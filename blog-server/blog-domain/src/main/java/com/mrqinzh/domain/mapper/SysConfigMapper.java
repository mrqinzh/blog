package com.mrqinzh.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrqinzh.core.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}
