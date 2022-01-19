package com.yuxing.trainee.core;

import lombok.Getter;

import java.io.Serializable;

/**
 * 分页查询基础类
 *
 * @author yuxing
 * @since 2022/1/15
 */
@Getter
public class Query implements Serializable {

    private static final long serialVersionUID = 5414242197348553095L;

    /**
     * 当前页码, 默认为第一页
     */
    private Integer page = 1;

    /**
     * 单页长度，默认单页长度为10
     */
    private Integer pageSize = 10;

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = 10;
            return;
        }
        this.pageSize = pageSize;
    }

    public Integer getPosition() {
        return (this.page - 1) * this.pageSize;
    }
}
