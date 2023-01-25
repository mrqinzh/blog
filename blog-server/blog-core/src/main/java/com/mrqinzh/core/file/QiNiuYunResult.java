package com.mrqinzh.core.file;

import lombok.Data;

@Data
public class QiNiuYunResult extends FileInfo {

    private String fileLink;

    @Override
    public String getFilePath() {
        return fileLink;
    }

    @Override
    public FilePlace getFilePlace() {
        return FilePlace.QINIUYUN;
    }
}
