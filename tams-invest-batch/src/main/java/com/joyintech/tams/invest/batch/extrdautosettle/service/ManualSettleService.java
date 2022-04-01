package com.joyintech.tams.invest.batch.extrdautosettle.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.ManualSettleDto;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zhangnian
 * @ClassName ManualSettleService
 * @description 手工交收
 * @date 2020/10/21 0021 16:42
 **/
@Service
public class ManualSettleService extends CommonExportService<ManualSettleDto>
        implements BaseProcessService<ManualSettleDto> {



    @Override
    public void doComplete(ManualSettleDto dto,OperationType operationType){
        //处理业务数据
        handleBusData(dto);
        //交收、回溯分别按有效、撤销状态处理持仓数据
        //交收失败
        if(StringUtil.equals(dto.getPageModel(), DictCodeListExt3.交收操作类型_交收.getValue())
                ||StringUtil.equals(dto.getPageModel(),DictCodeListExt3.交收操作类型_回溯.getValue())
                ||StringUtil.equals(dto.getPageModel(),DictCodeListExt3.交收操作类型_交收失败.getValue())){
            settleCash(dto);
        }
    }

    /**
     *功能描述：交收头寸处理
     * @author zhangnian
     * @date 2020/10/22 0022
     * @param  * @param dto
     * @return void
     */
    public void settleCash(ManualSettleDto dto){
        restTemplate.postForObject(
                ServiceApplication.TRADE_SERVICE_NAME + "/ManualSettle/settleCash", dto,
                Integer.class);
    }

    /**
     *功能描述 ：交收失败
     * @author zhangnian
     * @date 2020/10/22 0022
     * @param  * @param dto
     * @return void
     */
    public void recall(ManualSettleDto dto){
        restTemplate.postForObject(
                ServiceApplication.TRADE_SERVICE_NAME + "/ManualSettle/recall", dto,
                Integer.class);
    }

    /**
     *功能描述:处理业务数据
     * @author zhangnian
     * @date 2020/10/22 0022
     * @param  * @param entity
     * @return void
     */
    public void handleBusData(ManualSettleDto entity){
        restTemplate.postForObject(
                ServiceApplication.TRADE_SERVICE_NAME + "/ManualSettle/settle", entity,
                Integer.class);
    }

    @Override
    public void doInsert(ManualSettleDto entity, OperationType type) {

    }

    @Override
    public void doUpdate(ManualSettleDto entity) {

    }

    @Override
    public void doBack(ManualSettleDto entity) {

    }



    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if(StringUtil.equals(propName,"tradeDate") || StringUtil.equals(propName,"settleDate")){
            if(value instanceof Date){
                cell.setCellValue(DateUtil.format((Date)value));
            }else if (value instanceof Long){
                cell.setCellValue(DateUtil.format(new Date((Long) value)));
            }
        }
    }


}