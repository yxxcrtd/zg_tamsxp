package com.joyintech.tams.batch.svreport.liquidityriskinfo.runner;


import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.tams.batch.svreport.liquidityriskinfo.service.LiquidityRiskInfoImpMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service(value = "svrLiquidityRiskInfoInterfTaskService")
@Scope("prototype")
public class SvrLiquidityRiskInfoInterfTaskService extends BatchBaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LiquidityRiskInfoImpMainService mainService;

    @Override
    public JobExecution execute() {
        JobExecution result = new JobExecution(1L);
        result.setStatus(BatchStatus.FAILED);
        SysBatchExecInfo batchExecInfo = this.getBatchExecuteInfo();
        if (batchExecInfo == null) {
            logger.info("无启动参数，批处理退出。");
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate = new Date();
        Long bt = bdate.getTime();
        logger.info("流动性风险应对情况信息接口批处理启动:"+sdf.format(bdate));
        try {
            mainService.generateLoanDetailData(batchExecInfo.getExecuteDate());
            result.setStatus(BatchStatus.COMPLETED);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        } finally {
            Date edate = new Date();
            Long et = edate.getTime();
            Long time = (et-bt)/1000;
            logger.info("流动性风险应对情况信息接口批处理结束:" + sdf.format(edate) + "耗时" + time + "秒");
        }
        return result;
    }
}

