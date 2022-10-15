package com.joyintech.tams.report.chbsd.salestransactionmaintenancesub.service;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.SalesTransactionMaintenanceSubDto;
import com.joyintech.component.svrreport.chb.service.MsgSendService;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *  销售结算资金交易登记单批次报送 业务service - 按批次
 * @since 1.0.0
 * @version 1.0.0
 * @author sjj
 * @see SalesTransactionMaintenanceBatchServiceOneService
 */
@Service
public class SalesTransactionMaintenanceBatchServiceOneService extends AutoSendCBBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MsgSendService msgSend;


   /**
    * 单批次处理数据
    * @param handleIndex
    * @param dataCountPerTime
    * @param msgRstList
    * @param sendResult
    * @return
    * @throws Exception 
    */
    public boolean handleDataPerTime(int handleIndex, 
    		                         int dataCountPerTime,List<SalesTransactionMaintenanceSubDto> msgRstList,
    		                         List<SalesTransactionMaintenanceSubDto> sendResult, SendDataCheckType sendDataCheckType) {
        logger.info("【销售结算资金交易登记】直连报送处理开始，批次：" + handleIndex + "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        try {
            	 msgSend.sendSalesTransactionMaintenanceRegistSubMsgs(msgRstList, sendDataCheckType);
            	 sendResult.addAll(msgRstList);
        } catch (Exception e) {
	        logger.error("【销售结算资金交易登记】直连报送处理异常，批次：" + handleIndex + e.getMessage());
	        throw new BusinessException(e);
		}
        logger.info("【销售结算资金交易登记】直连报送处理结束，批次：" + handleIndex + "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return true;
    }
}
