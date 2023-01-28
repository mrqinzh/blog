package com.mrqinzh.domain.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.util.WebUtil;
import com.mrqinzh.core.entity.MyFile;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.file.FileClientType;
import com.mrqinzh.core.file.FileInfo;
import com.mrqinzh.core.file.FileSource;
import com.mrqinzh.core.file.GlobalFileHandler;
import com.mrqinzh.core.properties.GlobalProperties;
import com.mrqinzh.domain.mapper.FileMapper;
import com.mrqinzh.domain.service.FileService;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${oos.qiniu.domain}")
    private String domain;
    @Value("${oos.qiniu.bucketname}")
    private String bucketName;
    @Value("${oos.qiniu.access-key}")
    private String accessKey;
    @Value("${oos.qiniu.secret-key}")
    private String secretKey;

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private GlobalFileHandler handler;

    @Override
    public Resp add(MultipartFile file) {
        FileInfo upload = handler.upload(file, FileSource.DEFAULT);
        if (upload == null) {
            return DataResp.ok("");
        }
        MyFile save = upload.toMyFile();
        fileMapper.insert(save);
        return DataResp.ok(computeImageLink(upload));
    }

    @Override
    public Resp delete(String fileName) {
        MyFile dbFile = fileMapper.getByFileName(fileName);
        if (dbFile.getFilePlace().equals("本地")) {
            String filePath = dbFile.getFilePath() + "\\" + dbFile.getFileName();
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
        fileMapper.deleteStatus(fileName);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

    private String computeImageLink(FileInfo info) {
        if (FileClientType.QINIUYUN.equals(info.getFileClient())) {
            return info.getFileLink();
        }
        HttpServletRequest request = WebUtil.getRequest();
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                info.getFolderPath() +
                info.getFileName();
    }

}
