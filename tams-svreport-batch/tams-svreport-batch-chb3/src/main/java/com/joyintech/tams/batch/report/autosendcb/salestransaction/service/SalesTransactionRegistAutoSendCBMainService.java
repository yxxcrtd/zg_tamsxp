/*
 * 文件名：EquityInfoAutoSendCBMainService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：刘二通
 * 修改时间：2017年12月9日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.batch.report.autosendcb.salestransaction.service;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.chbsd.salestransactionmaintenancesub.service.SalesTransactionMaintenanceBatchService;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfoDistinctService;
import com.joyintech.tams.report.submitmanagement.equityinforegist.service.EquityInfoRegist001Service;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service.SalesTransactionMaintenanceRegist001Service;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 销售结算资金交易数据自动检查报送 业务service
 *
 * @author yangchen
 * @version 1.0 2022年10月14日
 * @see SalesTransactionRegistAutoSendCBMainService
 * @since 1.0
 */
@Service
public class SalesTransactionRegistAutoSendCBMainService extends AutoSendCBBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SalesTransactionMaintenanceRegist001Service salesTransactionMaintenanceRegist001Service;


    @Autowired(required = false)
    private SalesTransactionMaintenanceBatchService salesTransactionMaintenanceBatchServiceService;

    /**
     * 自动检查报送
     *
     * @see
     * @since 1.0
     */
    public void autoSendCB() {

        setBatchBeanName(BatchBeanName.销售结算资金交易数据自动检查报送);

        Date batchStartTime = DateUtil.now();

            logInfoBatchStart();
//            equityInfoDistinctService.checkBeforeSubmit();
            logInfoAutoCheckStart();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("statusListStr", DictCodeListExt2.报送数据状态_新建.getValue() + GlobalConstant.COMMA
                                       + DictCodeListExt2.报送数据状态_修改.getValue());
//            String keyListStr = equityInfoRegist001Service.getKeyList(param);
            logInfo("getAllUnSubmit查询全量数据开始");
            List<SalesTransactionMaintenanceRegist001Dto> list =salesTransactionMaintenanceRegist001Service.getAllUnSubmit(param);
            logInfo("getAllUnSubmit查询全量数据结束");
            if (CollectionUtils.isEmpty(list)) {
                logInfoNoHandleData(batchStartTime);
                return;
            }

            int keyCount = list.size();

            logInfo("sendAllDataBeforeCheck 报送前检查开始");
            List<ExcelValidDto> validRstList = salesTransactionMaintenanceRegist001Service.sendAllDataBeforeCheck(list);
            logInfo("sendAllDataBeforeCheck 报送前检查结束");

            logInfoAutoCheckResult(keyCount, validRstList.size());
            logInfoAutoSendStart();
            if (keyCount == validRstList.size()) {
                logInfoNoCheckOKData(batchStartTime, keyCount, validRstList.size());
                return;
            }

            Map<String, String> errorDataKeyMap = validRstList.stream().collect(
                JoyinCollectors.toMap(x -> x.getValidKey(), x -> x.getValidKey()));

            // 剔除检查不通过的数据(主键：uuid)
            List<SalesTransactionMaintenanceRegist001Dto> sendKeyList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();

            if (errorDataKeyMap.size() > 0) {
                for (SalesTransactionMaintenanceRegist001Dto salesTransactionMaintenanceRegist001Dto : list) {
                    if (!errorDataKeyMap.containsKey(salesTransactionMaintenanceRegist001Dto.getRegistSeqno())) {
                        sendKeyList.add(salesTransactionMaintenanceRegist001Dto);
                    }
                }
            }
            else {
                sendKeyList.addAll(list);
            }
            if (StringUtil.isEmptyList(sendKeyList)) {
                logInfoNoCheckOKData(batchStartTime, keyCount, validRstList.size());
                return;
            }

            //将报送数据按照 UUID主键 放入map中
            Map<String,List<SalesTransactionMaintenanceRegist001Dto>> registMap = new HashMap<String,List<SalesTransactionMaintenanceRegist001Dto>>();
            List<SalesTransactionMaintenanceRegist001Dto> equList = null;
            for (SalesTransactionMaintenanceRegist001Dto salesTransactionMaintenanceRegist001Dto2 : list) {
                if(StringUtil.isEmptyList(registMap.get(salesTransactionMaintenanceRegist001Dto2.getRegistSeqno()))){
                    equList = new ArrayList<SalesTransactionMaintenanceRegist001Dto>();
                }
                else{
                    equList = registMap.get(salesTransactionMaintenanceRegist001Dto2.getRegistSeqno());
                }
                equList.add(salesTransactionMaintenanceRegist001Dto2);
                registMap.put(salesTransactionMaintenanceRegist001Dto2.getRegistSeqno(), equList);
            }

            Iterator<Map.Entry<String, List<SalesTransactionMaintenanceRegist001Dto>>> entries = registMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<SalesTransactionMaintenanceRegist001Dto>> entry = entries.next();
                List<SalesTransactionMaintenanceRegist001Dto> registList = entry.getValue();
                logInfo("sendSalesTransactionCHB处理开始");

                try {
                    salesTransactionMaintenanceRegist001Service.sendSalesTransactionCHB(registList, true, SendDataCheckType.报送);
                } catch (Exception e) {
                        logger.error("【销售结算资金交易登记】发送销售结算资金交易信息处理异常" + e.getMessage());
                        throw new BusinessException(e);
                }

                logInfo("sendSalesTransactionCHB处理结束");
            }

            logInfoAutoSendResult(batchStartTime, keyCount, validRstList.size(),
                sendKeyList.size());

            logInfoBatchEnd();
    }
}
