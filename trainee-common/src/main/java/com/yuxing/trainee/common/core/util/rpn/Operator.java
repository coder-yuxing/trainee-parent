package com.yuxing.trainee.common.core.util.rpn;

/**
 * 操作符
 *
 * @author yuxing
 */
public interface Operator<T, R> {

    /**
     * 获取操作符标识
     *
     * @return 运算符标识
     */
    String getCode();

    /**
     * 获取操作符权重
     *
     * @return 权重值
     */
    int getWeight();

    /**
     * 是否为起始权重操作符
     *
     * @return true / false
     */
    boolean isStartWeightOperator();

    /**
     * 是否为截止权重操作符
     *
     * @return true / false
     */
    boolean isEndWeightOperator();

    /**
     * 执行运算
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 运算结果
     */
    R execute(T obj1, T obj2);

}
