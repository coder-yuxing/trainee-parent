package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.valueobject.*;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GenerateTemplateMetaDataService {

    private final ColumnMetadataService columnMetadataService;

    public List<GenerateTemplateMetadata> getGenerateTemplateMetadata(GlobalConfig config) {
        List<TableConfig> tableConfigs = config.getTableConfigs();
        if (Objects.isNull(tableConfigs) || tableConfigs.isEmpty()) {
            return Collections.emptyList();
        }

        return tableConfigs.stream().map(t -> {
            List<ColumnMetadata> columns = columnMetadataService.listByTableName(t.getTableName());
            return new GenerateTemplateMetadata(config, t, columns);
        }).collect(Collectors.toList());
    }
}
