package com.yuxing.trainee.search.api.goods.query;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxing.trainee.common.core.Query;
import com.yuxing.trainee.search.api.goods.constant.EsGoodsAggregationField;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 素材查询
 *
 * @author yuxing
 * @since 2022/1/15
 */
@EqualsAndHashCode(callSuper = true)
public class EsGoodsQuery extends Query {

    /**
     * 关键字搜索
     */
    @Getter
    @Setter
    private String keyword;

    /**
     * 类型筛选
     */
    @Getter
    @Setter
    private Integer type;

    /**
     * 启停用
     */
    @Getter
    @Setter
    private Boolean enabled;

    /**
     * 属性筛选
     */
    private List<String> props = new ArrayList<>(8);

    /**
     * 属性聚合
     */
    @Getter
    @Setter
    private List<Aggregation> aggregations;

    public void setProps(List<String> props) {
        this.props = props;
    }

    public void addProp(Long id, String value) {
        if (Objects.isNull(id) || StrUtil.isBlank(value)) {
            return;
        }
        this.props.add(id + "-" + value);
    }

    public List<Prop> getProps() {
        if (CollUtil.isEmpty(this.props)) {
            return Collections.emptyList();
        }
        return this.props.stream().map(p -> {
            String[] split = p.split("-");
            return new Prop(Long.parseLong(split[0]), split[1]);
        }).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Prop {
        /**
         * 属性ID
         */
        private Long id;

        /**
         * 属性值
         */
        private String value;
    }

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
