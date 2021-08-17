package com.mrqinzh.blog.service;

import com.mrqinzh.blog.util.Resp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

    Resp add(HttpServletRequest request, MultipartFile file);

    Resp delete(String condition);

}
