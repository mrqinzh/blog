package com.mrqinzh.server.service;

import com.mrqinzh.blog.model.resp.Resp;
import com.mrqinzh.blog.model.vo.PageVO;

public interface LoginLogService {

    Resp list(PageVO pageVO);

}
