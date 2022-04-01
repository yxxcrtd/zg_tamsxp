package com.joyintech.tams.invest.batch.extrdautosettle.writer;

import com.joyintech.batch.batchbase.baseclass.BaseWriter;
import com.joyintech.batch.batchbase.baseclass.entity.NeedToWriteEntity;
import com.joyintech.framework.JoyinRestTemplate;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoDto;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ExTrdAutoSettleInfoResult;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ManualSettleDto;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ManualSettleDtoResult;
import com.joyintech.tams.invest.batch.extrdautosettle.service.ManualSettleService;
import com.joyintech.tams.invest.batch.mproddivreindealgen.dto.MProdDivReinDealGenResult;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.workflow.engine.constant.OperationType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class ExTrdAutoSettleInfoWriter extends BaseWriter<NeedToWriteEntity> {

    /**
     * 手工交收服务
     */
    @Autowired
    private ManualSettleService manualSettleService;

    @Override
    public void write(List<? extends NeedToWriteEntity> items) throws Exception {
        //取process处理完成的结果集
        ManualSettleDtoResult manualSettleDtoResult= (ManualSettleDtoResult)items;

        List<ManualSettleDto> resultList = manualSettleDtoResult.getManualSettleDtoList();

        for (ManualSettleDto manualSettleDto: resultList) {
            // 调用页面交收处理逻辑
            // 由于页面上的传入参数在service.start()是selectedItem数组类型，
            // 此处未调用流程，所以直接complete传入selectedItem作为参数
            for (ManualSettleDto tmp: manualSettleDto.getSelectedItem()) {
                manualSettleService.doComplete(tmp, OperationType.NEW);
            }

        }

    }
}
