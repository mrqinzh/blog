package com.mrqinzh.server.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.core.entity.LoginLog;
import com.mrqinzh.common.model.resp.PageResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.core.mapper.LoginLogMapper;
import com.mrqinzh.server.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Resp list(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<LoginLog> loginLogs = loginLogMapper.list();
        return PageResp.ok(loginLogs);
    }
}
