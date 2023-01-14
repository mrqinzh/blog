package com.mrqinzh.domain.service.Impl;

import com.mrqinzh.core.entity.SysConfig;
import com.mrqinzh.domain.mapper.SysConfigMapper;
import com.mrqinzh.domain.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public void setting(SysConfig sysConfig) {
        sysConfigMapper.insert(sysConfig);
    }
}
