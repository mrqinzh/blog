package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.Resp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileService {

    Resp add(HttpServletRequest request, MultipartFile file);

    Resp delete(String fileName);

    Resp uploadToQiNiu(MultipartFile uploadFile) throws IOException;

}
