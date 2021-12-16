package com.yuxing.trainee.common.core.util.rpn;

import com.yuxing.trainee.common.core.util.StringUtils;

import java.math.BigDecimal;

/**
 * @author yuxing
 * @since 2021/12/16
 */
public class NumberExpressionCalculate extends ExpressionCalculate<BigDecimal> {

    public NumberExpressionCalculate(OperatorSet operatorSet) {
        super(operatorSet);
    }

    @Override
    protected BigDecimal doCalculate(Element elt1, Element elt2, Operator operator) {
        return (BigDecimal) operator.execute(new BigDecimal(elt1.getValue().toString()), new BigDecimal(elt2.getValue().toString()));
    }

    @Override
    protected BigDecimal toR(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        String str = value.toString();
        if (StringUtils.isNumeric(str)) {
            return new BigDecimal(str);
        }

        return null;
    }
}
