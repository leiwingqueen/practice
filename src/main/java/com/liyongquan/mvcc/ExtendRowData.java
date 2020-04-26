package com.liyongquan.mvcc;

import lombok.Data;

@Data
public class ExtendRowData extends RowData{
    /**
     * DB行号，有主键索引的这里为空
     */
    private Long dbRowId;
    /**
     * 事务ID
     */
    private long dbTrxId;
    /**
     * 回滚指针
     */
    private ExtendRowData dbRowPTR;

    public ExtendRowData(long id, String name, Long dbRowId, long dbTrxId, ExtendRowData dbRowPTR) {
        super(id, name);
        this.dbRowId = dbRowId;
        this.dbTrxId = dbTrxId;
        this.dbRowPTR = dbRowPTR;
    }
}
