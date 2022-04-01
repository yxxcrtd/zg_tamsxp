/*
 * 文件名：ChbPrdDeclareSubSetService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.msgassemble.service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.ChbAssetInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;

/**
 * 设置 资产要素登记
 * 
 * @description
 * @author lingxq
 * @version 1.0 2020年9月24日
 * @see ChbAssetInfoSubSetService
 * @since 1.0
 */
@Service
public class ChbAssetInfoSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbAssetInfoSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbAssetInfoDto> datas) throws Exception {
		logger.debug("开始组装【资产要素信息登记】报送报文");
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装【资产要素信息登记】报送报文");

		return chbMsgBuildInfoDto;
	}

	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbAssetInfoDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();
		
		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();
		
		List<String> registSeq = new ArrayList<String>();
		datas.forEach(item -> {

			ChbXmlUtil.rateFormat(item);
			
			Map<String, CnBondInterfItemDto> cnBondItemMap = new LinkedHashMap<String, CnBondInterfItemDto>();
			 String currRegistSeq = "";
			 // modify by zhangliang 20210130 重新发送不需要重生生成流水号 start
			 if (StringUtil.isNotEmpty(item.getSumitSeq())){
				 registSeq.add(item.getSumitSeq());
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(item.getSumitSeq(), item.getAssetCode());				
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			 }
			 else{
				 currRegistSeq = Sid.nextShort(20);
				 item.setSumitSeq(currRegistSeq);
				 registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getAssetCode());				
				//add by wudebao 20210424 在para里面设置唯一标识符 end
			 }
			 // modify by zhangliang 20210130 重新发送不需要重生生成流水号 end
//             if(DictCodeList.报送反馈状态_发送失败.getValue().equals(item.getSubmitFeedbackStatus())
//                     ||DictCodeList.报送反馈状态_中债退回.getValue().equals(item.getSubmitFeedbackStatus())){
//                 currRegistSeq = Sid.nextShort(20);
//                 item.setSumitSeq(currRegistSeq);
//                 registSeq.add(currRegistSeq);
//             }
//             else if(StringUtil.isEmpty(item.getSumitSeq())){
//                 currRegistSeq = Sid.nextShort(20);
//                 item.setSumitSeq(currRegistSeq);
//                 registSeq.add(currRegistSeq);
//             }
//             else{
//                 registSeq.add(item.getSumitSeq());
//             }
			buildXmlData(cnBondItemMap, item);
			infos.add(cnBondItemMap);
		});
		
		res.setInfos(infos);
		//add by wudebao 20210424 在para里面设置唯一标识符唯一标识符 start		
		res.setRegistSeqKeyMap(registSeqKeyMap);		
		//add by wudebao 20210424 在para里面保存唯一标识符 end	
		res.setDealType(DictCodeList.报表名称_资产要素信息登记.getValue());
		res.setSysDate(DateUtil.now());
		res.setRegistSeq(registSeq);
		res.setRegistBankCode(datas.get(0).getIssuePartyId());
		return res;
	}

	

	/**
	 * 资产要素登记xml数据格式封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//行内资产/负债编码
		if(StringUtil.isNotEmpty(item.getAssetCode())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
		    cnBondItem1.setValue(item.getAssetCode());
		    cnBondItemMap1.put("AssetsAndLiabilitiesIdentificationCode", cnBondItem1);
		}
		//发行机构代码
		if(StringUtil.isNotEmpty(item.getIssuePartyId())){
		    CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
		    cnBondItem2.setValue(item.getIssuePartyId());
		    cnBondItemMap1.put("IssuerIdentificationCode", cnBondItem2);
		}
		//资产/负债类别
		if(StringUtil.isNotEmpty(item.getAssetType2())){
		    CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
		    cnBondItem3.setValue(item.getAssetType2());
		    cnBondItemMap1.put("TypeOfAssetsAndLiabilities", cnBondItem3);
		}
		//交易流通场所
		if(StringUtil.isNotEmpty(item.getTradeMarket())){
		    CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
		    cnBondItem4.setValue(item.getTradeMarket());
		    cnBondItemMap1.put("TradingVenue", cnBondItem4);
		}
		//币种
		if(StringUtil.isNotEmpty(item.getCcy())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
		    cnBondItem5.setValue(item.getCcy());
		    cnBondItemMap1.put("Currency", cnBondItem5);
		}
		//资产负债登记备注
		if(StringUtil.isNotEmpty(item.getRemark())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getRemark());
		    cnBondItemMap1.put("AssetsAndLiabilitiesRegistrationDetails", cnBondItem6);
		}
		//现金及银行存款类资产
		if(DictCodeList.资产_负债类别_本行_他行存款_大额存单.getValue().equals(item.getAssetType())){
			buildXmlData_THCK(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_债券类资产_理财直接融资工具_同业存单.getValue().equals(item.getAssetType())){
			//债券、理财直融工具、同业存单
			buildXmlData_ZQRZTY(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_拆放同业_买入返售_资产_同业拆入_卖出回购_负债.getValue().equals(item.getAssetType())){
			//拆放同业及买入返售/同业拆入及卖出回购 
			buildXmlData_CFFSHG(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_非标准化债权类资产_信贷资产流转项目.getValue().equals(item.getAssetType())){
			//新增可投资资产以及非标准化债权类资产
			buildXmlData_FBXDLZ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_票据类资产_信用证类资产.getValue().equals(item.getAssetType())){
			//资产_负债类别_票据类资产_信用证类资产
			buildXmlData_PJXYZ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_权益类资产.getValue().equals(item.getAssetType())){
			//资产_负债类别_权益类资产
			buildXmlData_QYLZC(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_金融衍生品.getValue().equals(item.getAssetType())){
			//资产_负债类别_金融衍生品
			buildXmlData_JRYSP(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_QDII债券资产.getValue().equals(item.getAssetType())){
			//代客境外理财投资QDII（债券类资产）
			buildXmlData_QDIIZQZC(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_QDII拆出_逆回购资产.getValue().equals(item.getAssetType())){
			//代客境外理财投资QDII（拆出/逆回购）
			buildXmlData_QDIICCHG(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_QDII股票_基金类资产.getValue().equals(item.getAssetType())){
			//代客境外理财投资QDII（货币型基金、债券型基金、股票型基金、混合型基金、股票）
			buildXmlData_QDIIGPJJ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_QDII结构性票据类资产.getValue().equals(item.getAssetType())){
			//代客境外理财投资QDII(结构性票据) 
			buildXmlData_QDIIJGPJ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_资产管理产品.getValue().equals(item.getAssetType())){
			//资产管理产品 
			buildXmlData_ZGCP(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_商品类资产.getValue().equals(item.getAssetType())){
			//商品类资产
			buildXmlData_SPZC(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_另类资产.getValue().equals(item.getAssetType())){
			//另类资产 
			buildXmlData_NLZC(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_公募基金.getValue().equals(item.getAssetType())
				||DictCodeList.资产_负债类别_私募基金.getValue().equals(item.getAssetType())){
			//公募基金/私募基金 
			buildXmlData_GMSMJJ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_其他_负债_类资产.getValue().equals(item.getAssetType())){
			//负债（其他）
			buildXmlData_QTFZ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_各类资产的其他项.getValue().equals(item.getAssetType())){
			//其他债券
			buildXmlData_QTZQ(cnBondItemMap1,item);
		}else if(DictCodeList.资产_负债类别_委外投资_协议方式.getValue().equals(item.getAssetType())){
			//委外投资—协议方式
			buildXmlData_WWTZ(cnBondItemMap1,item);
		}
		//登记流水号
		if(StringUtil.isNotEmpty(item.getSumitSeq())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getSumitSeq());
			cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem7);
		}
	}

	/**
	 * 委外投资—协议方式报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_WWTZ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//委外投资协议名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetName());
		    cnBondItemMap1.put("OutsourcingInvestmentAgreementName", cnBondItem1);
		}
		//委外投资协议编号
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem2= new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetId());
		    cnBondItemMap1.put("OutsourcingInvestmentAgreementCode", cnBondItem2);
		}
		//受托人
		if(StringUtil.isNotEmpty(item.getTrustee())){
			CnBondInterfItemDto cnBondItem3= new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getTrustee());
		    cnBondItemMap1.put("Trustee", cnBondItem3);
		}
		//实际管理人
		if(StringUtil.isNotEmpty(item.getManagerId())){
			CnBondInterfItemDto cnBondItem19= new CnBondInterfItemDto();
			cnBondItem19.setValue(item.getManagerId());
		    cnBondItemMap1.put("ActualManager", cnBondItem19);
		}
		//托管人
		if(StringUtil.isNotEmpty(item.getTrusteeId())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getTrusteeId());
		    cnBondItemMap1.put("CustodianForOutsourcingInvestmentAgreement", cnBondItem4);
		}
		//委托投资金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
			cnBondItem20.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem20.setDecimals("2");
			cnBondItem20.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("OutsourcingInvestmentAmount", cnBondItem20);
		}
		//资金实际投向
		if(StringUtil.isNotEmpty(item.getTruActType())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getTruActType());
		    cnBondItemMap1.put("ActualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement", cnBondItem5);
		}
		//资金运用方式
		if(StringUtil.isNotEmpty(item.getInvestType())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getInvestType());
		    cnBondItemMap1.put("DetailsOnInvestmentForOutsourcingInvestmentAgreement", cnBondItem6);
		}
		//资金运用行业
		if(StringUtil.isNotEmpty(item.getCapUseInduLevel2())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getCapUseInduLevel2());
		    cnBondItemMap1.put("IndustryOfInvestmentForOutsourcingInvestmentAgreement", cnBondItem7);
		}
		//投资运作起始日期
		if(StringUtil.isNotEmpty(item.getPlanSdate())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(DateUtil.format(item.getPlanSdate()));
		    cnBondItemMap1.put("ValueDateOfOutsourcingInvestment", cnBondItem8);
		}
		//投资运作终止日期
		if(StringUtil.isNotEmpty(item.getPlanEdate())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(DateUtil.format(item.getPlanEdate()));
		    cnBondItemMap1.put("MaturityDateOfOutsourcingInvestment", cnBondItem9);
		}
		//委外投资属性
		if(StringUtil.isNotEmpty(item.getProProperty1())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getProProperty1());
		    cnBondItemMap1.put("TypeOfOutsourcingInvestment", cnBondItem10);
		}
		//是否有预期收益率
		if(StringUtil.isNotEmpty(item.getRateFlag())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getRateFlag());
		    cnBondItemMap1.put("ExpectedReturnPleaseSelectForOutsourcingInvestmentAgreement", cnBondItem11);
		}
		//预期最高收益率
		if(StringUtil.isNotEmpty(item.getMaxRate())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(String.format("%.7f",item.getMaxRate()));
			cnBondItem12.setUnitRef(ChbConstant.PURE);
			cnBondItem12.setDecimals("7");
		    cnBondItemMap1.put("MaximumExpectedAnnualReturnForOutsourcingInvestmentAgreement", cnBondItem12);
		}
		//预期最低收益率
		if(StringUtil.isNotEmpty(item.getMinRate())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(String.format("%.7f",item.getMinRate()));
			cnBondItem13.setUnitRef(ChbConstant.PURE);
			cnBondItem13.setDecimals("7");
		    cnBondItemMap1.put("MinimumExpectedAnnualReturnForOutsourcingInvestmentAgreement", cnBondItem13);
		}
		//管理费率
		if(StringUtil.isNotEmpty(item.getMgrFeeRate())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(String.format("%.7f",item.getMgrFeeRate()));
			cnBondItem14.setUnitRef(ChbConstant.PURE);
			cnBondItem14.setDecimals("7");
		    cnBondItemMap1.put("ManagementFeeRateForOutsourcingInvestmentAgreement", cnBondItem14);
		}
		//托管费率
		if(StringUtil.isNotEmpty(item.getTruFeeRate())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(String.format("%.7f",item.getTruFeeRate()));
			cnBondItem15.setUnitRef(ChbConstant.PURE);
			cnBondItem15.setDecimals("7");
			cnBondItemMap1.put("CustodianFeeRateForOutsourcingInvestmentAgreement", cnBondItem15);
		}
		//交易相关合计费率
		if(StringUtil.isNotEmpty(item.getTradeFeeRate())){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(String.format("%.7f",item.getTradeFeeRate()));
			cnBondItem16.setUnitRef(ChbConstant.PURE);
			cnBondItem16.setDecimals("7");
		    cnBondItemMap1.put("TransactionCostRateForOutsourcingInvestmentAgreement", cnBondItem16);
		}
		//中介服务机构合计费率
		if(StringUtil.isNotEmpty(item.getAgencyFeeRate())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(String.format("%.7f",item.getAgencyFeeRate()));
			cnBondItem17.setUnitRef(ChbConstant.PURE);
			cnBondItem17.setDecimals("7");
		    cnBondItemMap1.put("IntermediaryServiceFeeRateForOutsourcingInvestmentAgreement", cnBondItem17);
		}
		//其他合计费率
		if(StringUtil.isNotEmpty(item.getOtherFeeRate())){
			CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
			cnBondItem18.setValue(String.format("%.7f",item.getOtherFeeRate()));
			cnBondItem18.setUnitRef(ChbConstant.PURE);
			cnBondItem18.setDecimals("7");
		    cnBondItemMap1.put("OtherExpensesRateForOutsourcingInvestmentAgreement", cnBondItem18);
		}
	}

	/**
	 * 其他债券（隶属于债券一级分类）、
	 * 其他非标准化债权类资产（隶属于非标准化债权投资一级分类）、
	 * 其他权益类投资（隶属于权益类一级分类）、
	 * 其他新增可投资资产（隶属于新增可投资资产一级分类）、
	 * 其他代客境外理财投资QDII（隶属于代客境外理财投资QDII一级分类）报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QTZQ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForOther", cnBondItem1);
		}
		//行内资产类别
		if(StringUtil.isNotEmpty(item.getInAssetType())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getInAssetType());
		    cnBondItemMap1.put("AssetType", cnBondItem2);
		}
		//行内资产类别说明
		if(StringUtil.isNotEmpty(item.getInAssetTypeCmm())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getInAssetTypeCmm());
		    cnBondItemMap1.put("DetailsOnAssetType", cnBondItem3);
		}
		//金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setDecimals("2");
			cnBondItem4.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("AmountForOther", cnBondItem4);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForOther", cnBondItem5);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForOther", cnBondItem6);
		}
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForOther", cnBondItem7);
		}
		//是否有预期收益率
		if(StringUtil.isNotEmpty(item.getRateFlag())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getRateFlag());
		    cnBondItemMap1.put("ExpectedReturnPleaseSelectForOther", cnBondItem8);
		}
		//项目收益率（利率）
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem9.setUnitRef(ChbConstant.PURE);
			cnBondItem9.setDecimals("7");
		    cnBondItemMap1.put("ProjectAnnualReturnForOther", cnBondItem9);
		}
		//付息频率（个月/次）
		if(StringUtil.isNotEmpty(item.getIntpayCycle())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getIntpayCycle().toString());
			cnBondItem10.setUnitRef(ChbConstant.PURE);
			cnBondItem10.setDecimals("0");
		    cnBondItemMap1.put("InterestPaymentFrequencyMeasuredByMonthForOther", cnBondItem10);
		}
		//融资人
		if(StringUtil.isNotEmpty(item.getFinancie())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getFinancie());
		    cnBondItemMap1.put("DebtorForOther", cnBondItem11);
		}
		//融资人组织机构（社会信用）代码
		if(StringUtil.isNotEmpty(item.getFinOrgCode())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(item.getFinOrgCode());
		    cnBondItemMap1.put("OrganizationCodeForOther", cnBondItem12);
		}
		//外部评级机构名称及对融资人评级结果
		if(StringUtil.isNotEmpty(item.getFcrGradeOut())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getFcrGradeOut());
		    cnBondItemMap1.put("RatingAgencyAndIssuerRatingForOther", cnBondItem13);
		}
		//融资人类型（按规模划分）
		if(StringUtil.isNotEmpty(item.getFinancierType1())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(item.getFinancierType1());
		    cnBondItemMap1.put("DebtorTypeByScaleForOther", cnBondItem14);
		}
		//融资人类型（按技术领域划分）
		if(StringUtil.isNotEmpty(item.getFinancierType2())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(item.getFinancierType2());
		    cnBondItemMap1.put("DebtorTypeByTechnicalFieldForOther", cnBondItem15);
		}
		//融资人类型（按经济类型划分）
		if(StringUtil.isNotEmpty(item.getFinancierType3())){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(item.getFinancierType3());
		    cnBondItemMap1.put("DebtorTypeByEconomicTypeForOther", cnBondItem16);
		}
		//融资人所属行业
		if(StringUtil.isNotEmpty(item.getIndustryLevel2Type())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(item.getIndustryLevel2Type());
		    cnBondItemMap1.put("IndustryOfDebtorForOther", cnBondItem17);
		}
		//融资项目
		if(StringUtil.isNotEmpty(item.getFinanceProj())){
			CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
			cnBondItem18.setValue(item.getFinanceProj());
		    cnBondItemMap1.put("ProjectForOther", cnBondItem18);
		}
		//融资项目所属行业
		if(StringUtil.isNotEmpty(item.getProIndustryLevel2())){
			CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
			cnBondItem19.setValue(item.getProIndustryLevel2());
		    cnBondItemMap1.put("IndustryOfProjectForOther", cnBondItem19);
		}
		//项目是否属于重点监控行业和领域
		if(StringUtil.isNotEmpty(item.getIndustryFlag())){
			CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
			cnBondItem20.setValue(item.getIndustryFlag());
		    cnBondItemMap1.put("KeyMonitoringIndustryPleaseSelectForOther", cnBondItem20);
		}
		//重点监控行业和领域类别
		if(StringUtil.isNotEmpty(item.getMonitType())){
			CnBondInterfItemDto cnBondItem21 = new CnBondInterfItemDto();
			cnBondItem21.setValue(item.getMonitType());
		    cnBondItemMap1.put("KeyMonitoringIndustryTypeForOther", cnBondItem21);
		}
		//重点监控行业和领域类别说明
		if(StringUtil.isNotEmpty(item.getMonTypeComm())){
			CnBondInterfItemDto cnBondItem22 = new CnBondInterfItemDto();
			cnBondItem22.setValue(item.getMonTypeComm());
		    cnBondItemMap1.put("DetailsOnKeyMonitoringIndustryTypeForOther", cnBondItem22);
		}
		//对应资产外部评级
		if(StringUtil.isNotEmpty(item.getOutGrade())){
			CnBondInterfItemDto cnBondItem23 = new CnBondInterfItemDto();
			cnBondItem23.setValue(item.getOutGrade());
		    cnBondItemMap1.put("StandardizedInternalAssetRatingForOther", cnBondItem23);
		}
		//担保方式
		if(StringUtil.isNotEmpty(item.getGuaType())){
			CnBondInterfItemDto cnBondItem24 = new CnBondInterfItemDto();
			cnBondItem24.setValue(item.getGuaType());
		    cnBondItemMap1.put("GuaranteeMethodForOther", cnBondItem24);
		}
		//抵质押物类型
		if(StringUtil.isNotEmpty(item.getPledgeType())){
			CnBondInterfItemDto cnBondItem25 = new CnBondInterfItemDto();
			cnBondItem25.setValue(item.getPledgeType());
		    cnBondItemMap1.put("PledgeTypeForOther", cnBondItem25);
		}
		//抵质押物价值
		if(StringUtil.isNotEmpty(item.getPledgeValue())){
			CnBondInterfItemDto cnBondItem26 = new CnBondInterfItemDto();
			cnBondItem26.setValue(item.getPledgeValue().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem26.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem26.setDecimals("2");
		    cnBondItemMap1.put("PledgeValueCNYForOther", cnBondItem26);
		}
		//担保性质
		if(StringUtil.isNotEmpty(item.getNatureGuarantee())){
			CnBondInterfItemDto cnBondItem27 = new CnBondInterfItemDto();
			cnBondItem27.setValue(item.getNatureGuarantee());
		    cnBondItemMap1.put("GuaranteeTypeForOther", cnBondItem27);
		}
		//担保人与融资人关系
		if(StringUtil.isNotEmpty(item.getRelationFinan())){
			CnBondInterfItemDto cnBondItem28 = new CnBondInterfItemDto();
			cnBondItem28.setValue(item.getRelationFinan());
		    cnBondItemMap1.put("GuarantorTypeForOther", cnBondItem28);
		}
		//担保情况说明
		if(StringUtil.isNotEmpty(item.getGuaTypeRemark())){
			CnBondInterfItemDto cnBondItem29 = new CnBondInterfItemDto();
			cnBondItem29.setValue(item.getGuaTypeRemark());
		    cnBondItemMap1.put("DetailsOnGuaranteeStatusForOther", cnBondItem29);
		}
		//是否为债转股
		if(StringUtil.isNotEmpty(item.getDeptEquity())){
			CnBondInterfItemDto cnBondItem30 = new CnBondInterfItemDto();
			cnBondItem30.setValue(item.getDeptEquity());
		    cnBondItemMap1.put("DebtToEquitySwapPleaseSelectForOther", cnBondItem30);
		}
	}

	/**
	 * 负债（其他）报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QTFZ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForOtherLiabilities", cnBondItem1);
		}
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForOtherLiabilities", cnBondItem2);
		}
		//期限（天）
		if(StringUtil.isNotEmpty(item.getTermDate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getTermDate().toString());
			cnBondItem3.setUnitRef(ChbConstant.PURE);
			cnBondItem3.setDecimals("0");
		    cnBondItemMap1.put("TermToMaturityDaysForOtherLiabilities", cnBondItem3);
		}
		//负债规模
		if(StringUtil.isNotEmpty(item.getDebtScale())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getDebtScale().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setDecimals("2");
			cnBondItem4.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("LiabilityAmount", cnBondItem4);
		}

		/***1217升级**/
		//是否为同业借款
		if(StringUtil.isNotEmpty(item.getIsDepositFlag())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getIsDepositFlag());
			cnBondItemMap1.put("InterbankCashBorrowingPleaseSelect", cnBondItem1);
		}
		/***1217升级**/

		//利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("AnnualInterestRate", cnBondItem5);
		}
	}

	/**
	 * 公募基金/私募基金 报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_GMSMJJ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//基金代码
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetId());
		    cnBondItemMap1.put("FundIdentificationCode", cnBondItem1);
		}
		//基金名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("FundName", cnBondItem2);
		}
		//行业
		if(StringUtil.isNotEmpty(item.getIndustryShares2())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getIndustryShares2());
		    cnBondItemMap1.put("IndustryForPublicFundsPrivateFunds", cnBondItem3);
		}
		//登记备案机构
		if(StringUtil.isNotEmpty(item.getRegistOrg1())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getRegistOrg1());
		    cnBondItemMap1.put("RegistrationAgency", cnBondItem4);
		}

		/***1217升级**/

		//是否为固定收益类
		if(StringUtil.isNotEmpty(item.getProfitFlag())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getProfitFlag());
			cnBondItemMap1.put("FixedIncomeFundPleaseSelect", cnBondItem4);
		}
		/***1217升级**/

		//是否属于政府投资基金
		if(StringUtil.isNotEmpty(item.getIsGovernFund())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getIsGovernFund());
		    cnBondItemMap1.put("GovernmentInvestmentFundPleaseSelect", cnBondItem5);
		}
		//政府投资基金投向
		if(StringUtil.isNotEmpty(item.getGovernInvestFund())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getGovernInvestFund());
		    cnBondItemMap1.put("InvestmentDirectionOfGovernmentInvestmentFund", cnBondItem6);
		}
		//是否由金融资产投资公司发行
		if(StringUtil.isNotEmpty(item.getFinancialComissue())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getFinancialComissue());
		    cnBondItemMap1.put("IssuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds", cnBondItem7);
		}
		//基金公司名称
		if(StringUtil.isNotEmpty(item.getFundEnpeName())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getFundEnpeName());
		    cnBondItemMap1.put("NameOfInvestmentFund", cnBondItem8);
		}
		//基金管理机构名称
		if(StringUtil.isNotEmpty(item.getMngOrgName())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getMngOrgName());
		    cnBondItemMap1.put("NameOfFundManagementInstitution", cnBondItem9);
		}
		//基金托管机构名称
		if(StringUtil.isNotEmpty(item.getTruOrgName())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getTruOrgName());
		    cnBondItemMap1.put("NameOfFundCustodianInstitution", cnBondItem10);
		}
		//投资阶段
		if(StringUtil.isNotEmpty(item.getInvestStage())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getInvestStage());
		    cnBondItemMap1.put("InvestmentStageForPublicFundsPrivateFunds", cnBondItem11);
		}
		//投资企业类型（按规模划分）
		if(StringUtil.isNotEmpty(item.getSharesType1())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(item.getSharesType1());
		    cnBondItemMap1.put("InvestmentEnterpriseTypeByScale", cnBondItem12);
		}
		//投资企业类型（按技术领域划分）
		if(StringUtil.isNotEmpty(item.getSharesType3())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getSharesType3());
		    cnBondItemMap1.put("InvestmentEnterpriseTypeByTechnicalField", cnBondItem13);
		}
		//投资企业类型（按经济类型划分）
		if(StringUtil.isNotEmpty(item.getSharesType2())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(item.getSharesType2());
		    cnBondItemMap1.put("InvestmentEnterpriseTypeByEconomicType", cnBondItem14);
		}
		//基金投资资产
		if(StringUtil.isNotEmpty(item.getFundInvestAsset())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(item.getFundInvestAsset());
		    cnBondItemMap1.put("InvestmentAssets", cnBondItem15);
		}
	}

	/**
	 * 另类资产 报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_NLZC(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForAlternativeInvestments", cnBondItem1);
		}
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForAlternativeInvestments", cnBondItem2);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForAlternativeInvestments", cnBondItem3);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForAlternativeInvestments", cnBondItem4);
		}
		//资产收益率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("AssetAnnualReturnForAlternativeInvestments", cnBondItem5);
		}
		//资产价值
		if(StringUtil.isNotEmpty(item.getAssetValue())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getAssetValue().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem6.setDecimals("2");
			cnBondItem6.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("AssetValueForAlternativeInvestments", cnBondItem6);
		}
	}

	/**
	 * 商品类资产报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_SPZC(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForCommodities", cnBondItem1);
		}
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForCommodities", cnBondItem2);
		}
		//期限（天）
		if(StringUtil.isNotEmpty(item.getTermDate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getTermDate().toString());
			cnBondItem3.setUnitRef(ChbConstant.PURE);
			cnBondItem3.setDecimals("0");
		    cnBondItemMap1.put("TermToMaturityDaysForCommodities", cnBondItem3);
		}
		//资产收益率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem4.setUnitRef(ChbConstant.PURE);
			cnBondItem4.setDecimals("7");
		    cnBondItemMap1.put("AssetAnnualReturnForCommodities", cnBondItem4);
		}
		//资产价值
		if(StringUtil.isNotEmpty(item.getAssetValue())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getAssetValue().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem5.setDecimals("2");
			cnBondItem5.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("AssetValueForCommodities", cnBondItem5);
		}
	}

	/**
	 * 资产管理产品 报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_ZGCP(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//资管计划名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetName());
		    cnBondItemMap1.put("AssetManagementPlanName", cnBondItem1);
		}

		/***1217升级**/

		//是否为银行理财产品
		if(StringUtil.isNotEmpty(item.getFinancialProductFlag())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getFinancialProductFlag());
			cnBondItemMap1.put("WealthManagementProductPleaseSelect", cnBondItem7);
		}

		//产品登记编码
		if(StringUtil.isNotEmpty(item.getProdRegisterCode())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getProdRegisterCode());
			cnBondItemMap1.put("ProductRegistrationCode", cnBondItem7);
		}
		/***1217升级**/


		//是否由金融资产投资公司发行
		if(StringUtil.isNotEmpty(item.getFinancialComissue())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getFinancialComissue());
		    cnBondItemMap1.put("IssuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans", cnBondItem2);
		}
		//资管计划发起人机构编码
		if(StringUtil.isNotEmpty(item.getPartyCode())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getPartyCode());
		    cnBondItemMap1.put("AssetManagementPlanIssuerCode", cnBondItem3);
		}
		//资管计划登记编码
		if(StringUtil.isNotEmpty(item.getCheckCode())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getCheckCode());
		    cnBondItemMap1.put("AssetManagementPlanRegistrationCode", cnBondItem4);
		}
		//管理人
		if(StringUtil.isNotEmpty(item.getManagerId())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getManagerId());
		    cnBondItemMap1.put("Manager", cnBondItem5);
		}
		//托管人
		if(StringUtil.isNotEmpty(item.getTrusteeId())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getTrusteeId());
		    cnBondItemMap1.put("Custodian", cnBondItem6);
		}
		//金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem7.setDecimals("2");
			cnBondItem7.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("AmountForAssetManagementPlans", cnBondItem7);
		}
		//资金实际投向
		if(StringUtil.isNotEmpty(item.getTruActType())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getTruActType());
		    cnBondItemMap1.put("ActualInvestmentDirectionOfFund", cnBondItem8);
		}
		//资金运用方式
		if(StringUtil.isNotEmpty(item.getInvestType())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getInvestType());
		    cnBondItemMap1.put("DetailsOnInvestment", cnBondItem9);
		}
		//资金运用行业
		if(StringUtil.isNotEmpty(item.getCapUseInduLevel2())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getCapUseInduLevel2());
		    cnBondItemMap1.put("IndustryOfInvestment", cnBondItem10);
		}
		//资管计划成立日期
		if(StringUtil.isNotEmpty(item.getPlanSdate())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(DateUtil.format(item.getPlanSdate()));
		    cnBondItemMap1.put("ValueDateOfAssetManagementPlan", cnBondItem11);
		}
		//资管计划终止日期
		if(StringUtil.isNotEmpty(item.getPlanEdate())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(DateUtil.format(item.getPlanEdate()));
		    cnBondItemMap1.put("MaturityDateOfAssetManagementPlan", cnBondItem12);
		}
		//资管计划属性
		if(StringUtil.isNotEmpty(item.getProProperty())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getProProperty());
		    cnBondItemMap1.put("TypeOfAssetManagementPlan", cnBondItem13);
		}
		//是否有预期收益率
		if(StringUtil.isNotEmpty(item.getRateFlag())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(item.getRateFlag());
		    cnBondItemMap1.put("ExpectedReturnPleaseSelectForAssetManagementPlans", cnBondItem14);
		}
		//预期最高收益率
		if(StringUtil.isNotEmpty(item.getMaxRate())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(String.format("%.7f",item.getMaxRate()));
			cnBondItem15.setUnitRef(ChbConstant.PURE);
			cnBondItem15.setDecimals("7");
		    cnBondItemMap1.put("MaximumExpectedAnnualReturn", cnBondItem15);
		}
		//预期最低收益率
		if(StringUtil.isNotEmpty(item.getMinRate())){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(String.format("%.7f",item.getMinRate()));
			cnBondItem16.setUnitRef(ChbConstant.PURE);
			cnBondItem16.setDecimals("7");
		    cnBondItemMap1.put("MinimumExpectedAnnualReturn", cnBondItem16);
		}
		//购买结构
		if(StringUtil.isNotEmpty(item.getBuyStruct())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(item.getBuyStruct());
		    cnBondItemMap1.put("InvestmentStructure", cnBondItem17);
		}
		//管理方式
		if(StringUtil.isNotEmpty(item.getManageType())){
			CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
			cnBondItem18.setValue(item.getManageType());
		    cnBondItemMap1.put("ManagementType", cnBondItem18);
		}
		//管理费率
		if(StringUtil.isNotEmpty(item.getMgrFeeRate())){
			CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
			cnBondItem19.setValue(String.format("%.7f",item.getMgrFeeRate()));
			cnBondItem19.setUnitRef(ChbConstant.PURE);
			cnBondItem19.setDecimals("7");
		    cnBondItemMap1.put("ManagementFeeRate", cnBondItem19);
		}
		//托管费率
		if(StringUtil.isNotEmpty(item.getTruFeeRate())){
			CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
			cnBondItem20.setValue(String.format("%.7f",item.getTruFeeRate()));
			cnBondItem20.setUnitRef(ChbConstant.PURE);
			cnBondItem20.setDecimals("7");
		    cnBondItemMap1.put("CustodianFeeRateForAssetManagementPlans", cnBondItem20);
		}
		//交易相关合计费率
		if(StringUtil.isNotEmpty(item.getTradeFeeRate())){
			CnBondInterfItemDto cnBondItem21 = new CnBondInterfItemDto();
			cnBondItem21.setValue(String.format("%.7f",item.getTradeFeeRate()));
			cnBondItem21.setUnitRef(ChbConstant.PURE);
			cnBondItem21.setDecimals("7");
		    cnBondItemMap1.put("TransactionCostRate", cnBondItem21);
		}
		//中介服务机构合计费率
		if(StringUtil.isNotEmpty(item.getAgencyFeeRate())){
			CnBondInterfItemDto cnBondItem22 = new CnBondInterfItemDto();
			cnBondItem22.setValue(String.format("%.7f",item.getAgencyFeeRate()));
			cnBondItem22.setUnitRef(ChbConstant.PURE);
			cnBondItem22.setDecimals("7");
		    cnBondItemMap1.put("IntermediaryServiceFeeRate", cnBondItem22);
		}
		//其他合计费率
		if(StringUtil.isNotEmpty(item.getOtherFeeRate())){
			CnBondInterfItemDto cnBondItem23 = new CnBondInterfItemDto();
			cnBondItem23.setValue(String.format("%.7f",item.getOtherFeeRate()));
			cnBondItem23.setUnitRef(ChbConstant.PURE);
			cnBondItem23.setDecimals("7");
		    cnBondItemMap1.put("OtherExpensesRate", cnBondItem23);
		}
	}

	/**
	 * 代客境外理财投资QDII(结构性票据) 报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QDIIJGPJ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForQdiiStructuredNotes", cnBondItem1);
		}
		//合约名称
		if(StringUtil.isNotEmpty(item.getContractName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getContractName());
		    cnBondItemMap1.put("ContractName", cnBondItem2);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForQdiiStructuredNotes", cnBondItem3);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForQdiiStructuredNotes", cnBondItem4);
		}
		//票面利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("CouponRateForQdiiStructuredNotes", cnBondItem5);
		}
		//付息频率（个月/次）
		if(StringUtil.isNotEmpty(item.getIntpayCycle())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getIntpayCycle().toString());
			cnBondItem6.setUnitRef(ChbConstant.PURE);
			cnBondItem6.setDecimals("0");
		    cnBondItemMap1.put("InterestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes", cnBondItem6);
		}
		//固定收益部分所占比例
		if(StringUtil.isNotEmpty(item.getIncomeRatio())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(String.format("%.7f",item.getIncomeRatio()));
			cnBondItem7.setUnitRef(ChbConstant.PURE);
			cnBondItem7.setDecimals("7");
		    cnBondItemMap1.put("PercentageOfFixedIncomeInvestments", cnBondItem7);
		}
		//衍生金融工具所占比例
		if(StringUtil.isNotEmpty(item.getDerInstrRatio())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(String.format("%.7f",item.getDerInstrRatio()));
			cnBondItem8.setUnitRef(ChbConstant.PURE);
			cnBondItem8.setDecimals("7");
		    cnBondItemMap1.put("PercentageOfDerivativeInvestments", cnBondItem8);
		}
		//衍生金融工具具体投资方式
		if(StringUtil.isNotEmpty(item.getDerInstrInvType())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getDerInstrInvType());
		    cnBondItemMap1.put("TypeOfDerivativeInvestments", cnBondItem9);
		}
		//衍生金融工具挂钩标的资产
		if(StringUtil.isNotEmpty(item.getDerInstrTarget())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getDerInstrTarget());
		    cnBondItemMap1.put("UnderlyingAsset", cnBondItem10);
		}
		//利息结算方式
		if(StringUtil.isNotEmpty(item.getAcctMode())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getAcctMode());
		    cnBondItemMap1.put("DetailsOnProceeds", cnBondItem11);
		}
		//含权情况说明
		if(StringUtil.isNotEmpty(item.getSpecialCmt1())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(item.getSpecialCmt1());
		    cnBondItemMap1.put("DetailsOnOption", cnBondItem12);
		}
		//结构化票据最高收益率
		if(StringUtil.isNotEmpty(item.getMaxYield())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(String.format("%.7f",item.getMaxYield()));
			cnBondItem13.setUnitRef(ChbConstant.PURE);
			cnBondItem13.setDecimals("7");
		    cnBondItemMap1.put("StructuredNoteMaximumAnnualReturn", cnBondItem13);
		}
		//结构化票据最低收益率
		if(StringUtil.isNotEmpty(item.getMinYield())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(String.format("%.7f",item.getMinYield()));
			cnBondItem14.setUnitRef(ChbConstant.PURE);
			cnBondItem14.setDecimals("7");
		    cnBondItemMap1.put("StructuredNoteMinimumAnnualReturn", cnBondItem14);
		}
		//挂钩标的资产基准价格
		if(StringUtil.isNotEmpty(item.getBasicPrice())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(item.getBasicPrice().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem15.setDecimals("2");
			cnBondItem15.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("UnderlyingAssetStrikePrice", cnBondItem15);
		}
		//挂钩标的资产登记日价格
		if(StringUtil.isNotEmpty(item.getRegDatePrice())){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(item.getRegDatePrice().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem16.setDecimals("2");
			cnBondItem16.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("UnderlyingAssetPriceOnRegistrationDate", cnBondItem16);
		}
		//交易费
		if(StringUtil.isNotEmpty(item.getTradeFee())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(item.getTradeFee());
		    cnBondItemMap1.put("TransactionCosts", cnBondItem17);
		}
	}

	/**
	 * 代客境外理财投资QDII（货币型基金、债券型基金、股票型基金、混合型基金、股票）报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QDIIGPJJ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForQdiiFundsAndBonds", cnBondItem1);
		}
		//股票/基金代码
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetId());
		    cnBondItemMap1.put("StockFundIdentificationCode", cnBondItem2);
		}
		//股票/基金名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getAssetName());
		    cnBondItemMap1.put("StockFundName", cnBondItem3);
		}
		//发行机构
		if(StringUtil.isNotEmpty(item.getIssuePartyName())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getIssuePartyName());
		    cnBondItemMap1.put("IssuerForQdiiFundsAndBonds", cnBondItem4);
		}
		//行业
		if(StringUtil.isNotEmpty(item.getIndustryShares2())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getIndustryShares2());
		    cnBondItemMap1.put("IndustryForQdiiFundsAndBonds", cnBondItem5);
		}
	}

	/**
	 * 代客境外理财投资QDII（拆出/逆回购）报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QDIICCHG(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForQdiiInterbankLendingReverseRepo", cnBondItem1);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForQdiiInterbankLendingReverseRepo", cnBondItem2);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForQdiiInterbankLendingReverseRepo", cnBondItem3);
		}
		//对手方
		if(StringUtil.isNotEmpty(item.getRivalParty())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getRivalParty());
		    cnBondItemMap1.put("CounterpartyForQdiiInterbankLendingReverseRepo", cnBondItem4);
		}
		//年利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("AnnualInterestRateForQdiiInterbankLendingReverseRepo", cnBondItem5);
		}
		//计息基础
		if(StringUtil.isNotEmpty(item.getBasis())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getBasis());
		    cnBondItemMap1.put("InterestAccrualBasisForQdiiInterbankLendingReverseRepo", cnBondItem6);
		}

	}

	/**
	 * 代客境外理财投资QDII（债券类资产）报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QDIIZQZC(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getCountryArea());
		    cnBondItemMap1.put("CountryOrRegionForQdiiBonds", cnBondItem1);
		}
		//债券名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("BondName", cnBondItem2);
		}
		//债券代码
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getAssetId());
		    cnBondItemMap1.put("BondIdentificationCode", cnBondItem3);
		}
		//发行机构
		if(StringUtil.isNotEmpty(item.getIssuePartyName())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getIssuePartyName());
		    cnBondItemMap1.put("IssuerForQdiiBonds", cnBondItem4);
		}
		//发行机构所属行业
		if(StringUtil.isNotEmpty(item.getIndustryLevel2())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getIndustryLevel2());
		    cnBondItemMap1.put("IndustryOfIssuerForQdiiBonds", cnBondItem5);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForQdiiBonds", cnBondItem6);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForQdiiBonds", cnBondItem7);
		}
		//期限（月）
		if(StringUtil.isNotEmpty(item.getTermMonth())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getTermMonth().toString());
			cnBondItem8.setUnitRef(ChbConstant.PURE);
			cnBondItem8.setDecimals("0");
		    cnBondItemMap1.put("TermToMaturityMonths", cnBondItem8);
		}
		//发行机构主体信用评级
		if(StringUtil.isNotEmpty(item.getPartyGrade())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getPartyGrade());
		    cnBondItemMap1.put("IssuerRatingForQdiiBonds", cnBondItem9);
		}
		//债券信用评级
		if(StringUtil.isNotEmpty(item.getSecCreditGradeType())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getSecCreditGradeType());
		    cnBondItemMap1.put("BondRating", cnBondItem10);
		}
		//票面利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem11.setUnitRef(ChbConstant.PURE);
			cnBondItem11.setDecimals("7");
		    cnBondItemMap1.put("CouponRateForQdiiBonds", cnBondItem11);
		}
		//付息频率（个月/次）
		if(StringUtil.isNotEmpty(item.getIntpayCycle())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(item.getIntpayCycle().toString());
			cnBondItem12.setUnitRef(ChbConstant.PURE);
			cnBondItem12.setDecimals("0");
		    cnBondItemMap1.put("InterestPaymentFrequencyMeasuredByMonthForQdiiBonds", cnBondItem12);
		}
		//担保情况说明
		if(StringUtil.isNotEmpty(item.getGuaTypeRemark())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getGuaTypeRemark());
		    cnBondItemMap1.put("DetailsOnGuaranteeStatusForQdiiBonds", cnBondItem13);
		}
		//是否含权等特殊条款情况说明
		if(StringUtil.isNotEmpty(item.getSpecialCmt())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(item.getSpecialCmt());
		    cnBondItemMap1.put("DetailsOnSpecialTermsIEEmbeddedOptions", cnBondItem14);
		}
	}

	/**
	 * 资产_负债类别_金融衍生品报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_JRYSP(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {

		/***1217升级**/

		//所属国家或地区
		if(StringUtil.isNotEmpty(item.getCountryArea())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getCountryArea());
			cnBondItemMap1.put("CountryOrRegion", cnBondItem7);
		}
		/***1217升级**/


		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForDerivatives", cnBondItem1);
		}
		//名义本金
		if(StringUtil.isNotEmpty(item.getNomCapi())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getNomCapi().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem2.setDecimals("2");
			cnBondItem2.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("NominalPrincipal", cnBondItem2);
		}
		//标的类别
		if(StringUtil.isNotEmpty(item.getTargetType3())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			List<Map<String, CnBondInterfItemDto>> mapList3 = new ArrayList<Map<String, CnBondInterfItemDto>>();
			Map<String, CnBondInterfItemDto> cnBondItemMap3_1 =  null;//子节点map
			String[] tmp = item.getTargetType3().split(",");
			for(int i=0;i<tmp.length;i++){
				cnBondItemMap3_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();
				CnBondInterfItemDto cnBondItem3_1 = new CnBondInterfItemDto();//第一个子节点
				cnBondItem3_1.setValue(tmp[i]);
				cnBondItemMap3_1.put("UnderlyingAssetType",cnBondItem3_1);
				mapList3.add(cnBondItemMap3_1);
			}
			cnBondItem3.setMaps(mapList3);
			cnBondItemMap1.put("UnderlyingAssetTypeTuple", cnBondItem3);
		}
		//持有目的
		if(StringUtil.isNotEmpty(item.getHoldPurpose())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getHoldPurpose());
		    cnBondItemMap1.put("HoldingObjective", cnBondItem4);
		}
	}

	/**
	 * 资产_负债类别_权益类资产报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_QYLZC(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//股票代码
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetId());
		    cnBondItemMap1.put("StockCode", cnBondItem1);
		}
		//股票/企业名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameOfStockEnterprise", cnBondItem2);
		}
		//股票类型
		if(StringUtil.isNotEmpty(item.getSharesType())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getSharesType());
		    cnBondItemMap1.put("StockType", cnBondItem3);
		}
		//行业
		if(StringUtil.isNotEmpty(item.getIndustryShares2())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getIndustryShares2());
		    cnBondItemMap1.put("IndustryForEquities", cnBondItem4);
		}
		//投资阶段
		if(StringUtil.isNotEmpty(item.getInvestStage())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getInvestStage());
		    cnBondItemMap1.put("InvestmentStageForEquities", cnBondItem5);
		}
		//股权退出安排
		if(StringUtil.isNotEmpty(item.getEquityQuit())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(DateUtil.format(item.getEquityQuit()));
		    cnBondItemMap1.put("EquityWithdrawalDate", cnBondItem6);
		}
		//企业类型（按规模划分）
		if(StringUtil.isNotEmpty(item.getSharesType1())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getSharesType1());
		    cnBondItemMap1.put("EnterpriseTypeByScale", cnBondItem11);
		}
		//企业类型（按技术领域划分）
		if(StringUtil.isNotEmpty(item.getSharesType3())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getSharesType3());
		    cnBondItemMap1.put("EnterpriseTypeByTechnicalField", cnBondItem7);
		}
		//企业类型（按经济类型划分）
		if(StringUtil.isNotEmpty(item.getSharesType2())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getSharesType2());
		    cnBondItemMap1.put("EnterpriseTypeByEconomicType", cnBondItem8);
		}
		//是否为质押融资
		if(StringUtil.isNotEmpty(item.getIsPledge())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getIsPledge());
		    cnBondItemMap1.put("PledgedFinancingPleaseSelect", cnBondItem9);
		}
		//是否为债转股
		if(StringUtil.isNotEmpty(item.getDeptEquity())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getDeptEquity());
		    cnBondItemMap1.put("DebtToEquitySwapPleaseSelectForEquities", cnBondItem10);
		}
	}

	/**
	 * 资产_负债类别_票据类资产_信用证类资产报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_PJXYZ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//是否为收/受益权
		if(StringUtil.isNotEmpty(item.getIsRight())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getIsRight());
		    cnBondItemMap1.put("IncomeBeneficialOwnershipPleaseSelect", cnBondItem1);
		}
		//是否属于买入返售
		if(StringUtil.isNotEmpty(item.getIsResale())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getIsResale());
		    cnBondItemMap1.put("BuybackPleaseSelectForNonStandardDebtObligations", cnBondItem2);
		}
		//类型
		if(StringUtil.isNotEmpty(item.getNotstandSecType())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getNotstandSecType());
		    cnBondItemMap1.put("TypeForNonStandardDebtObligations", cnBondItem3);
		}
		/***1217升级**/

		//是否为标准化票据
		if(StringUtil.isNotEmpty(item.getIsBzhpj())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getIsBzhpj());
			cnBondItemMap1.put("StandardizedBillOrNotePleaseSelect", cnBondItem3);
		}
		//票据代码
		if(StringUtil.isNotEmpty(item.getBillCode())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getBillCode());
			cnBondItemMap1.put("BillOrNoteCode", cnBondItem3);
		}
		/***1217升级**/

		//数量
		if(StringUtil.isNotEmpty(item.getNotstandSecNum())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getNotstandSecNum().toString());
			cnBondItem4.setUnitRef(ChbConstant.PURE);
			cnBondItem4.setDecimals("0");
		    cnBondItemMap1.put("QuantityForNonStandardDebtObligations", cnBondItem4);
		}
		//合计金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem5.setUnitRef(item.getCcy());
			cnBondItem5.setDecimals("2");
		    cnBondItemMap1.put("AggregateAmount", cnBondItem5);
		}
		//加权剩余期限（天）
		if(StringUtil.isNotEmpty(item.getRemainTerm())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getRemainTerm().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem6.setDecimals("2");
			cnBondItem6.setUnitRef(ChbConstant.PURE);
		    cnBondItemMap1.put("WeightedAverageRemainingMaturityDays", cnBondItem6);
		}
		//最长剩余期限（天）
		if(StringUtil.isNotEmpty(item.getMaxRemainTerm())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getMaxRemainTerm().toString());
			cnBondItem7.setUnitRef(ChbConstant.PURE);
			cnBondItem7.setDecimals("0");
		    cnBondItemMap1.put("MaximumRemainingMaturityDays", cnBondItem7);
		}
		//最短剩余期限（天）
		if(StringUtil.isNotEmpty(item.getMinRemainTerm())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getMinRemainTerm().toString());
			cnBondItem8.setUnitRef(ChbConstant.PURE);
			cnBondItem8.setDecimals("0");
		    cnBondItemMap1.put("MinimumRemainingMaturityDays", cnBondItem8);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForNonStandardDebtObligations", cnBondItem9);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForNonStandardDebtObligations", cnBondItem10);
		}
		//行业
		if(StringUtil.isNotEmpty(item.getIndustryShares2())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getIndustryShares2());
		    cnBondItemMap1.put("IndustryForNonStandardDebtObligations", cnBondItem11);
		}
		//贴现利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem12.setUnitRef(ChbConstant.PURE);
			cnBondItem12.setDecimals("7");
		    cnBondItemMap1.put("DiscountRate", cnBondItem12);
		}
	}

	/**
	 * 新增可投资资产以及非标准化债权类资产报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_FBXDLZ(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//收/受益权类型
		if(StringUtil.isNotEmpty(item.getProfitType())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getProfitType());
		    cnBondItemMap1.put("IncomeBeneficialOwnershipType", cnBondItem1);
		}
		//是否属于买入返售
		if(StringUtil.isNotEmpty(item.getIsResale())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getIsResale());
		    cnBondItemMap1.put("BuybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem2);
		}
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem3);
		}
		/***1217升级**/

		//资产代码
		if(StringUtil.isNotEmpty(item.getCreditAssetCode())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getCreditAssetCode());
			cnBondItemMap1.put("CreditAssetTradingCode", cnBondItem3);
		}
		/***1217升级**/

		//金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(item.getCcy());
			cnBondItem4.setDecimals("2");
		    cnBondItemMap1.put("AmountForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem4);
		}
		//份额面值
		if(StringUtil.isNotEmpty(item.getPrinBaseamt())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getPrinBaseamt().setScale(4, RoundingMode.HALF_UP).toString());
			cnBondItem5.setUnitRef(item.getCcy());
			cnBondItem5.setDecimals("4");
		    cnBondItemMap1.put("UnitParValue", cnBondItem5);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem6);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem7);
		}
		//法定到期日
		if(StringUtil.isNotEmpty(item.getFmdate())){
			CnBondInterfItemDto cnBondItem62 = new CnBondInterfItemDto();
			cnBondItem62.setValue(DateUtil.format(item.getFmdate()));
		    cnBondItemMap1.put("StatutoryMaturityDate", cnBondItem62);
		}
		//是否有预期收益率
		if(StringUtil.isNotEmpty(item.getRateFlag())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getRateFlag());
		    cnBondItemMap1.put("ExpectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem8);
		}
		//项目收益率（利率）
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem9.setUnitRef(ChbConstant.PURE);
			cnBondItem9.setDecimals("7");
		    cnBondItemMap1.put("ProjectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem9);
		}
		//计息类型
		if(StringUtil.isNotEmpty(item.getCouponSpecies())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getCouponSpecies());
		    cnBondItemMap1.put("CouponType", cnBondItem10);
		}
		//规则付息标识
		if(StringUtil.isNotEmpty(item.getInterestRuleFlag())){
			CnBondInterfItemDto cnBondItem63 = new CnBondInterfItemDto();
			cnBondItem63.setValue(item.getInterestRuleFlag());
		    cnBondItemMap1.put("RegularInterestPaymentPleaseSelect", cnBondItem63);
		}
		//付息频率（个月/次）
		if(StringUtil.isNotEmpty(item.getIntpayCycle())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getIntpayCycle().toString());
			cnBondItem11.setUnitRef(ChbConstant.PURE);
			cnBondItem11.setDecimals("0");
		    cnBondItemMap1.put("InterestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem11);
		}
		//利息分布方式
		if(StringUtil.isNotEmpty(item.getIntpayRule())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(item.getIntpayRule());
		    cnBondItemMap1.put("CouponAllocationType", cnBondItem12);
		}
		//还本付息情况说明
		if(StringUtil.isNotEmpty(item.getRepayCmt())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getRepayCmt());
		    cnBondItemMap1.put("DetailsOnPrincipalAndInterestPayment", cnBondItem13);
		}
		//计息基础
		if(StringUtil.isNotEmpty(item.getBasis())){
			CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
			cnBondItem14.setValue(item.getBasis());
		    cnBondItemMap1.put("InterestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem14);
		}
		//增信机构代码
		if(StringUtil.isNotEmpty(item.getCreditOrgCode())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
			cnBondItem15.setValue(item.getCreditOrgCode());
		    cnBondItemMap1.put("CreditEnhancementInstitutionCode", cnBondItem15);
		}
		//增信机构名称
		if(StringUtil.isNotEmpty(item.getCreditOrgName())){
			CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
			cnBondItem16.setValue(item.getCreditOrgName());
		    cnBondItemMap1.put("CreditEnhancementInstitutionName", cnBondItem16);
		}
		//融资总费率
		if(StringUtil.isNotEmpty(item.getTotalFeeRate())){
			CnBondInterfItemDto cnBondItem64 = new CnBondInterfItemDto();
			cnBondItem64.setValue(String.format("%.7f",item.getTotalFeeRate()));
			cnBondItem64.setUnitRef(ChbConstant.PURE);
			cnBondItem64.setDecimals("7");
		    cnBondItemMap1.put("TotalFeeRate", cnBondItem64);
		}
		//基准利率种类
		if(StringUtil.isNotEmpty(item.getBasisType())){
			CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
			cnBondItem17.setValue(item.getBasisType());
		    cnBondItemMap1.put("BenchmarkInterestRateType", cnBondItem17);
		}
		//是否有浮动因子
		if(StringUtil.isNotEmpty(item.getFloatFactorFlag())){
			CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
			cnBondItem18.setValue(item.getFloatFactorFlag());
		    cnBondItemMap1.put("FloatingFactorPleaseSelect", cnBondItem18);
		}
		//浮动因子
		if(StringUtil.isNotEmpty(item.getFloatFactor())){
			CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
			cnBondItem19.setValue(String.format("%.7f",item.getFloatFactor()));
			cnBondItem19.setUnitRef(ChbConstant.PURE);
			cnBondItem19.setDecimals("7");
		    cnBondItemMap1.put("FloatingRate", cnBondItem19);
		}
		//利差（BP）
		if(StringUtil.isNotEmpty(item.getSpreadRate())){
			CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
			cnBondItem20.setValue(String.format("%.7f",item.getSpreadRate()));
			cnBondItem20.setUnitRef(ChbConstant.PURE);
			cnBondItem20.setDecimals("7");
		    cnBondItemMap1.put("YieldSpreadBasisPoints", cnBondItem20);
		}
		//结构档次
		if(StringUtil.isNotEmpty(item.getStructGrade())){
			CnBondInterfItemDto cnBondItem65 = new CnBondInterfItemDto();
			cnBondItem65.setValue(item.getStructGrade());
		    cnBondItemMap1.put("StructureGrade", cnBondItem65);
		}
		//还本方式
		if(StringUtil.isNotEmpty(item.getRepaymentType())){
			CnBondInterfItemDto cnBondItem21 = new CnBondInterfItemDto();
			cnBondItem21.setValue(item.getRepaymentType());
		    cnBondItemMap1.put("PrincipalPaymentType", cnBondItem21);
		}
		//分期还本条款标识
		if(StringUtil.isNotEmpty(item.getPeriodRepaymentFlag())){
			CnBondInterfItemDto cnBondItem22 = new CnBondInterfItemDto();
			cnBondItem22.setValue(item.getPeriodRepaymentFlag());
		    cnBondItemMap1.put("InstallmentRepayType", cnBondItem22);
		}
		//基础资产类型
		if(StringUtil.isNotEmpty(item.getBasicAssetType())){
			CnBondInterfItemDto cnBondItem23 = new CnBondInterfItemDto();
			cnBondItem23.setValue(item.getBasicAssetType());
		    cnBondItemMap1.put("BaseAssetType", cnBondItem23);
		}
		//超额收益分配比例
		if(StringUtil.isNotEmpty(item.getExcessProfitRate())){
			CnBondInterfItemDto cnBondItem24 = new CnBondInterfItemDto();
			cnBondItem24.setValue(String.format("%.7f",item.getExcessProfitRate()));
			cnBondItem24.setUnitRef(ChbConstant.PURE);
			cnBondItem24.setDecimals("7");
		    cnBondItemMap1.put("PercentageOfExcessEarningsDistribution", cnBondItem24);
		}
		//融资人
		if(StringUtil.isNotEmpty(item.getFinancie())){
			CnBondInterfItemDto cnBondItem25 = new CnBondInterfItemDto();
			cnBondItem25.setValue(item.getFinancie());
		    cnBondItemMap1.put("DebtorForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem25);
		}
		//融资人内部信用评级
		if(StringUtil.isNotEmpty(item.getFcrGradeIn())){
			CnBondInterfItemDto cnBondItem26 = new CnBondInterfItemDto();
			cnBondItem26.setValue(item.getFcrGradeIn());
		    cnBondItemMap1.put("InternalDebtorRating", cnBondItem26);
		}
		//外部评级机构名称及对融资人评级结果
		if(StringUtil.isNotEmpty(item.getFcrGradeOut())){
			CnBondInterfItemDto cnBondItem27 = new CnBondInterfItemDto();
			cnBondItem27.setValue(item.getFcrGradeOut());
		    cnBondItemMap1.put("RatingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem27);
		}
		//融资人类型（按规模划分）
		if(StringUtil.isNotEmpty(item.getFinancierType1())){
			CnBondInterfItemDto cnBondItem28 = new CnBondInterfItemDto();
			cnBondItem28.setValue(item.getFinancierType1());
		    cnBondItemMap1.put("DebtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem28);
		}
		//融资人类型（按技术领域划分）
		if(StringUtil.isNotEmpty(item.getFinancierType2())){
			CnBondInterfItemDto cnBondItem29 = new CnBondInterfItemDto();
			cnBondItem29.setValue(item.getFinancierType2());
		    cnBondItemMap1.put("DebtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem29);
		}
		//融资人类型（按经济类型划分）
		if(StringUtil.isNotEmpty(item.getFinancierType3())){
			CnBondInterfItemDto cnBondItem30 = new CnBondInterfItemDto();
			cnBondItem30.setValue(item.getFinancierType3());
		    cnBondItemMap1.put("DebtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem30);
		}
		//融资人所属行业
		if(StringUtil.isNotEmpty(item.getIndustryLevel2Type())){
			CnBondInterfItemDto cnBondItem31 = new CnBondInterfItemDto();
			cnBondItem31.setValue(item.getIndustryLevel2Type());
		    cnBondItemMap1.put("IndustryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem31);
		}
		//融资人所属地区
		if(StringUtil.isNotEmpty(item.getFinArea())){
			CnBondInterfItemDto cnBondItem32 = new CnBondInterfItemDto();
			//融资人所属地区  地区字典如果为四个直辖市,且是市辖区或者县.则直接替换为  地区代码+"市辖区" 或者 地区代码+"县"
			String area = item.getFinArea();
			if(item.getFinArea().startsWith("110")||
					item.getFinArea().contains("120")||
					item.getFinArea().contains("310")||
					item.getFinArea().contains("500")){
				if(item.getFinArea().split(" ")[0].endsWith("100")){
					area = item.getFinArea().split(" ")[0]+" "+"市辖区";
				}else if(item.getFinArea().split(" ")[0].endsWith("200")){
					area = item.getFinArea().split(" ")[0]+" "+"县";
				}
			}
			cnBondItem32.setValue(area);
			cnBondItemMap1.put("RegionOfDebtor", cnBondItem32);
		}
		//融资人组织机构（社会信用）代码
		if(StringUtil.isNotEmpty(item.getFinOrgCode())){
			CnBondInterfItemDto cnBondItem33 = new CnBondInterfItemDto();
			cnBondItem33.setValue(item.getFinOrgCode());
		    cnBondItemMap1.put("OrganizationCodeForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem33);
		}
		//项目是否属于重点监控行业和领域
		if(StringUtil.isNotEmpty(item.getIndustryFlag())){
			CnBondInterfItemDto cnBondItem34 = new CnBondInterfItemDto();
			cnBondItem34.setValue(item.getIndustryFlag());
		    cnBondItemMap1.put("KeyMonitoringIndustryPleaseSelect", cnBondItem34);
		}
		//重点监控行业和领域类别
		if(StringUtil.isNotEmpty(item.getMonitType())){
			CnBondInterfItemDto cnBondItem35 = new CnBondInterfItemDto();
			cnBondItem35.setValue(item.getMonitType());
		    cnBondItemMap1.put("KeyMonitoringIndustryType", cnBondItem35);
		}
		//重点监控行业和领域类别说明
		if(StringUtil.isNotEmpty(item.getMonTypeComm())){
			CnBondInterfItemDto cnBondItem36 = new CnBondInterfItemDto();
			cnBondItem36.setValue(item.getMonTypeComm());
		    cnBondItemMap1.put("DetailsOnKeyMonitoringIndustryType", cnBondItem36);
		}
		//融资项目所属行业
		if(StringUtil.isNotEmpty(item.getProIndustryLevel2())){
			CnBondInterfItemDto cnBondItem37 = new CnBondInterfItemDto();
			cnBondItem37.setValue(item.getProIndustryLevel2());
		    cnBondItemMap1.put("IndustryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem37);
		}
		//融资项目
		if(StringUtil.isNotEmpty(item.getFinanceProj())){
			CnBondInterfItemDto cnBondItem38 = new CnBondInterfItemDto();
			cnBondItem38.setValue(item.getFinanceProj());
		    cnBondItemMap1.put("ProjectForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem38);
		}
		//担保方式
		if(StringUtil.isNotEmpty(item.getGuaType())){
			CnBondInterfItemDto cnBondItem39 = new CnBondInterfItemDto();
			cnBondItem39.setValue(item.getGuaType());
		    cnBondItemMap1.put("GuaranteeMethod", cnBondItem39);
		}
		//抵质押物类型
		if(StringUtil.isNotEmpty(item.getPledgeType())){
			CnBondInterfItemDto cnBondItem40 = new CnBondInterfItemDto();
			cnBondItem40.setValue(item.getPledgeType());
		    cnBondItemMap1.put("PledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem40);
		}
		//抵质押物价值
		if(StringUtil.isNotEmpty(item.getPledgeValue())){
			CnBondInterfItemDto cnBondItem41 = new CnBondInterfItemDto();
			cnBondItem41.setValue(item.getPledgeValue().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem41.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem41.setDecimals("2");
		    cnBondItemMap1.put("PledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem41);
		}
		//担保性质
		if(StringUtil.isNotEmpty(item.getNatureGuarantee())){
			CnBondInterfItemDto cnBondItem42 = new CnBondInterfItemDto();
			cnBondItem42.setValue(item.getNatureGuarantee());
		    cnBondItemMap1.put("GuaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem42);
		}
		//担保人与融资人关系
		if(StringUtil.isNotEmpty(item.getRelationFinan())){
			CnBondInterfItemDto cnBondItem43 = new CnBondInterfItemDto();
			cnBondItem43.setValue(item.getRelationFinan());
		    cnBondItemMap1.put("GuarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem43);
		}
		//担保情况说明
		if(StringUtil.isNotEmpty(item.getGuaTypeRemark())){
			CnBondInterfItemDto cnBondItem44 = new CnBondInterfItemDto();
			cnBondItem44.setValue(item.getGuaTypeRemark());
		    cnBondItemMap1.put("DetailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem44);
		}
		//行权方式
		if(StringUtil.isNotEmpty(item.getExerciseType())){
			CnBondInterfItemDto cnBondItem45 = new CnBondInterfItemDto();
			cnBondItem45.setValue(item.getExerciseType());
		    cnBondItemMap1.put("ExerciseDateType", cnBondItem45);
		}
		//固定行权日
		if(StringUtil.isNotEmpty(item.getExerciseDate())){
			CnBondInterfItemDto cnBondItem46 = new CnBondInterfItemDto();
			cnBondItem46.setValue(DateUtil.format(item.getExerciseDate()));
		    cnBondItemMap1.put("FixedExerciseDate", cnBondItem46);
		}
		//首次行权日期
		if(StringUtil.isNotEmpty(item.getFirstExerciseDate())){
			CnBondInterfItemDto cnBondItem47 = new CnBondInterfItemDto();
			cnBondItem47.setValue(DateUtil.format(item.getFirstExerciseDate()));
		    cnBondItemMap1.put("FirstExerciseDate", cnBondItem47);
		}
		//行权周期
		if(StringUtil.isNotEmpty(item.getExercisePeriod())){
			CnBondInterfItemDto cnBondItem66 = new CnBondInterfItemDto();
			cnBondItem66.setValue(item.getExercisePeriod().toString());
			cnBondItem66.setUnitRef(ChbConstant.PURE);
			cnBondItem66.setDecimals("0");
		    cnBondItemMap1.put("ExercisePeriod", cnBondItem66);
		}
		//行权价格
		if(StringUtil.isNotEmpty(item.getExercisePrice())){
			CnBondInterfItemDto cnBondItem48 = new CnBondInterfItemDto();
			cnBondItem48.setValue(item.getExercisePrice().setScale(4, RoundingMode.HALF_UP).toString());
			cnBondItem48.setDecimals("4");
			cnBondItem48.setUnitRef(item.getCcy());
		    cnBondItemMap1.put("ExercisePrice", cnBondItem48);
		}
		//永续条款类型
		if(StringUtil.isNotEmpty(item.getPerpetualClauseType())){
			CnBondInterfItemDto cnBondItem49 = new CnBondInterfItemDto();
			cnBondItem49.setValue(item.getPerpetualClauseType());
		    cnBondItemMap1.put("PerpetualType", cnBondItem49);
		}
		//利息递延条款类型
		if(StringUtil.isNotEmpty(item.getDeferredInterestType())){
			CnBondInterfItemDto cnBondItem50 = new CnBondInterfItemDto();
			cnBondItem50.setValue(item.getDeferredInterestType());
		    cnBondItemMap1.put("DeferredInterestType", cnBondItem50);
		}
		//递延利息是否计息
		if(StringUtil.isNotEmpty(item.getDeferredInterestInt())){
			CnBondInterfItemDto cnBondItem51 = new CnBondInterfItemDto();
			cnBondItem51.setValue(item.getDeferredInterestInt());
		    cnBondItemMap1.put("InterestBearingForDeferredInterestPleaseSelect", cnBondItem51);
		}
		//首次重定价日期
		if(StringUtil.isNotEmpty(item.getFirstReprceDate())){
			CnBondInterfItemDto cnBondItem52 = new CnBondInterfItemDto();
			cnBondItem52.setValue(DateUtil.format(item.getFirstReprceDate()));
		    cnBondItemMap1.put("FirstRepriceDate", cnBondItem52);
		}
		//重定价周期
		if(StringUtil.isNotEmpty(item.getReprceCycle())){
			CnBondInterfItemDto cnBondItem53 = new CnBondInterfItemDto();
			cnBondItem53.setValue(item.getReprceCycle().toString());
			cnBondItem53.setUnitRef(ChbConstant.PURE);
			cnBondItem53.setDecimals("0");
		    cnBondItemMap1.put("RepricePeriod", cnBondItem53);
		}
		//部分赎回标识
		if(StringUtil.isNotEmpty(item.getPartRedemFlag())){
			CnBondInterfItemDto cnBondItem54 = new CnBondInterfItemDto();
			cnBondItem54.setValue(item.getPartRedemFlag());
		    cnBondItemMap1.put("PartialRedemptionPleaseSelect", cnBondItem54);
		}
		//部分赎回比例
		if(StringUtil.isNotEmpty(item.getPartRedemRate())){
			CnBondInterfItemDto cnBondItem55 = new CnBondInterfItemDto();
			cnBondItem55.setValue(String.format("%.7f",item.getPartRedemRate()));
			cnBondItem55.setUnitRef(ChbConstant.PURE);
			cnBondItem55.setDecimals("7");
		    cnBondItemMap1.put("PartialRedemptionRates", cnBondItem55);
		}
		//融资人主体评级
		if(StringUtil.isNotEmpty(item.getFinancierGrade())){
			CnBondInterfItemDto cnBondItem56 = new CnBondInterfItemDto();
			cnBondItem56.setValue(item.getFinancierGrade());
		    cnBondItemMap1.put("DebtorRating", cnBondItem56);
		}
		//资产内部评级
		if(StringUtil.isNotEmpty(item.getInGrade())){
			CnBondInterfItemDto cnBondItem57 = new CnBondInterfItemDto();
			cnBondItem57.setValue(item.getInGrade());
		    cnBondItemMap1.put("InternalAssetRating", cnBondItem57);
		}
		//资产外部评级
		if(StringUtil.isNotEmpty(item.getOutGrade())){
			CnBondInterfItemDto cnBondItem58 = new CnBondInterfItemDto();
			cnBondItem58.setValue(item.getOutGrade());
		    cnBondItemMap1.put("StandardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations", cnBondItem58);
		}
		//含权类型
		if(StringUtil.isNotEmpty(item.getContainType())){
			CnBondInterfItemDto cnBondItem59 = new CnBondInterfItemDto();
			cnBondItem59.setValue(item.getContainType());
		    cnBondItemMap1.put("OptionType", cnBondItem59);
		}
		//选择权
		if(StringUtil.isNotEmpty(item.getChose())){
			CnBondInterfItemDto cnBondItem60 = new CnBondInterfItemDto();
			cnBondItem60.setValue(item.getChose());
		    cnBondItemMap1.put("OptionRight", cnBondItem60);
		}
		//行权条件说明
		if(StringUtil.isNotEmpty(item.getConditionCmm())){
			CnBondInterfItemDto cnBondItem61 = new CnBondInterfItemDto();
			cnBondItem61.setValue(item.getConditionCmm());
		    cnBondItemMap1.put("DetailsOnOptionExerciseCondition", cnBondItem61);
		}
	}

	/**
	 * 拆放同业及买入返售/同业拆入及卖出回购报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_CFFSHG(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem1);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem2);
		}
		//对手方
		if(StringUtil.isNotEmpty(item.getRivalParty())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getRivalParty());
		    cnBondItemMap1.put("CounterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem3);
		}
		//对手方类型
		if(StringUtil.isNotEmpty(item.getRivalPartyType())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getRivalPartyType());
		    cnBondItemMap1.put("CounterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem4);
		}
		//年利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("AnnualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem5);
		}
		//计息基础
		if(StringUtil.isNotEmpty(item.getBasis())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getBasis());
		    cnBondItemMap1.put("InterestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo", cnBondItem6);
		}
		//回购标的类别
		if(StringUtil.isNotEmpty(item.getTargetType1())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getTargetType1());
		    cnBondItemMap1.put("CollateralType", cnBondItem7);
		}
		//回购标的金额
		if(StringUtil.isNotEmpty(item.getTargetAmt())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getTargetAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem8.setUnitRef(item.getCcy());
			cnBondItem8.setDecimals("2");
		    cnBondItemMap1.put("CollateralValue", cnBondItem8);
		}

	}

	/**
	 * 债券、理财直融工具、同业存单报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_ZQRZTY(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//代码
		if(StringUtil.isNotEmpty(item.getAssetId())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAssetId());
		    cnBondItemMap1.put("IdentificationCode", cnBondItem1);
		}
		//名称
		if(StringUtil.isNotEmpty(item.getAssetName())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAssetName());
		    cnBondItemMap1.put("NameForBondsDirectFinancingVehiclesAndInterbankCds", cnBondItem2);
		}
		//具体类别
		if(StringUtil.isNotEmpty(item.getChbSectype())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(item.getChbSectype());
		    cnBondItemMap1.put("SpecificBondType", cnBondItem3);
		}
		//发行方式
		if(StringUtil.isNotEmpty(item.getIssueMode())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(item.getIssueMode());
		    cnBondItemMap1.put("IssuingModeOfBond", cnBondItem4);
		}
		//主体评级
		if(StringUtil.isNotEmpty(item.getSecMainGrade())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(item.getSecMainGrade());
		    cnBondItemMap1.put("IssuerRatingForBondsDirectFinancingVehiclesAndInterbankCds", cnBondItem5);
		}
		//发行机构类型（按规模划分）
		if(StringUtil.isNotEmpty(item.getPartyType1())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getPartyType1());
		    cnBondItemMap1.put("IssuingInstitutionTypeByScale", cnBondItem6);
		}
		//发行机构类型（按技术领域划分）
		if(StringUtil.isNotEmpty(item.getPartyType3())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getPartyType3());
		    cnBondItemMap1.put("IssuingInstitutionTypeByTechnicalField", cnBondItem7);
		}
		//发行机构类型（按经济类型划分）
		if(StringUtil.isNotEmpty(item.getPartyType2())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			cnBondItem8.setValue(item.getPartyType2());
		    cnBondItemMap1.put("IssuingInstitutionTypeByEconomicType", cnBondItem8);
		}
		//发行机构所属行业
		if(StringUtil.isNotEmpty(item.getIndustryLevel2())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getIndustryLevel2());
		    cnBondItemMap1.put("IndustryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds", cnBondItem9);
		}
		//登记托管机构
		if(StringUtil.isNotEmpty(item.getRegistOrg())){
			CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
			cnBondItem10.setValue(item.getRegistOrg());
		    cnBondItemMap1.put("RegistryAndDepository", cnBondItem10);
		}
		//登记托管机构说明
		if(StringUtil.isNotEmpty(item.getRegistorgComment())){
			CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
			cnBondItem11.setValue(item.getRegistorgComment());
		    cnBondItemMap1.put("DetailsOnRegistryAndDepository", cnBondItem11);
		}
	}

	/**
	 * 现金及银行存款类资产报文封装
	 * @param cnBondItemMap1
	 * @param item
	 */
	private void buildXmlData_THCK(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAssetInfoDto item) {
		//资金存入银行
		if(StringUtil.isNotEmpty(item.getBankName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getBankName());
		    cnBondItemMap1.put("DepositBank", cnBondItem1);
		}

		/***1217升级**/

		//存款账号
		if(StringUtil.isNotEmpty(item.getAccountNo())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(item.getAccountNo());
			cnBondItemMap1.put("DepositAccountNumber", cnBondItem1);
		}
		/***1217升级**/

		//存款金额
		if(StringUtil.isNotEmpty(item.getAmt())){
			CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
			cnBondItem2.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem2.setUnitRef(item.getCcy());
			cnBondItem2.setDecimals("2");
		    cnBondItemMap1.put("DepositAmount", cnBondItem2);
		}
		//起息日
		if(StringUtil.isNotEmpty(item.getVdate())){
			CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
			cnBondItem3.setValue(DateUtil.format(item.getVdate()));
		    cnBondItemMap1.put("ValueDateForCashAndBankDepositsIncludingCds", cnBondItem3);
		}
		//到期日
		if(StringUtil.isNotEmpty(item.getMdate())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(item.getMdate()));
		    cnBondItemMap1.put("MaturityDateForCashAndBankDepositsIncludingCds", cnBondItem4);
		}
		//年利率
		if(StringUtil.isNotEmpty(item.getAssetYield())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(String.format("%.7f",item.getAssetYield()));
			cnBondItem5.setUnitRef(ChbConstant.PURE);
			cnBondItem5.setDecimals("7");
		    cnBondItemMap1.put("AnnualInterestRateForCashAndBankDepositsIncludingCds", cnBondItem5);
		}
		//计息基础
		if(StringUtil.isNotEmpty(item.getBasis())){
			CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
			cnBondItem6.setValue(item.getBasis());
		    cnBondItemMap1.put("InterestAccrualBasisForCashAndBankDepositsIncludingCds", cnBondItem6);
		}
		//存款类型
		if(StringUtil.isNotEmpty(item.getDepoType())){
			CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
			cnBondItem7.setValue(item.getDepoType());
		    cnBondItemMap1.put("DepositsType", cnBondItem7);
		}
		//结构性存款挂钩标的类别
		if(StringUtil.isNotEmpty(item.getTargetType())){
			CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
			List<Map<String, CnBondInterfItemDto>> mapList8 = new ArrayList<Map<String, CnBondInterfItemDto>>();;
			Map<String, CnBondInterfItemDto> cnBondItemMap8_1 =  null;//子节点map
			String[] tmp = item.getTargetType().split(",");
			for(int i=0;i<tmp.length;i++){
				cnBondItemMap8_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();
				CnBondInterfItemDto cnBondItem3_1 = new CnBondInterfItemDto();//第一个子节点
				cnBondItem3_1.setValue(tmp[i]);
				cnBondItemMap8_1.put("UnderlyingAssetTypeOfStructuredDeposits",cnBondItem3_1);
				mapList8.add(cnBondItemMap8_1);
			}
			cnBondItem8.setMaps(mapList8);
			cnBondItemMap1.put("UnderlyingAssetTypeOfStructuredDepositsTuple", cnBondItem8);
		}
		
		//结构性存款挂钩标的
		if(StringUtil.isNotEmpty(item.getTarget())){
			CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
			cnBondItem9.setValue(item.getTarget());
		    cnBondItemMap1.put("UnderlyingAssetOfStructuredDeposits", cnBondItem9);
		}
	}
}
