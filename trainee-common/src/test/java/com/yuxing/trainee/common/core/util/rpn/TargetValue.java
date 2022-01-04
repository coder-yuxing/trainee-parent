package com.yuxing.trainee.common.core.util.rpn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 目标值
 *
 * @author yuxing
 * @since 2022/1/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetValue {

    /**
     * 值
     */
    private Object value;

    /**
     * 是否为集合
     */
    private boolean collection;
}
