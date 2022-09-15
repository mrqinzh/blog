package com.mrqinzh.common.util;

import com.mrqinzh.common.constant.FileConstant;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static Map<String, Object> getFilePath(HttpServletRequest request, MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();

        String originName = file.getOriginalFilename();

        String filePath = sdf.format(new Date());
        String fileType = originName.substring(originName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;

        File baseFolder = new File(FileConstant.BASE_FOLDER_PATH + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdir();
        }
        File realPath = new File(baseFolder, fileName);
        try {
            FileCopyUtils.copy(file.getBytes(), realPath);

            StringBuilder sb = new StringBuilder();
            sb.append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append(FileConstant.BASE_FOLDER_PATH)
                    .append(filePath)
                    .append("/")
                    .append(fileName);

            // success: 判断文件是否导入成功
            resultMap.put("success", true);
            resultMap.put("resultUrl", sb.toString());
            resultMap.put("fileType", fileType);
            resultMap.put("fileName", fileName);
            resultMap.put("filePath", baseFolder);
            return resultMap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        resultMap.put("success", false);
        return resultMap;
    }

    public static String getFileSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }

}
