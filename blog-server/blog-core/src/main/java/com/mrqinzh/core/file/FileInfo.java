package com.mrqinzh.core.file;

import com.mrqinzh.core.entity.MyFile;
import lombok.Data;

import java.util.Date;

@Data
public class FileInfo {

    private String fileSize;
    private String fileName;
    private String fileType;
    private String fileLink;
    private String folderPath;
    private FileClientType fileClient;

    public MyFile toMyFile() {
        MyFile file = new MyFile();
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setFileSize(fileSize);
        file.setFilePath(fileLink);
        file.setFilePlace(fileClient.name());
        file.setFileCreateTime(new Date());
        return file;
    }

}
