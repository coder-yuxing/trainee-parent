package com.yuxing.trainee.core.util.rpn;

import com.yuxing.trainee.core.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author yuxing
 * @since 2021/12/16
 */
public class QuotaVariable extends Variable {

    public QuotaVariable(String variable) {
        super(variable);
    }

    @Override
    protected VariableType getByCode(String code) {
        return QuotaVariableType.getByCode(code).orElseThrow(() -> new IllegalArgumentException("参数错误"));
    }

    @Override
    protected Variable newInstance(String variable) {
        return new QuotaVariable(variable);
    }

    @Override
    public Comparable convert2ComparableValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        QuotaVariableType idType = (QuotaVariableType) this.getIdType();
        switch (idType) {
            case SYSTEM:
            case SPACE:
            case CUSTOM:
            case CATEGORY:
            case ARTIFICIAL:
            case DOORWAY:
                if (value instanceof BigDecimal) {
                    return (BigDecimal) value;
                }
                if (StringUtils.isNumeric(value.toString())) {
                    return new BigDecimal(value.toString());
                }
                return BigDecimal.ZERO;
            default:
                return null;
        }
    }
}
