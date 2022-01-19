package com.yuxing.trainee.search.infrastructure.util;

import com.yuxing.trainee.core.Pager;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.*;
import java.util.stream.Collectors;

/**
 * es 搜索相关工具类
 *
 * @author yuxing
 * @since 2022/1/16
 */
public class ElasticsearchUtils {

    private ElasticsearchUtils() {
    }

    /**
     * 解析聚合数据
     *
     * @param aggregations 聚合数据
     * @return java.lang.Map
     */
    public static Map<String, List<Pager.AggregationInfo>> parseAggregations(Aggregations aggregations) {
        if (Objects.isNull(aggregations)) {
            return Collections.emptyMap();
        }
        List<Aggregation> list = aggregations.asList();
        Map<String, List<Pager.AggregationInfo>> result = new HashMap<>(list.size());
        for (Aggregation agg : aggregations) {
            if (agg instanceof Terms) {
                result.put(agg.getName(), ElasticsearchUtils.assemble2AggregationInfos((Terms) agg, false));
            }

            if (agg instanceof ParsedNested) {
                ParsedNested parsedNested = (ParsedNested) agg;
                result.put(parsedNested.getName(), ElasticsearchUtils.parseAsTerms(parsedNested.getAggregations()));
            }
        }
        return result;
    }

    private static List<Pager.AggregationInfo> parseAsTerms(Aggregations aggregations) {
        return aggregations.asList().stream()
                .map(a -> ElasticsearchUtils.assemble2AggregationInfos((Terms) a, true))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    private static List<Pager.AggregationInfo> assemble2AggregationInfos(Terms terms, boolean parseSubAggregations) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        return buckets.stream()
                .map(b -> ElasticsearchUtils.assemble2AggregationInfo(b, parseSubAggregations))
                .collect(Collectors.toList());
    }

    private static Pager.AggregationInfo assemble2AggregationInfo(Terms.Bucket bucket, boolean parseSubAggregations) {
        Pager.AggregationInfo info = new Pager.AggregationInfo();
        info.setKey(bucket.getKeyAsString());
        info.setDocCount(bucket.getDocCount());
        if (parseSubAggregations) {
            info.setChild(assemble2AggregationInfos(bucket.getAggregations()));
        }
        return info;
    }

    private static List<Pager.AggregationInfo> assemble2AggregationInfos(Aggregations aggregations) {
        if (Objects.isNull(aggregations)) {
            return Collections.emptyList();
        }
        return aggregations.asList().stream().map(agg -> {
            Terms longTerms = (Terms) agg;
            List<? extends Terms.Bucket> buckets = longTerms.getBuckets();
            return buckets.stream()
                    .map(b -> ElasticsearchUtils.assemble2AggregationInfo(b, false))
                    .collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
