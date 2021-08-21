package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.mapper.TagMapper;
import com.mrqinzh.blog.model.dto.PageDTO;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.service.TagService;
import com.mrqinzh.blog.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Resp page(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Tag> tags = tagMapper.page();
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);

        return Resp.sendPageData(pageInfo);
    }

    @Override
    public Resp list() {
        List<Tag> tags = tagMapper.list();
        return Resp.ok(tags);
    }

    @Override
    @Transactional
    public Resp add(Tag tag) {
        tagMapper.add(tag);
        return Resp.ok(null);
    }

    @Override
    @Transactional
    public Resp delete(Integer id) {
        tagMapper.delete(id);
        return Resp.ok(null);
    }
}
