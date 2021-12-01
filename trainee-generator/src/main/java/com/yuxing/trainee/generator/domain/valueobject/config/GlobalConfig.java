package com.yuxing.trainee.generator.domain.valueobject.config;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;
import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * 全局配置
 *
 * @author yuxing
 */
@Data
public class GlobalConfig {

    GlobalConfig(String configPath, DatabaseType databaseType, String beanModuleName, String beanPackage, String mapperModuleName, String mapperPackage, String beanMapperPackage, List<TableConfig> tableConfigs, boolean isCover, String author, String datePattern) {
        this.configPath = configPath;
        this.databaseType = databaseType;
        this.beanModuleName = beanModuleName;
        this.beanPackage = beanPackage;
        this.mapperModuleName = mapperModuleName;
        this.mapperPackage = mapperPackage;
        this.beanMapperPackage = beanMapperPackage;
        this.tableConfigs = tableConfigs;
        this.isCover = isCover;
        this.author = author;
        this.datePattern = datePattern;
    }

    public static GlobalConfig.GlobalConfigBuilder builder() {
        return new GlobalConfig.GlobalConfigBuilder();
    }

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

    public static class GlobalConfigBuilder {
        private String configPath;
        private DatabaseType databaseType;
        private String beanModuleName;
        private String beanPackage;
        private String mapperModuleName;
        private String mapperPackage;
        private String beanMapperPackage;
        private List<TableConfig> tableConfigs;
        private boolean isCover;
        private String author;
        private String datePattern;

        GlobalConfigBuilder() {
        }

        public GlobalConfig.GlobalConfigBuilder configPath(String configPath) {
            this.configPath = configPath;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder databaseType(DatabaseType databaseType) {
            this.databaseType = databaseType;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder beanModuleName(String beanModuleName) {
            this.beanModuleName = beanModuleName;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder beanPackage(String beanPackage) {
            this.beanPackage = beanPackage;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder mapperModuleName(String mapperModuleName) {
            this.mapperModuleName = mapperModuleName;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder mapperPackage(String mapperPackage) {
            this.mapperPackage = mapperPackage;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder beanMapperPackage(String beanMapperPackage) {
            this.beanMapperPackage = beanMapperPackage;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder tableConfigs(List<TableConfig> tableConfigs) {
            this.tableConfigs = tableConfigs;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder isCover(boolean isCover) {
            this.isCover = isCover;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder author(String author) {
            this.author = author;
            return this;
        }

        public GlobalConfig.GlobalConfigBuilder datePattern(String datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        public GlobalConfig build() {
            if (StringUtils.isEmpty(this.configPath)) {
                throw new IllegalArgumentException("configPath 不允许为空");
            }

            if (Objects.isNull(this.databaseType)) {
                throw new IllegalArgumentException("databaseType 不允许为空");
            }

            return new GlobalConfig(this.configPath, this.databaseType, this.beanModuleName, this.beanPackage, this.mapperModuleName, this.mapperPackage, this.beanMapperPackage, this.tableConfigs, this.isCover, this.author, this.datePattern);
        }

        public String toString() {
            return "GlobalConfig.GlobalConfigBuilder(configPath=" + this.configPath + ", databaseType=" + this.databaseType + ", beanModuleName=" + this.beanModuleName + ", beanPackage=" + this.beanPackage + ", mapperModuleName=" + this.mapperModuleName + ", mapperPackage=" + this.mapperPackage + ", beanMapperPackage=" + this.beanMapperPackage + ", tableConfigs=" + this.tableConfigs + ", isCover=" + this.isCover + ", author=" + this.author + ", datePattern=" + this.datePattern + ")";
        }
    }

}
