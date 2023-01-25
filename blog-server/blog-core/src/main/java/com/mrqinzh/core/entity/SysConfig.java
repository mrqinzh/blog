package com.mrqinzh.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig extends BaseEntity {

    private String configKey;

    private String configValue;
}
