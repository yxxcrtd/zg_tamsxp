package com.joyintech.component.svrreport.chb.msgassemble.service;

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.*;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.*;

/**
 * 从业人员登记报文生成
 * @description 
 * @author 赵永生
 * @version 1.0 2020年10月17日
 * @see ChbAstPosSubSetService
 * @since 1.0
 */
@Service
public class ChbAstPosSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbAstPosSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbAstPosSub> datas)  throws Exception {
		logger.debug("开始组装资产持仓登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装资产持仓登记报送报文");

		return chbMsgBuildInfoDto;
	}
	
	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbAstPosSub> datas) {
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
			//利率统一除以100
			ChbXmlUtil.rateFormat(item);
			String currRegistSeq = "";
			// modify by zhangliang 20210130 重新发送不需要重生生成流水号 start
			if (StringUtil.isNotEmpty(item.getSubmitSeq())){
				registSeq.add(item.getSubmitSeq());
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(item.getSubmitSeq(), item.getPrdRegistCode() + ',' + item.getAssetId()+ ','+ item.getInterAssetCode() + ',' +item.getSubjectName()+","
						+ DateUtil.format(item.getValueDate()) + ',' + item.getCcy());
				//add by wudebao 20210424 在para里面保存唯一标识符 end	
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSubmitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getPrdRegistCode() + ',' + item.getAssetId()+ ','+ item.getInterAssetCode() + ',' +item.getSubjectName()+","
						+ DateUtil.format(item.getValueDate()) + ',' + item.getCcy());
				//add by wudebao 20210424 在para里面设置唯一标识符 end	
			}

			generateNodes(cnBondItemMap, item);
			infos.add(cnBondItemMap);
		});
		
		res.setDealType(DictCodeList.报表名称_资产持仓登记.getValue());
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
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbAstPosSub sub) {
	    //发行机构代码
		if(StringUtil.isNotEmpty(sub.getIssuePartyId())){
	    CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
	    cnBondItem1.setValue(sub.getIssuePartyId());
	    cnBondItemMap1.put("IssuerIdentificationCode", cnBondItem1);
		}
		//产品登记编码
		if(StringUtil.isNotEmpty(sub.getPrdRegistCode())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getPrdRegistCode());
			cnBondItemMap1.put("ProductRegistrationCode", cnBondItem1);
		}

		//持仓类别
		if(StringUtil.isNotEmpty(sub.getPosType())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getPosType());
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesType", cnBondItem1);
		}

		//行内资产/负债编码
		if(StringUtil.isNotEmpty(sub.getAssetId())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getAssetId());
			cnBondItemMap1.put("AssetsAndLiabilitiesIdentificationCode", cnBondItem1);
		}

		//资产穿透情况
		if(StringUtil.isNotEmpty(sub.getInterAssetType())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getInterAssetType());
			cnBondItemMap1.put("InvestedAssetsLiabilitiesStructure", cnBondItem1);
		}

		//中间层数
		if(StringUtil.isNotEmpty(sub.getInterLeveNo())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getInterLeveNo());
			cnBondItem1.setDecimals("0");
			cnBondItem1.setUnitRef(ChbConstant.PURE);
			cnBondItemMap1.put("NumberOfMezzanines", cnBondItem1);
		}


		//中间层行内资产/负债编码
		if(StringUtil.isNotEmpty(sub.getInterAssetCode())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getInterAssetCode());
			cnBondItemMap1.put("MezzanineAssetsLiabilitiesIdentificationCode", cnBondItem1);
		}

		//会计科目名称
		if(StringUtil.isNotEmpty(sub.getSubjectName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
			cnBondItem1.setValue(sub.getSubjectName());
			cnBondItemMap1.put("AccountTitle", cnBondItem1);
		}

		//金额
		if(StringUtil.isNotEmpty(sub.getAmt())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(sub.getCcy());
			cnBondItem4.setDecimals("1");
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationInvestedAmount", cnBondItem4);
		}

		//折算人民币金额（元）
		if(StringUtil.isNotEmpty(sub.getCnyAmt())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getCnyAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem4.setDecimals("1");
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationInvestedAmountCny", cnBondItem4);
		}

		//公允价值
		if(StringUtil.isNotEmpty(sub.getFloatIngpl())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getFloatIngpl().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(sub.getCcy());
			cnBondItem4.setDecimals("0");
			cnBondItemMap1.put("FairValue", cnBondItem4);
		}

		//折算人民币公允价值
		if(StringUtil.isNotEmpty(sub.getCnyFloatIngpl())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getCnyFloatIngpl().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem4.setDecimals("0");
			cnBondItemMap1.put("FairValueCNY", cnBondItem4);
		}

		//单位估值（净价）
		if(StringUtil.isNotEmpty(sub.getUnitValueCprice())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getUnitValueCprice().setScale(4, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem4.setDecimals("2");
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationPerUnitValuationNet", cnBondItem4);
		}
		//单位估值（全价）
		if(StringUtil.isNotEmpty(sub.getUnitValueFprice())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(sub.getUnitValueFprice().setScale(4, RoundingMode.HALF_UP).toString());
			cnBondItem5.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem5.setDecimals("0");
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationPerUnitValuationFull", cnBondItem5);
		}

		//数量
		if(StringUtil.isNotEmpty(sub.getUnitValueFprice())){
			CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
			cnBondItem5.setValue(sub.getUnitValueFprice().setScale(5, RoundingMode.HALF_UP).toString());
			cnBondItem5.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem5.setDecimals("4");
			cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationQuantity", cnBondItem5);
		}

		//产品状态统计日
		if(StringUtil.isNotEmpty(sub.getValueDate())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(sub.getValueDate()));
			cnBondItemMap1.put("ReportingDate", cnBondItem4);
		}
	    //备注
		if(StringUtil.isNotEmpty(sub.getRemark())){
	    CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
	    cnBondItem1.setValue(sub.getRemark());
	    cnBondItemMap1.put("ProductInvestedAssetsLiabilitiesRegistrationDetails", cnBondItem1);
		}
		//登记流水号
		if(StringUtil.isNotEmpty(sub.getSubmitSeq())){
	    CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
	    cnBondItem19.setValue(sub.getSubmitSeq());
	    cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem19);
		}
	}
}
