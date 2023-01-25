package com.mrqinzh.core.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.core.properties.GlobalProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class QiNiuYunClient extends AbstractFileClient<QiNiuYunResult> {

    public static final String QINIU_FILE_PRE_KEY = "oss:qiniuyun:";

    @Value("${oos.qiniu.domain}")
    private String domain;
    @Value("${oos.qiniu.bucketname}")
    private String bucketName;
    @Value("${oos.qiniu.access-key}")
    private String accessKey;
    @Value("${oos.qiniu.secret-key}")
    private String secretKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public QiNiuYunResult upload(byte[] file) throws Exception {
        Configuration cfg = new Configuration(Region.huadong());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucketName);

        String newFileName = getFileName();
        Response response = uploadManager.put(file, newFileName, uploadToken);
        Map res = objectMapper.readValue(response.bodyString(), Map.class);
        String url = GlobalProperties.MY_HTTP + domain + "/" + res.get("key");

        QiNiuYunResult result = new QiNiuYunResult();
        result.setFileLink(url);
        result.setFileName(newFileName);
        return result;
    }

    @Override
    protected String getFileName() {
        return QINIU_FILE_PRE_KEY + UUID.randomUUID();
    }

}
