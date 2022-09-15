package com.mrqinzh.common.model.resp;

import com.mrqinzh.common.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "返回数据类")
public final class DataResp<T> extends Resp {

    private T data;

    private DataResp(T data){
        super(AppStatus.SUCCESS);
        this.data = data;
    }

    public static <T> DataResp<T> ok(T data) {
        return new DataResp<>(data);
    }

}
