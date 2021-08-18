package com.mrqinzh.blog.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mrqinzh.blog.mapper.FileMapper;
import com.mrqinzh.blog.model.entity.MyFile;
import com.mrqinzh.blog.service.FileService;
import com.mrqinzh.blog.util.FileUtil;
import com.mrqinzh.blog.util.Resp;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @Transactional(rollbackFor = Exception.class)
    public Resp add(HttpServletRequest request, MultipartFile file) {

        Map<String, Object> fileData = FileUtil.getFilePath(request, file);
        if (!((Boolean) fileData.get("status"))) {
            return Resp.error(200, "文件上传失败");
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

        boolean add = fileMapper.add(dbFile);
        if (add) {
            return Resp.ok(fileData.get("resultUrl").toString());
        }
        return Resp.error(200, "数据库保存错误");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp delete(String condition) {
        MyFile dbFile = fileMapper.getOne(condition);
        String filePath =  dbFile.getFilePath() + "\\" + dbFile.getFileName();
        File file = new File(filePath);
        if (!file.exists()) {
            return Resp.ok("文件不存在");
        }
        if (file.delete()) {
            return Resp.ok("文件已删除");
        }
        fileMapper.delete(condition);

        return Resp.error(200, "错误操作");
    }

    public Resp uploadToQiNiu(MultipartFile uploadFile) throws IOException {
        Configuration cfg = new Configuration(Region.huadong());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        String fileName = uploadFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + suffix;

        Response response = uploadManager.put(uploadFile.getBytes(), fileName, upToken);
        System.out.println("res.bodyString() = " + response.bodyString());
        JSONObject res = JSONObject.parseObject(response.bodyString());

        String url = domain + "/" + res.getString("key");
        return Resp.ok(url);
    }

}
