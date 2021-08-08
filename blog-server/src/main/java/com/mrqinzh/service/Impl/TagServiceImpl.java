package com.mrqinzh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrqinzh.mapper.TagMapper;
import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Tag;
import com.mrqinzh.service.TagService;
import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Page page(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Tag> tags = tagMapper.page();
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);

        return Page.getPageData(pageInfo);
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
