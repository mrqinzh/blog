package com.mrqinzh.application.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.core.access.AccessPermission;
import com.mrqinzh.core.access.RoleType;
import com.mrqinzh.core.entity.SysConfig;
import com.mrqinzh.domain.config.ConfigKey;
import com.mrqinzh.domain.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sys/config")
@RestController
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @AccessPermission(RoleType.SUPER_ADMIN)
    @PostMapping("setting")
    public Resp setting(@RequestBody SysConfig sysConfig) {
        BizAssert.isTrue(ConfigKey.validConfigKey(sysConfig.getConfigKey()), "config key is error");
        sysConfigService.setting(sysConfig);
        return Resp.success();
    }

    @GetMapping("")
    public Resp getPageConfig(PageVO<SysConfig> pageVO) {
        return DataResp.ok(sysConfigService.page(pageVO));
    }

    @GetMapping("byKeys")
    public Resp getConfigByKeys(String[] configKeys) {
        return DataResp.ok(sysConfigService.getByConfigKey(configKeys));
    }

}
