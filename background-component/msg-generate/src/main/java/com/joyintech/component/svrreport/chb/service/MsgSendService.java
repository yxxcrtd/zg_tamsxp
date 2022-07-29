/*
 * 文件名：MsgAssembleService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月23日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.service;

import java.util.List;

import com.joyintech.component.svrreport.chb.contant.SendDataCheckType;
import com.joyintech.component.svrreport.chb.dto.*;

/**
 * 组装报文数据
 * @description 
 * @author 王波
 * @version 1.0 2020年9月23日
 * @see MsgSendService
 * @since 1.0
 */
public interface MsgSendService {
	
	/**
	 * 
	 * <设置产品申报登记>
	 * @param datas 产品申报数据体
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbPrdDeclareSubMsgs(List<ChbPrdDeclareSubDto> datas) throws Exception;
	void sendChbPrdDeclareSubMsgs(List<ChbPrdDeclareSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;

	/**
	 * <设置产品发行登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @throws Exception 
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbPrdIssueSubMsgs(List<ChbPrdIssueSubDto> datas) throws Exception;
	void sendChbPrdIssueSubMsgs(List<ChbPrdIssueSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置产品募集总量登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	// ChbMsgReturnInfoDto sendChbPrdRaisendotSubMsgs(List<ChbPrdRaisetotSubDto> datas) throws Exception;
	void sendChbPrdRaisendotSubMsgs(List<ChbPrdRaisetotSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置产品存续期登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbPrdDealSubMsgs(List<ChbPrdDealSubDto> datas) throws Exception;
	void sendChbPrdDealSubMsgs(List<ChbPrdDealSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置产品终止登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbPrdEndSubMsgs(List<ChbPrdEndSubDto> datas) throws Exception;
	void sendChbPrdEndSubMsgs(List<ChbPrdEndSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置资产负债登记要素报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbAssendInfoMsgs(List<ChbAssetInfoDto> datas) throws Exception;
	void sendChbAssendInfoMsgs(List<ChbAssetInfoDto> datas, SendDataCheckType sendDataCheckType) throws Exception;

	/**
	 * <设置底层资产持仓登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbBotAstPosSubMsgs(List<ChbBotAstPosSubDto> datas) throws Exception;
	void sendChbBotAstPosSubMsgs(List<ChbBotAstPosSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置资产交易登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbInvestTradeSubMsgs(List<ChbInvestTradeSubDto> datas) throws Exception;
	void sendChbInvestTradeSubMsgs(List<ChbInvestTradeSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置资产交易登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 */
	//ChbMsgReturnInfoDto sendChbAstValueSubMsgs(List<ChbAstValueSubDto> datas) throws Exception;
	void sendChbAstValueSubMsgs(List<ChbAstValueSubDto> datas, SendDataCheckType sendDataCheckType) throws Exception;
	
	/**
	 * <设置从业人员登记报文>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201017
	 */
	//ChbMsgReturnInfoDto sendChbEmployeeInfoSubMsgs(List<ChbEmployeeInfoDto> datas) throws Exception;
	void sendChbEmployeeInfoSubMsgs(List<ChbEmployeeInfoDto> datas, SendDataCheckType sendDataCheckType) throws Exception;

	/***0722升级**/
	/**
	 * <产品状态登记>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201017
	 */
	void sendProdStatusSubMsgs(List<ChbPrdStatusSub> datas, SendDataCheckType sendDataCheckType) throws Exception;


	/**
	 * <资产持仓登记>
	 * @param datas 数据内容
	 * @return 报送信息「需要拼装的数据」
	 * @see MsgSendService
	 * @since 1.0
	 * @add by zhaoyongsheng 20201017
	 */
	void sendChbAstPosSubMsgs(List<ChbAstPosSub> datas, SendDataCheckType sendDataCheckType) throws Exception;

	/***0722升级**/
}
