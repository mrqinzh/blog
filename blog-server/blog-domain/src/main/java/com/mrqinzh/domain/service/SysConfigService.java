package com.mrqinzh.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrqinzh.core.entity.SysConfig;

import java.util.List;

public interface SysConfigService extends IService<SysConfig> {

    void setting(SysConfig sysConfig);

    List<SysConfig> getByConfigKey(String[] configKeys);
}
