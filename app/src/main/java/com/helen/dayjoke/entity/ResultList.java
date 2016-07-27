package com.helen.dayjoke.entity;

import java.util.List;

/**
 * Created by Helen on 2016/7/27.
 *
 */
public class ResultList<D> extends BaseEn{
    private int count;
    private int total;
    private int page;
    private int err;
    private List<D> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<D> getItems() {
        return items;
    }

    public void setItems(List<D> items) {
        this.items = items;
    }
}
