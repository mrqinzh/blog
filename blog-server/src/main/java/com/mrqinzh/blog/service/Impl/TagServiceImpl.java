package com.mrqinzh.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.mapper.TagMapper;
import com.mrqinzh.blog.model.vo.PageVO;
import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.model.resp.PageResp;
import com.mrqinzh.blog.model.entity.Tag;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.service.TagService;
import com.mrqinzh.blog.model.resp.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Resp page(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Tag> tags = tagMapper.page(pageVO);

        return PageResp.ok(tags);
    }

    @Override
    public Resp list() {
        List<Tag> tags = tagMapper.list();
        return DataResp.ok(tags);
    }

    @Override
    public Resp add(Tag tag) {
        tagMapper.add(tag);
        return Resp.sendMsg(AppStatus.INSERT_SUCCESS);
    }

    @Override
    public Resp delete(Integer id) {
        tagMapper.delete(id);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

    @Override
    public Resp getById(Integer id) {
        Tag tag = tagMapper.getById(id);
        return DataResp.ok(tag);
    }
}
