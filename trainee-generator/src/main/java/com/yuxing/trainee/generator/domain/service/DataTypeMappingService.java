package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.valueobject.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.DatabaseType;
import com.yuxing.trainee.generator.domain.valueobject.MySqlAndJavaDataTypeMappingTable;

/**
 * 数据类型映射服务
 *
 * @author yuxing
 */
public class DataTypeMappingService {

    /**
     * 查询数据类型映射
     *
     * @param databaseType 数据库类型
     * @param sqlType      sql数据类型
     * @return DataTypeMapping
     */
    public DataTypeMapping getByDatabaseTypeAndSqlType(DatabaseType databaseType, String sqlType) {
        if (DatabaseType.MYSQL == databaseType) {
            return MySqlAndJavaDataTypeMappingTable.getBySqlType(sqlType).orElse(null);
        }
        return null;
    }
}
