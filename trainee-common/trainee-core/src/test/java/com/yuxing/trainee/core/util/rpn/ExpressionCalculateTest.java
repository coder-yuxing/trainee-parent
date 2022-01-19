package com.yuxing.trainee.core.util.rpn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.BiFunction;

public class ExpressionCalculateTest {

    private static final BiFunction<FormulaCalculationParameterSet, String, Variable> getValue = FormulaCalculationParameterSet::parseVariable;

    private FormulaCalculationParameterSet parameterSet;

    private NumberExpressionCalculate numberExpressionCalculate;
    private BoolExpressionCalculate boolExpressionCalculate;

    @Before
    public void setUp() {

        numberExpressionCalculate = new NumberExpressionCalculate(new NumberOperatorSet());
        boolExpressionCalculate = new BoolExpressionCalculate(new BoolOperatorSet());

        FormulaCalculationParameterSet.SystemParam parameter = new FormulaCalculationParameterSet.SystemParam();
        parameter.setOs(BigDecimal.valueOf(130));
        parameter.setIs(BigDecimal.valueOf(110));
        HashMap<String, BigDecimal> map = new HashMap<>(1);
        map.put("art:1", BigDecimal.valueOf(1));

        parameterSet = new FormulaCalculationParameterSet();
        parameterSet.setSysParam(parameter);
        parameterSet.setArtificialParam(map);
    }

    @Test
    public void logicCalculateTest() {
        // 满足条件
        final String condition = "( art:1 > 0 || art:2 > 0 || art:3 > 0 ) && ( sys:os > 120 )";
        Assert.assertTrue(boolExpressionCalculate.calculate(condition, this.parameterSet, getValue));

        // 不满足条件(变量不存在)
        final String condition1 = "( art:1 > 0 || art:2 > 0 || art:3 > 0 ) && ( sys:s > 20 )";
        Assert.assertFalse(boolExpressionCalculate.calculate(condition1, this.parameterSet, getValue));

    }

    @Test
    public void numericalCalculateTest() {
        final String expression = "sys:os / 120 * 100";
        BigDecimal calculate = numberExpressionCalculate.calculate(expression, this.parameterSet, getValue);
        Assert.assertEquals(BigDecimal.valueOf(109.00).setScale(2), calculate);

        final String expression2 = "10";
        BigDecimal calculate2 = numberExpressionCalculate.calculate(expression2, this.parameterSet, getValue);
        Assert.assertEquals(BigDecimal.valueOf(10), calculate2);
    }

}