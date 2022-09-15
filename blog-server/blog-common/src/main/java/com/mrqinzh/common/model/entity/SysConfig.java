package com.mrqinzh.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig {

    private Integer id;

    private String configKey;

    private String configValue;
}
