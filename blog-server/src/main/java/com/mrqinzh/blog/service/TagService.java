package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.util.Page;
import com.mrqinzh.blog.util.Resp;

public interface TagService {

    /**
     * 分页查询 tag
     */
    Page page(PageDTO pageDTO);

    /**
     * 查询 tags limit 20
     */
    Resp list();

    Resp add(Tag tag);

    Resp delete(Integer id);

}
