package com.yuxing.trainee.common.core.util.rpn;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;

/**
 * @author yuxing
 * @since 2021/12/16
 */
@Slf4j
public class BoolExpressionCalculate extends ExpressionCalculate<Boolean> {

    public BoolExpressionCalculate(OperatorSet operatorSet) {
        super(operatorSet);
    }

    @Override
    protected Boolean doCalculate(Element elt1, Element elt2, Operator operator) {
        if (Element.Type.VARIABLE == elt1.getType()) {
            Variable variable = (Variable) elt1.getValue();
            if (QuotaVariableType.CATEGORY_PROPERTY == variable.getIdType()) {
                Object value = variable.getValue();
                if (Objects.isNull(value)) {
                    return false;
                }
                Set<String> value1 = (Set<String>) variable.getValue();
                String value2 = elt2.getValue().toString();
                boolean result = value1.contains(value2);
                log.debug("执行：{} {} {}, 结果: {}", value1, operator.getCode(), value2, result);
                return result;
            }
            Comparable value1 = variable.getComparableValue();
            Comparable value2 = variable.convert2ComparableValue(elt2.getValue());
            Boolean result = (Boolean) operator.execute(value1, value2);
            log.debug("执行：{} {} {}, 结果: {}", value1, operator.getCode(), value2, result);
            return result;
        }
        Boolean value1 = (Boolean) elt1.getValue();
        Boolean value2 = (Boolean) elt2.getValue();
        Boolean result = (Boolean) operator.execute(value1, value2);
        log.debug("执行：{} {} {}, 结果: {}", value1, operator.getCode(), value2, result);
        return result;
    }

    @Override
    protected Boolean toR(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return Boolean.valueOf(value.toString());
    }
}
