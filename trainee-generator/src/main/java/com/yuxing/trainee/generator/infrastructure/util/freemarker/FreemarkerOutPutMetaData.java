package com.yuxing.trainee.generator.infrastructure.util.freemarker;

import lombok.Data;

/**
 * @author yuxing
 */
@Data
public class FreemarkerOutPutMetaData {

    public FreemarkerOutPutMetaData() {}

    private String templatePath = "/template";

    private String templateName;

    private String fileName;

    private boolean isCover;

    private Object data;

}
