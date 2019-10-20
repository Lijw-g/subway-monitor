package com.subway.monitor.common.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ListResult<T> implements Serializable {

    private List<T> list;
    private int count;

    public ListResult(List<T> list) {
        this(list, list == null ? 0 : list.size());
    }

    public ListResult(List<T> list, int count) {
        this.list = list == null ? Collections.emptyList() : list;
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public int getCount() {
        return count;
    }
}
