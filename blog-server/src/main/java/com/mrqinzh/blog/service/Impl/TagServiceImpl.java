package com.mrqinzh.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.blog.exception.BizException;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Resp page(PageVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());
        List<Tag> tags = tagMapper.page(pageVO);

        return PageResp.ok(tags);
    }

    @Override
    public List<Tag> getByLimit() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.last("limit 20");
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public void add(Tag tag) {
        if (tag.getTagImg() == null) {
            throw new BizException(AppStatus.BAD_REQUEST, "必须上传标签对应图片！");
        }
        tagMapper.insert(tag);
    }

    @Override
    public Resp delete(Integer id) {
        tagMapper.delete(id);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

}
