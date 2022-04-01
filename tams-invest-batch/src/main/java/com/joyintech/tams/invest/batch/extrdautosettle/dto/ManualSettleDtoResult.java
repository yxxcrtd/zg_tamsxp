package com.joyintech.tams.invest.batch.extrdautosettle.dto;

import com.joyintech.batch.batchbase.baseclass.entity.NeedToWriteEntity;

import java.util.List;

public class ManualSettleDtoResult extends NeedToWriteEntity {
    /**
     * 待处理数据
     */
    private List<ManualSettleDto> manualSettleDtoList;

    public List<ManualSettleDto> getManualSettleDtoList() {
        return manualSettleDtoList;
    }

    public void setManualSettleDtoList(List<ManualSettleDto> manualSettleDtoList) {
        this.manualSettleDtoList = manualSettleDtoList;
    }
}
