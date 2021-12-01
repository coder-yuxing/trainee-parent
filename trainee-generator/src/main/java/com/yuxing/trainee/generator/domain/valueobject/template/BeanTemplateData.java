package com.yuxing.trainee.generator.domain.valueobject.template;

import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Bean 对象模板数据
 *
 * @author yuxing
 */
@Data
public class BeanTemplateData {

    public BeanTemplateData(GlobalConfig config, TableConfig tableConfig, List<ColumnMetadata> columns) {
        this.packageName = config.getBeanPackage();
        this.importQuotes = this.assemble2ImportQuotes(columns);
        this.author = config.getAuthor();
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern(config.getDatePattern()));
        this.name = tableConfig.getBeanName();
        this.remarks = tableConfig.getRemarks();
        this.fields = this.assemble2Fields(columns);
    }

    private List<String> assemble2ImportQuotes(List<ColumnMetadata> columns) {
        return columns.stream()
                .map(ColumnMetadata::getDataTypeMapping)
                .filter(DataTypeMapping::shouldImport)
                .map(DataTypeMapping::getClassName)
                .collect(toList());
    }

    private List<Field> assemble2Fields(List<ColumnMetadata> columns) {
        return columns.stream().map(this::assemble2Field).collect(toList());
    }

    private Field assemble2Field(ColumnMetadata column) {
        Field fieldMetaData = new Field();
        fieldMetaData.setName(column.getFieldName());
        fieldMetaData.setRemarks(column.getRemarks());
        fieldMetaData.setType(column.getDataTypeMapping().getShortName());
        return fieldMetaData;
    }

    /**
     * bean 所属包
     */
    private String packageName;

    /**
     * 所需引用
     */
    private List<String> importQuotes;

    /**
     * 作者
     */
    private String author;

    /**
     * 日期
     */
    private String date;

    /**
     * bean name
     */
    private String name;

    /**
     * java bean remarks
     */
    private String remarks;

    /**
     * bean 字段列表
     */
    private List<Field> fields;

    @Data
    public static class Field {

        /**
         * 字段名
         */
        private String name;

        /**
         * 字段类型
         */
        private String type;

        /**
         * 字段注释
         */
        private String remarks;
    }
}
