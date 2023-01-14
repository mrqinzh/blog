package com.mrqinzh.application.controller;

import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.entity.SysConfig;
import com.mrqinzh.domain.config.ConfigKey;
import com.mrqinzh.domain.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sys/config")
@RestController
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("setting")
    public Resp setting(SysConfig sysConfig) {
        BizAssert.isFalse(ConfigKey.validConfigKey(sysConfig.getConfigKey()), "config key is error");
        systemConfigService.setting(sysConfig);
        return Resp.success();
    }

}
