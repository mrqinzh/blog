package com.mrqinzh.blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyFile implements Serializable {

    private Integer id;

    private String fileName;

    private String filePath;

    private String fileType;

    private Date fileCreateTime;

    private String fileSize;

}
