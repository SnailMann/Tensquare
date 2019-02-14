package com.snailmann.tensquare.common.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页返回对象
 *
 * @author SnailMann
 */
@Data
public class PageResult<T> {

    /**
     * 总数
     */
    private long total;
    /**
     * 当页数据
     */
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
