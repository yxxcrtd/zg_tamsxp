package com.joyintech.tams.invest.batch.extrdautosettle.dto;

import com.joyintech.batch.batchbase.baseclass.entity.NeedOperateEntity;

import java.util.List;

public class ExTrdAutoSettleInfoReaderResult extends NeedOperateEntity {

    /**
     * 自动交收待处理数据
     */
    List<ExTrdAutoSettleInfoDto> autoSettleInfoDtos;

    public List<ExTrdAutoSettleInfoDto> getAutoSettleInfoDtos() {
        return autoSettleInfoDtos;
    }

    public void setAutoSettleInfoDtos(List<ExTrdAutoSettleInfoDto> autoSettleInfoDtos) {
        this.autoSettleInfoDtos = autoSettleInfoDtos;
    }
}
