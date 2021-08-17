package com.mrqinzh.blog.controller;

import com.mrqinzh.blog.service.FileService;
import com.mrqinzh.blog.util.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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


}
