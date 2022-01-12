package com.yuxing.trainee.detection.domain.entity;

import com.yuxing.trainee.detection.domain.valuaobject.DetectionReport;

/**
 * 检测接口
 *
 * @author yuxing
 * @since 2022/1/12
 */
public interface Detect {

    /**
     * 执行检测，输出检测报告
     *
     * @return 检测报告
     */
    DetectionReport execute();
}
