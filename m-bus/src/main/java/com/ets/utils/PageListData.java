package com.ets.utils;

import com.ets.common.Page;

import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 11:50
 */
public class PageListData<T> extends Page {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
