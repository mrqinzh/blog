package com.mrqinzh.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.model.entity.Tag;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.PageResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.PageVO;
import com.mrqinzh.server.mapper.TagMapper;
import com.mrqinzh.server.service.TagService;
import org.apache.commons.lang3.StringUtils;
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
    public List<Tag> getByLimit() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.last("limit 20");
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public void add(Tag tag) {
        if (StringUtils.isBlank(tag.getTagImg())) {
            throw new BizException(AppStatus.BAD_REQUEST, "必须上传标签对应图片！");
        }
        tagMapper.insert(tag);
    }

    @Override
    public Resp delete(Integer id) {
        tagMapper.deleteById(id);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

    @Override
    public void update(Tag tag) {
        if (tag.getId() == null || StringUtils.isBlank(tag.getTagImg())) {
            throw new BizException(AppStatus.BAD_REQUEST);
        }
        tagMapper.updateById(tag);
    }

    @Override
    public Tag getById(Integer id) {
        return tagMapper.selectById(id);
    }

}
