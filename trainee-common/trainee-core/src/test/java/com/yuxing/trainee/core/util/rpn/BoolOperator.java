package com.yuxing.trainee.core.util.rpn;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 布尔运算操作符
 *
 * @author yuxing
 */
@SuppressWarnings("unchecked")
@AllArgsConstructor
public enum BoolOperator implements Operator<Comparable, Boolean> {

    /**
     * 左括号
     */
    LEFT_BRACKET("(", 0, "(") {
        @Override
        public boolean isStartWeightOperator() {
            return true;
        }
    },

    /**
     * 右括号
     */
    RIGHT_BRACKET(")", 0, ")") {
        @Override
        public boolean isEndWeightOperator() {
            return true;
        }
    },

    /**
     * 或
     */
    OR("||", 0, "||") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(Boolean.TRUE) == 0 || comparable2.compareTo(Boolean.TRUE) == 0;
        }
    },

    /**
     * 且
     */
    AND("&&", 1, "&&") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(Boolean.TRUE) == 0 && comparable2.compareTo(Boolean.TRUE) == 0;
        }
    },

    /**
     * 等于
     */
    EQUAL("==", 1, "==") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) == 0;
        }
    },

    /**
     * 不等于
     */
    NOT_EQUAL("!=", 1, "≠") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) != 0;
        }
    },

    /**
     * 小于
     */
    LESS_THAN("<", 1, "<") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) < 0;
        }
    },

    /**
     * 小于或等于
     */
    EQUAL_OR_LESS_THAN("<=", 1,"<=") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) <= 0;
        }
    },

    /**
     * 大于
     */
    GREATER_THAN(">", 1, ">") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) > 0;
        }
    },

    /**
     * 大于或等于
     */
    EQUAL_OR_GREATER_THAN(">=", 1, ">=") {
        @Override
        protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
            return comparable1.compareTo(comparable2) >= 0;
        }
    },
    ;

    /**
     * 操作符标识符
     */
    public final String code;

    /**
     * 操作符权重
     */
    public final int weight;

    /**
     * 别名
     */
    public final String alias;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Boolean execute(Comparable obj1, Comparable obj2) {
        if (Objects.isNull(obj1) || Objects.isNull(obj2)) {
            return false;
        }
        return this.doExecute(obj1, obj2);
    }

    protected boolean doExecute(Comparable comparable1, Comparable comparable2) {
        return false;
    }


    private final static Map<String, BoolOperator> OPERATOR_MAP = new HashMap<>(BoolOperator.values().length);

    static {
        for (BoolOperator o : BoolOperator.values()) {
            OPERATOR_MAP.put(o.code, o);
            if (!o.code.equals(o.alias)) {
                OPERATOR_MAP.put(o.alias, o);
            }
        }
    }

    public static Optional<BoolOperator> getByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return Optional.empty();
        }
        return Optional.ofNullable(OPERATOR_MAP.get(code));
    }

    public static Set<String> getAllCodes() {
        return Arrays.stream(BoolOperator.values()).map(o -> {
            if (!o.code.equals(o.alias)) {
                return Arrays.asList(o.code, o.alias);
            }
            return Collections.singletonList(o.code);
        }).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public boolean isEndWeightOperator() {
        return false;
    }

    @Override
    public boolean isStartWeightOperator() {
        return false;
    }
}
