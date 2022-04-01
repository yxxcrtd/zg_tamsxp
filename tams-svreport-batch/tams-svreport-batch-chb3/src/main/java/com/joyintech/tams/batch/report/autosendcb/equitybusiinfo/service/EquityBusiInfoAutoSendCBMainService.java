/*
 * 文件名：EquityBusiInfoAutoSendCBMainService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：刘二通
 * 修改时间：2017年11月14日
 * 修改内容：新建
 * 系统名称：中债报送系统
 */

package com.joyintech.tams.batch.report.autosendcb.equitybusiinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.framework.constant.BatchDictCodeList;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto.EquityBusiInfoRegist001Dto;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.service.EquityBusiInfoRegist001Service;

/**
 * 业务明细数据自动检查报送 业务service
 *
 * @author 刘二通
 * @version 1.0 2017年11月14日
 * @see EquityBusiInfoAutoSendCBMainService
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EquityBusiInfoAutoSendCBMainService extends AutoSendCBBaseService {

    /**
     * EquityBusiInfo001Service
     */
    @Autowired
    private EquityBusiInfoRegist001Service equityBusiInfoRegist001Service;
    /**
     * 投资者明细信息登记mapper
     */
    @Autowired
    private EquityBusiInfoRegist001Mapper equityBusiInfoRegist001Mapper;
    /**
     * 自动检查报送
     *
     * @see
     * @since 1.0
     */
    public void autoSendCB(SysBatchExecInfo batchExecuteInfo) {

        setBatchBeanName(BatchBeanName.投资者明细报送);
        String svrSystemType=SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SEND_EQUDET);
        if(svrSystemType!=null&&svrSystemType.equals("01")){
        	String paraCodes = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, ReportGlobalConstant.SYS_PARA_IS_SEND_EQUDET_WHEN_DEVIATION);
        	Map<String, Object> map=new HashMap<String, Object>();
        	if (StringUtil.equals(BatchDictCodeList.批处理启动模式_手动.getValue(),batchExecuteInfo.getStartMode())) {
        		map.put("holdDateStart", DateUtil.format(batchExecuteInfo.getExecuteDate()));
        		map.put("holdDateEnd", DateUtil.format(batchExecuteInfo.getExecuteDate()));
        	}
        	map.put("sendStatus", "01");
        	Date startDate;
        	List<Date> days;
        	if (StringUtil.equals(BatchDictCodeList.批处理启动模式_手动.getValue(),batchExecuteInfo.getStartMode())) {
        		startDate=batchExecuteInfo.getExecuteDate();
        		days=new ArrayList<Date>();
        		days.add(startDate);
        	}else{
        		//查询出所有未报送数据的日期 
        		days=equityBusiInfoRegist001Mapper.selectAllUnRegistDate();
        		if(StringUtil.isEmptyList(days)){
					logInfoNoHandleData(DateUtil.now());
        			return;
        		}else{
        			startDate=days.get(0);
//                    endDate=days.get(days.size()-1);
        		}
        	}
        	//删除之前先将数据入His表 留痕处理
        	map.put("sendStatus", null);
        	map.put("sendStatusList", null);
        	map.put("holdDateStart", null);
            map.put("holdDateEnd", null);
        	int count=equityBusiInfoRegist001Mapper.selectCountSizeWithSameKey(map);
        	if(count>0){
        		equityBusiInfoRegist001Mapper.insertEquDetRegistHis(map);
        		equityBusiInfoRegist001Mapper.deleteEquDetSubmitRegist(map);
        	}
        	//按日期循环报送   
        	for(Date day:days){
        		//调用投资者份额检查批处理   (配置到任务组中)
//                if("02".equals(paraCodes)){
//                    equityShareCheckMainService.doEquityShareCheck(DateUtil.format(day));
//                }
        		//查询出对应日期所有的登记银行代码(按登记银行代码分次报)
        		List<String> bankCodes=equityBusiInfoRegist001Mapper.selectAllBanksByDate(day);
        		if(StringUtil.isEmptyList(bankCodes)){
					logInfoNoHandleData(DateUtil.now());
				}
        		for(String bank:bankCodes){
        			
        			//投资者份额检查不通过的是否报送 01 是  02 否
        			Map<String, Object> paramMap = new HashMap<String, Object>();
        			paramMap.put("holdDate", day);
        			int dataCountPerTime = Integer.valueOf(
        					CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SEND_CB_DATA_COUNT_PER_TIME));
        			int dataCountPerDay = Integer.valueOf(
        					CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));
        			
        			logInfo("开始按批次处理持有信息数据，每批次处理数据条数：" + dataCountPerTime);
        			paramMap.put("dataCountPerTime", dataCountPerTime);
        			paramMap.put("bankcode", bank);
        			paramMap.put("paraCodes", paraCodes);
        			paramMap.put("dataCountPerDay", dataCountPerDay);
        			
        			// 处理批次
        			int handleIndex = 1;
        			while (true) {
        				// 查询符合条件的待报送数据（前500000条）
        				boolean hasData = this.handleDataPerTime(handleIndex, paramMap);
        				if (!hasData) {
        					break;
        				}
        				handleIndex++;
        			}
        		}
        	}
        }
            
    }
    /**
     * 单批次处理明细信息数据
     *
     * @see
     * @since 1.0
     */
    private boolean handleDataPerTime(int handleIndex, Map<String, Object> paramMap) {
        logInfo("处理开始，批次：" + handleIndex + "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        boolean hasData = true;
        Date batchStartTime = DateUtil.now();
        // 清空数据报送临时表
        logInfo("开始清空数据报送临时表");
        equityBusiInfoRegist001Mapper.trunequdetSubTempTable();
        logInfo("清空数据报送临时表结束");

        // 将待处理数据的报送流水号插入临时表
        logInfo("开始将待处理数据的登记序号插入临时表");
        equityBusiInfoRegist001Mapper.insertUnregistCheckOkDataToTempTable(paramMap);
        logInfo("将待处理数据的登记序号插入临时表结束");

        // 查询待报送数据
        logInfo("开始查询待报送数据");
        List<EquityBusiInfoRegist001Dto> unregistDataList = equityBusiInfoRegist001Mapper.selectAllUnregistCheckOkData();
        logInfo("查询待报送数据结束，数据条数：" + unregistDataList.size());
        if (StringUtil.isEmptyList(unregistDataList)) {
            logInfo("已无待报送数据，处理结束，批次：" + handleIndex + "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			logInfoNoHandleData(DateUtil.now());
            return false;
        }
        //查询出所有未报送数据条数   
        Date cdate=(Date)paramMap.get("holdDate");
        int allUnregistDataCount=equityBusiInfoRegist001Mapper.selectAllDataUnSendCount(cdate,paramMap.get("bankcode").toString());
        logInfoAutoSendResult(batchStartTime, allUnregistDataCount,
            allUnregistDataCount-unregistDataList.size(), unregistDataList.size());
        // 将待报送数据批量更新报送状态
        logInfo("开始将待报送数据批量更新报送状态");
        equityBusiInfoRegist001Mapper.updateSubmitStatus();
        logInfo("将待报送数据批量更新报送状态结束");
        equityBusiInfoRegist001Service.dealDataList(unregistDataList, false);
        equityBusiInfoRegist001Service.sendBusiInfoRegistCHB(unregistDataList, true);
        unregistDataList.clear();
        logInfo("处理结束，批次：" + handleIndex + "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return hasData;
    }

   
}