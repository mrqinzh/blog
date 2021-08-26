package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.dto.Resp;

public interface LoginLogService {

    Resp list(PageDTO pageDTO);

}
