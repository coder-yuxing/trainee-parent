package com.yuxing.trainee.test.infrastructure.feign.config;

import com.yuxing.trainee.spring.logtrace.FeignLogTraceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 全局配置
 *
 * @author yuxing
 * @since 2022/1/19
 */
@Configuration
public class FeignConfiguration {

    /**
     * 配置 feign 日志追踪拦截器
     */
    @Bean
    public FeignLogTraceInterceptor feignLogTraceInterceptor() {
        return new FeignLogTraceInterceptor();
    }
}
