package com.yuxing.trainee.generator.domain.valueobject.datatype;

import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mysql 与 java 数据类型映射表
 *
 * @author yuxing
 */
@AllArgsConstructor
public enum MySqlAndJavaDataTypeMappingTable implements DataTypeMapping {

    /**
     * 类型映射
     */
    CHAR_2_STRING("CHAR", "String", false, "java.lang.String"),

    VARCHAR_2_STRING("VARCHAR", "String", false, "java.lang.String"),

    DECIMAL_2_BIG_DECIMAL("DECIMAL", "BigDecimal", true, "java.math.BigDecimal"),

    TINYINT_2_INTEGER("TINYINT",  "Integer", false, "java.lang.Integer"),

    TINYINT_2_BOOLEAN("TINYINT", "Boolean",  false, "java.lang.Boolean"),

    BIGINT_2_LONG("BIGINT", "Long", false, "java.lang.Long"),

    DOUBLE_2_DOUBLE("DOUBLE", "Double", false, "java.lang.Double"),

    TIMESTAMP_2_DATE("TIMESTAMP", "Date", true, "java.util.Date"),

    DATETIME_2_DATE("DATETIME", "Date", true, "java.util.Date"),

    INTEGER_2_INTEGER("INT", "Integer", false, "java.lang.Integer"),

    LONG_TEXT_2_STRING("LONGTEXT", "String", false, "java.lang.String"),
    ;


    public final String dbDataType;

    public final String javaType;

    public final boolean needImport;

    public final String className;

    @Override
    public boolean shouldImport() {
        return this.needImport;
    }

    @Override
    public String getShortName() {
        return this.javaType;
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public boolean isBoolType() {
        return "Boolean".equalsIgnoreCase(this.javaType);
    }

    public static List<MySqlAndJavaDataTypeMappingTable> listByDbDataType(String dbDataType) {
        if (StringUtils.isEmpty(dbDataType)) {
            return Collections.emptyList();
        }
        return Arrays.stream(MySqlAndJavaDataTypeMappingTable.values()).filter(t -> t.dbDataType.equalsIgnoreCase(dbDataType)).collect(Collectors.toList());
    }
}
