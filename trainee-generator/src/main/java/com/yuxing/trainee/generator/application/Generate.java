package com.yuxing.trainee.generator.application;

import com.yuxing.trainee.generator.domain.service.ColumnMetadataService;
import com.yuxing.trainee.generator.domain.service.DataTypeMappingService;
import com.yuxing.trainee.generator.domain.service.GenerateTemplateMetaDataService;
import com.yuxing.trainee.generator.domain.valueobject.*;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerOutPutMetaData;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerUtils;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcUtils;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.yuxing.trainee.generator.infrastructure.util.LambdaExceptionUtils.rethrowConsumer;

@AllArgsConstructor
public class Generate {

    private final GlobalConfig config;

    public void execute() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils(config.getConfigPath());
        DataTypeMappingService dataTypeMappingService = new DataTypeMappingService();
        ColumnMetadataService columnMetadataService = new ColumnMetadataService(jdbcUtils, config.getDatabaseType(), dataTypeMappingService);
        GenerateTemplateMetaDataService generateTemplateMetaDataService = new GenerateTemplateMetaDataService(columnMetadataService);
        // 获取各数据表初始化模板数据
        List<GenerateTemplateMetadata> generateTemplateMetadata = generateTemplateMetaDataService.getGenerateTemplateMetadata(config);

        List<FreemarkerOutPutMetaData> collect = generateTemplateMetadata.stream()
                .map(m -> this.assemble2FreemarkerOutPutMetaData(config, m))
                .flatMap(Collection::stream).collect(Collectors.toList());
        collect.forEach(rethrowConsumer(FreemarkerUtils::write));
    }

    private List<FreemarkerOutPutMetaData> assemble2FreemarkerOutPutMetaData(GlobalConfig config, GenerateTemplateMetadata generate) {
        ArrayList<FreemarkerOutPutMetaData> result = new ArrayList<>(3);
        result.add(this.assemble2FreemarkerOutPutMetaDataByBeanData(config, generate.getBean()));
        result.add(this.assemble2FreemarkerOutPutMetaDataByBeanMapperData(config, generate.getBeanMapper()));
        result.add(this.assemble2FreemarkerOutPutMetaDataByBeanDataByMapperData(config, generate.getMapper()));
        return result;
    }

    private FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanData(GlobalConfig config, BeanTemplateData bean) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("bean.ftl");
        String replace = config.getBeanPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + bean.getName() + ".java");
        metaData.setCover(config.isCover());
        metaData.setData(bean);
        return metaData;
    }

    private FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanMapperData(GlobalConfig config, BeanMapperTemplateData beanMapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("beanMapper.ftl");
        String replace = config.getBeanMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + beanMapper.getName() + ".java");
        metaData.setCover(config.isCover());
        metaData.setData(beanMapper);
        return metaData;
    }

    private FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanDataByMapperData(GlobalConfig config, MapperTemplateData mapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("mapper.ftl");
        String replace = config.getMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + mapper.getName() + ".xml");
        metaData.setCover(config.isCover());
        metaData.setData(mapper);
        return metaData;
    }
}
