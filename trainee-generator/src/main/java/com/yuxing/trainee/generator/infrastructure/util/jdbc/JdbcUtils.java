package com.yuxing.trainee.generator.infrastructure.util.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

/**
 * jdbc 工具类
 *
 * @author yuxing
 */
@Slf4j
public class JdbcUtils {

    private final JdbcProperties properties;

    public JdbcUtils(String configPath) {
        JdbcPropertiesHandler handler = new JdbcPropertiesHandler(configPath);

        this.properties = handler.getJdbcProperties();
        try {
            Class.forName(properties.getDriverClassName());
        } catch (Exception e) {
            log.error("加载驱动 {} 异常, 请检查依赖", properties.getDriverClassName());
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws SQLException exception
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }

    /**
     * 执行查询
     *
     * @param sql             查询语句
     * @param parseResultSet  解析 ResultSet 方法
     * @param <R>             返回值类型
     * @return 查询结果集
     */
    public <R> List<R> executeQuery(String sql, Function<ResultSet, List<R>> parseResultSet) {
        try (
            Connection connection = this.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            final ResultSet resultSet = prepareStatement.executeQuery()
        ) {
            if (resultSet != null) {
                return parseResultSet.apply(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
