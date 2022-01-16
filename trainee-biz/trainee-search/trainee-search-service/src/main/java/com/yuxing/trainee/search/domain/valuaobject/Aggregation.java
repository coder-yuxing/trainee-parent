package com.yuxing.trainee.search.domain.valuaobject;

import com.yuxing.trainee.search.api.constant.EsGoodsAggregationField;
import lombok.Data;

/**
 * 聚合
 *
 * @author yuxing
 * @since 2022/1/16
 */
@Data
public class Aggregation {

    /**
     * 聚合名称
     */
    private String name;

    /**
     * 聚合属性
     */
    private EsGoodsAggregationField field;
}
