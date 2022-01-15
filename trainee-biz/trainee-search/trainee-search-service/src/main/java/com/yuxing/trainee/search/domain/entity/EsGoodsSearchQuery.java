package com.yuxing.trainee.search.domain.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Objects;

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

    public NativeSearchQuery getQuery() {
        BoolQueryBuilder builder = new BoolQueryBuilder();
        // 条件筛选
        if (Objects.nonNull(this.type)) {
            builder.filter(QueryBuilders.termQuery("type", this.type));
        }
        // 关键字查询
        if (StrUtil.isNotBlank(this.keyword)) {
            String[] fieldNames = new String[] {"name"};
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(this.keyword, fieldNames);
            multiMatchQuery.minimumShouldMatch("80%");
            builder.must(multiMatchQuery);
            builder.should(QueryBuilders.termQuery("code", this.keyword));
        }

        return new NativeSearchQueryBuilder()
                .withQuery(builder)
                // 分页查询
                .withPageable(PageRequest.of(this.page, this.pageSize)).build();
    }
}
