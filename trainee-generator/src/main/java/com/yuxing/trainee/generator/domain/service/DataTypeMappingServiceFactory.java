package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.service.impl.MySqlDataTypeMappingServiceImpl;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;

/**
 * 数据映射服务工厂
 *
 * @author yuxing
 */
public abstract class DataTypeMappingServiceFactory {

    public static DataTypeMappingService getInstance(DatabaseType databaseType) {
        if (databaseType == DatabaseType.MYSQL) {
            return new MySqlDataTypeMappingServiceImpl();
        }
        return null;
    }
}
