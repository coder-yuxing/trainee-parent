package com.yuxing.trainee.generator.application.assembler;

import cn.hutool.core.collection.CollUtil;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.template.*;
import com.yuxing.trainee.generator.infrastructure.util.freemarker.FreemarkerOutPutMetaData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FreemarkerOutPutMetaDataAssembler {

    public static List<FreemarkerOutPutMetaData> assemble2FreemarkerOutPutMetaData(GlobalConfig config, List<GenerateTemplateMetadata> generateTemplateMetadata) {
        if (CollUtil.isEmpty(generateTemplateMetadata)) {
            return Collections.emptyList();
        }
        return generateTemplateMetadata.stream().map(m -> assemble2FreemarkerOutPutMetaData(config, m)).flatMap(Collection::stream).collect(Collectors.toList());
    }
    public static List<FreemarkerOutPutMetaData> assemble2FreemarkerOutPutMetaData(GlobalConfig config, GenerateTemplateMetadata generate) {
        ArrayList<FreemarkerOutPutMetaData> result = new ArrayList<>(3);
        result.add(assemble2FreemarkerOutPutMetaDataByBeanData(config, generate.getBean()));
        result.add(assemble2FreemarkerOutPutMetaDataByBeanMapperData(config, generate.getBeanMapper()));
        result.add(assemble2FreemarkerOutPutMetaDataByBeanDataByMapperData(config, generate.getMapper()));
        if (config.isNeedExtMapper() && generate.getExtMapper() != null) {
            result.add(assemble2FreemarkerOutPutMetaDataByBeanDataByExtMapperData(config, generate.getExtMapper()));
            result.add(assemble2FreemarkerOutPutMetaDataByBeanExtMapperData(config, generate.getExtBeanMapper()));
        }
        return result;
    }

    private static FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanData(GlobalConfig config, BeanTemplateData bean) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("bean.ftl");
        String replace = config.getBeanPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + bean.getName() + ".java");
        metaData.setCover(config.isCover());
        metaData.setData(bean);
        return metaData;
    }

    private static FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanMapperData(GlobalConfig config, BeanMapperTemplateData beanMapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("beanMapper.ftl");
        String replace = config.getBeanMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + beanMapper.getName() + ".java");
        metaData.setCover(config.isCover());
        metaData.setData(beanMapper);
        return metaData;
    }

    private static FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanExtMapperData(GlobalConfig config, BeanExtMapperTemplateData beanMapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("beanExtMapper.ftl");
        String replace = config.getBeanMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + beanMapper.getName() + ".java");
        metaData.setCover(config.isCover());
        metaData.setData(beanMapper);
        return metaData;
    }

    private static FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanDataByMapperData(GlobalConfig config, MapperTemplateData mapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("mapper.ftl");
        String replace = config.getMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + mapper.getName() + ".xml");
        metaData.setCover(config.isCover());
        metaData.setData(mapper);
        return metaData;
    }

    private static FreemarkerOutPutMetaData assemble2FreemarkerOutPutMetaDataByBeanDataByExtMapperData(GlobalConfig config, ExtMapperTemplateData mapper) {
        FreemarkerOutPutMetaData metaData = new FreemarkerOutPutMetaData();
        metaData.setTemplateName("mapperExt.ftl");
        String replace = config.getMapperPackage().replace(".", "/");
        metaData.setFileName("/" + config.getBeanModuleName() + "/src/main/java/" + replace + "/" + mapper.getName() + ".xml");
        metaData.setCover(config.isCover());
        metaData.setData(mapper);
        return metaData;
    }
}
