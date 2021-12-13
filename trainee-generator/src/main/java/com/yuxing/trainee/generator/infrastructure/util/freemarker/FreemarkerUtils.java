package com.yuxing.trainee.generator.infrastructure.util.freemarker;

import com.yuxing.trainee.generator.infrastructure.util.FileUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;

/**
 * freemarker 导出工具类
 * @author yuxing
 */
@Slf4j
public class FreemarkerUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_29);

    private FreemarkerUtils() {}

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }

    public static ClassTemplateLoader getFileTemplateLoader(String path) {
        return new ClassTemplateLoader(FreemarkerUtils.class, path);
    }

    /**
     * 根据模板写出文件
     * @param metaData   模板写出数据
     * @throws Exception 写出异常
     */
    public static void write(FreemarkerOutPutMetaData metaData) throws Exception {
        ClassTemplateLoader classTemplateLoader = getFileTemplateLoader(metaData.getTemplatePath());
        getConfiguration().setTemplateLoader(classTemplateLoader);
        Template template = getConfiguration().getTemplate(metaData.getTemplateName());
        String absolutePath = new File("").getAbsolutePath();
        File file = FileUtils.create(absolutePath + metaData.getFileName(), metaData.isCover());
        if (Objects.isNull(file)) {
            log.error("{} 文件创建失败, 写出程序结束。", metaData.getFileName());
            return;
        }
        try (
            OutputStream fos = new FileOutputStream(file, metaData.isCover());
            Writer out = new OutputStreamWriter(fos)
        ) {
            template.process(metaData.getData(), out);
        } catch (Exception e) {
            log.error("写出模板文件失败, ", e);
        }
    }


}
