package com.mrqinzh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class File implements Serializable {

    private Integer id;

    private String fileName;

    private String filePath;

    private String fileType;

    private Date fileCreateDate;

    private String fileSize;

    private String fileSuffix;

}
