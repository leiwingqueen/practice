package com.liyongquan.mvcc;

import java.util.ArrayList;
import java.util.List;

public class RowDataStorage {
    private List<ExtendRowData> list=new ArrayList<>();

    public int append(ExtendRowData rowData){
        return 1;
    }

    public int remove(ExtendRowData rowData){
        return 1;
    }

    public ExtendRowData get(long id){
        return null;
    }
}
