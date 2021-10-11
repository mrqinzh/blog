package com.mrqinzh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrqinzh.blog.model.entity.MyFile;
import com.mrqinzh.blog.model.resp.Resp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileService extends IService<MyFile> {

    Resp add(HttpServletRequest request, MultipartFile file);

    Resp delete(String fileName);

    String uploadToQiNiu(MultipartFile uploadFile);

}
