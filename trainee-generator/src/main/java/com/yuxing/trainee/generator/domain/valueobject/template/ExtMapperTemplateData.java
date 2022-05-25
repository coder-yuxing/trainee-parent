package com.yuxing.trainee.generator.domain.valueobject.template;

import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
import lombok.Data;

/**
 * mapper文件模板所需数据
 *
 * @author yuxing
 */
@Data
public class ExtMapperTemplateData {

    public ExtMapperTemplateData(GlobalConfig config, TableConfig tableConfig) {
        this.namespace = config.getBeanMapperPackage() + "." + tableConfig.getBeanName() + "ExtMapper";
        this.sourceBaseColumnList = config.getBeanMapperPackage() + "." + tableConfig.getBeanName() + "Mapper.Base_Column_List";
        this.name = tableConfig.getBeanName() + "ExtMapper";
    }

    /**
     * mapper namespace
     */
    private String namespace;

    private String sourceBaseColumnList;

    /**
     * 文件名
     */
    private String name;
}
