package com.joyintech.tams.batch.report.chbastposregistexport.runner;


import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.batch.report.chbastposregistexport.service.ChbAstPosRegistExportMainService;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import com.joyintech.tams.report.system.subinfobatchlog.service.ChbSubinfoBatchLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


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

    @Resource
    private ChbSubinfoBatchLogService chbSubinfoBatchLogService;


    @Override
    public JobExecution execute() throws BatchException {
        JobExecution result = new JobExecution(1L);
        result.setStatus(BatchStatus.FAILED);
        chbAstPosRegistExportMainService.getExportData();
        result.setStatus(BatchStatus.COMPLETED);
        return result;

    }

}
