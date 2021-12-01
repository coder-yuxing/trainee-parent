package com.yuxing.trainee.generator.domain.valueobject;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 全局配置
 *
 * @author yuxing
 */
@Data
@Builder
public class GlobalConfig {

    /**
     * 配置文件路径
     */
    private String configPath;

    /**
     * 数据库类型
     */
    private DatabaseType databaseType;

    /**
     * bean所属模块名称
     */
    private String beanModuleName;

    /**
     * bean对象所在包名
     */
    private String beanPackage;

    /**
     * mapper文件所属模块名称
     */
    private String mapperModuleName;

    /**
     * mapper文件所属包名
     */
    private String mapperPackage;

    /**
     * BeanMapper文件所属包名
     */
    private String beanMapperPackage;

    /**
     * 数据表配置
     */
    private List<TableConfig> tableConfigs;

    /**
     * 是否覆盖文件
     */
    private boolean isCover;

    /**
     * 创建人
     */
    private String author;

    /**
     * 日期格式
     */
    private String datePattern;

}
