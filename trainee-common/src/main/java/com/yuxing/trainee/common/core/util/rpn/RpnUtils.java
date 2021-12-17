package com.yuxing.trainee.common.core.util.rpn;

import cn.hutool.core.util.StrUtil;
import com.yuxing.trainee.common.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 中缀表达式转逆波兰表达式
 *
 * @author yuxing
 */
@Slf4j
public class RpnUtils {

    private RpnUtils() {}

    /**
     * 表达式内元素分隔符
     */
    public static final String SEPARATOR = " ";

    /**
     * 解析公式(中缀表达式解析为逆波兰表达式).
     * note:
     * 1. 公式中各元素之间必须由空格 " " 分隔
     * 2. 变量不允许由数字开头
     *
     * e.g.
     *   String formula = "( 1 + ( 4 + 5 + 2 ) - 3 ) + ( 6 + 8 )";
     *   Stack<String> result = RpnUtils.parse(formula);
     *
     *   >>> ["1","4","5","+","2","+","+","3","-","6","8","+","+"]
     *
     * @param formula     运算公式(中缀表达式)
     * @param operatorSet 公式中操作符集合
     * @return 逆波兰表达式元素栈
     */
    public static <L, M, R extends Operator<L, M>> Deque<Element> parse(String formula, OperatorSet<L, M, R> operatorSet) {
        if (StrUtil.isBlank(formula)) {
            return new ArrayDeque<>();
        }
        String expression = StrUtil.trimEnd(StrUtil.trimStart(formula));
        // 操作符栈
        Deque<Element> operatorStack = new ArrayDeque<>();
        // 变量/常量存储栈
        Deque<Element> resultStack = new ArrayDeque<>();

        String[] formulaSplit = expression.split(SEPARATOR);
        int index = 0;
        while (index < formulaSplit.length) {
            String elt = formulaSplit[index];
            if (operatorSet.isOperator(elt)) {
                R operator = operatorSet.getOperator(elt);
                // 遇到权重开始符则直接压入操作符栈中
                if (operator.isStartWeightOperator()) {
                    operatorStack.push(new Element(elt, Element.Type.OPERATOR));
                }
                // 遇到权重截止符时，处理解析结果集，将操作符栈中相应元素压入其中
                else if (operator.isEndWeightOperator()) {
                    pushElement2ResultStack(operatorStack, resultStack, operatorSet);
                }
                else {
                    pushOperator(operatorStack, resultStack, operatorSet, operator);
                }
            } else {
                // 若当前字符并非操作符，则从公式中获取完整值压入结果集栈中
                int endIndex = pushVariableOrConst(resultStack, index, formulaSplit, operatorSet);
                index = --endIndex;
            }
            index++;
        }

        // 当算量表达式解析结束后，将操作符栈中剩余元素弹出压入解析结果栈中
        while (!operatorStack.isEmpty()) {
            resultStack.push(operatorStack.pop());
        }

        return reverseStack(resultStack);
    }

    /**
     * 使栈中的值逆序
     *
     * @param stack 公式解析栈
     * @return Deque<String>
     */
    private static Deque<Element> reverseStack(Deque<Element> stack) {
        Deque<Element> reverseStack = new ArrayDeque<>();
        while(!stack.isEmpty()) {
            reverseStack.push(stack.pop());
        }
        return reverseStack;
    }

    /**
     * 是否为变量
     * - 变量名不允许数字开头
     * - 变量名不支持存在中文
     * - 不允许包含操作符
     *
     * @param o 变量名/常量
     * @return bool
     */
    public static boolean isVariable(String o) {
        // 是否为空
        if (StrUtil.isBlank(o)) {
            return false;
        }
        // 是否包含中文
        if (StringUtils.isContainChinese(o)) {
            return false;
        }
        // 首字母不允许为数字
        char firstChar = o.toCharArray()[0];
        return firstChar < 48 || firstChar > 57;
    }

    /**
     * 将操作符栈中元素依次压入临时存储栈中，直到操作符栈为空或遇到左括号
     * 当遇到右括号时，依次将操作符栈中的栈顶运算符弹出，并压入解析结果栈中，直到遇到左括号并且将该对括号丢弃
     * @param operatorStack 操作符栈
     * @param resultStack   结果集栈
     */
    private static <L, M, R extends Operator<L, M>> void pushElement2ResultStack(Deque<Element> operatorStack, Deque<Element> resultStack, OperatorSet<L, M, R> operatorSet) {
        while (!operatorStack.isEmpty()) {
            Element topOperatorElement = operatorStack.pop();
            String topOperatorCode = (String) topOperatorElement.getValue();
            R operator = operatorSet.getOperator(topOperatorCode);
            if (operator.isStartWeightOperator()) {
                break;
            } else {
                resultStack.push(topOperatorElement);
            }
        }
    }

    /**
     * 操作符入栈
     *
     * @param operatorStack 操作符栈
     * @param resultStack   结果集栈
     * @param operatorSet   操作符集合
     * @param operator      操作符
     */
    private static <L, M, R extends Operator<L, M>> void pushOperator(Deque<Element> operatorStack, Deque<Element> resultStack, OperatorSet<L, M, R> operatorSet, R operator) {
        while (!operatorStack.isEmpty()) {
            // 获取当前操作符栈的栈顶元素
            Element topOperatorElement = operatorStack.pop();
            String topOperatorCode = (String) topOperatorElement.getValue();
            R topOperator = operatorSet.getOperator(topOperatorCode);
            // 若栈顶元素为左括号，则将当前操作符直接入栈
            if (topOperator.isStartWeightOperator()) {
                operatorStack.push(topOperatorElement);
                break;
            } else {
                // 比较当前操作符与栈顶操作符的优先级,若优先级比当前栈顶元素优先级高，则直接入栈
                if (topOperator.getWeight() < operator.getWeight()) {
                    operatorStack.push(topOperatorElement);
                    break;
                } else {
                    resultStack.push(topOperatorElement);
                }
            }
        }
        operatorStack.push(new Element(operator.getCode(), Element.Type.OPERATOR));
    }

    /**
     * 从公式中获取变量/常量，并压入栈中
     *
     * @param resultStack  结果集栈
     * @param startIndex   变量/常量在公式中的起始下标
     * @param formulaSplit 公式元素数组
     * @return 变量/常量在公式中的结束下标
     */
    private static <L, M, R extends Operator<L, M>> int pushVariableOrConst(Deque<Element> resultStack, int startIndex, String[] formulaSplit, OperatorSet<L, M, R> operatorSet) {
        int i = startIndex;
        StringBuilder builder = new StringBuilder();
        while (!operatorSet.isOperator(formulaSplit[i])) {
            String s = formulaSplit[i++];
            // 单个变量 / 常量 内部不允许存在空格
            if (StrUtil.isNotBlank(s)) {
                builder.append(s);
            }

            if (i >= formulaSplit.length) {
                break;
            }
        }
        String value = builder.toString();
        resultStack.push(new Element(value, isVariable(value) ? Element.Type.VARIABLE : Element.Type.CONSTANT));
        return i;
    }


}
