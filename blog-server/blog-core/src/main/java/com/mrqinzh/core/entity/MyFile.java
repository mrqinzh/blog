package com.mrqinzh.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@TableName("file")
@Data
@Accessors(chain = true)
public class MyFile implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String fileName;

    private String filePath;

    private String fileType;

    private Date fileCreateTime;

    private String fileSize;

    private String filePlace;

    private Integer status;

}
