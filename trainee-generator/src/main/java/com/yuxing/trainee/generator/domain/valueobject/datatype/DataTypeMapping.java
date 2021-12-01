package com.yuxing.trainee.generator.domain.valueobject.datatype;

/**
 * 数据类型映射
 *
 * @author yuxing
 */
public interface DataTypeMapping {

    /**
     * 是否需要引入java全类型名称
     *
     * @return bool
     */
    boolean shouldImport();

    /**
     * 获取java类型名称
     *
     * @return java类型名称
     */
    String getShortName();

    /**
     * 类名称
     *
     * @return 类名
     */
    String getClassName();
}
