package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.dto.resp.Resp;

public interface LoginLogService {

    Resp list(PageDTO pageDTO);

}
