package com.yuxing.trainee.core.util.rpn;

import cn.hutool.core.util.ReflectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

/**
 * 公式算量所需参数集
 *
 * @author yuxing
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class FormulaCalculationParameterSet extends CalculationParameter {

    private static final long serialVersionUID = -4882602234764935171L;

    /**
     * 系统参数
     */
    private SystemParam sysParam;

    /**
     * 自定义参数
     */
    private Map<String, BigDecimal> customParam;

    /**
     * 工艺参数
     */
    private Map<String, BigDecimal> artificialParam;

    @Override
    protected Variable newVariableInstance(String variableCode) {
        return new QuotaVariable(variableCode);
    }

    @Override
    protected boolean fillVariableValue(Variable variable) {
        BigDecimal value = null;
        QuotaVariableType idType = (QuotaVariableType) variable.getIdType();
        switch (idType) {
            case ARTIFICIAL:
                value = Optional.ofNullable(this.artificialParam.get(variable.getVariableCode())).orElse(BigDecimal.ZERO);
                break;
            case SYSTEM:
                value = Optional.ofNullable((BigDecimal) ReflectUtil.getFieldValue(this.sysParam, variable.getId().toLowerCase(Locale.ROOT))).orElse(BigDecimal.ZERO);
                break;
            case CUSTOM:
                value = Optional.ofNullable(this.customParam.get(variable.getVariableCode())).orElse(BigDecimal.ZERO);
                break;
            default:
        }
        variable.setValue(value);
        return value != null;
    }

    /**
     * 系统参数
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemParam {

        /**
         * 建筑面积
         */
        private BigDecimal bs;

        /**
         * 套内面积 -> 套内面积(不含飘窗)
         */
        private BigDecimal is;

        /**
         * 户型外框面积
         */
        private BigDecimal os;

        /**
         * 套内面积(含飘窗)
         */
        private BigDecimal ifs;


    }
}
