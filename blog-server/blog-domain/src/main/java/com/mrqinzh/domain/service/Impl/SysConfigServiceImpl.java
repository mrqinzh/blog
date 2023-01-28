package com.mrqinzh.domain.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrqinzh.core.entity.SysConfig;
import com.mrqinzh.domain.mapper.SysConfigMapper;
import com.mrqinzh.domain.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public void setting(SysConfig sysConfig) {
        sysConfigMapper.updateById(sysConfig);
    }

    @Override
    public List<SysConfig> getByConfigKey(String[] configKeys) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<SysConfig>()
                .in(SysConfig::getConfigKey, configKeys);
        return sysConfigMapper.selectList(queryWrapper);
    }
}
