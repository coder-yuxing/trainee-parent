package com.yuxing.trainee.generator.application.command;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;
import lombok.Getter;

import java.util.List;

/**
 * 初始化mapper文件命令
 *
 * @author yuxing
 */
@Getter
public class GenerateMapperFileCommand {

    public GenerateMapperFileCommand(GenerateMapperFileCommandBuilder builder) {
        this.configPath = builder.configPath;
        this.databaseType = builder.databaseType;
        this.tableConfigs = builder.tableConfigs;
    }

    public static GenerateMapperFileCommandBuilder builder(String configPath, DatabaseType databaseType, List<TableConfig> tableConfigs) {
        return new GenerateMapperFileCommandBuilder(configPath, databaseType, tableConfigs);
    }

    public static class GenerateMapperFileCommandBuilder {

        public GenerateMapperFileCommandBuilder(String configPath, DatabaseType databaseType, List<TableConfig> tableConfigs) {
            this.configPath = configPath;
            this.databaseType = databaseType;
            this.tableConfigs = tableConfigs;
        }

        public GenerateMapperFileCommandBuilder isCover(boolean cover) {
            isCover = cover;
            return this;
        }

        public GenerateMapperFileCommandBuilder author(String author) {
            this.author = author;
            return this;
        }

        public GenerateMapperFileCommandBuilder datePattern(String datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        public GenerateMapperFileCommandBuilder beanModuleName(String beanModuleName) {
            this.beanModuleName = beanModuleName;
            return this;
        }

        public GenerateMapperFileCommandBuilder beanPackage(String beanPackage) {
            this.beanPackage = beanPackage;
            return this;
        }

        public GenerateMapperFileCommandBuilder mapperModuleName(String mapperModuleName) {
            this.mapperModuleName = mapperModuleName;
            return this;
        }

        public GenerateMapperFileCommandBuilder mapperPackage(String mapperPackage) {
            this.mapperPackage = mapperPackage;
            return this;
        }

        public GenerateMapperFileCommandBuilder beanMapperPackage(String beanMapperPackage) {
            this.beanMapperPackage = beanMapperPackage;
            return this;
        }

        public GenerateMapperFileCommand build() {
            GenerateMapperFileCommand command = new GenerateMapperFileCommand(this);
            command.isCover = this.isCover;
            command.author = this.author;
            command.datePattern = this.datePattern;
            command.beanPackage = this.beanPackage;
            command.beanModuleName = this.beanModuleName;
            command.mapperModuleName = this.mapperModuleName;
            command.mapperPackage = mapperPackage;
            command.beanMapperPackage = this.beanMapperPackage;
            return command;
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


    @Getter
    public static class TableConfig {

        public TableConfig(TableConfigBuilder builder) {
            this.tableName = builder.tableName;
        }

        public static TableConfigBuilder builder(String tableName) {
            return new TableConfigBuilder(tableName);
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

        @Getter
        public static class TableConfigBuilder {

            public TableConfigBuilder(String tableName) {
                this.tableName = tableName;
            }

            public TableConfigBuilder beanName(String beanName) {
                this.beanName = beanName;
                return this;
            }

            public TableConfigBuilder remarks(String remarks) {
                this.remarks = remarks;
                return this;
            }

            public TableConfig build() {
                TableConfig tableConfig = new TableConfig(this);
                tableConfig.remarks = this.remarks;
                tableConfig.beanName = this.beanName;
                return tableConfig;
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

    }


}
