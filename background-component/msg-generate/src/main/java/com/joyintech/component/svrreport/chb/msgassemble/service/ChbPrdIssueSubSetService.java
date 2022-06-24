/*
 * 文件名：ChbPrdIssueSubSetService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.msgassemble.service;

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbPrdIssueSubDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 产品发行登记报文生成
 * @description
 * @author 赵永生
 * @version 1.0 2020年9月24日
 * @see ChbPrdIssueSubSetService
 * @since 1.0
 */
@Service
public class ChbPrdIssueSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbPrdIssueSubSetService.class);

	@Autowired
	private ChbMsgSetCommonService chbMsgSetCommonService;

	/**
	 * 设置消息
	 *
	 * @param datas
	 * @return
	 * @see
	 * @since 1.0
	 */
	public ChbMsgBuildInfoDto setMsg(List<ChbPrdIssueSubDto> datas)  throws Exception {
		logger.debug("开始组装产品发行登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装产品发行登记报送报文");

		return chbMsgBuildInfoDto;
	}

	/**
	 * 生成标准接口的数据结构
	 *
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbPrdIssueSubDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();

		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();


		List<String> registSeq = new ArrayList<String>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();
		datas.forEach(item -> {
			//利率统一除以100
			ChbXmlUtil.rateFormat(item);

			Map<String, CnBondInterfItemDto> cnBondItemMap = new LinkedHashMap<String, CnBondInterfItemDto>();
			String currRegistSeq = "";
			// modify by zhangliang 20210130 重新发送不需要重生生成流水号 start
			if (StringUtil.isNotEmpty(item.getSumitSeq())){
				registSeq.add(item.getSumitSeq());
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(item.getSumitSeq(), item.getProdRegistCode() + ',' + item.getProdId());
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSumitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getProdRegistCode() + ',' + item.getProdId());
				//add by wudebao 20210424 在para里面设置唯一标识符 end
			}
//            if(DictCodeList.报送反馈状态_发送失败.getValue().equals(item.getSubmitFeedbackStatus())
//                    ||DictCodeList.报送反馈状态_中债退回.getValue().equals(item.getSubmitFeedbackStatus())){
//                currRegistSeq = Sid.nextShort(20);
//                registSeq.add(currRegistSeq);
//            }
//            else if(StringUtil.isEmpty(item.getSumitSeq())){
//                currRegistSeq = Sid.nextShort(20);
//                registSeq.add(currRegistSeq);
//            }
//            else{
//                registSeq.add(item.getSumitSeq());
//            }
			generateNodes(cnBondItemMap, item);
			infos.add(cnBondItemMap);
		});

		res.setDealType(DictCodeList.报表名称_产品发行登记.getValue());
		res.setSysDate(DateUtil.now());
		//res.setEquityConfDate(equityConfDate);
		res.setRegistBankCode(datas.get(0).getIssuePartyId());
		//	List<String> registSeq = new ArrayList<String>();



		res.setRegistSeq(registSeq);
		res.setInfos(infos);
		//add by wudebao 20210424 在para里面设置唯一标识符唯一标识符 start		
		res.setRegistSeqKeyMap(registSeqKeyMap);
		//add by wudebao 20210424 在para里面保存唯一标识符 end	

		return res;
	}
	/**
	 * 生成标准接口的数据结构
	 *
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbPrdIssueSubDto sub) {

		//产品登记编码
		if(StringUtil.isNotEmpty(sub.getProdRegistCode())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getProdRegistCode());
			cnBondItemMap1.put("ProductRegistrationCode", cnBondItem1);
		}
		//理财产品代码
		if(StringUtil.isNotEmpty(sub.getProdId())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(sub.getProdId());
			cnBondItemMap1.put("ProductCode", cnBondItem2);
		}
		//发行机构代码
		if(StringUtil.isNotEmpty(sub.getIssuePartyId())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(sub.getIssuePartyId());
			cnBondItemMap1.put("IssuerIdentificationCode", cnBondItem3);
		}
		//募集起始日期
		if(sub.getRaiseVdate() != null){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(sub.getRaiseVdate(), "yyyy-MM-dd"));
			cnBondItemMap1.put("SubscriptionStartDate", cnBondItem4);
		}
		//募集结束日期
		if(sub.getRaiseEnddate() != null){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(DateUtil.format(sub.getRaiseEnddate(), "yyyy-MM-dd"));
			cnBondItemMap1.put("SubscriptionEndDate", cnBondItem5);
		}
		//产品起始日期
		if(sub.getPrdVdate() != null){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(DateUtil.format(sub.getPrdVdate(), "yyyy-MM-dd"));
			cnBondItemMap1.put("ProductValueDate", cnBondItem6);
		}
		//产品结束日期
		if(sub.getPrdMdate() != null){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(DateUtil.format(sub.getPrdMdate(), "yyyy-MM-dd"));
			cnBondItemMap1.put("ProductMaturityDate", cnBondItem7);
		}
		//管理方式
		if(StringUtil.isNotEmpty(sub.getManageType())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(sub.getManageType());
			cnBondItemMap1.put("ManagementMethod", cnBondItem8);
		}
		//是否为结构化（分级）产品
		if(StringUtil.isNotEmpty(sub.getIsConstracte())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(sub.getIsConstracte());
			cnBondItemMap1.put("StructuredProductPleaseSelect", cnBondItem9);
		}

		/***1217升级**/

		//分级比例
		if(StringUtil.isNotEmpty(sub.getGradProportion())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(String.format("%.7f",sub.getGradProportion()));
			cnBondItem3.setUnitRef(ChbConstant.PURE);
			cnBondItem3.setDecimals("7");
			cnBondItemMap1.put("RatioOfPriority", cnBondItem3);
		}

		/***1217升级**/

		//业绩比较基准上限
		if(sub.getYieldHigh() != null){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(String.format("%.7f",sub.getYieldHigh()));
			cnBondItem10.setUnitRef(ChbConstant.PURE);
			cnBondItem10.setDecimals("7");
			cnBondItemMap1.put("UpperLimitOfPerformanceBenchmarkRate", cnBondItem10);
		}


		//业绩比较基准下限
		if(sub.getYieldLow() != null){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(String.format("%.7f",sub.getYieldLow()));
			cnBondItem11.setUnitRef(ChbConstant.PURE);
			cnBondItem11.setDecimals("7");
			cnBondItemMap1.put("LowerLimitOfPerformanceBenchmarkRate", cnBondItem11);
		}
		//业绩比较基准说明
		if(StringUtil.isNotEmpty(sub.getYieldRemark())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(sub.getYieldRemark());
			cnBondItemMap1.put("DetailsOnPerformanceBenchmarkRate", cnBondItem11);
		}
		//开放模式 01 有规律开放/02 无规律开放
		if(StringUtil.isNotEmpty(sub.getOpenMode())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(sub.getOpenMode());
			cnBondItemMap1.put("OpeningMode", cnBondItem12);
		}
		//规律开放周期
		if(StringUtil.isNotEmpty(sub.getOpenCircle())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(sub.getOpenCircle());
			cnBondItemMap1.put("RegularOpeningPeriod", cnBondItem13);
		}
		//其他规律开放周期 （天）
		if(sub.getOpenCircleDays() != null){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(String.valueOf(sub.getOpenCircleDays().setScale(0, BigDecimal.ROUND_UP)));
			cnBondItem13.setUnitRef(ChbConstant.PURE);
			cnBondItem13.setDecimals("0");
			cnBondItemMap1.put("OtherRegularOpeningPeriodDays", cnBondItem13);
		}
		//无规律开放说明
		if(StringUtil.isNotEmpty(sub.getRuleComment())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(sub.getRuleComment());
			cnBondItemMap1.put("DetailsOnIrregularOpeningPeriod", cnBondItem13);
		}
		//首次开放周期起始日
		if(sub.getFirstOpenDay() != null){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(DateUtil.format(sub.getFirstOpenDay(), "yyyy-MM-dd"));
			cnBondItemMap1.put("StartDateOfFirstOpeningPeriod", cnBondItem14);
		}
		//节假日是否开放
		if(StringUtil.isNotEmpty(sub.getHolidayOnFlag())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(sub.getHolidayOnFlag());
			cnBondItemMap1.put("HolidayOpeningPleaseSelect", cnBondItem15);
		}
		//平均开放次数（年化）
		if(sub.getAvgOpenCount() != null){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(String.valueOf(sub.getAvgOpenCount().setScale(2, BigDecimal.ROUND_HALF_UP)));
			cnBondItem16.setUnitRef(ChbConstant.PURE);
			cnBondItem16.setDecimals("2");
			cnBondItemMap1.put("AverageOpeningFrequencyAnnualized", cnBondItem16);
		}
		//开放期业务
		if(StringUtil.isNotEmpty(sub.getOpenBuss())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(sub.getOpenBuss());
			cnBondItemMap1.put("BusinessTypeDuringOpeningPeriod", cnBondItem17);
		}
		//开放期业务说明
		if(StringUtil.isNotEmpty(sub.getOpenBussRemark())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(sub.getOpenBussRemark());
			cnBondItemMap1.put("DetailsOnBusinessTypeDuringOpeningPeriod", cnBondItem17);
		}
		/***1217升级**/
		//资金托管账号
		/*if(StringUtil.isNotEmpty(sub.getBusiAcctNo())){
	    CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
	    cnBondItem18.setValue(sub.getBusiAcctNo());
	    cnBondItemMap1.put("FundCustodyAccountNumber", cnBondItem18);
		}
		//资金托管账户
		if(StringUtil.isNotEmpty(sub.getAcctName())){
	    CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
	    cnBondItem19.setValue(sub.getAcctName());
	    cnBondItemMap1.put("FundCustodyAccountName", cnBondItem19);
		}*/
		/***1217升级**/
		//登记流水号
		if(StringUtil.isNotEmpty(sub.getSumitSeq())){
			CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
			cnBondItem20.setValue(sub.getSumitSeq());
			cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem20);
		}
	}
}
