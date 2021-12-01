package com.yuxing.trainee.generator.domain.valueobject;

import lombok.Data;

import java.util.List;

/**
 * 代码生成所需元数据
 *
 * @author yuxing
 */
@Data
public class GenerateTemplateMetadata {

    public GenerateTemplateMetadata(GlobalConfig config, TableConfig tableConfig, List<ColumnMetadata> columns) {
        this.bean = new BeanTemplateData(config, tableConfig, columns);
        this.mapper = new MapperTemplateData(config, tableConfig, columns);
        this.beanMapper = new BeanMapperTemplateData(config, tableConfig);
    }

    /**
     * bean模板数据
     */
    private BeanTemplateData bean;

    /**
     * mapper文件模板数据
     */
    private MapperTemplateData mapper;

    /**
     * BeanMapper文件模板数据
     */
    private BeanMapperTemplateData beanMapper;
}
