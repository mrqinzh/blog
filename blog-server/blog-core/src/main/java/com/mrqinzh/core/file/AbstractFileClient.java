package com.mrqinzh.core.file;

import com.mrqinzh.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractFileClient<T extends FileInfo> implements FileClient {

    public final FileInfo upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        FileInfo res = null;
        try {
            res = upload(file.getBytes());
            res.setFileType(FileUtil.getSuffix(originalFilename));
            res.setFileSize(FileUtil.getFileSize(file.getSize()));
        } catch (Exception e) {
            log.error(this.getClass().getName() + "上传文件失败", e);
        }
        return res;
    }

    public abstract T upload(byte[] file) throws Exception;

    protected abstract String getFileName();

}
