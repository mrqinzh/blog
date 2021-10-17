package com.mrqinzh.blog.model.vo.email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "邮件信息类", description = "前端传入要发送的邮件信息")
public class EmailVO {

    @NotNull
    @ApiModelProperty(value = "邮件主题")
    private String emailTitle;

    @NotNull
    @ApiModelProperty(value = "邮件内容")
    private String emailContent;

    @NotNull
    @ApiModelProperty(value = "收件人")
    private String to;

    private String filePath;

}
