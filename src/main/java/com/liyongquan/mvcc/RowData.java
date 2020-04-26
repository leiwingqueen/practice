package com.liyongquan.mvcc;

import lombok.Data;

@Data
public class RowData {
    long id;
    String name;

    public RowData() {
    }

    public RowData(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
