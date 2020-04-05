package com.zhurong.demo01.entity;

import java.util.List;

public class ResultObject<T> {
    private int dataCount;
    private List<T> data;
    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
