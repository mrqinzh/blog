package com.mrqinzh.blog.model.dto.resp;

import com.mrqinzh.blog.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public final class DataResp<T> extends BaseResp {

    private T data;

    private DataResp(T data){
        super(AppStatus.SUCCESS);
        this.data = data;
    }

    public static <T> DataResp<T> ok(T data) {
        return new DataResp<>(data);
    }

}
