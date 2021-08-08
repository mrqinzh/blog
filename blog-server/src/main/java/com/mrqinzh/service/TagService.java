package com.mrqinzh.service;

import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Tag;
import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;

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
