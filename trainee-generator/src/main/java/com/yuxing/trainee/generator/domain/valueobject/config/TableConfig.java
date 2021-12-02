package com.yuxing.trainee.generator.domain.valueobject.config;

import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import lombok.Data;

import java.util.Optional;

/**
 * 表配置信息
 *
 * @author yuxing
 */
@Data
public class TableConfig {

    public TableConfig(String tableName) {
        this.tableName = tableName;
    }

    public void setBeanName(String beanName) {
        this.beanName = Optional.ofNullable(beanName).orElse(StringUtils.toUpperCaseFirstLetter(this.tableName));
    }

    public void setRemarks(String remarks) {
        this.remarks = Optional.ofNullable(remarks).orElse("");
    }

    /**
     * 数据表名称
     */
    private final String tableName;

    /**
     * 映射model对象名称
     */
    private String beanName;

    /**
     * 注释
     */
    private String remarks;
}
