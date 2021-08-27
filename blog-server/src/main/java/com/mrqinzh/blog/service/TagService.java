package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.dto.resp.BaseResp;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.model.dto.resp.Resp;

public interface TagService {

    /**
     * 分页查询 tag
     */
    Resp page(PageDTO pageDTO);

    /**
     * 查询 tags limit 20
     */
    Resp list();

    Resp add(Tag tag);

    Resp delete(Integer id);

}
