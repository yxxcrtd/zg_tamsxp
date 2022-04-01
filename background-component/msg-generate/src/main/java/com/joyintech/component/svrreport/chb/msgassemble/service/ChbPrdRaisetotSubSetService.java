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
import com.joyintech.component.svrreport.chb.dto.ChbPrdRaisetotSubDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;

/**
 * 产品募集总量登记报文生成
 * @description 
 * @author 赵永生
 * @version 1.0 2020年9月24日
 * @see ChbPrdRaisetotSubSetService
 * @since 1.0
 */
@Service
public class ChbPrdRaisetotSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbPrdRaisetotSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbPrdRaisetotSubDto> datas)  throws Exception {
		logger.debug("开始组装产品募集总量登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装产品募集总量登记报送报文");

		return chbMsgBuildInfoDto;
	}
	
	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbPrdRaisetotSubDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();
		
		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();

		
		List<String> registSeq = new ArrayList<String>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();
		datas.forEach(item -> {
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
			if(!cnBondItemMap.isEmpty()){//忽略不符合规则的数据记录
				infos.add(cnBondItemMap);
			}
		});
		
		res.setDealType(DictCodeList.报表名称_募集总量登记.getValue());
		res.setSysDate(DateUtil.now());
	    //res.setEquityConfDate(equityConfDate);
		res.setRegistBankCode(datas.get(0).getIssuePartyId());
	//	List<String> registSeq = new ArrayList<String>();


		
		res.setRegistSeq(registSeq);
		res.setInfos(infos);
		//add by wudebao 20210424 在para里面设置唯一标识符唯一标识符 start		
		res.setRegistSeqKeyMap(registSeqKeyMap);		
		//add by wudebao 20210424 在para里面保存唯一标识符 end	
		System.out.print(infos.toString());
		return res;
	}
	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbPrdRaisetotSubDto sub) {
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

		/***1217升级**/
		//5.3.3 资金托管账号
		if(StringUtil.isNotEmpty(sub.getBusiAcctNo())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getBusiAcctNo());
			cnBondItemMap1.put("FundCustodyAccountNumber", cnBondItem1);
		}

		//5.3.4 资金托管账户
		if(StringUtil.isNotEmpty(sub.getAcctName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getAcctName());
			cnBondItemMap1.put("FundCustodyAccountName", cnBondItem1);
		}
		/***1217升级**/

		//个人投资者总数
		if(sub.getSingleTotal() != null){
	    CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
	    cnBondItem3.setValue(String.valueOf(sub.getSingleTotal()));
	    cnBondItem3.setUnitRef(ChbConstant.PERSON);
	    cnBondItem3.setDecimals("0");
	    cnBondItemMap1.put("NumberOfIndividualInvestors", cnBondItem3);
		}
		//法人投资者总数
		if(sub.getLegalTotal() != null){
	    CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
	    cnBondItem4.setValue(String.valueOf(sub.getLegalTotal()));
	    cnBondItem4.setUnitRef(ChbConstant.PERSON);
	    cnBondItem4.setDecimals("0");
	    cnBondItemMap1.put("NumberOfCorporateInvestors", cnBondItem4);
		}
		//非法人投资者总数
		if(sub.getUnlegalTotal() != null){
	    CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
	    cnBondItem5.setValue(String.valueOf(sub.getUnlegalTotal()));
	    cnBondItem5.setUnitRef(ChbConstant.PERSON);
	    cnBondItem5.setDecimals("0");
	    cnBondItemMap1.put("NumberOfUnincorporatedInvestors", cnBondItem5);
		}
		//实际募集金额
		if(sub.getTotalAmt() != null){
	    CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
	    cnBondItem6.setValue(String.valueOf(sub.getTotalAmt().setScale(2, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem6.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem6.setDecimals("2");
	    cnBondItemMap1.put("ActualSubscribedAmountCNY", cnBondItem6);
		}
		//募集总份额
		if(sub.getRaiseQty() != null){
	    CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
	    cnBondItem7.setValue(String.valueOf(sub.getRaiseQty().setScale(5, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem7.setUnitRef(ChbConstant.PURE);
	    cnBondItem7.setDecimals("5");
	    cnBondItemMap1.put("SubscribedUnits", cnBondItem7);
		}
		//是否代销 参照数据字典
		if(StringUtil.isNotEmpty(sub.getIsConsign())){
	    CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
	    cnBondItem8.setValue(sub.getIsConsign());
	    cnBondItemMap1.put("OtherDistributionAgentsPleaseSelect", cnBondItem8);
		}
		//代销总金额
		if(sub.getConsignAmt() != null){
	    CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
	    cnBondItem9.setValue(String.valueOf(sub.getConsignAmt().setScale(2, BigDecimal.ROUND_HALF_UP)));
	    cnBondItem9.setUnitRef(ChbConstant.币种全称_中债_人民币);
	    cnBondItem9.setDecimals("2");
	    cnBondItemMap1.put("AmountByOtherDistributionAgents", cnBondItem9);
		}
		//产品销售区域及募集金额
		if(StringUtil.isNotEmpty(sub.getSaleAreaAmt())){
		String [] saleAreaAmts = sub.getSaleAreaAmt().split(ChbConstant.SEMICOLON);
	    CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
	    //子节点开始
	    List<Map<String, CnBondInterfItemDto>> mapList10 = new ArrayList<Map<String, CnBondInterfItemDto>>();
	    for(String str : saleAreaAmts){
		    String [] subStr = str.split(ChbConstant.COMMA);
		    if(subStr.length != 2){//忽略不符合规则的数据记录
		    	cnBondItemMap1.clear(); 
		    	return;
		    }
	    	Map<String, CnBondInterfItemDto> cnBondItemMap10_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
		    CnBondInterfItemDto cnBondItem10_1 = new CnBondInterfItemDto();//第一个子节点
		    cnBondItem10_1.setValue(subStr[0]);
		    cnBondItemMap10_1.put("ProductSalesRegion",cnBondItem10_1);
		    CnBondInterfItemDto cnBondItem10_2 = new CnBondInterfItemDto();//第二个子节点
		    cnBondItem10_2.setValue(String.valueOf(new BigDecimal(subStr[1]).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem10_2.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem10_2.setDecimals("2");
		    cnBondItemMap10_1.put("SubscriptionAmountByRegion",cnBondItem10_2);
		    mapList10.add(cnBondItemMap10_1);
	    }
	    cnBondItem10.setMaps(mapList10);//第一个父子节点组合结束
	    cnBondItemMap1.put("ProductAreaAndSubscriptionAmountTuple", cnBondItem10);
		}
		//认购币种及金额
		if(StringUtil.isNotEmpty(sub.getSubscribeCcyAmt())){
		String [] subscribeCcyAmts = sub.getSubscribeCcyAmt().split(ChbConstant.SEMICOLON);
	    CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
	    //子节点开始
	    List<Map<String, CnBondInterfItemDto>> mapList11 = new ArrayList<Map<String, CnBondInterfItemDto>>();
	    for(String str : subscribeCcyAmts){
	    	String [] subStr = str.split(ChbConstant.COMMA);
		    if(subStr.length != 3){//忽略不符合规则的数据记录
		    	cnBondItemMap1.clear(); 
		    	return;
		    }
			Map<String, CnBondInterfItemDto> cnBondItemMap11_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			CnBondInterfItemDto cnBondItem11_1 = new CnBondInterfItemDto();//第一个子节点
			cnBondItem11_1.setValue(String.valueOf(new BigDecimal(subStr[1]).setScale(2, BigDecimal.ROUND_HALF_UP)));
			cnBondItem11_1.setUnitRef(subStr[0]);
			cnBondItem11_1.setDecimals("2");
			cnBondItemMap11_1.put("SubscriptionAmount",cnBondItem11_1);
			CnBondInterfItemDto cnBondItem11_2 = new CnBondInterfItemDto();//第二个子节点
			cnBondItem11_2.setValue(String.valueOf(new BigDecimal(subStr[2]).setScale(2, BigDecimal.ROUND_HALF_UP)));
			cnBondItem11_2.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem11_2.setDecimals("2");
			cnBondItemMap11_1.put("SubscriptionAmountCNY",cnBondItem11_2);
			mapList11.add(cnBondItemMap11_1);
	    }
	    cnBondItem11.setMaps(mapList11);//第一个父子节点组合结束
	    cnBondItemMap1.put("SubscriptionCurrencyAndSubscriptionAmountTuple", cnBondItem11);
		}
		//登记备注
	    if(StringUtil.isNotEmpty(sub.getRemark())){
	    CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
	    cnBondItem12.setValue(sub.getRemark());
	    cnBondItemMap1.put("InitialSubscriptionRegistrationDetails", cnBondItem12);
	    }
		//登记流水号
	    if(StringUtil.isNotEmpty(sub.getSumitSeq())){
	    CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
	    cnBondItem12.setValue(sub.getSumitSeq());
	    cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem12);
	    }




	}
}
