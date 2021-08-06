package com.qin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户留言实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveMsg implements Serializable {

    private int id;
    private int user_id;
    private String msg_comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date msg_time;

    private User user;
}
