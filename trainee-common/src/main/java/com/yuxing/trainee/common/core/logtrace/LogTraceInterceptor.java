package com.yuxing.trainee.common.core.logtrace;

import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 日志追踪拦截器
 *
 * 拦截所有请求，并将请求header中的traceId填充值MDC, 当 traceId不存在时则创建一个
 *
 * @author yuxing
 */
public class LogTraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 若存在上层调用，则使用上层调用的traceId
        String traceId = request.getHeader(Constant.TRACE_ID);
        if (StrUtil.isBlank(traceId)) {
            traceId = this.getTraceId();
        }
        MDC.put(Constant.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 调用结束后删除
        MDC.remove(Constant.TRACE_ID);
    }

    private String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}