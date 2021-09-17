package com.mrqinzh.blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag implements Serializable {

    private Integer id;

    private String tagName;

    private String tagImg;

}
