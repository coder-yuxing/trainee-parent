package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.repository.SchemaRepository;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import com.yuxing.trainee.generator.domain.valueobject.config.TableConfig;
import com.yuxing.trainee.generator.domain.valueobject.datatype.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.template.ColumnMetadata;
import com.yuxing.trainee.generator.domain.valueobject.template.GenerateTemplateMetadata;
import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcProperties;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcPropertiesHandler;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class GenerateTemplateMetaDataService {

    private final SchemaRepository schemaRepository;

    public List<GenerateTemplateMetadata> getGenerateTemplateMetadata(GlobalConfig config) {
        List<TableConfig> tableConfigs = config.getTableConfigs();
        if (Objects.isNull(tableConfigs) || tableConfigs.isEmpty()) {
            return Collections.emptyList();
        }

        return tableConfigs.stream().map(t -> {
            List<ColumnMetadata> columns = schemaRepository.listColumnMetadataByTableName(t.getTableName());
            return new GenerateTemplateMetadata(config, t, columns);
        }).collect(Collectors.toList());
    }

}
