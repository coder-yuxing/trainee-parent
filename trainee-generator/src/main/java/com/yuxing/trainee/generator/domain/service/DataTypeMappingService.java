package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;

/**
 * 数据类型映射服务
 *
 * @author yuxing
 */
public interface DataTypeMappingService {

    /**
     * 查询数据类型映射
     *
     * @param columnName 列名
     * @param dbDataType sql数据类型
     * @return DataTypeMapping
     */
    DataTypeMapping getByDbDataType(String columnName, String dbDataType);
}
