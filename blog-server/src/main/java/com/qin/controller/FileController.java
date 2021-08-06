package com.qin.controller;

import com.qin.pojo.Resp;
import com.qin.pojo.User;
import com.qin.service.UserService;
import com.qin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    private UserService userService;

    private static final String baseFolderPath = "/files/";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @DeleteMapping("/delImg/{url}")
    public Resp<String> delImg(@PathVariable("url") String imgUrl){
        String filePath = sdf.format(new Date());
        File file = new File(baseFolderPath + filePath + "/" + imgUrl);
        if (file.isFile() && file.delete()) {
            return Resp.success("");
        }
        return Resp.error("500","error");
    }

    /**
     * 用户上传头像
     * @param headImg   上传的图片
     * @param request   请求参数
     * @return  自定义返回类型
     */
    @PostMapping("/headImg")
    public Resp<String> uploadHeadImg(@RequestParam("file") MultipartFile headImg,
                                      HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        try {
            Resp<String> stringResp = getImgUrl(request, headImg);
            String url = stringResp.getBody();
            user.setHead_img(url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Resp<>("403","先登录，再上传吧","");
        }
        int i = userService.updateUserInfo(user);
        if (i != 1){
            return Resp.error("500","完蛋，上传失败了");
        }
        return new Resp<>("200","success",user.getHead_img());
    }

    /**
     * markdown图片上传方法
     * @param request 请求参数
     * @param image 上传图片
     * @return 自定义返回类型
     */
    @PostMapping("/uploadImg")
    public Resp<String> uploadImg(HttpServletRequest request, MultipartFile image){
        return getImgUrl(request, image);
    }

    public static Resp<String> getImgUrl(HttpServletRequest request, MultipartFile image){
        String filePath = sdf.format(new Date());
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + Objects.requireNonNull(image.getOriginalFilename()).replaceAll(" ", "");
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
            FileCopyUtils.copy(image.getBytes(), dest);
            url.append("/").append(imgName);

            return Resp.success(url.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return new Resp<>("200", "上传失败", "");
        }
    }
}
