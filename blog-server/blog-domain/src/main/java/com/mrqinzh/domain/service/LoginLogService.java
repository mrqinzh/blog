package com.mrqinzh.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.core.entity.LoginLog;

public interface LoginLogService extends IService<LoginLog> {

    Resp list(PageVO pageVO);

}
