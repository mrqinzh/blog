package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.req.PageVO;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.model.vo.resp.Resp;

public interface TagService {

    /**
     * 分页查询 tag
     */
    Resp page(PageVO pageVO);

    /**
     * 查询 tags limit 20
     */
    Resp list();

    Resp add(Tag tag);

    Resp delete(Integer id);

    Resp getById(Integer id);
}
