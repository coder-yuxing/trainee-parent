package com.yuxing.trainee.search.api.goods.query;

import com.yuxing.trainee.common.core.Query;
import com.yuxing.trainee.search.api.goods.constant.EsGoodsAggregationField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
     * 属性聚合
     */
    private List<Aggregation> aggregations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Aggregation {

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
