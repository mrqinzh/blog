package com.mrqinzh.domain.service;

import com.mrqinzh.common.model.resp.Resp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

    Resp add(HttpServletRequest request, MultipartFile file);

    Resp delete(String fileName);

    String uploadToQiNiu(MultipartFile uploadFile);

}
