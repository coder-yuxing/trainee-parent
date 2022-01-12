package com.yuxing.trainee.detection.domain.valuaobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 检测报告
 *
 * @author yuxing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetectionReport {

    /**
     * 检测类型
     */
    private Menu type;

    /**
     * 问题项，为空则为检测无问题
     */
    private Collection<Problematic> problematics = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Problematic {

        /**
         * 错误描述
         */
        private String error;

        /**
         * 级别： 1 异常 2 提醒
         */
        private Integer level;
    }
}
