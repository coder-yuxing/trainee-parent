package com.yuxing.trainee.generator.domain.service;

import com.yuxing.trainee.generator.domain.valueobject.ColumnMetadata;
import com.yuxing.trainee.generator.domain.valueobject.DataTypeMapping;
import com.yuxing.trainee.generator.domain.valueobject.DatabaseType;
import com.yuxing.trainee.generator.infrastructure.util.StringUtils;
import com.yuxing.trainee.generator.infrastructure.util.jdbc.JdbcUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class ColumnMetadataService {

    private static final String sql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns\n" +
            " \t\t\twhere table_name = \"{0}\" and table_schema = (select database()) order by ordinal_position";

    private final JdbcUtils jdbcUtils;
    private final DatabaseType databaseType;
    private final DataTypeMappingService dataTypeMappingService;

    /**
     * 查询指定数据表列元数据集合
     *
     * @param tableName 数据表名称
     * @return 表全部列元数据集合
     */
    public List<ColumnMetadata> listByTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            log.debug("表名为空, 无法获取元数据");
            return Collections.emptyList();
        }

        Function<ResultSet, List<ColumnMetadata>> parseMetaData = this::parseMetaData;
        log.debug(">>> 开始查询数据表 {} 元数据: ", tableName);
        List<ColumnMetadata> columnMetadata = jdbcUtils.executeQuery(MessageFormat.format(sql, tableName), parseMetaData);
        log.debug("查询数据表 {} 元数据结束 <<<", tableName);
        return columnMetadata;
    }

    public List<ColumnMetadata> parseMetaData(ResultSet metaData) {
        try {
            List<ColumnMetadata> columns = new ArrayList<>(metaData.getRow());
            while (metaData.next()) {
                ColumnMetadata columnMetaData = new ColumnMetadata();
                String columnName = metaData.getString("columnName");
                String dbDataType = metaData.getString("dataType");
                String remarks = metaData.getString("columnComment");
                String key = metaData.getString("columnKey");
                columnMetaData.setColumnName(columnName);
                columnMetaData.setFieldName(StringUtils.underline2Hump(columnName));
                DataTypeMapping dataTypeMapping = dataTypeMappingService.getByDatabaseTypeAndSqlType(this.databaseType, dbDataType);
                columnMetaData.setDataTypeMapping(dataTypeMapping);
                columnMetaData.setRemarks(remarks);
                boolean isPrimaryKey = !StringUtils.isEmpty(key);
                columnMetaData.setIsPrimaryKey(isPrimaryKey);
                columns.add(columnMetaData);
                log.debug("获取到数据列：columnName: {}, dbDataType: {}, remarks: {}, isPrimaryKey: {}, DataTypeMapping: {}", columnName, dbDataType, remarks, isPrimaryKey, dataTypeMapping);
            }
            return columns;
        } catch (Exception e) {
            log.error("遍历获取元数据失败", e);
        }
        return Collections.emptyList();
    }
}
