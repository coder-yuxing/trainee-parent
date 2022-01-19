package com.yuxing.trainee.core.util.rpn;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数值运算操作符
 *
 * @author yuxing
 */
@AllArgsConstructor
public enum NumberOperator implements Operator<BigDecimal, BigDecimal> {

    /**
     * 左括号
     */
    LEFT_BRACKET("(", 0) {
        @Override
        public boolean isStartWeightOperator() {
            return true;
        }
    },

    /**
     * 右括号
     */
    RIGHT_BRACKET(")", 0) {
        @Override
        public boolean isEndWeightOperator() {
            return true;
        }
    },

    /**
     * 加法
     */
    ADD("+", 1) {
        @Override
        protected BigDecimal doExecute(BigDecimal num1, BigDecimal num2) {
            return NumberUtil.add(num1, num2);
        }
    },

    /**
     * 减法
     */
    SUBTRACT("-", 1) {
        @Override
        protected BigDecimal doExecute(BigDecimal num1, BigDecimal num2) {
            return NumberUtil.sub(num1, num2);
        }
    },

    /**
     * 乘法
     */
    MULTIPLY("*", 2) {
        @Override
        protected BigDecimal doExecute(BigDecimal num1, BigDecimal num2) {
            return NumberUtil.mul(num1, num2);
        }
    },

    /**
     * 除法
     */
    DIVIDE("/", 2) {
        @Override
        protected BigDecimal doExecute(BigDecimal num1, BigDecimal num2) {
            return NumberUtil.round(NumberUtil.div(num1, num2), 2, RoundingMode.CEILING);
        }
    }

    ;

    /**
     * 操作符标识符
     */
    public final String code;

    /**
     * 操作符权重
     */
    public final int weight;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public BigDecimal execute(BigDecimal num1, BigDecimal num2) {
        if (Objects.isNull(num1) || Objects.isNull(num2)) {
            return BigDecimal.ZERO;
        }
        return this.doExecute(num1, num2);
    }

    @Override
    public boolean isEndWeightOperator() {
        return false;
    }

    @Override
    public boolean isStartWeightOperator() {
        return false;
    }

    protected BigDecimal doExecute(BigDecimal num1, BigDecimal num2) {
        return BigDecimal.ZERO;
    }

    private final static Map<String, NumberOperator> OPERATOR_MAP = new HashMap<>(NumberOperator.values().length);

    static {
        for (NumberOperator o : NumberOperator.values()) {
            OPERATOR_MAP.put(o.code, o);
        }
    }

    public static Optional<NumberOperator> getByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return Optional.empty();
        }
        return Optional.ofNullable(OPERATOR_MAP.get(code));
    }

    public static Set<String> getAllCodes() {
        return Arrays.stream(NumberOperator.values()).map(o -> o.code).collect(Collectors.toSet());
    }
}
