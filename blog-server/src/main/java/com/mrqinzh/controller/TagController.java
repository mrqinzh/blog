package com.mrqinzh.controller;

import com.mrqinzh.model.dto.PageDTO;
import com.mrqinzh.model.entity.Tag;
import com.mrqinzh.service.TagService;
import com.mrqinzh.util.Page;
import com.mrqinzh.util.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "标签接口")
@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "添加标签")
    @PostMapping("add")
    public Resp add(@RequestBody Tag tag) {
        return tagService.add(tag);
    }

    @ApiOperation(value = "分页查询 tags")
    @GetMapping("page")
    public Page page(PageDTO pageDTO) {
        return tagService.page(pageDTO);
    }

    @ApiOperation(value = "查询所有标签，limit 20")
    @GetMapping("list")
    public Resp list() {
        return tagService.list();
    }

    @ApiOperation(value = "根据 tagId 删除标签")
    @DeleteMapping("{id}")
    public Resp delete(@PathVariable Integer id) {
        return tagService.delete(id);
    }

}
