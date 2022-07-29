package com.joyintech.tams.svreport.chbsd.prodstatusregistsub.service;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.ChbPrdStatusSub;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import com.joyintech.tams.svreport.chb.prodstatusregist.service.ProdStatusRegist001Service;
import com.joyintech.tams.svreport.chbsd.prdendsub.dto.SdProdTerminationSvrDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能:产品状态登记报送批处理主service
 * 
 * @since 1.0.0
 * @version 1.0.0
 * @作者：zhaoyongsheng @创建时间： @修改记录：
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class SdProdStatusRegistBatchService {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private SdProdStatusRegistBatchServiceOneService sdProdStatusRegistBatchServiceOneService;

	@Autowired
	private ProdStatusRegist001Service prodStatusRegist001Service;


	
	public void sendSubInfoMsg(Date batchDate, List<String> prodIdList) throws InvocationTargetException, IllegalAccessException {
		logger.debug("产品状态登记未报送数据检索开始------" + df.format(new Date()));
		// 1、报送数据检索,取得 List
		List<ProdStatusRegist001Dto> listSub = prodStatusRegist001Service.selcteDataByIds(DateUtil.format(batchDate), prodIdList);
		logger.debug("产品状态登记未报送数据检索结束------" + df.format(new Date()));

		if(StringUtil.isEmptyList(listSub)){
			logger.info("没有未报送数据，批处理结束");
    		return;
    	}

		// 调用check逻辑
		logger.debug("产品状态登记数据检查开始------检查前数据个数：" + listSub.size());
		List<CheckReport001Dto> resultlist = prodStatusRegist001Service.check(null,prodIdList);
		Map<String,CheckReport001Dto> checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getRefseqno,(p)->p,(key1, key2)->key1));
		for (int i = listSub.size() - 1; i >= 0; i--){
			if (checkmap.containsKey(listSub.get(i).getRegistSeqno())
					&& (StringUtil.isNotEmpty(checkmap.get(listSub.get(i).getRegistSeqno()).getErrorInfo())
					|| (StringUtil.isNotEmpty(checkmap.get(listSub.get(i).getRegistSeqno()).getWarnInfo())
					&& !StringUtil.equals("01", CacheUtil.getSysParam("CHB_WARNING_CHECKRESULT_IS_SEND"))))){
				listSub.remove(i);
			}
		}
		logger.debug("产品状态登记数据检查结束------检查后数据个数：" + listSub.size());

		if(StringUtil.isEmptyList(listSub)){
			logger.info("检查后的数据全部不合规，批处理结束");
    		return;
    	}
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		List<ChbPrdStatusSub> dataList = new ArrayList<ChbPrdStatusSub>();
		for(ProdStatusRegist001Dto sdDto : listSub){
			ChbPrdStatusSub target = new ChbPrdStatusSub();
			BeanUtils.copyProperties(sdDto, target);
			dataList.add(target);
		}

		this.sendBatchCommon(dataList, SendDataCheckType.报送);
	}

	/**
	 * 发送共通处理
	 * @param dataList
	 */
	public void sendBatchCommon(List<ChbPrdStatusSub> dataList, SendDataCheckType sendDataCheckType) {
		//3、调用报送组件
		logger.debug("产品状态登记报文报送开始------" + df.format(new Date()));
		//获取单批次发送条目数
		int dataCountPerTime = Integer.valueOf(
				CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SEND_CB_DATA_COUNT_PER_TIME_FIRST));
		// 处理批次
		int handleIndex = 1;
		int dataCount = dataList.size();
		// 报送数据条数
		List<ChbPrdStatusSub> sendResult = new ArrayList<ChbPrdStatusSub>();
		List<ChbPrdStatusSub> newDataList = new ArrayList<ChbPrdStatusSub>();
		newDataList.addAll(dataList);
		//分批处理数据报送
		if (dataCount <= dataCountPerTime) {
			sdProdStatusRegistBatchServiceOneService
					.handleDataPerTime(handleIndex, dataCountPerTime,dataList,sendResult, sendDataCheckType);
		}else{
			// 分批次数
			int part = dataCount / dataCountPerTime;
			for (int i = 0; i < part; i++ ) {
				sdProdStatusRegistBatchServiceOneService
						.handleDataPerTime(handleIndex, dataCountPerTime,newDataList.subList(0, dataCountPerTime),
								sendResult, sendDataCheckType);
				newDataList.subList(0, dataCountPerTime).clear();
				handleIndex ++;

			}
			// 最后剩下的数据
			if (!StringUtil.isEmptyList(newDataList)) {
				sdProdStatusRegistBatchServiceOneService
						.handleDataPerTime(handleIndex, dataCountPerTime,newDataList,sendResult, sendDataCheckType);
			}
		}
		//ChbMsgReturnInfoDto returnMsg = msgSend.sendChbPrdEndSubMsgs(dataList);
		logger.debug("产品状态登记报文报送结束------" + df.format(new Date()));
		//4、日志输出
		logger.info("产品状态登记自动报送处理结束, 共检索出未报送数据 " + dataList.size() + " 条 "
				+" 实际报送数据 " + sendResult.size() + "条");
	}

}
