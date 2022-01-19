package com.yuxing.trainee.core.util.rpn;

public class BoolOperatorSet implements OperatorSet<Comparable, Boolean, BoolOperator> {


    @Override
    public boolean isOperator(String code) {
        return BoolOperator.getAllCodes().contains(code);
    }

    @Override
    public BoolOperator getOperator(String code) {
        return BoolOperator.getByCode(code).orElse(null);
    }
}
