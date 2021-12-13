package com.yuxing.trainee.generator.infrastructure.repository;

import com.yuxing.trainee.generator.domain.repository.DataTypeMappingRepository;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;

/**
 * 数据映射服务工厂
 *
 * @author yuxing
 */
public abstract class DataTypeMappingRepositoryFactory {

    public static DataTypeMappingRepository getInstance(DatabaseType databaseType) {
        if (databaseType == DatabaseType.MYSQL) {
            return new MySqlDataTypeMappingServiceImpl();
        }
        return null;
    }
}
