package com.yuxing.trainee.test.infrastructure.config;

import com.yuxing.trainee.core.logtrace.LogTraceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * web mvc 配置
 *
 * @author yuxing
 * @since 2022/1/19
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.logTraceInterceptor()).addPathPatterns("/**");
    }

    /**
     * 注册日志追踪拦截器到spring容器
     */
    @Bean
    public LogTraceInterceptor logTraceInterceptor() {
        return new LogTraceInterceptor();
    }
}
