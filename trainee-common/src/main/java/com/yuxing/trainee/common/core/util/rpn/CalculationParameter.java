package com.yuxing.trainee.common.core.util.rpn;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 算量参数
 *
 * @author dingguangren
 */
@Slf4j
public abstract class CalculationParameter implements Serializable {

    private static final long serialVersionUID = 710979013078836336L;

    /**
     * 根据变量名查询变量值
     *
     * @param variableCode 变量
     * @return 变量值
     */
    public Variable parseVariable(String variableCode) {
        if (StrUtil.isBlank(variableCode)) {
            log.debug("变量为空，无法获取对应值");
            return null;
        }

        Variable variable = this.newVariableInstance(variableCode);
        if (variable.hasParent()) {
            Variable parent = variable.getParent();
            if (this.fillVariableValue(parent)) {
                this.fillVariableValue(variable);
                return variable;
            }
        }
        this.fillVariableValue(variable);
        return variable;
    }

    /**
     * 获取变量实例
     *
     * @param variableCode 变量code
     * @return 变量实例
     */
    protected abstract Variable newVariableInstance(String variableCode);

    /**
     * 获取变量值
     *
     * @param variable 变量
     * @return 是否填充变量值成功
     */
    protected abstract boolean fillVariableValue(Variable variable);
}