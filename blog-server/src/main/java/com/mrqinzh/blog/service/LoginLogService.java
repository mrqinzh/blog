package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.req.PageVO;
import com.mrqinzh.blog.model.vo.resp.Resp;

public interface LoginLogService {

    Resp list(PageVO pageVO);

}
