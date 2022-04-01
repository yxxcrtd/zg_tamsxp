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
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbInvestTradeSubDto;
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;

/**
 * 设置 资产交易登记
 * 
 * @description
 * @author lingxq
 * @version 1.0 2020年9月24日
 * @see ChbInvestTradeSubSetService
 * @since 1.0
 */
@Service
public class ChbInvestTradeSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbInvestTradeSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbInvestTradeSubDto> datas) throws Exception {
		logger.debug("开始组装【资产交易信息登记】报送报文");
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装【资产交易信息登记】报送报文");

		return chbMsgBuildInfoDto;
	}

	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbInvestTradeSubDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();
		
		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();
		
		List<String> registSeq = new ArrayList<String>();
		datas.forEach(item -> {
			//利率统一除以100
			ChbXmlUtil.rateFormat(item);
			
			 Map<String, CnBondInterfItemDto> cnBondItemMap = new LinkedHashMap<String, CnBondInterfItemDto>();
			 String currRegistSeq = "";
			// modify by zhangliang 20210130 重新发送不需要重生生成流水号 start
			if (StringUtil.isNotEmpty(item.getSumitSeq())){
				registSeq.add(item.getSumitSeq());
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(item.getSumitSeq(), item.getProdRegistCode() + ',' + item.getProdId()
				 + ',' + item.getTradeCode() + ',' + item.getAssetCode() + ',' + item.getCurAssetType());				
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSumitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getProdRegistCode() + ',' + item.getProdId()
				 + ',' + item.getTradeCode() + ',' + item.getAssetCode() + ',' + item.getCurAssetType());				
				//add by wudebao 20210424 在para里面设置唯一标识符 end	
			}
//             if(DictCodeList.报送反馈状态_发送失败.getValue().equals(item.getSubmitFeedbackStatus())
//                     ||DictCodeList.报送反馈状态_中债退回.getValue().equals(item.getSubmitFeedbackStatus())){
//                 currRegistSeq =  Sid.nextShort(20);
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

		res.setDealType(DictCodeList.报表名称_资产交易信息登记.getValue());
		res.setSysDate(DateUtil.now());
		res.setRegistSeq(registSeq);
		res.setRegistBankCode(datas.get(0).getRegistBankCode());
		return res;
	}

	/**
	 * 资产交易登记xml数据格式封装
	 * @param cnBondItemMap1
	 * @param item
	 * 交易登记 [abstract]
		  交易登记 [tuple]
			产品登记编码
			发行机构代码
			行内资产/负债编码
			行内交易编码
			资金流动类型
			资金流动类型说明
			数量
			资产计量方式
			折算人民币金额
			发生金额
			单位成交价格（净价）
			单位成交价格（全价）
			到期收益率
			交易对手方
			交易对手方类型
			成交编号/合同号
			交易日
			交易登记备注
			登记流水号
	 */
	private void buildXmlData(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbInvestTradeSubDto item) {
		//产品登记编码
		if(StringUtil.isNotEmpty(item.getProdRegistCode())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
		    cnBondItem1.setValue(item.getProdRegistCode());
		    cnBondItemMap1.put("ProductRegistrationCode", cnBondItem1);
		}
		//行内资产/负债编码
		if(StringUtil.isNotEmpty(item.getAssetCode())){
		    CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
		    cnBondItem3.setValue(item.getAssetCode());
		    cnBondItemMap1.put("AssetsAndLiabilitiesIdentificationCode", cnBondItem3);
		}
		//发行机构代码
		if(StringUtil.isNotEmpty(item.getRegistBankCode())){
		    CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
		    cnBondItem2.setValue(item.getRegistBankCode());
		    cnBondItemMap1.put("IssuerIdentificationCode", cnBondItem2);
		}
		//行内交易编码
		if(StringUtil.isNotEmpty(item.getTradeCode())){
		    CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
		    cnBondItem4.setValue(item.getTradeCode());
		    cnBondItemMap1.put("TransactionRegistrationCode", cnBondItem4);
		}
		//资金流动类型
		if(StringUtil.isNotEmpty(item.getCurAssetType())){
		    CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
		    cnBondItem5.setValue(item.getCurAssetType());
		    cnBondItemMap1.put("CashflowType", cnBondItem5);
		}
		//资金流动类型说明
		if(StringUtil.isNotEmpty(item.getCurAssetdetType())){
		    CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
		    cnBondItem6.setValue(item.getCurAssetdetType());
		    cnBondItemMap1.put("DetailsOnCashflowType", cnBondItem6);
		}
		//数量
		if(StringUtil.isNotEmpty(item.getNum())){
		    CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
		    cnBondItem7.setValue(item.getNum().setScale(5, RoundingMode.HALF_UP).toString());
		    cnBondItem7.setUnitRef(ChbConstant.PURE);
		    cnBondItem7.setDecimals("5");
		    cnBondItemMap1.put("Quantity", cnBondItem7);
		}
		//资产计量方式
		if(StringUtil.isNotEmpty(item.getAssetMethod())){
		    CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
		    cnBondItem8.setValue(item.getAssetMethod());
		    cnBondItemMap1.put("MethodOfAssetMeasurement", cnBondItem8);
		}
		//折算人民币金额
		if(StringUtil.isNotEmpty(item.getCnyAmt())){
		    CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
		    cnBondItem9.setValue(item.getCnyAmt().setScale(2, RoundingMode.HALF_UP).toString());
		    cnBondItem9.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem9.setDecimals("2");
		    cnBondItemMap1.put("SettlementAmountCNYForTransaction", cnBondItem9);
		}
		//发生金额
		if(StringUtil.isNotEmpty(item.getAmt())){
		    CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
		    cnBondItem10.setValue(item.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
		    cnBondItem10.setUnitRef(item.getCcy());
		    cnBondItem10.setDecimals("2");
		    cnBondItemMap1.put("SettlementAmount", cnBondItem10);
		}
		//单位成交价格（净价）
		if(StringUtil.isNotEmpty(item.getUnitPriceNet())){
		    CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
		    cnBondItem11.setValue(item.getUnitPriceNet().setScale(4, RoundingMode.HALF_UP).toString());
		    cnBondItem11.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem11.setDecimals("4");
		    cnBondItemMap1.put("PerUnitTransactionPriceNet", cnBondItem11);
		}
		//单位成交价格（全价）
		if(StringUtil.isNotEmpty(item.getUnitPriceFull())){
		    CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
		    cnBondItem12.setValue(item.getUnitPriceFull().setScale(4, RoundingMode.HALF_UP).toString());
		    cnBondItem12.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem12.setDecimals("4");
		    cnBondItemMap1.put("PerUnitTransactionPriceFull", cnBondItem12);
		}
		//到期收益率
		if(StringUtil.isNotEmpty(item.getEndRate())){
		    CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
		    cnBondItem13.setValue(String.format("%.7f",item.getEndRate()));
		    cnBondItem13.setUnitRef(ChbConstant.PURE);
		    cnBondItem13.setDecimals("7");
		    cnBondItemMap1.put("RateOfAnnualReturn", cnBondItem13);
		}


		/***1217升级**/

		//交易审批人身份证号
		if(StringUtil.isNotEmpty(item.getInvestApproverId())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getInvestApproverId());
			cnBondItemMap1.put("TransactionApproverIDNumber", cnBondItem13);
		}

		//交易审批人姓名
		if(StringUtil.isNotEmpty(item.getInvestApproverName())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getInvestApproverName());
			cnBondItemMap1.put("TransactionApproverName", cnBondItem13);
		}


		//交易员身份证号
		if(StringUtil.isNotEmpty(item.getInvestTraderId())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getInvestTraderId());
			cnBondItemMap1.put("TraderIDNumber", cnBondItem13);
		}



		//交易员姓名
		if(StringUtil.isNotEmpty(item.getInvestTraderName())){
			CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
			cnBondItem13.setValue(item.getInvestTraderName());
			cnBondItemMap1.put("TraderName", cnBondItem13);
		}

		/***1217升级**/


		//交易对手方
		if(StringUtil.isNotEmpty(item.getTradeCounter())){
		    CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
		    cnBondItem14.setValue(item.getTradeCounter());
		    cnBondItemMap1.put("TradeCounterparty", cnBondItem14);
		}
		//交易对手方类型
		if(StringUtil.isNotEmpty(item.getRivalPartyType())){
			CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
		    cnBondItem15.setValue(item.getRivalPartyType());
		    cnBondItemMap1.put("CounterpartyType", cnBondItem15);
		}
		//成交编号/合同号
		if(StringUtil.isNotEmpty(item.getContractCode())){
		    CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
		    cnBondItem16.setValue(item.getContractCode());
		    cnBondItemMap1.put("TransactionIdentificationCodeContractNumber", cnBondItem16);
		} 
		//交易日
		if(StringUtil.isNotEmpty(item.getDealdate())){
		    CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
		    cnBondItem17.setValue(DateUtil.format(item.getDealdate()));
		    cnBondItemMap1.put("TradeDate", cnBondItem17);
		}
		//交易登记备注
		if(StringUtil.isNotEmpty(item.getRemark())){
		    CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
		    cnBondItem18.setValue(item.getRemark());
		    cnBondItemMap1.put("TransactionRegistrationDetails", cnBondItem18);
		}
		//登记流水号
		if(StringUtil.isNotEmpty(item.getSumitSeq())){
		    CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
		    cnBondItem19.setValue(item.getSumitSeq());
		    cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem19);
		}
	}
}
