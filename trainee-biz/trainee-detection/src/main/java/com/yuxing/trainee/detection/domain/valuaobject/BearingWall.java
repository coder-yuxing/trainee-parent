package com.yuxing.trainee.detection.domain.valuaobject;

import com.yuxing.trainee.common.core.util.rpn.CalculationParameter;
import com.yuxing.trainee.common.core.util.rpn.Variable;

import java.util.Optional;

/**
 * 承重墙
 *
 * @author yuxing
 * @since 2022/1/12
 */
public class BearingWall extends CalculationParameter {

    public BearingWall(Integer num) {
        this.detectionType = DetectionItemType.BEARING_WALL;
        this.num = num;
    }

    /**
     * 对象类型
     */
    private final DetectionItemType detectionType;

    /**
     * 承重墙数量
     */
    private final Integer num;

    @Override
    protected Variable newVariableInstance(String variableCode) {
        return new DetectVariable(variableCode);
    }

    @Override
    protected boolean fillVariableValue(Variable variable) {
        DetectVariableType idType = (DetectVariableType) variable.getIdType();
        if (DetectVariableType.NUM == idType) {
            variable.setValue(Optional.ofNullable(this.num).orElse(0));
            return true;
        }
        return false;
    }
}
