package com.mrqinzh.blog.model.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "邮件信息类", description = "前端传入要发送的邮件信息")
public class EmailVO {

    private String emailTitle;

    private String emailContent;

    private String to;

    private String filePath;

}
