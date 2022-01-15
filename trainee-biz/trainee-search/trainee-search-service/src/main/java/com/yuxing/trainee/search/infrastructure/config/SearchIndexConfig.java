package com.yuxing.trainee.search.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 索引文档配置
 *
 * @author yuxing
 * @since 2022/1/14
 */
@Data
@Configuration("searchIndexConfig")
@ConfigurationProperties(prefix = "trainee-search.index")
public class SearchIndexConfig {

    /**
     * 素材索引名称
     */
    private String goods;

}
