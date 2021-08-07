package com.mrqinzh.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {

    private static final String baseFolderPath = "/files/";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static String getFilePath(HttpServletRequest request, MultipartFile file) {
        String filePath = sdf.format(new Date());
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(baseFolderPath)
                .append(filePath);
        try {
            File dest = new File(baseFolder, imgName);
            FileCopyUtils.copy(file.getBytes(), dest);
            url.append("/").append(imgName);

            return url.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
