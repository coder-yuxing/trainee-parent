package com.yuxing.trainee.test.infrastructure.config;

import com.yuxing.trainee.spring.logtrace.LogTraceInterceptor;
import com.yuxing.trainee.spring.logtrace.LogTraceResponseBodyAdvice;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Collections;

/**
 * web mvc 配置
 *
 * @author yuxing
 * @since 2022/1/19
 */
@Configuration
@AllArgsConstructor
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

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, Validator validator) {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter(contentNegotiationManager, conversionService, validator);
        adapter.setResponseBodyAdvice(Collections.singletonList(this.logTraceResponseBodyAdvice()));
        return adapter;
    }

    /**
     * 接口响应值增加traceId
     */
    @Bean
    public LogTraceResponseBodyAdvice logTraceResponseBodyAdvice() {
        return new LogTraceResponseBodyAdvice();
    }

}
