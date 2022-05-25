package generate;

import com.yuxing.trainee.generator.application.Generate;
import com.yuxing.trainee.generator.application.command.GenerateMapperFileCommand;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;

import java.util.Arrays;
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
                .builder("t_user")
                .beanName("User")
                .remarks("用户")
                .build();
        //
        // GenerateMapperFileCommand.TableConfig tableConfig2 = GenerateMapperFileCommand.TableConfig
        //         .builder("t_goods_prop")
        //         .beanName("GoodsProp")
        //         .remarks("素材属性属性值")
        //         .build();

        List<GenerateMapperFileCommand.TableConfig> tableConfigs = Arrays.asList(tableConfig1);
        GenerateMapperFileCommand command = GenerateMapperFileCommand.builder("generate.properties", DatabaseType.MYSQL, tableConfigs)
                .author(AUTHOR)
                .datePattern(FORMAT)
                .isCover(true)
                .needExtMapper(true)
                .beanModuleName("trainee-biz/trainee-test")
                .beanPackage("com.yuxing.trainee.test.infrastructure.dao.model")
                .mapperModuleName("trainee-biz/trainee-test")
                .mapperPackage("com.yuxing.trainee.test.infrastructure.dao.xml")
                .beanMapperPackage("com.yuxing.trainee.test.infrastructure.dao.mapper")
                .build();

        new Generate().execute(command);

    }

}
