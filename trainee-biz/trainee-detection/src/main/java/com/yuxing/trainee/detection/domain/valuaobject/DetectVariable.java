package com.yuxing.trainee.detection.domain.valuaobject;

import com.yuxing.trainee.common.core.util.rpn.Variable;
import com.yuxing.trainee.common.core.util.rpn.VariableType;

/**
 * 检测变量
 *
 * @author yuxing
 * @since 2022/1/12
 */
public class DetectVariable extends Variable {


    protected DetectVariable(String variable) {
        super(variable);
    }

    @Override
    protected VariableType getByCode(String code) {
        return null;
    }

    @Override
    protected Variable newInstance(String variable) {
        return null;
    }

    @Override
    public Comparable convert2ComparableValue(Object value) {
        return null;
    }
}
