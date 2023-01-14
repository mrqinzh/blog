package com.mrqinzh.domain.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.core.entity.MyFile;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.DataResp;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.util.FileUtil;
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
import java.util.Map;
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

    @Override
    public Resp add(HttpServletRequest request, MultipartFile file) {

        // 将文件保存至磁盘，并返回相关参数
        Map<String, Object> fileData = FileUtil.getFilePath(request, file);
        if (!((Boolean) fileData.get("success"))) {
            throw new BizException(AppStatus.IMAGE_UPLOAD_ERROR);
        }

        String fileSize = FileUtil.getFileSize(file.getSize());
        String fileType = fileData.get("fileType").toString();
        String fileName = fileData.get("fileName").toString();
        String filePath = fileData.get("filePath").toString();

        MyFile dbFile = new MyFile();
        dbFile.setFileCreateTime(new Date());
        dbFile.setFileName(fileName);
        dbFile.setFilePath(filePath);
        dbFile.setFileSize(fileSize);
        dbFile.setFileType(fileType);
        dbFile.setFilePlace("本地");

        fileMapper.insert(dbFile);
        return DataResp.ok(fileData.get("resultUrl").toString());
    }

    @Override
    public Resp delete(String fileName) {
        MyFile dbFile = fileMapper.getByFileName(fileName);
        if (dbFile.getFilePlace().equals("本地")) {
            String filePath =  dbFile.getFilePath() + "\\" + dbFile.getFileName();
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
        fileMapper.deleteStatus(fileName);
        return Resp.sendMsg(AppStatus.DELETE_SUCCESS);
    }

    @Override
    public String uploadToQiNiu(MultipartFile uploadFile) {
        try {
            Configuration cfg = new Configuration(Region.huadong());
            UploadManager uploadManager = new UploadManager(cfg);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucketName);

            // 修改文件名
            String fileName = uploadFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + suffix;

            Response response = uploadManager.put(uploadFile.getBytes(), fileName, upToken);
            JSONObject res = JSONObject.parseObject(response.bodyString());

            String url = GlobalProperties.MY_HTTP + domain + "/" + res.getString("key");

            // 将添加的图片信息保存至数据库
            MyFile myFile = new MyFile();
            myFile.setFilePlace("七牛云")
                    .setFileName(fileName)
                    .setFileCreateTime(new Date())
                    .setFileType(suffix)
                    .setFilePath(url);

            fileMapper.insert(myFile);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(AppStatus.IMAGE_UPLOAD_ERROR);
        }

    }

}
