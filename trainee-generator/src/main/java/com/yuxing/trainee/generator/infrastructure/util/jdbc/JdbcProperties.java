package com.yuxing.trainee.generator.infrastructure.util.jdbc;

import lombok.Data;

/**
 * 数据源配置属性
 *
 * @author yuxing
 */
@Data
public class JdbcProperties {

    /**
     * 驱动名称
     */
    private String driverClassName;

    /**
     * 数据源地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
