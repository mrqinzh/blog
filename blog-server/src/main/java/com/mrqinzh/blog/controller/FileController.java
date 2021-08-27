package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.exception.BizException;
import com.mrqinzh.blog.model.enums.ExceptionEnums;
import com.mrqinzh.blog.service.FileService;
import com.mrqinzh.blog.model.dto.resp.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "文件接口")
@CrossOrigin
@RequestMapping("file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "添加一个文件")
    @PostMapping("add")
    public Resp add(HttpServletRequest request, MultipartFile file) {
        return fileService.add(request, file);
    }

    @DeleteMapping("delete/{fileName}")
    public Resp delete(@PathVariable String fileName) {
        return fileService.delete(fileName);
    }

    @PostMapping("qiniu")
    public Resp uploadToQiNiu(MultipartFile file) {
        return fileService.uploadToQiNiu(file);
    }


}
