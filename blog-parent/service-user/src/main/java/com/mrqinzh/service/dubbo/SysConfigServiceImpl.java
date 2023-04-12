package com.mrqinzh.service.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrqinzh.apis.config.SysConfigService;
import com.mrqinzh.common.entity.SysConfig;
import com.mrqinzh.service.mapper.SysConfigMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Resource
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
