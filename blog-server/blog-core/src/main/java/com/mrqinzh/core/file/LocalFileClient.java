package com.mrqinzh.core.file;

import org.springframework.stereotype.Component;

@Component
public class LocalFileClient extends AbstractFileClient<FileInfo> {

    @Override
    public FileInfo upload(byte[] file) throws Exception {
        return null;
    }

    @Override
    protected String getFileName() {
        return null;
    }
}
