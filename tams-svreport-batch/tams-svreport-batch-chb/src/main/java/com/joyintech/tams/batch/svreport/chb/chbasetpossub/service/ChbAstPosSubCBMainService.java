package com.joyintech.tams.batch.svreport.chb.chbasetpossub.service;

import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.svreport.chb.astposregist.service.ChbAstPosSubRegistBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 功能:资产持仓登记批处理调度入口
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbAstPosSubCBMainService extends AutoSendCBBaseService {

    @Autowired
    private ChbAstPosSubRegistBatchService chbAstPosSubRegistBatchService;
    
	public void createRegist(Date batchTime) throws BatchException {
		chbAstPosSubRegistBatchService.createRegist(batchTime);
	}
	
}