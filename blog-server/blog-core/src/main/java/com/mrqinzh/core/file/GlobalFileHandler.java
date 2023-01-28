package com.mrqinzh.core.file;

import com.mrqinzh.common.util.BizAssert;
import com.mrqinzh.common.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GlobalFileHandler {

    @Value("${mrqinzh.blog.oss.client:LOCAL}")
    private String fileClient;

    public FileInfo upload(MultipartFile file, FileSource source) {
        BizAssert.notNull(file, "file is not null !");
        BizAssert.notNull(source, "source is not null !");
        AbstractFileClient fileClient = getFileClient();
        return fileClient.upload(file, source);
    }

    private AbstractFileClient getFileClient() {
        FileClientType fileClientType = null;
        try {
            fileClientType = FileClientType.valueOf(fileClient);
        } catch (IllegalArgumentException e) {
            return new LocalFileClient();
        }
        switch (fileClientType) {
            case QINIUYUN:
                return SpringContextUtil.getBean(QiNiuYunClient.class);
            case LOCAL:
            default:
                return SpringContextUtil.getBean(LocalFileClient.class);
        }
    }

}
