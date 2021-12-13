package com.yuxing.trainee.generator.application;

import com.yuxing.trainee.generator.application.assembler.FreemarkerOutPutMetaDataAssembler;
import com.yuxing.trainee.generator.application.assembler.GlobalConfigAssembler;
import com.yuxing.trainee.generator.application.command.GenerateMapperFileCommand;
import com.yuxing.trainee.generator.domain.repository.SchemaRepository;
import com.yuxing.trainee.generator.domain.service.GenerateTemplateMetaDataService;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.template.GenerateTemplateMetadata;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerOutPutMetaData;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerUtils;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.yuxing.trainee.generator.infrastructure.util.LambdaExceptionUtils.rethrowConsumer;

@AllArgsConstructor
public class Generate {

    public void execute(GenerateMapperFileCommand command) throws Exception {
        GlobalConfig config = GlobalConfigAssembler.INSTANCE.toConfig(command);

        SchemaRepository schemaRepository = new SchemaRepository(config.getConfigPath(), config.getDatabaseType());
        GenerateTemplateMetaDataService generateTemplateMetaDataService = new GenerateTemplateMetaDataService(schemaRepository);

        // 获取各数据表初始化模板数据
        List<GenerateTemplateMetadata> generateTemplateMetadata = generateTemplateMetaDataService.getGenerateTemplateMetadata(config);

        List<FreemarkerOutPutMetaData> collect = FreemarkerOutPutMetaDataAssembler.assemble2FreemarkerOutPutMetaData(config, generateTemplateMetadata);
        // 根据freemarker模板写出文件
        collect.forEach(rethrowConsumer(FreemarkerUtils::write));
    }

}
