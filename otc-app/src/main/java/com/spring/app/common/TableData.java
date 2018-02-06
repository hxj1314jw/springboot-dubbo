package com.spring.app.common;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */
public class TableData<T> {
    private long total;
    private List<T> rows;

    public TableData(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public TableData() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
