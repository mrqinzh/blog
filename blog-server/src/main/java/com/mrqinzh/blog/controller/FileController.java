package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.model.resp.DataResp;
import com.mrqinzh.blog.service.FileService;
import com.mrqinzh.blog.model.resp.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "文件接口")
@RequestMapping("file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "添加一个文件到系统中")
    @PostMapping("add")
    public Resp add(HttpServletRequest request, MultipartFile file) {
        return fileService.add(request, file);
    }

    @ApiOperation(value = "根据文件名删除文件")
    @DeleteMapping("delete/{fileName}")
    public Resp delete(@PathVariable String fileName) {
        return fileService.delete(fileName);
    }

    @ApiOperation(value = "上传图片至七牛云")
    @PostMapping("qiniu")
    public Resp uploadToQiNiu(MultipartFile file) {
        String url = fileService.uploadToQiNiu(file);
        return DataResp.ok(url);
    }

}
