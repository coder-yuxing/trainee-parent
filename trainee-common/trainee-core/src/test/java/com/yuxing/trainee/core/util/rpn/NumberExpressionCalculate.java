package com.yuxing.trainee.core.util.rpn;

import com.yuxing.trainee.core.util.StringUtils;

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
        if (Element.Type.EMPTY == elt1.getType() || Element.Type.EMPTY == elt2.getType()) {
            throw new IllegalArgumentException("参数错误");
        }
        if (Element.Type.OPERATOR == elt1.getType() || Element.Type.OPERATOR == elt2.getType()) {
            throw new IllegalArgumentException("参数错误");
        }

        if (Element.Type.VARIABLE == elt1.getType()) {
            String v1 = ((Variable) elt1.getValue()).getValue().toString();
            if (Element.Type.VARIABLE == elt2.getType()) {
                String v2 = ((Variable) elt2.getValue()).getValue().toString();
                return (BigDecimal) operator.execute(new BigDecimal(v1), new BigDecimal(v2));
            }
            return (BigDecimal) operator.execute(new BigDecimal(v1), new BigDecimal(elt2.getValue().toString()));
        }

        if (Element.Type.VARIABLE == elt2.getType()) {
            String v2 = ((Variable) elt2.getValue()).getValue().toString();
            return (BigDecimal) operator.execute(new BigDecimal(elt1.getValue().toString()), new BigDecimal(v2));
        }

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
