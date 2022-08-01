package com.joyintech.tams.batch.svreport.chb.chbastposregistexport.runner;

import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.tams.batch.svreport.chb.chbastposregistexport.service.ChbAstPosRegistAutoManualMainService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 资产持仓登记手工处理
 *
 * @author zhoubin
 * @version 1.0 2022年07月27日
 * @see ChbAstPosRegistAutoManualService
 * @since 1.0
 */
@Service(value = "chbAstPosRegistAutoManualService")
@Scope("prototype")
public class ChbAstPosRegistAutoManualService extends BatchBaseService{

    @Autowired
    private ChbAstPosRegistAutoManualMainService mainService;
	
	@Override
	public JobExecution execute() throws BatchException {
        JobExecution result = new JobExecution(1L);
        result.setStatus(BatchStatus.FAILED);

        mainService.autoManual();
        
        result.setStatus(BatchStatus.COMPLETED);
        return result;
	}
}
