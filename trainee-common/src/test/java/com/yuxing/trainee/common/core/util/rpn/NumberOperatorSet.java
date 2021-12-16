package com.yuxing.trainee.common.core.util.rpn;

import java.math.BigDecimal;

public class NumberOperatorSet implements OperatorSet<BigDecimal, BigDecimal, NumberOperator> {

    @Override
    public boolean isOperator(String code) {
        return NumberOperator.getAllCodes().contains(code);
    }

    @Override
    public NumberOperator getOperator(String code) {
        return NumberOperator.getByCode(code).orElse(null);
    }

}
