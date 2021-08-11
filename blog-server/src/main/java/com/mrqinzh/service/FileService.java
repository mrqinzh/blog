package com.mrqinzh.service;

import com.mrqinzh.util.Resp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

    Resp add(HttpServletRequest request, MultipartFile file);

}
