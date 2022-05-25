package com.yuxing.trainee.generator.domain.valueobject.template;

import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * BeanMapper 对象模板所需数据
 *
 * @author yuxing
 */
@Data
public class BeanExtMapperTemplateData {

    public BeanExtMapperTemplateData(GlobalConfig config, TableConfig tableConfig) {
        this.packageName = config.getBeanMapperPackage();
        this.remarks = tableConfig.getRemarks();
        this.author = config.getAuthor();
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern(config.getDatePattern()));
        this.name = tableConfig.getBeanName() + "ExtMapper";
        this.baseName = tableConfig.getBeanName() + "Mapper";
    }

    /**
     * 所属包名
     */
    private String packageName;

    /**
     * 类注释
     */
    private String remarks;

    /**
     * 创建人
     */
    private String author;

    /**
     * 创建时间
     */
    private String date;

    /**
     * beanExtMapper 名称
     */
    private String name;

    /**
     * beanMapper 名称
     */
    private String baseName;

}
