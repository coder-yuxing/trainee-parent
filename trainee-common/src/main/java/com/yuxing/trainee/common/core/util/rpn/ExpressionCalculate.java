package com.yuxing.trainee.common.core.util.rpn;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 公式计算
 *
 * 1. 公式算量当前支持变量类型 {@link VariableType}
 * 2. 公式格式说明：
 *    公式内各元素说明：
 *    1. 变量{@link Variable}：公式内变量由变量类型、变量标志符、分隔符组成；变量定义不允许由数字开头
 *      1. varCode: 变量类型 {@link VariableType}, 确定数据查找范围
 *      2. varVal: 变量标识符/变量值
 *      3. 分隔符{@link Variable#SEPARATOR}: 冒号用于分隔变量类型与变量标识符
 *      4. 分隔符{@link Variable#SUBORDINATE_SEPARATE}: @符号用于分隔两个变量，用于标识两个变量的从属关系：spc:1@cat:123 即品类数据123必须属于空间1，当前解析，仅支持一级从属关系
 *    2. operator: 操作符; 系统当前支持操作符 {@link Operator}
 *    3. 优先级: 元素之间优先级由 "()" 表示，操作符之间当前支持数学操作符: + - * / 之前的优先级，逻辑判断与比较运算操作符之前无优先级关系
 *    4. 分隔符：各元素之间由空格分隔
 *    公式格式实例：( spc:1@cat:123 > 0 ) && ( art:1 > 1 )
 * 3. 公式解析方法{@link RpnUtils}
 * 4. 公式计算：{@link ExpressionCalculate#calculate(String, CalculationParameter, BiFunction)} (String, CalculationParameter, BiFunction)}
 *    公式计算遵循 解析公式 -> 替换变量 -> 值比较/计算 的顺序，替换变量过程依赖算量参数集合，数据集必须继承自 {@link CalculationParameter} 并且实现方法{@link CalculationParameter#fillVariableValue(Variable)}
 *
 * @author yuxing
 */
@Slf4j
@SuppressWarnings("unchecked")
@AllArgsConstructor
public abstract class ExpressionCalculate<R> {

    private final OperatorSet operatorSet;

    /**
     * 执行表达式
     *
     * @param expression 表达式
     * @param parameter  表达式所需参数
     * @param <T>        参数数据类型
     * @return 若计算成功则返回执行结果，否则返回null, 表示计算失败
     */
    public <T extends CalculationParameter> R calculate(String expression, T parameter, BiFunction<T, String, Variable> getVariableValue) {
        if (StrUtil.isBlank(expression) || Objects.isNull(parameter) || Objects.isNull(getVariableValue)) {
            return null;
        }
        Deque<Element> formula = RpnUtils.parse(expression, this.operatorSet);
        if (Objects.isNull(formula)) {
            return null;
        }
        log.debug("解析并计算公式:【{}】", expression);
        Deque<Element> valueStack = new LinkedList<>();
        for (Element element : formula) {
            // 若不为操作符，则跳过
            if (Element.Type.OPERATOR != element.getType()) {
                // 若为变量则从参数对象中获取实际值，若为数值直接入栈
                if (Element.Type.VARIABLE == element.getType()) {
                    Variable variable = getVariableValue.apply(parameter, (String) element.getValue());
                    if (Objects.isNull(variable) || Objects.isNull(variable.getValue())) {
                        valueStack.push(new Element(null, Element.Type.EMPTY));
                    } else {
                        valueStack.push(new Element(variable, Element.Type.VARIABLE));
                    }
                } else {
                    valueStack.push(element);
                }
            } else {
                // 若为操作符，则获取前两位元素并按操作符计算
                Operator operator = this.operatorSet.getOperator((String) element.getValue());
                if (Objects.isNull(operator)) {
                    log.debug("公式【{}】存在非法操作符：{}", expression, element.getValue());
                    return null;
                }
                Element elt2 = valueStack.pop();
                Element elt1 = valueStack.pop();
                R result = this.doCalculate(elt1, elt2, operator);
                log.debug("执行：{} {} {}, 结果: {}", elt1.getValue(), operator.getCode(), elt2.getValue(), result);
                valueStack.push(new Element(result, Element.Type.CONSTANT));
            }
        }

        if (CollUtil.isEmpty(valueStack)) {
            log.debug("公式:【{}】计算异常", expression);
            return null;
        }

        Element resultEle = valueStack.pop();
        if (Element.Type.CONSTANT != resultEle.getType()) {
            log.debug("公式:【{}】计算异常", expression);
            return null;
        }
        R result = this.toR(resultEle.getValue());
        log.debug("公式:【{}】 计算结果: {}", expression, result);
        return result;
    }

    /**
     * 执行计算
     *
     * @param elt1     参数1
     * @param elt2     参数2
     * @param operator 计算方式
     * @return 计算结果
     */
    protected abstract R doCalculate(Element elt1, Element elt2, Operator operator);

    /**
     * 数据类型转换
     *
     * @param value 值
     * @return R
     */
    protected abstract R toR(Object value);
}
