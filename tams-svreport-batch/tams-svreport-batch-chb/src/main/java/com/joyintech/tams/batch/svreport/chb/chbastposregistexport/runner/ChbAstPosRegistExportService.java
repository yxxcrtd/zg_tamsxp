package com.joyintech.tams.batch.svreport.chb.chbastposregistexport.runner;


import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.tams.batch.svreport.chb.chbastposregistexport.service.ChbAstPosRegistExportMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 资产持仓登记批处理导出
 *
 * @author zhoubin
 * @version 1.0 2022年07月18日
 * @see ChbAstPosRegistExportService
 * @since 1.0
 */
@Service(value = "chbAstPosRegistExportService")
@Scope("prototype")
public class ChbAstPosRegistExportService extends BatchBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ChbAstPosRegistExportMainService chbAstPosRegistExportMainService;


    @Override
    public JobExecution execute() throws BatchException {
        JobExecution result = new JobExecution(1L);
        result.setStatus(BatchStatus.FAILED);
        chbAstPosRegistExportMainService.getExportData();
        result.setStatus(BatchStatus.COMPLETED);
        return result;

    }

}
