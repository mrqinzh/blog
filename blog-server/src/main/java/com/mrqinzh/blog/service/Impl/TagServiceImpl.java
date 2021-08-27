package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.mapper.TagMapper;
import com.mrqinzh.blog.model.dto.req.PageDTO;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.service.TagService;
import com.mrqinzh.blog.model.dto.resp.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Resp page(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Tag> tags = tagMapper.page();

        return Resp.sendPageData(tags);
    }

    @Override
    public Resp list() {
        List<Tag> tags = tagMapper.list();
        return Resp.ok(tags);
    }

    @Override
    public Resp add(Tag tag) {
        tagMapper.add(tag);
        return Resp.ok(null);
    }

    @Override
    public Resp delete(Integer id) {
        tagMapper.delete(id);
        return Resp.ok(null);
    }
}
