/*
 * 文件名：MsgAssembleServiceImpl.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月23日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.service.impl;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.contant.SubTableType;
import com.joyintech.component.svrreport.chb.dto.*;
import com.joyintech.component.svrreport.chb.msgassemble.service.*;
import com.joyintech.component.svrreport.chb.msglog.service.ChbComponentMsgLogService;
import com.joyintech.component.svrreport.chb.msgsend.dto.ChbSendMsgInfo;
import com.joyintech.component.svrreport.chb.msgsend.service.CnBondSendService;
import com.joyintech.component.svrreport.chb.reportstatus.dao.ChbComponentStatusUpdateMapper;
import com.joyintech.component.svrreport.chb.reportstatus.service.ChbComponentStatusUpdateService;
import com.joyintech.component.svrreport.chb.service.MsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 组装报文数据
 * @author 王波
 * @version 1.0 2020年9月23日
 * @see MsgSendServiceImpl
 * @since 1.0
 */
@Component
public class MsgSendServiceImpl implements MsgSendService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MsgSendServiceImpl.class);

	/**
	 * 产品申报登记 - 报文设置
	 */
	@Autowired
	private ChbPrdDeclareSubSetService chbPrdDeclareSubSetService;
	
	/**
	 * 产品发行登记 - 报文设置
	 */
	@Autowired
	private ChbPrdIssueSubSetService chbPrdIssueSubSetService;
	

	/**
	 * 产品募集总量登记 - 报文设置
	 */
	@Autowired
	private ChbPrdRaisetotSubSetService chbPrdRaisetotSubSetService;
	
	/**
	 * 产品存续期登记 - 报文设置
	 */
	@Autowired
	private ChbPrdDealSubSetService chbPrdDealSubSetService;
	
	/**
	 * 产品终止登记 - 报文设置
	 */
	@Autowired
	private ChbPrdEndSubSetService chbPrdEndSubSetService;
	
	/**
	 * 底层资产持仓登记 - 报文设置
	 */
	@Autowired
	private ChbBottomAstPosSubSetService chbBottomAstPosSubSetService;
	
	/**
	 * 资产估值登记 - 报文设置
	 */
	@Autowired
	private ChbAssetValSubSetService chbAssetValSubSetService;
	
	/**
	 * 资产交易登记 - 报文设置
	 */
	@Autowired
	private ChbInvestTradeSubSetService chbInvestTradeSubSetService;
	
	/**
	 * 资产要素登记 - 报文设置
	 */
	@Autowired
	private ChbAssetInfoSubSetService chbAssetInfoSubSetService;

	/**
	 * 从业人员登记 - 报文设置
	 */
	@Autowired
	private ChbEmployeeInfoSubSetService chbEmployeeInfoSubSetService;
	
	@Autowired
	private CnBondSendService cnBondSendService;

	/**
	 * 更新状态服务
	 */
	@Autowired
	private ChbComponentStatusUpdateService chbComponentStatusUpdateService;

	/**
	 * 日志状态服务
	 */
	@Autowired
	private ChbComponentMsgLogService chbComponentMsgLogService;

	/**
	 * 线程池
	 */
	@Autowired(required = false)
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * {@linkplain}
	 */
	@Autowired
	private ChbComponentStatusUpdateMapper chbComponentStatusUpdateMapper;


	/**
	 * 中债直连报送返回信息
	 * @param chbMsgBuildInfoDto 报文数据
	 * @return
	 * @throws Exception
	 */
	private void getChbMsgSendInfo(ChbMsgBuildInfoDto chbMsgBuildInfoDto, SendDataCheckType sendDataCheckType) throws Exception {
		ChbBondSubmitPara para = chbMsgBuildInfoDto.getChbBondSubmitPara();
		ChbSendMsgInfo sendInfo  = new ChbSendMsgInfo();
		sendInfo.setMsgType(getDiReportCode(para.getDealType()));
		sendInfo.setFilePath(chbMsgBuildInfoDto.getFilePath());

		// 此处需要加一个校验，校验待发送数据状态是否发生变更
		if (!StringUtil.isEmptyList(para.getRegistSeq())){
			SubTableType subTableType = SubTableType.genSubTableTypeBySubmitType(para.getDealType());
			if (subTableType != null){
				List<ChbSubmitStatusDto> chbSubmitStatusDtoList = chbComponentStatusUpdateMapper.querySubDataStatusBySumitSeq(subTableType.getTableId(), para.getRegistSeq());
				if (!StringUtil.isEmptyList(chbSubmitStatusDtoList)){
					if (!StringUtil.isEmptyList(chbSubmitStatusDtoList.stream().filter(x -> !StringUtil.equals(x.getStatus(), sendDataCheckType.getStatus())
							|| !StringUtil.equals(x.getSendStatus(), sendDataCheckType.getSendStatus())
							|| !StringUtil.equals(x.getSubmitFeedbackStatus(), sendDataCheckType.getSubmitFeedbackStatus())).collect(
							Collectors.toList()))){
						throw new RuntimeException("MSG:中债直连发送数据状态发生变更，请刷新后操作！");
					}
				}
			}
		}
		logger.info("数据库更新-修改状态为发送中");
		//2. 更新数据库状态
		chbComponentStatusUpdateService.updateSendStatus(para.getRegistSeq(), para.getDealType(),
				DictCodeList.报送状态_接口报送.getValue(), DictCodeList.报送反馈状态_发送中.getValue());
		logger.info("数据库更新结束,开始线程处理~发送中修改完毕");

		// 线程异步处理
		threadPoolTaskExecutor.execute(new Runnable() {
			public void run() {
				// 3. 发送数据
				ChbMsgReturnInfoDto chbMsgReturnInfoDto = null;
				try{
					chbMsgReturnInfoDto = cnBondSendService.sendCnBond(sendInfo,
							chbMsgBuildInfoDto.getChbBondSubmitPara(),
							chbMsgBuildInfoDto.getLogList(),
							chbMsgBuildInfoDto.getInterfLog());
				}catch (Throwable e){
					//调用中债发送API的时候，可能抛出Throwable、exception两种异常。这里只处理父类Throwable
					chbMsgReturnInfoDto = null;
					logger.error("调用中债发送API的时候，可能抛出Throwable、exception两种异常。这里只处理父类Throwable 调用异常:",e);
				}finally{
					//无论是否有错误.都需要更新数据状态.否则后面的操作全部无法执行下去,且无法判断数据状态
					//4. 处理返回结果
					chbComponentStatusUpdateService.sendBackHandle(chbMsgReturnInfoDto, para, chbMsgBuildInfoDto);
				}
			}
		});
	}

	/**
	 ** 产品报表编码与DI接口编码转换
	 */
	public String getDiReportCode(String getDealType){
		String DIReportCode = "";

		switch (getDealType){
			case "01":
				DIReportCode = DictCodeList.报表编码_产品申报登记.getValue();
				break;
			case "02":
			case "03":
				DIReportCode = DictCodeList.报表编码_产品发行登记.getValue();
				break;
			case "04":
				DIReportCode = DictCodeList.报表编码_募集总量登记.getValue();
				break;
			case "05":
				DIReportCode = DictCodeList.报表编码_资管计划底层持仓信息登记.getValue();
				break;
			case "06":
				DIReportCode = DictCodeList.报表编码_产品存续期登记.getValue();
				break;
			case "07":
				DIReportCode = DictCodeList.报表编码_产品终止登记.getValue();
				break;
			case "09":
				DIReportCode = DictCodeList.报表编码_从业人员登记.getValue();
				break;
			case "10":
				DIReportCode = DictCodeList.报表编码_资产要素信息登记.getValue();
				break;
			case "11":
				DIReportCode = DictCodeList.报表编码_资产交易信息登记.getValue();
				break;
			case "12":
				DIReportCode = DictCodeList.报表编码_资产估值信息登记.getValue();
				break;

		}
		return DIReportCode;
	}

	/**
	 * <设置产品申报登记>
	 * @param datas 产品申报数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201012
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbPrdDeclareSubMsgs(List<ChbPrdDeclareSubDto> datas) throws Exception {
	public void sendChbPrdDeclareSubMsgs(List<ChbPrdDeclareSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbPrdDeclareSubSetService.setMsg(datas);
		
		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}

	/**
	 * <设置产品发行登记>
	 * @param datas 产品发行数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201012
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbPrdIssueSubMsgs(List<ChbPrdIssueSubDto> datas) throws Exception {
	public void sendChbPrdIssueSubMsgs(List<ChbPrdIssueSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbPrdIssueSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}
	/**
	 * <设置产品募集总量登记>
	 * @param datas 产品募集总量数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201012
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbPrdRaisendotSubMsgs(List<ChbPrdRaisetotSubDto> datas) throws Exception  {
	public void sendChbPrdRaisendotSubMsgs(List<ChbPrdRaisetotSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception  {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbPrdRaisetotSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}
	/**
	 * <设置产品存续期登记>
	 * @param datas 产品存续期数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201012
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbPrdDealSubMsgs(List<ChbPrdDealSubDto> datas) throws Exception {
	public void sendChbPrdDealSubMsgs(List<ChbPrdDealSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbPrdDealSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}
	/**
	 * <设置产品终止登记>
	 * @param datas 产品终止数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201012
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbPrdEndSubMsgs(List<ChbPrdEndSubDto> datas) throws Exception {
	public void sendChbPrdEndSubMsgs(List<ChbPrdEndSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbPrdEndSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}

	@Override
	//public ChbMsgReturnInfoDto sendChbAssendInfoMsgs(List<ChbAssetInfoDto> datas)  throws Exception{
	public void sendChbAssendInfoMsgs(List<ChbAssetInfoDto> datas, SendDataCheckType sendDataCheckType)  throws Exception{
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbAssetInfoSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//
//		return chbMsgReturnInfoDto;
	}

	@Override
	//public ChbMsgReturnInfoDto sendChbBotAstPosSubMsgs(List<ChbBotAstPosSubDto> datas) throws Exception{
	public void sendChbBotAstPosSubMsgs(List<ChbBotAstPosSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception{
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbBottomAstPosSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}

	@Override
	//public ChbMsgReturnInfoDto sendChbInvestTradeSubMsgs(List<ChbInvestTradeSubDto> datas) throws Exception {
	public void sendChbInvestTradeSubMsgs(List<ChbInvestTradeSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception {
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbInvestTradeSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}

	@Override
	//public ChbMsgReturnInfoDto sendChbAstValueSubMsgs(List<ChbAstValueSubDto> datas) throws Exception{
	public void sendChbAstValueSubMsgs(List<ChbAstValueSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception{
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbAssetValSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}
	
	/**
	 * <设置从业人员登记>
	 * @param datas 从业人员数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201017
	 */
	@Override
	//public ChbMsgReturnInfoDto sendChbEmployeeInfoSubMsgs(List<ChbEmployeeInfoDto> datas) throws Exception{
	public void sendChbEmployeeInfoSubMsgs(List<ChbEmployeeInfoDto> datas, SendDataCheckType sendDataCheckType) throws Exception{
		// 1. 设置报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbEmployeeInfoSubSetService.setMsg(datas);

		// 2. 发送数据
		getChbMsgSendInfo(chbMsgBuildInfoDto, sendDataCheckType);
//		ChbMsgReturnInfoDto chbMsgReturnInfoDto = getChbMsgSendInfo(chbMsgBuildInfoDto);
//		return chbMsgReturnInfoDto;
	}

}
