package com.joyintech.tams.invest.batch.extrdautosettle.dto;

import com.joyintech.tams.common.base.entity.TrdManualSettleInfo;
import com.joyintech.tams.invest.batch.mproddivreindealgen.dto.MProdDivReinDealDelDto;

/**
 *  自动交收批处理任务数据
 *
 * @author fuyongsheng
 * @version 1.0 2021年11月11日
 * @see ExTrdAutoSettleInfoDto
 * @since 1.0
 */
public class ExTrdAutoSettleInfoDto extends TrdManualSettleInfo {
    /**
     * 交易委托编号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
