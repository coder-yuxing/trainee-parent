package com.yuxing.trainee.generator.domain.valueobject.config;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * 全局配置
 *
 * @author yuxing
 */
@Getter
public class GlobalConfig {

    private static final String DEFAULT_VALUE = "-";

    private static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";

    private static final String DEFAULT_MODULE_NAME = "generate";

    private static final String DEFAULT_PACKAGE = "generate";

    public GlobalConfig(String configPath, DatabaseType databaseType, List<TableConfig> tableConfigs) {
        this.configPath = configPath;
        this.databaseType = databaseType;
        this.tableConfigs = tableConfigs;
    }

    /**
     * 配置文件路径
     */
    private final String configPath;

    /**
     * 数据库类型
     */
    private final DatabaseType databaseType;

    /**
     * 数据表配置
     */
    private final List<TableConfig> tableConfigs;

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
     * 需求扩展Mapper
     */
    private boolean needExtMapper;

    public void setCover(boolean cover) {
        isCover = cover;
    }

    public void setAuthor(String author) {
        this.author = Optional.ofNullable(author).orElse(DEFAULT_VALUE);
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = Optional.ofNullable(datePattern).orElse(DEFAULT_DATE_PATTERN);
    }

    public void setBeanModuleName(String beanModuleName) {
        this.beanModuleName = Optional.ofNullable(beanModuleName).orElse(DEFAULT_MODULE_NAME);
    }

    public void setBeanPackage(String beanPackage) {
        this.beanPackage = Optional.ofNullable(beanPackage).orElse(DEFAULT_PACKAGE);
    }

    public void setMapperModuleName(String mapperModuleName) {
        this.mapperModuleName = Optional.ofNullable(mapperModuleName).orElse(DEFAULT_MODULE_NAME);
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = Optional.ofNullable(mapperPackage).orElse(DEFAULT_PACKAGE);
    }

    public void setBeanMapperPackage(String beanMapperPackage) {
        this.beanMapperPackage = Optional.ofNullable(beanMapperPackage).orElse(DEFAULT_PACKAGE);
    }

    public void setNeedExtMapper(boolean needExtMapper) {
        this.needExtMapper = needExtMapper;
    }
}
