package com.joyintech.tams.batch.svreport.liquidityriskinfo.service;


import com.joyintech.framework.exception.BusinessException;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.LiquidityRiskInfoFileImpService;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service(value = "LiquidityRiskInfoImpMainService")
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class LiquidityRiskInfoImpMainService extends AutoSendCBBaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LiquidityRiskInfoFileImpService liquidityRiskInfoFileImpService;

    public void generateLoanDetailData(Date batchDate) {
        setBatchBeanName(BatchBeanName.流动性风险应对情况信息接口数据接入);
        logInfoBatchStart();
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.流动性风险应对情况信息接口.getCode());
        if (interfConfig == null) {
            logger.info("CHB_INTERF_CONFIG表中无配置项");
        }
        String contentSrc = interfConfig.getContentSrc();

        logger.debug("流动性风险应对情况信息数据接入批处理开始");
        if (InterfConstants.CONTENTSRC_FILE_XML.equals(contentSrc)
                || InterfConstants.CONTENTSRC_FILE_TXT.equals(contentSrc)) {
            String result = liquidityRiskInfoFileImpService.execute(batchDate, InterfDefine.流动性风险应对情况信息接口.getCode());
            if ("fail".equals(result)) {
                throw new BusinessException("fail");
            }
        } else {
            logger.error("CHB_INTERF_CONFIG表中内容来源配置无效!");
        }
        logger.debug("流动性风险应对情况信息数据接入批处理结束");
        logInfoBatchEnd();
    }
}
