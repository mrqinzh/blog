package com.mrqinzh.service.file;

import com.mrqinzh.common.enums.FileSourceType;
import com.mrqinzh.common.enums.MimeType;
import com.mrqinzh.common.resp.Resp;

public interface FileService {

    String add(byte[] file, MimeType mimeType, FileSourceType fileSourceType);

    void delete(String fileKey);

}
