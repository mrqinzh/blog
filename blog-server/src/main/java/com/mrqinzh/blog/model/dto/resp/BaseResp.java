package com.mrqinzh.blog.model.dto.resp;

import com.mrqinzh.blog.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class BaseResp {

    private Integer code;

    private String msg;

    public BaseResp(AppStatus status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

}
