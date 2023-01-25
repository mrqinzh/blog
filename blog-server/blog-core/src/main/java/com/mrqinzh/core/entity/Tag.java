package com.mrqinzh.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tag")
@Data
public class Tag extends BaseEntity {

    private String tagName;

    private String tagImg;

}
