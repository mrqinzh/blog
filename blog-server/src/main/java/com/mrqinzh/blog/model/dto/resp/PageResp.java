package com.mrqinzh.blog.model.dto.resp;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PageResp<T> {

    private PageData<T> data;

    private PageResp(PageData<T> data) {
        this.data = data;
    }

    public static <T> PageResp<T> of(Page<T> page) {
        PageData<T> data = new PageData<>(page);
        return new PageResp<>(data);
    }

    @Data
    public static class PageData<T> {

        @ApiModelProperty(value = "分页后的内容")
        private List<T> data;

        /**
         * 分页属性
         */
        @ApiModelProperty(value = "当前页面序号", example = "1")
        private Integer pageNum;
        @ApiModelProperty(value = "当前页面大小", example = "20")
        private Integer pageSize;
        @ApiModelProperty(value = "总页面数", example = "5")
        private Integer pages;
        @ApiModelProperty(value = "总记录数", example = "100")
        private Long total;

        PageData(Page<T> page) {
            this.data = page.getResult();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.total = page.getTotal();
        }
    }
}
