package com.yuxing.trainee.core.util.rpn;

/**
 * 操作符集合
 *
 * @author yuxing
 */
public interface OperatorSet<L, M, R extends Operator<L, M>> {

    /**
     * 是否为操作符
     *
     * @param code 操作符标识
     * @return true / false
     */
    boolean isOperator(String code);

    /**
     * 获取操作符对象
     *
     * @param code 操作符标识
     * @return 操作符对象
     */
    R getOperator(String code);

}
