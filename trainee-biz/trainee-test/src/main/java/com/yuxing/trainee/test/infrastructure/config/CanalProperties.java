package com.yuxing.trainee.test.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * canal配置属性值
 *
 * @author yuxing
 * @since 2022/1/18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "trainee-test.canal.consumer")
public class CanalProperties {

    private Set<String> monitorTables;

    private String topic;

    private String group;
}
