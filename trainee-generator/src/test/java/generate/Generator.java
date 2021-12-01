package generate;

import com.yuxing.trainee.generator.application.Generate;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DatabaseType;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;

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
        TableConfig tableConfig1 = TableConfig.builder().tableName("t_actuarial_ignore_update_item").beanName("ActuarialIgnoreUpdateItem").remarks("商品").build();

        List<TableConfig> tableConfigs = Collections.singletonList(tableConfig1);
        GlobalConfig globalConfig = GlobalConfig.builder()
                .configPath("generate.properties")
                .databaseType(DatabaseType.MYSQL)
                .author(AUTHOR)
                .datePattern(FORMAT)
                .isCover(true)
                .beanModuleName("trainee-biz/trainee-test")
                .beanPackage("com.yuxing.trainee.test.infrastructure.dao.model")
                .mapperModuleName("trainee-biz/trainee-test")
                .beanMapperPackage("com.yuxing.trainee.test.infrastructure.dao.mapper")
                .mapperPackage("com.yuxing.trainee.test.infrastructure.dao.xml")
                .tableConfigs(tableConfigs).build();

        new Generate(globalConfig).execute();

    }

}
