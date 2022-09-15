package com.mrqinzh.server.service;

import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;

public interface LoginLogService {

    Resp list(PageVO pageVO);

}
