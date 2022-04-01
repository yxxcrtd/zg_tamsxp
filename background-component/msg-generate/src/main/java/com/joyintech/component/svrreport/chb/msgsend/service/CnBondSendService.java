/*
 * 文件名：CnBondSendService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.msgsend.service;

import java.util.ArrayList;
import java.util.List;

import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DataIndexSequenceType;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.AsynBizInfoEntity;
import com.joyintech.component.svrreport.chb.dto.AsynDocumentEntity;
import com.joyintech.component.svrreport.chb.dto.AsynMsgHeaderEntity;
import com.joyintech.component.svrreport.chb.dto.AsynReportEntity;
import com.joyintech.component.svrreport.chb.dto.AsynSysXcptnEntity;
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbInfoReportDto;
import com.joyintech.component.svrreport.chb.dto.ChbMsgReturnInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbSubmitStatusDto;
import com.joyintech.component.svrreport.chb.dto.CommonNodeEntity;
import com.joyintech.component.svrreport.chb.dto.InterfLog;
import com.joyintech.component.svrreport.chb.msgsend.dto.ChbSendMsgInfo;
import com.joyintech.component.svrreport.chb.msgsend.dto.ExtFprDClient;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;
import com.joyintech.component.svrreport.chb.util.InterfLogUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 报文发送
 * @description 
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see CnBondSendService
 * @since 1.0
 */
@Service	
public class CnBondSendService {

	@Value("${application.chb.isExceptionWhenSendTimeout:01}")
	private String isExceptionWhenSendTimeout;

	@Autowired
	private ExtFprDClient dclientComponent;

	
	/**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CnBondSendService.class);

	/**
	 * 添加中债挡板直连标识
	 * 02直连，01标识挡板
	 */
	@Value("${application.chb.dangbanFlag:02}")
	private String dangbanFlag;


	/**
	 * 发送报文到中债
	 * @param sendMsgInfo 发送的报文信息
	 * @param para 标准报文参数
	 * @return 返回发送结果
	 * @throws Exception 异常
	 */
	public ChbMsgReturnInfoDto sendCnBond(ChbSendMsgInfo sendMsgInfo,
										  ChbBondSubmitPara para,
										  List<InterfLog> logList,
										  InterfLog log) throws Exception {
		ChbMsgReturnInfoDto res = new ChbMsgReturnInfoDto();
        logger.debug("报送中债处理开始...");
		/** 根据挡板标识来确认是否打开 start**/
//        AsynReportEntity asynReportEntity = markDangBan();
		//AsynReportEntity asynReportEntity = recurrence(sendMsgInfo.getMsgType(), sendMsgInfo.getFilePath());
		AsynReportEntity asynReportEntity = null;
		if(StrUtil.equals(dangbanFlag, DictCodeList.是否_中债_是.getValue())){
			asynReportEntity = markDangBan();
		}
		else {
			asynReportEntity = recurrence(sendMsgInfo.getMsgType(), sendMsgInfo.getFilePath());
		}
		/** 根据挡板标识来确认是否打开 end**/
		logger.debug("报送中债处理结束...");
		if (asynReportEntity == null) {
			logger.info("报送中债获取信息失败:返回报文为空!");
			logList.addAll(InterfLogUtil.createLogList(log, DictCodeList.接口类型_发送联机交易.getValue(), ChbConstant.FAILURE,
					ChbConstant.WARNING, "返回报文为空,文件地址：" + FileUtil.exist("./AppCfg/DC/cfg"), true));
			// 返回之前写日志
			//chbComponentMsgLogService.insertInterfLogList(logList);
			return null;
		}
		res.setDealType(para.getDealType());
		res.setFilePath(sendMsgInfo.getFilePath());
		saveData(asynReportEntity, para,  res);
		logList.addAll(
				InterfLogUtil.createLogList(log, DictCodeList.接口类型_结束.getValue(), ChbConstant.SUCCESS, ChbConstant.UNWARNING, null, false));
		// 返回之前写日志
		//chbComponentMsgLogService.insertInterfLogList(logList);
		return res;
	}
	
	AsynReportEntity markDangBan(){
        AsynReportEntity as=new AsynReportEntity();
        AsynDocumentEntity document=new AsynDocumentEntity();
        as.setDocument(document);
        AsynSysXcptnEntity sysXcptn =new AsynSysXcptnEntity();
        document.setSysXcptn(sysXcptn);
        CommonNodeEntity fileId=new CommonNodeEntity();
        CommonNodeEntity xcptnRtrCd=new CommonNodeEntity();
        CommonNodeEntity xcptnRtrTx=new CommonNodeEntity();
        fileId.setValue("01");
        xcptnRtrCd.setValue("0000");
        xcptnRtrTx.setValue("01");
        sysXcptn.setFileId(fileId);
        sysXcptn.setXcptnRtrCd(xcptnRtrCd);
        sysXcptn.setXcptnRtrTx(xcptnRtrTx);
        AsynMsgHeaderEntity msgHeader=new AsynMsgHeaderEntity();
        AsynBizInfoEntity bizInfo=new AsynBizInfoEntity();
        CommonNodeEntity origMsgId=new CommonNodeEntity();
        origMsgId.setValue("aaaaaaa");
        bizInfo.setOrigMsgId(origMsgId);
        msgHeader.setBizInfo(bizInfo);
        as.setMsgHeader(msgHeader);
        return as;
    }

	/**
	 *
	 * @param asynReportEntity
	 * @param para
	 */
	private void saveData(AsynReportEntity asynReportEntity, ChbBondSubmitPara para,
						  ChbMsgReturnInfoDto res) {
		ChbInfoReportDto report = new ChbInfoReportDto();

		List<ChbSubmitStatusDto> statusDtos = new ArrayList<>();

		// 报送编码
		String reportNo = Sid.nextShort(20);
		// 报送结果码
		String rcntCode = getVaule(asynReportEntity.getDocument().getSysXcptn().getXcptnRtrCd());

		// 返回报送结果码
		res.setRetCode(rcntCode);

		// 报送编码
		report.setReportNo(reportNo);
		// 报送报文编码
		report.setRepMesNo(getVaule(asynReportEntity.getMsgHeader().getBizInfo().getOrigMsgId()));

		// 报送结果码
		report.setUploadResult(rcntCode);

		// 报送结果信息
		report.setUploadResultMsg(
				getVaule(asynReportEntity.getDocument().getSysXcptn().getXcptnRtrTx()));

		// 报送结果文件代码
		report.setUploadFileId(getVaule(asynReportEntity.getDocument().getSysXcptn().getFileId()));
		// 业务日期
		report.setSendTime(para.getSysDate());
		// 报送条数
		report.setCheckTotalCount(para.getRegistSeq().size());

		// 有效
		report.setStatus(DictCodeList.维护数据状态_有效.getValue());

		// 报送类型
		report.setSubmitType(para.getDealType());

		res.setChbInfoReportDto(report);
		// 插入报送表
//		chbComponentMsgLogService.insertReportInfo(report);

		for (String seq : para.getRegistSeq()) {
			// 中债报送状态
			ChbSubmitStatusDto submitStatus = new ChbSubmitStatusDto();
			// 报送序列号
			submitStatus.setSeqno(seq);
			// 报送编码
			submitStatus.setReportNo(reportNo);
			// 报送流水号
			submitStatus.setSubmitNum(seq);
			// 业务类型
			submitStatus.setSubmitType(para.getDealType());
			//识别标识  通过流水号获取
			submitStatus.setUniqueIdenti(para.getRegistSeqKeyMap().get(seq));
			submitStatus.setEquityConfDate(para.getEquityConfDate());
			if (ChbConstant.XCPTNRTRCD_0000.equals(rcntCode)) {
				submitStatus.setSubmitFeedbackStatus(DictCodeList.报送反馈状态_发送成功.getValue());
			}
			else {
				submitStatus.setSubmitFeedbackStatus(DictCodeList.报送反馈状态_发送失败.getValue());
			}
			// 有效
			submitStatus.setStatus(DictCodeList.维护数据状态_有效.getValue());
			statusDtos.add(submitStatus);
		}
		res.setChbSubmitStatusDtos(statusDtos);
//		chbComponentMsgLogService.insertSubmitStatusInfo(statusDtos);
//		if (ChbConstant.XCPTNRTRCD_0000.equals(rcntCode)) {
//			// 更新数据库状态
//			chbComponentStatusUpdateService.updateFeedbackStatus(para.getRegistSeq()
//					, para.getDealType(), DictCodeList.报送反馈状态_发送成功.getValue());
//		} else {
//			chbComponentStatusUpdateService.updateFeedbackStatus(para.getRegistSeq()
//					, para.getDealType(), DictCodeList.报送反馈状态_发送失败.getValue());
//		}
	}
	


	/**
	 * @param entity entity
	 * @return 值
	 */
	private String getVaule(CommonNodeEntity entity) {
		if (entity == null) {
			return null;
		}
		return entity.getValue();
	}

	private AsynReportEntity recurrence(String msgType, String filePath) throws Exception {
		AsynReportEntity asynReportEntity = null;
		String request = null;

		// 报送中债出现超时时是否抛异常回滚（01:是,02:否）
		boolean isExceptionWhenSendcbTimeout = StrUtil.equals(isExceptionWhenSendTimeout, DictCodeList.是否_中债_是.getValue());

		try {

			request = dclientComponent.sendFileMsg(msgType, filePath);
			asynReportEntity = ChbXmlUtil.convertToJavaBean(request, AsynReportEntity.class);
		}
		catch (Exception e) {
			logger.error("报文：" + request);
			logger.error("异常：", e);

			if (isExceptionWhenSendcbTimeout) {
				throw new Exception(e);
			}
			return null;
		}
		catch (Throwable e) {
			logger.error("报文：" + request);
			logger.error("异常：", e);

			if (isExceptionWhenSendcbTimeout) {
				throw e;
			}
			return null;
		}
		return asynReportEntity;
	}



}
