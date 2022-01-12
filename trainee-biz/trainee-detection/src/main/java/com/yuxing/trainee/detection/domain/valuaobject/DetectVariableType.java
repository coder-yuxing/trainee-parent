package com.yuxing.trainee.detection.domain.valuaobject;

import com.yuxing.trainee.common.core.util.rpn.VariableType;

/**
 * 检测变量类型
 *
 * @author yuxing
 * @since 2022/1/12
 */
public enum DetectVariableType implements VariableType {

    NUM("num"),
    ;

    DetectVariableType(String code) {
        this.code = code;
    }

    public final String code;

    @Override
    public String getCode() {
        return this.code;
    }
}
