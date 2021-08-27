package com.mrqinzh.blog.model.dto.resp;

import com.github.pagehelper.PageInfo;
import com.mrqinzh.blog.model.enums.AppStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class PageResp<T> extends Resp {

    private T data;

    private PageResp(T data) {
        super(AppStatus.SUCCESS);
        this.data = data;
    }

    public static <T> PageResp<T> ok(List<T> listData) {
        PageInfo<T> pageInfo = new PageInfo<>(listData);
        Map<String, Object> map = new HashMap<>(8);

        map.put("currentPage", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("totalCount", pageInfo.getTotal());
        map.put("totalPage", pageInfo.getPages());
        map.put("rows", pageInfo.getList());
        return new PageResp(map);
    }

}
