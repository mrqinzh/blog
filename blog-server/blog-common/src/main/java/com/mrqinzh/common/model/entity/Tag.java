package com.mrqinzh.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("tag")
@Data
public class Tag implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String tagName;

    private String tagImg;

}
