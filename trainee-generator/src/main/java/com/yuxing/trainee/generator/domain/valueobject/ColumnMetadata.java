package com.yuxing.trainee.generator.domain.valueobject;

import lombok.Data;

/**
 * 数据表中单列元数据
 */
@Data
public class ColumnMetadata {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列映射字段名
     */
    private String fieldName;

    /**
     * 注释
     */
    private String remarks;

    /**
     * 是否为主键
     */
    private Boolean isPrimaryKey;

    /**
     * 字段类型映射
     */
    private DataTypeMapping dataTypeMapping;
}
