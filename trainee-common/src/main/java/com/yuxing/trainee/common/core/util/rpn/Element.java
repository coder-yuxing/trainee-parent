package com.yuxing.trainee.common.core.util.rpn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公式解析后元素
 *
 * @author dingguangren
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class Element {

    /**
     * 元素值
     */
    private Object value;

    /**
     * 元素类型
     */
    private Type type;

    public enum Type {
        /**
         * 操作符
         */
        OPERATOR,

        /**
         * 常量
         */
        CONSTANT,

        /**
         * 变量
         */
        VARIABLE,

        /**
         * 常量集合
         */
        CONSTANT_COLLECTION,

        /**
         * 空值
         */
        EMPTY,

    }
}