package com.joyintech.tams.batch.svreport.chb.inLiquidityRiskSub.runner;

import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.tams.batch.svreport.chb.inLiquidityRiskSub.service.InLiquidityRiskInfoMainService;
import com.joyintech.tams.common.svreport.chb.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  流动性风险登记生成
 * @author wangyaning
 * @version 1.0 2022年08月15日
 * @see 
 * @since 1.0
 */
@Service(value = "svrInLiquidityRiskInfoTaskService")
@Scope("prototype")
public class SvrInLiquidityRiskInfoTaskService extends BatchBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InLiquidityRiskInfoMainService mainService;
    @Override
    public JobExecution execute() throws BatchException {
        JobExecution result = new JobExecution(1l);
        result.setStatus(BatchStatus.FAILED);
        SysBatchExecInfo batchExecuteInfo = this.getBatchExecuteInfo();
        if (batchExecuteInfo == null) {
            logger.info("无启动参数，批处理退出。");
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
        logger.info("流动性风险登记信息生成批处理启动:"+sdf.format(bdate));
        try {
            List<String> prodListex  = new   ArrayList<String>();
         
            mainService.generateDurationRegData(ToolsUtil.getCurrentTime(batchExecuteInfo.getExecuteDate())
            		                             ,prodListex);
            result.setStatus(BatchStatus.COMPLETED);
        }
        catch (BatchException e) {
            logger.error("流动性风险登记信息生成批处理Error",e);
        	throw e;
        }
        catch (Exception e) {
			logger.info("批处理异常" + e.getMessage());
        	logger.error("批处理异常Error",e);
        }finally{
        	Date edate=new Date();
            Long et=edate.getTime();
            Long time=(et-bt)/1000;
        	logger.info("流动性风险登记信息报送批处理结束:"+sdf.format(edate)+"耗时"+time+"秒");
        }
        return result;
    }


}
