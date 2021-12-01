package com.yuxing.trainee.generator.domain.valueobject.config;

import lombok.Builder;
import lombok.Data;

/**
 * 表配置信息
 *
 * @author yuxing
 */
@Data
@Builder
public class TableConfig {

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 映射model对象名称
     */
    private String beanName;

    /**
     * 注释
     */
    private String remarks;
}
