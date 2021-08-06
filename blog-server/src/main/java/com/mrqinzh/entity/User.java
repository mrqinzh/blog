package com.mrqinzh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private int id;
    private String uid;

    private String name;
    private String uname;
    private String head_img;

    private String sex;
    private String tel;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;
    private String email;

    private String password;
    private String salt;

    @JsonIgnore
    private String perms;
}
