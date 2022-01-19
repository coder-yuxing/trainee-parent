package com.yuxing.trainee.generator.domain.valueobject.template;

import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * mapper文件模板所需数据
 *
 * @author yuxing
 */
@Data
public class MapperTemplateData {

    public MapperTemplateData(GlobalConfig config, TableConfig tableConfig, List<ColumnMetadata> columns) {
        this.namespace = config.getBeanMapperPackage() + "." + tableConfig.getBeanName() + "Mapper";
        this.beanClassName = config.getBeanPackage() + "." + tableConfig.getBeanName();
        this.resultMap = columns;
        this.columnNameList = this.assemble2ColumnNameList(columns);
        this.tableName = tableConfig.getTableName();
        Optional<ColumnMetadata> first = columns.stream().filter(ColumnMetadata::getIsPrimaryKey).findFirst();
        if (first.isPresent()) {
            ColumnMetadata columnMetadata = first.get();
            this.idColumnName = columnMetadata.getColumnName();
            this.idType = columnMetadata.getDataTypeMapping().getClassName();
        }
        this.name = tableConfig.getBeanName() + "Mapper";
    }

    private String assemble2ColumnNameList(List<ColumnMetadata> columns) {
        return columns.stream().map(ColumnMetadata::getColumnName).collect(Collectors.joining(", "));
    }

    /**
     * mapper namespace
     */
    private String namespace;

    /**
     * bean 全类名
     */
    private String beanClassName;

    /**
     * 字段映射
     */
    private List<ColumnMetadata> resultMap;

    /**
     * 表列名拼接  ,
     */
    private String columnNameList;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 文件名
     */
    private String name;

    /**
     * 主键类型
     */
    private String idType;

    /**
     * id列名
     */
    private String idColumnName;

    /**
     * 存在逻辑删除字段
     */
    public boolean getHasLogicDeletedField() {
        return this.columnNameList.contains("is_deleted");
    }
}
