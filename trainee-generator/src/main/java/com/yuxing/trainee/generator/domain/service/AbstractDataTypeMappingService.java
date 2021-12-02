package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class AbstractDataTypeMappingService implements DataTypeMappingService {

    /**
     * bool 类型命名字段前缀集合
     */
    private static final List<String> boolPrefixSet = Arrays.asList("is", "has");

    private static final String boolJavaType = "Boolean";

    protected DataTypeMapping filter(String columnName, String dbDataType, List<? extends DataTypeMapping> dataTypeMappings) {
        if (dataTypeMappings.isEmpty()) {
            log.warn("columnName：{}, dbDataType: {} 未获取到对应的 java数据类型", columnName, dbDataType);
            return this.getEmptyDataTypeMapping();
        }

        if (dataTypeMappings.size() == 1) {
            return dataTypeMappings.get(0);
        }

        // 若字段名包含 bool 语言的前缀，则尝试寻找 bool 类型的 java 数据类型映射
        boolean hasBoolPrefix = boolPrefixSet.stream().anyMatch(columnName::startsWith);
        if (hasBoolPrefix) {
            Optional<? extends DataTypeMapping> first = dataTypeMappings.stream().filter(m -> boolJavaType.equals(m.getShortName())).findFirst();
            if (first.isPresent()) {
                return first.get();
            }
        }
        return dataTypeMappings.get(0);
    }

    protected DataTypeMapping getEmptyDataTypeMapping() {
        return new DataTypeMapping() {
            @Override
            public boolean shouldImport() {
                return false;
            }

            @Override
            public String getShortName() {
                return "";
            }

            @Override
            public String getClassName() {
                return "";
            }

            @Override
            public boolean isBoolType() {
                return false;
            }
        };
    }
}
