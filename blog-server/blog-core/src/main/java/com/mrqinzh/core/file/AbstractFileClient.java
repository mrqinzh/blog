package com.mrqinzh.core.file;

import com.mrqinzh.common.constant.FileConstant;
import com.mrqinzh.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
public abstract class AbstractFileClient {

    private String folderPath;
    private String fileSuffix;
    private String newFileNameWithSuffix;

    public final FileInfo upload(MultipartFile file, FileSource source) {
        freshProperties(file, source);
        FileInfo res = null;
        try {
            doUpload(file.getBytes());
            res = new FileInfo();
            res.setFileSize(FileUtil.getFileSize(file.getSize()));
            res.setFileType(fileSuffix);
            res.setFolderPath(folderPath);
            res.setFileName(newFileNameWithSuffix);

            res.setFileClient(clientType());
            res.setFileLink(getFileLink());
        } catch (Exception e) {
            log.error(this.getClass().getName() + "上传文件失败", e);
        }
        return res;
    }

    private void freshProperties(MultipartFile file, FileSource source) {
        this.fileSuffix = FileUtil.getSuffix(file.getOriginalFilename());
        this.newFileNameWithSuffix = getFileName() + fileSuffix;
        String sourcePlace = source.getSource().endsWith("/") ? source.getSource() : source.getSource()+"/";
        this.folderPath = FileConstant.BASE_FOLDER_PATH +
                sourcePlace +
                FileConstant.filePathFormat.format(new Date()) + "/";
        FileUtil.checkFilePath(folderPath);
    }

    public abstract void doUpload(byte[] file) throws Exception;
    public abstract String getFileName();
    public abstract String getFileLink();
    public abstract FileClientType clientType();

    public String getFolderPath() {
        return folderPath;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getNewFileNameWithSuffix() {
        return newFileNameWithSuffix;
    }
}
