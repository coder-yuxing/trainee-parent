package com.yuxing.trainee.search.api.query;

import com.yuxing.trainee.common.core.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
