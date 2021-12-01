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
     * @param sqlDataType sql数据类型
     * @return DataTypeMapping
     */
    DataTypeMapping getBySqlDataType(String sqlDataType);
}
