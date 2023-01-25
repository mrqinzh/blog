package com.mrqinzh.core.file;

import com.mrqinzh.core.entity.MyFile;
import lombok.Data;

import java.util.Date;

@Data
public abstract class FileInfo {

    private String fileSize;
    private String fileName;
    private String fileType;


    public MyFile toMyFile() {
        MyFile file = new MyFile();
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setFileSize(fileSize);
        file.setFilePath(getFilePath());
        file.setFilePlace(getFilePlace().name());
        file.setFileCreateTime(new Date());
        return file;
    }

    public abstract String getFilePath();
    public abstract FilePlace getFilePlace();

}
