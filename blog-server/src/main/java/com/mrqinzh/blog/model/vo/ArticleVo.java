package com.mrqinzh.blog.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "添加文章请求实体")
@Data
public class ArticleVo {

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章摘要")
    private String articleSummary;

    @ApiModelProperty(value = "文章封面图")
    private String articleCoverImg;

    @ApiModelProperty(value = "文章markdown内容")
    private String articleContentMd;

    @ApiModelProperty(value = "文章标签")
    private String articleTag;

    @ApiModelProperty(value = "文章类型： 原创 --- 转载")
    private String articleType;

}
