package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.mapper.LoginLogMapper;
import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.LoginLog;
import com.mrqinzh.blog.service.LoginLogService;
import com.mrqinzh.blog.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Resp list(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<LoginLog> loginLogs = loginLogMapper.list();
        return Resp.sendPageData(loginLogs);
    }
}
