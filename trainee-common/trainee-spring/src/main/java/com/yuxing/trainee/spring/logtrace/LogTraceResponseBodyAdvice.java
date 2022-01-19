package com.yuxing.trainee.spring.logtrace;

import cn.hutool.core.util.ReflectUtil;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 对接口响应信息增加额外处理
 *
 * @author yuxing
 */
public class LogTraceResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        this.fillTraceId(body);
        return body;
    }

    /**
     * 接口响应值中增加traceId
     *
     * @param body 请求响应内容
     */
    private void fillTraceId(Object body) {

        // 填充traceId
        Field traceId = ReflectUtil.getField(body.getClass(), Constant.TRACE_ID);
        if (Objects.nonNull(traceId)) {
            ReflectUtil.setFieldValue(body, traceId, MDC.get(Constant.TRACE_ID));
        }
    }
}
