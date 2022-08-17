package com.joyintech.tams.batch.svreport.chb.inLiquidityRiskSub.service;

import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.tams.svreport.chbhz.inLiquidityRiskSub.service.InLiquidityRiskInfoBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 功能:流动性风险登记生成
 * @since 
 * @version 
 * @作者：wangyaning
 * @创建时间：
 * @修改记录：
 */
@Service(value="inLiquidityRiskInfoMainService")
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class InLiquidityRiskInfoMainService {

    @Autowired
	private InLiquidityRiskInfoBatchService inLiquidityRiskInfoMainService;
	
    /**
     * @param batchDate
     * @see
     * @since 1.0
     */
    public void generateDurationRegData(Date batchDate,List<String> prodIdList) throws BatchException {
        inLiquidityRiskInfoMainService.generateDeclareInfoData(batchDate, prodIdList);
    }

}