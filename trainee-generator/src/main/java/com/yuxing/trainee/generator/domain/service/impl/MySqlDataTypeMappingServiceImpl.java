package com.yuxing.trainee.generator.domain.service.impl;

import com.yuxing.trainee.generator.domain.service.AbstractDataTypeMappingService;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.datatype.MySqlAndJavaDataTypeMappingTable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * MySQL 数据类型与Java数据类型映射服务类
 *
 * @author yuxing
 */
@Slf4j
public class MySqlDataTypeMappingServiceImpl extends AbstractDataTypeMappingService {

    @Override
    public DataTypeMapping getByDbDataType(String columnName, String dbDataType) {
        List<MySqlAndJavaDataTypeMappingTable> dataTypeMappings = MySqlAndJavaDataTypeMappingTable.listByDbDataType(dbDataType);
        return this.filter(columnName, dbDataType, dataTypeMappings);
    }
}
