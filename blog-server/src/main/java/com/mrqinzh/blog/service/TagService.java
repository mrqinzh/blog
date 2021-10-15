package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.model.resp.Resp;

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
