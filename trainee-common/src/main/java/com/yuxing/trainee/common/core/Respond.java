package com.yuxing.trainee.common.core;

/**
 * 响应接口
 *
 * @author yuxing
 * @since 2022/1/15
 */
public interface Respond {

    /**
     * 获取响应码
     *
     * @return 响应码
     */
    int getCode();

    /**
     * 获取响应码说明
     * @return 响应码说明
     */
    String getMessage();
}
