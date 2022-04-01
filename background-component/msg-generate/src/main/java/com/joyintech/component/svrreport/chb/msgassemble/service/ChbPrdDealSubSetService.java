package com.joyintech.component.svrreport.chb.msgassemble.service;

import java.math.BigDecimal;
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
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbPrdDealSubDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;

/**
 * 产品存续期登记报文生成
 * @description 
 * @author 赵永生
 * @version 1.0 2020年9月24日
 * @see ChbPrdDealSubSetService
 * @since 1.0
 */
@Service
public class ChbPrdDealSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbPrdDealSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbPrdDealSubDto> datas)  throws Exception {
		logger.debug("开始组装产品存续期登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装产品存续期登记报送报文");

		return chbMsgBuildInfoDto;
	}
	
	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbPrdDealSubDto> datas) {
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
				registSeqKeyMap.put(item.getSumitSeq(), item.getProdRegistCode() + ',' + item.getProdId() + ',' + item.getMdate());				
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSumitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getProdRegistCode() + ',' + item.getProdId() + ',' + item.getMdate());				
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
			if(!cnBondItemMap.isEmpty()){//忽略不符合规则的数据记录
				infos.add(cnBondItemMap);
			}
			
		});
		
		res.setDealType(DictCodeList.报表名称_产品存续期登记.getValue());
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
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbPrdDealSubDto sub) {
	    //产品登记编码
		if(StringUtil.isNotEmpty(sub.getProdRegistCode())){
		CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
	    cnBondItem1.setValue(sub.getProdRegistCode());
	    cnBondItemMap1.put("ProductRegistrationCode", cnBondItem1);
		}
		//发行机构代码
		if(StringUtil.isNotEmpty(sub.getIssuePartyId())){
	    CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
	    cnBondItem2.setValue(sub.getIssuePartyId());
	    cnBondItemMap1.put("IssuerIdentificationCode", cnBondItem2);
		}
	    //初始净值
	    if(sub.getOriginalValue() != null){
	    CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
	    cnBondItem3.setValue(String.valueOf(sub.getOriginalValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem3.setUnitRef(sub.getValueCcy());
	    cnBondItem3.setDecimals("5");
	    cnBondItemMap1.put("InitialNetValue", cnBondItem3);
	    }
	    //产品净值
	    if(sub.getPrdValue() != null){
	    CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
	    cnBondItem4.setValue(String.valueOf(sub.getPrdValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem4.setUnitRef(sub.getValueCcy());
	    cnBondItem4.setDecimals("5");
	    cnBondItemMap1.put("NetValueOfProduct", cnBondItem4);
	    }
	    //累计净值
	    if(sub.getTotalValue() != null){
	    CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
	    cnBondItem5.setValue(String.valueOf(sub.getTotalValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem5.setUnitRef(sub.getValueCcy());
	    cnBondItem5.setDecimals("5");
	    cnBondItemMap1.put("AggregateNetValue", cnBondItem5);
	    }
	    //人民币初始净值
	    if(sub.getCnyOrlValue() != null){
	    CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
	    cnBondItem6.setValue(String.valueOf(sub.getCnyOrlValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem6.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem6.setDecimals("5");
	    cnBondItemMap1.put("InitialNetValueCNY", cnBondItem6);
	    }
	    //折算人民币净值
	    if(sub.getCnyPrdValue() != null){
	    CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
	    cnBondItem7.setValue(String.valueOf(sub.getCnyPrdValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem7.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem7.setDecimals("5");
	    cnBondItemMap1.put("NetValueOfProductCNY", cnBondItem7);
	    }
	    //人民币累计净值
	    if(sub.getCnyTalValue() != null){
	    CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
	    cnBondItem8.setValue(String.valueOf(sub.getCnyTalValue().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem8.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem8.setDecimals("5");
	    cnBondItemMap1.put("AggregateNetValueCNY", cnBondItem8);
	    }
	    //实现收益率
	    if(sub.getReaYield() != null){
	    CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
	    cnBondItem9.setValue(String.format("%.7f",sub.getReaYield()));
	    cnBondItem9.setUnitRef(ChbConstant.PURE);
	    cnBondItem9.setDecimals("7");
	    cnBondItemMap1.put("RealizedAnnualReturn", cnBondItem9);
	    }
	    //最新预期收益率
	    if(sub.getNewExpYield() != null){
	    CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
	    cnBondItem10.setValue(String.format("%.7f",sub.getNewExpYield()));
	    cnBondItem10.setUnitRef(ChbConstant.PURE);
	    cnBondItem10.setDecimals("7");
	    cnBondItemMap1.put("ExpectedAnnualReturn", cnBondItem10);
	    }
	    //银行实现收益(元)
	    if(sub.getBankImpInt() != null){
	    CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
	    cnBondItem11.setValue(String.valueOf(sub.getBankImpInt().setScale(2, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem11.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem11.setDecimals("2");
	    cnBondItemMap1.put("NetIncomeOfBankCNY", cnBondItem11);
	    }
		//业务起始日
		if(StringUtil.isNotEmpty(sub.getVdate())){
	    CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
	    cnBondItem12.setValue(sub.getVdate());
	    cnBondItemMap1.put("BusinessStartDate", cnBondItem12);
		}

		/***1217升级**/

		//净值日期
		if(StringUtil.isNotEmpty(sub.getYieldDate())){
			CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
			cnBondItem12.setValue(DateUtil.format(sub.getYieldDate(), "yyyy-MM-dd"));
			cnBondItemMap1.put("NetValueDate", cnBondItem12);
		}
		/***1217升级**/


		//业务结束日
		if(StringUtil.isNotEmpty(sub.getMdate())){
	    CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
	    cnBondItem13.setValue(sub.getMdate());
	    cnBondItemMap1.put("BusinessEndDate", cnBondItem13);
		}
	    //累计申购份额
	    if(sub.getPurchaseQty() != null){
	    CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
	    cnBondItem14.setValue(String.valueOf(sub.getPurchaseQty().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem14.setUnitRef(ChbConstant.PURE);
	    cnBondItem14.setDecimals("5");
	    cnBondItemMap1.put("SubscribedUnitsOverLatestSubscriptionPeriod", cnBondItem14);
	    }
	    //累计赎回份额
	    if(sub.getRedeemQty() != null){
	    CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
	    cnBondItem15.setValue(String.valueOf(sub.getRedeemQty().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem15.setUnitRef(ChbConstant.PURE);
	    cnBondItem15.setDecimals("5");
	    cnBondItemMap1.put("RedeemedUnitsOverLatestSubscriptionPeriod", cnBondItem15);
	    }
	    //每万份份额分红
	    if(sub.getShareAmtWan() != null){
	    CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
	    cnBondItem15.setValue(String.valueOf(sub.getShareAmtWan().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem15.setUnitRef(ChbConstant.PURE);
	    cnBondItem15.setDecimals("5");
	    cnBondItemMap1.put("UnitsBonusPerTenThousandUnits", cnBondItem15);
	    }
	    //每万份现金分红
	    if(sub.getCashAmtWan() != null){
	    CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
	    cnBondItem15.setValue(String.valueOf(sub.getCashAmtWan().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem15.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem15.setDecimals("5");
	    cnBondItemMap1.put("CashBonusPerTenThousandUnitsCNY", cnBondItem15);
	    }
	    //产品余额
	    if(sub.getBalanceAmt() != null){
	    CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
	    cnBondItem16.setValue(String.valueOf(sub.getBalanceAmt().setScale(2, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem16.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem16.setDecimals("2");
	    cnBondItemMap1.put("ProductOutstandingAmountCNY", cnBondItem16);	 
	    }
	    //产品份额
	    if(sub.getPrdQty() != null){
	    CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
	    cnBondItem17.setValue(String.valueOf(sub.getPrdQty().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem17.setUnitRef(ChbConstant.PURE);
	    cnBondItem17.setDecimals("5");
	    cnBondItemMap1.put("OutstandingUnits", cnBondItem17);
	    }
		//币种及申购兑付金额
		if(StringUtil.isNotEmpty(sub.getCcyPurchase())){
	    CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
	    //子节点开始
	    List<Map<String, CnBondInterfItemDto>> mapList18 = new ArrayList<Map<String, CnBondInterfItemDto>>();
	    String [] ccyPurchases = sub.getCcyPurchase().split(ChbConstant.SEMICOLON);
	    for(String str : ccyPurchases){
	    	String [] subStr = str.split(ChbConstant.COMMA);
		    if(subStr.length != 4){//忽略不符合规则的数据记录
		    	cnBondItemMap1.clear(); 
		    	return;
		    }
		    Map<String, CnBondInterfItemDto> cnBondItemMap18_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
		    CnBondInterfItemDto cnBondItem18_1 = new CnBondInterfItemDto();//第一个子节点
		    cnBondItem18_1.setValue(subStr[0]);
		    cnBondItemMap18_1.put("Currency",cnBondItem18_1);
		    CnBondInterfItemDto cnBondItem18_2 = new CnBondInterfItemDto();//第二个子节点
		    cnBondItem18_2.setValue(String.valueOf(new BigDecimal(subStr[1]).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem18_2.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem18_2.setDecimals("2");
		    cnBondItemMap18_1.put("SubscribedAmountOverLatestSubscriptionPeriodCNY",cnBondItem18_2);
		    CnBondInterfItemDto cnBondItem18_3 = new CnBondInterfItemDto();//第一个子节点
		    cnBondItem18_3.setValue(String.valueOf(new BigDecimal(subStr[2]).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem18_3.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem18_3.setDecimals("2");
		    cnBondItemMap18_1.put("RedeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY",cnBondItem18_3);
		    CnBondInterfItemDto cnBondItem18_4 = new CnBondInterfItemDto();//第二个子节点
		    cnBondItem18_4.setValue(String.valueOf(new BigDecimal(subStr[3]).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem18_4.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem18_4.setDecimals("2");
		    cnBondItemMap18_1.put("RedeemedInterestPaymentOverLatestSubscriptionPeriodCNY",cnBondItem18_4);
		    mapList18.add(cnBondItemMap18_1);
	    }
	    cnBondItem18.setMaps(mapList18);//第一个父子节点组合结束
	    cnBondItemMap1.put("CurrencySubscriptionAndRedemptionTuple", cnBondItem18);
		}
		//存续期登记备注
		if(StringUtil.isNotEmpty(sub.getRemark())){
	    CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
	    cnBondItem19.setValue(sub.getRemark());
	    cnBondItemMap1.put("SubsequentSubscriptionRegistrationDetails", cnBondItem19);
	    }
		//登记流水号
		if(StringUtil.isNotEmpty(sub.getSumitSeq())){
	    CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
	    cnBondItem20.setValue(sub.getSumitSeq());
	    cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem20);
		}

	}
}
