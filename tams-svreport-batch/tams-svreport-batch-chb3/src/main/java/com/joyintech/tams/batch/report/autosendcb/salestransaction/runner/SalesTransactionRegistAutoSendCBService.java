/*
 * 文件名：SalesTransactionRegistAutoSendCBService.java
 * 版权：Copyright 2022 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：yangchen
 * 修改时间：2022年10月14日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.batch.report.autosendcb.salestransaction.runner;

import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.tams.batch.report.autosendcb.salestransaction.service.SalesTransactionRegistAutoSendCBMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 销售结算资金交易数据自动检查报送
 *
 * @author yangchen
 * @version 1.0 2022年10月14日
 * @see SalesTransactionRegistAutoSendCBService
 * @since 1.0
 */
@Service(value = "salesTransactionRegistAutoSendCBService")
@Scope("prototype")
public class SalesTransactionRegistAutoSendCBService extends BatchBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 投资者身份信息数据自动检查报送 业务service
     */
    @Autowired
    private SalesTransactionRegistAutoSendCBMainService mainService;

    @Override
    public JobExecution execute() {
        JobExecution result = new JobExecution(1l);
        result.setStatus(BatchStatus.FAILED);
        SysBatchExecInfo batchExecuteInfo = this.getBatchExecuteInfo();
        if (batchExecuteInfo == null) {
            logger.info("无启动参数，批处理退出。");
            return result;
        }

        try {
            mainService.autoSendCB();
        }
        catch (Exception e) {
//            getLogger().log(e);
            logger.error("销售结算资金交易数据自动检查报送Error",e);
        }
        result.setStatus(BatchStatus.COMPLETED);
        return result;
    }
}
