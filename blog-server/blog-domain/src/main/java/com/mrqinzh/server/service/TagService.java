package com.mrqinzh.server.service;

import com.mrqinzh.common.model.entity.Tag;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;

import java.util.List;

public interface TagService {

    /**
     * 分页查询 tag
     */
    Resp page(PageVO pageVO);

    /**
     * 查询 tags limit 20
     */
    List<Tag> getByLimit();

    void add(Tag tag);

    Resp delete(Integer id);

    void update(Tag tag);

    Tag getById(Integer id);
}
