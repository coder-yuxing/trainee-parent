package com.yuxing.trainee.generator.application;

import com.yuxing.trainee.generator.application.assembler.FreemarkerOutPutMetaDataAssembler;
import com.yuxing.trainee.generator.domain.service.DataTypeMappingService;
import com.yuxing.trainee.generator.domain.service.DataTypeMappingServiceFactory;
import com.yuxing.trainee.generator.domain.service.GenerateTemplateMetaDataService;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.template.GenerateTemplateMetadata;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerOutPutMetaData;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerUtils;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcUtils;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.yuxing.trainee.generator.infrastructure.util.LambdaExceptionUtils.rethrowConsumer;

@AllArgsConstructor
public class Generate {

    private final GlobalConfig config;

    public void execute() throws Exception {
        JdbcUtils jdbcUtils = new JdbcUtils(config.getConfigPath());
        DataTypeMappingService dataTypeMappingService = DataTypeMappingServiceFactory.getDataTypeMappingService(config.getDatabaseType());
        GenerateTemplateMetaDataService generateTemplateMetaDataService = new GenerateTemplateMetaDataService(jdbcUtils, dataTypeMappingService);
        // 获取各数据表初始化模板数据
        List<GenerateTemplateMetadata> generateTemplateMetadata = generateTemplateMetaDataService.getGenerateTemplateMetadata(config);

        List<FreemarkerOutPutMetaData> collect = generateTemplateMetadata.stream()
                .map(m -> FreemarkerOutPutMetaDataAssembler.assemble2FreemarkerOutPutMetaData(config, m))
                .flatMap(Collection::stream).collect(Collectors.toList());

        // 根据freemarker模板写出文件
        collect.forEach(rethrowConsumer(FreemarkerUtils::write));
    }

}
