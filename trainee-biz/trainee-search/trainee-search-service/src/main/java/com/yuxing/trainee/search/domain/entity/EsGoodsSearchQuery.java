package com.yuxing.trainee.search.domain.entity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxing.trainee.search.api.goods.constant.EsGoodsAggregationField;
import com.yuxing.trainee.search.domain.valuaobject.Aggregation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 素材文档查询
 *
 * @author yuxing
 * @since 2022/1/15
 */
@Data
public class EsGoodsSearchQuery {

    public EsGoodsSearchQuery(Integer page, Integer pageSize, String keyword, Integer type) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.type = type;
    }

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 单页长度
     */
    private Integer pageSize;

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
     * 聚合字段
     */
    private List<Aggregation> aggregations;

    public NativeSearchQuery getQuery() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder builder = new BoolQueryBuilder();
        // 条件筛选
        if (Objects.nonNull(this.type)) {
            builder.filter(QueryBuilders.termQuery("type", this.type));
        }

        if (Objects.nonNull(this.enabled)) {
            builder.filter(QueryBuilders.termQuery("enabled", this.enabled));
        }

        // 关键字查询
        if (StrUtil.isNotBlank(this.keyword)) {
            String[] fieldNames = new String[] {"name"};
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(this.keyword, fieldNames);
            multiMatchQuery.minimumShouldMatch("80%");
            builder.must(multiMatchQuery);
            builder.should(QueryBuilders.termQuery("code", this.keyword));
        }

        // 属性值嵌套查询
        if (CollUtil.isNotEmpty(this.props)) {
            this.props.forEach(p -> {
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("props",
                        QueryBuilders.boolQuery()
                                .must(QueryBuilders.termQuery("props.name", p.id))
                                .must(QueryBuilders.termsQuery("props.values", p.value)),
                        ScoreMode.Total);
                builder.filter(nestedQuery);
            });
        }
        queryBuilder.withQuery(builder);

        // 聚合查询
        if (CollUtil.isNotEmpty(this.aggregations)) {
            // 简单聚合
            Map<EsGoodsAggregationField, List<Aggregation>> map = this.aggregations.stream().collect(Collectors.groupingBy(Aggregation::getField));
            List<Aggregation> categoryAgg = map.get(EsGoodsAggregationField.CATEGORY);
            if (CollUtil.isNotEmpty(categoryAgg)) {
                queryBuilder.addAggregation(AggregationBuilders.terms(categoryAgg.get(0).getName()).field("categoryId"));
            }

            // 嵌套聚合 -> nested
            List<Aggregation> propAgg = map.get(EsGoodsAggregationField.PROP);
            if (CollUtil.isNotEmpty(propAgg)) {
                TermsAggregationBuilder aggregation = AggregationBuilders.terms("prop").field("props.name")
                        .subAggregation(AggregationBuilders.terms("propValue").field("props.values"));
                NestedAggregationBuilder nestedAggregationBuilder = AggregationBuilders.nested(propAgg.get(0).getName(), "props")
                        .subAggregation(aggregation);
                queryBuilder.addAggregation(nestedAggregationBuilder);
            }

        }

        // 分页查询
        queryBuilder.withPageable(PageRequest.of(this.page, this.pageSize));
        return queryBuilder.build();

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
}
