package com.yuxing.trainee.generator.infrastructure.util.jdbc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * 数据源配置处理类
 *
 * @author yuxing
 */
@Data
@Slf4j
public class JdbcPropertiesHandler {

    private static final Properties properties = new Properties();

    /**
     * 数据源配置文件地址
     */
    private final String configPath;

    public JdbcPropertiesHandler(String configPath) {
        if (Objects.isNull(configPath)) {
            log.error("数据源配置文件路径不允许为空");
            throw new NullPointerException("数据源配置文件路径不允许为空");
        }
        this.configPath = configPath;
    }

    /**
     * 读取配置文件
     *
     * @return JdbcProperties
     */
    public JdbcProperties getJdbcProperties() {
        JdbcProperties jdbcProperties = new JdbcProperties();
        try (InputStream inputStream = JdbcPropertiesHandler.class.getClassLoader().getResourceAsStream(this.configPath)) {
            properties.load(inputStream);

            jdbcProperties.setDriverClassName(properties.getProperty("driver-class-name"));
            jdbcProperties.setUrl(properties.getProperty("url"));
            jdbcProperties.setUsername(properties.getProperty("username"));
            jdbcProperties.setPassword(properties.getProperty("password"));
        } catch (Exception e) {
            log.error("读取数据源配置异常", e);
        }
        return jdbcProperties;
    }


}
