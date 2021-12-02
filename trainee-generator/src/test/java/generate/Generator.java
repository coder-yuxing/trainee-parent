package generate;

import com.yuxing.trainee.generator.application.Generate;
import com.yuxing.trainee.generator.application.command.GenerateMapperFileCommand;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;

import java.util.Collections;
import java.util.List;

/**
 * 代码生成工具
 *
 * @author yuxing
 */
public class Generator {

    private static final String AUTHOR = "yuxing";

    private static final String FORMAT = "yyyy/MM/dd";

    public static void main(String[] args) throws Exception {
        GenerateMapperFileCommand.TableConfig tableConfig1 = GenerateMapperFileCommand.TableConfig
                .builder("t_actuarial_ignore_update_item")
                .beanName("ActuarialIgnoreUpdateItem")
                .remarks("忽略清单更新项")
                .build();

        List<GenerateMapperFileCommand.TableConfig> tableConfigs = Collections.singletonList(tableConfig1);
        GenerateMapperFileCommand command = GenerateMapperFileCommand.builder("generate.properties", DatabaseType.MYSQL, tableConfigs)
                .author(AUTHOR)
                .datePattern(FORMAT)
                .isCover(true)
                .beanModuleName("trainee-biz/trainee-test")
                .beanPackage("com.yuxing.trainee.test.infrastructure.dao.model")
                .mapperModuleName("trainee-biz/trainee-test")
                .beanMapperPackage("com.yuxing.trainee.test.infrastructure.dao.mapper")
                .mapperPackage("com.yuxing.trainee.test.infrastructure.dao.xml")
                .build();

        new Generate().execute(command);

    }

}
