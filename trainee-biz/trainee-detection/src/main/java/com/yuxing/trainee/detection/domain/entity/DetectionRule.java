package com.yuxing.trainee.detection.domain.entity;

import com.yuxing.trainee.detection.domain.valuaobject.DetectionItemType;
import com.yuxing.trainee.detection.domain.valuaobject.DetectionReport;
import com.yuxing.trainee.detection.domain.valuaobject.ErrorLevel;
import com.yuxing.trainee.detection.domain.valuaobject.Menu;
import lombok.Data;

/**
 * 检测规则
 *
 * @author yuxing
 * @since 2022/1/12
 */
@Data
public class DetectionRule implements Detect {

    /**
     * 检测对象类型
     */
    private DetectionItemType detectionItemType;

    /**
     * 检测报告分组
     */
    private Menu menu;

    /**
     * 检测报告级别
     */
    private ErrorLevel level;

    /**
     * prop:num > 0
     */
    private String expression;

    /**
     * 警告提示语
     */
    private String warnTips;

    @Override
    public DetectionReport execute() {
        return null;
    }
}
