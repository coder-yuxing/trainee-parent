package com.yuxing.trainee.search.api.goods.query;

import com.yuxing.trainee.common.core.Query;
import com.yuxing.trainee.search.api.goods.constant.EsGoodsAggregationField;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 素材查询
 *
 * @author yuxing
 * @since 2022/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EsGoodsQuery extends Query {

    private static final long serialVersionUID = 2950292092412638104L;

    /**
     * 关键字搜索
     */
    private String keyword;

    /**
     * 类型筛选
     */
    private Integer type;

    /**
     * 启停用
     */
    private Boolean enabled;

    /**
     * 属性筛选
     */
    private List<Prop> props;

    /**
     * 属性聚合
     */
    private List<Aggregation> aggregations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Prop implements Serializable {

        private static final long serialVersionUID = 2645598698241399L;

        /**
         * 属性ID
         */
        private String name;

        /**
         * 属性值
         */
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Aggregation implements Serializable {

        private static final long serialVersionUID = 6745600752681155234L;

        /**
         * 聚合名称
         */
        private String name;

        /**
         * 聚合属性
         */
        private EsGoodsAggregationField field;
    }
}
