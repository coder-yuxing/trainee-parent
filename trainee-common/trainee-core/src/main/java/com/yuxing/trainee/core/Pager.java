package com.yuxing.trainee.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 *
 * @author yuxing
 * @since 2022/1/15
 */
@Data
@NoArgsConstructor
public class Pager<T> {

    public Pager(Integer page, Integer pageSize, Long total) {
        this.page = page == null || page < 1 ? 1 : page;
        this.total = total == null || total <= 0 ? 0 : total;
        this.pageSize = pageSize == null || pageSize <= 0 ? 10 : pageSize;
        this.totalPage = (int) Math.ceil(this.total / (this.pageSize.doubleValue()));

        if (this.totalPage == 0) {
            this.page = 1;
        } else {
            if (this.pageSize > this.total) {
                this.page = 1;
            }

            if (this.page > this.totalPage) {
                this.page = this.totalPage;
            }
        }
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
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总数据量
     */
    private Long total;

    /**
     * 当前页数据
     */
    private List<T> data;

    /**
     * 聚合数据
     */
    private Map<String, List<AggregationInfo>> aggregations;

    @Data
    public static class AggregationInfo implements Serializable {

        private static final long serialVersionUID = -4917654280443832357L;

        private String key;

        private Long docCount;

        private List<AggregationInfo> child = new ArrayList<>();

        public void addChild(AggregationInfo child) {
            this.child.add(child);
        }

    }
}
