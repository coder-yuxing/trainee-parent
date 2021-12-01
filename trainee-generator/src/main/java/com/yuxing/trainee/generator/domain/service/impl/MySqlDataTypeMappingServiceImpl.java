package com.yuxing.trainee.generator.domain.service.impl;

import com.yuxing.trainee.generator.domain.service.DataTypeMappingService;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.datatype.MySqlAndJavaDataTypeMappingTable;

/**
 * MySQL 数据类型与Java数据类型映射服务类
 *
 * @author yuxing
 */
public class MySqlDataTypeMappingServiceImpl implements DataTypeMappingService {

    @Override
    public DataTypeMapping getBySqlDataType(String sqlType) {
        return MySqlAndJavaDataTypeMappingTable.getBySqlType(sqlType).orElse(null);
    }
}
