package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.FileMapper;
import com.mrqinzh.service.FileService;
import com.mrqinzh.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final String BASE_FOLDER_PATH = "/files/"; // 文件存储根路径
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private FileMapper fileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp add(HttpServletRequest request, MultipartFile file) {
        String filePath = sdf.format(new Date());
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        File baseFolder = new File(BASE_FOLDER_PATH + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdir();
        }
        File realPath = new File(baseFolder, fileName);
        try {
            FileCopyUtils.copy(file.getBytes(), realPath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(BASE_FOLDER_PATH)
                .append(filePath)
                .append("/")
                .append(fileName);

        return null;
    }
}
