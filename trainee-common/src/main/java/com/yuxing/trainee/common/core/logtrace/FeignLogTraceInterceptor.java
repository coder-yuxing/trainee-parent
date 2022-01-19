package com.yuxing.trainee.common.core.logtrace;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

/**
 * feign log trace Interceptor
 *
 * @author yuxing
 */
public class FeignLogTraceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 将traceId 从 MDC中填充至 feign 请求的header中
        String traceId = MDC.get(Constant.TRACE_ID);
        if (traceId != null) {
            template.header(Constant.TRACE_ID, traceId);
        }
    }
}