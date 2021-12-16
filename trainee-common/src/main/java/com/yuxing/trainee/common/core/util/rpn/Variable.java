package com.yuxing.trainee.common.core.util.rpn;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 变量
 *
 * @author yuxing
 */
@Data
public abstract class Variable implements Serializable {

    private static final long serialVersionUID = 1405042916794260330L;

    /**
     * 分隔符
     */
    public static final String SEPARATOR = ":";

    /**
     * 变量从属类型分隔符
     */
    public static final String SUBORDINATE_SEPARATE = "@";

    protected Variable(String variable) {
        if (StrUtil.isBlank(variable) || !variable.contains(SEPARATOR)) {
            throw new IllegalArgumentException();
        }

        String realVar = variable;
        if (variable.contains(SUBORDINATE_SEPARATE)) {
            String[] split = variable.split(SUBORDINATE_SEPARATE);
            this.parent = this.newInstance(split[0]);
            realVar = split[1];
        }

        String[] split = realVar.split(SEPARATOR);
        this.idType = this.getByCode(split[0]);
        this.id = split[1];
        this.variableCode = realVar;
    }

    /**
     * 获取变量类型
     *
     * @param code 变量类型code
     * @return 变量类型
     */
    protected abstract VariableType getByCode(String code);

    /**
     * 获取父级实例
     *
     * @param variable 变量Code
     * @return 父级实例
     */
    protected abstract Variable newInstance(String variable);

    /**
     * 变量类型
     */
    private VariableType idType;

    /**
     * 变量ID
     */
    private String id;

    /**
     * 变量值
     */
    private Object value;

    /**
     * 父级变量
     */
    private Variable parent;

    /**
     * 变量code
     */
    private String variableCode;

    public boolean hasParent() {
        return Objects.nonNull(this.parent);
    }

    public Comparable getComparableValue() {
        return this.convert2ComparableValue(this.value);
    }

    public abstract Comparable convert2ComparableValue(Object value);
}
