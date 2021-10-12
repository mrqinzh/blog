package com.mrqinzh.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("t_message")
@ApiModel(value = "留言信息实体类")
@Data
public class MyMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String avatar;

    private String nickname;

    private String content;

    private String time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String ip;

    private Integer status;

}
