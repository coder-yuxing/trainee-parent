package com.yuxing.trainee.generator.domain.valueobject.template;

import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
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
public class BeanMapperTemplateData {

    public BeanMapperTemplateData(GlobalConfig config, TableConfig tableConfig) {
        this.packageName = config.getBeanMapperPackage();
        this.beanClassName = config.getBeanPackage() + "." + tableConfig.getBeanName();
        this.remarks = tableConfig.getRemarks();
        this.author = config.getAuthor();
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern(config.getDatePattern()));
        this.name = tableConfig.getBeanName() + "Mapper";
        this.beanName = tableConfig.getBeanName();
        this.paramName = StringUtils.toUpperCaseFirstLetter(tableConfig.getBeanName());
    }

    /**
     * 所属包名
     */
    private String packageName;

    /**
     * bean全类名
     */
    private String beanClassName;

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
     * beanMapper 名称
     */
    private String beanMapperName;

    /**
     * bean 名称
     */
    private String beanName;

    /**
     * 入参名称
     */
    private String paramName;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 主键数据类型
     */
    private DataTypeMapping primaryKeyDataType;
}
