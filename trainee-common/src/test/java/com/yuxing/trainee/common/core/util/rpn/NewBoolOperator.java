package com.yuxing.trainee.common.core.util.rpn;

import cn.hutool.core.util.StrUtil;
import com.yuxing.trainee.common.core.util.rpn.BoolOperator;
import com.yuxing.trainee.common.core.util.rpn.Operator;
import com.yuxing.trainee.common.core.util.rpn.TargetValue;
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
public enum NewBoolOperator implements Operator<TargetValue, Boolean> {

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
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(Boolean.TRUE) == 0 || comparable2.compareTo(Boolean.TRUE) == 0;
        }
    },

    /**
     * 且
     */
    AND("&&", 1, "&&") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(Boolean.TRUE) == 0 && comparable2.compareTo(Boolean.TRUE) == 0;
        }
    },

    /**
     * 等于
     */
    EQUAL("==", 1, "==") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) == 0;
        }
    },

    /**
     * 不等于
     */
    NOT_EQUAL("!=", 1, "≠") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) != 0;
        }
    },

    /**
     * 小于
     */
    LESS_THAN("<", 1, "<") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) < 0;
        }
    },

    /**
     * 小于或等于
     */
    EQUAL_OR_LESS_THAN("<=", 1,"<=") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) <= 0;
        }
    },

    /**
     * 大于
     */
    GREATER_THAN(">", 1, ">") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) > 0;
        }
    },

    /**
     * 大于或等于
     */
    EQUAL_OR_GREATER_THAN(">=", 1, ">=") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            Comparable comparable1 = this.convert2Comparable(obj1);
            Comparable comparable2 = this.convert2Comparable(obj2);
            return comparable1.compareTo(comparable2) >= 0;
        }
    },

    /**
     * 包含
     */
    IN("IN", 1, "in") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            if (!obj1.isCollection() || !obj2.isCollection()) {
                return false;
            }
            Set<String> set1 = (Set<String>) obj1.getValue();
            Set<String> set2 = (Set<String>) obj2.getValue();
            return set1.containsAll(set2);
        }
    },

    /**
     * 不包含
     */
    NOT_IN("NOT_IN", 1, "notin") {
        @Override
        protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
            if (!obj1.isCollection() || !obj2.isCollection()) {
                return false;
            }
            Set<String> set1 = (Set<String>) obj1.getValue();
            Set<String> set2 = (Set<String>) obj2.getValue();
            return set2.stream().noneMatch(set1::contains);
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
    public Boolean execute(TargetValue obj1, TargetValue obj2) {
        if (Objects.isNull(obj1) || Objects.isNull(obj2)) {
            return false;
        }
        return this.doExecute(obj1, obj2);
    }

    protected boolean doExecute(TargetValue obj1, TargetValue obj2) {
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

    protected Comparable convert2Comparable(TargetValue targetValue) {
        Comparable comparable;
        if (targetValue.isCollection()) {
            Set<String> set = (Set<String> ) targetValue.getValue();
            comparable = new ArrayList<>(set).get(0);
        } else {
            comparable = (Comparable) targetValue.getValue();
        }
        return comparable;
    }
}
