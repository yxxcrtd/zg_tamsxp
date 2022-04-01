package com.joyintech.tams.invest.common.dto.trade.entrust;

import com.joyintech.tams.common.base.entity.EtrFinishStatus;

/**
 * @ClassName EtrFinishStatusDto
 * @Description: TODO
 * @Author wangxuelei
 * @Date 2020/2/18 0018
 * @Version V1.0
 **/
public class EtrFinishStatusDto extends EtrFinishStatus {
    private String cstpTradeId;

    public boolean isDayEndBatch() {
        return isDayEndBatch;
    }

    public void setDayEndBatch(boolean dayEndBatch) {
        isDayEndBatch = dayEndBatch;
    }

    /**
     * 是否是日终批数据
     */
    private boolean isDayEndBatch;

    public String getCstpTradeId() {
        return cstpTradeId;
    }

    public void setCstpTradeId(String cstpTradeId) {
        this.cstpTradeId = cstpTradeId;
    }
}
