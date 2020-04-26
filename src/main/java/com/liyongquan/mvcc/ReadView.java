package com.liyongquan.mvcc;

import lombok.Data;

@Data
public class ReadView {
    private long lowTrixId;
    private long upLimitId;
    private long[] trx_ids;
    private long creator_trx_id;
}
