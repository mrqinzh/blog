package com.mrqinzh.blog.model.dto.resp;

import com.mrqinzh.blog.model.enums.AppStatus;
import lombok.Data;

@Data
public class BaseResp {

    private Integer code;

    private String msg;

    public BaseResp(AppStatus status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

}
