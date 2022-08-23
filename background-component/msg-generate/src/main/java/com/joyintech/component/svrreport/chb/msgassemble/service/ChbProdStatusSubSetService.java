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
 * @see ChbProdStatusSubSetService
 * @since 1.0
 */
@Service
public class ChbProdStatusSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbProdStatusSubSetService.class);

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
	public ChbMsgBuildInfoDto setMsg(List<ChbPrdStatusSub> datas)  throws Exception {
		logger.debug("开始组装产品状态登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装产品状态登记报送报文");

		return chbMsgBuildInfoDto;
	}
	
	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbPrdStatusSub> datas) {
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
				registSeqKeyMap.put(item.getSubmitSeq(), item.getPrdRegistCode() + ',' + item.getPrdStatusDate());
				//add by wudebao 20210424 在para里面保存唯一标识符 end	
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSubmitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面设置唯一标识符 start
				registSeqKeyMap.put(currRegistSeq, item.getPrdRegistCode() + ',' + item.getPrdStatusDate());
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
		
		res.setDealType(DictCodeList.报表名称_产品状态登记.getValue());
		res.setSysDate(DateUtil.now());
		res.setRegistBankCode(datas.get(0).getIssuePartyId());

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
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap1, ChbPrdStatusSub sub) {
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
	    //理财产品总资产金额(元)
		if(StringUtil.isNotEmpty(sub.getPrdTotalAmt())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getPrdTotalAmt().setScale(2, RoundingMode.HALF_UP).toString());
			cnBondItem4.setUnitRef(ChbConstant.币种全称_中债_人民币);
			cnBondItem4.setDecimals("0");
			cnBondItemMap1.put("AggregateInvestedAssetAmountCNY", cnBondItem4);
		}

		//理财产品杠杆率
		if(StringUtil.isNotEmpty(sub.getPrdLeverage())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(String.format("%.7f",sub.getPrdLeverage()));
			cnBondItem4.setUnitRef(ChbConstant.PURE);
			cnBondItem4.setDecimals("2");
			cnBondItemMap1.put("LeverageRatio", cnBondItem4);
		}

		//产品状态统计日
		if(StringUtil.isNotEmpty(sub.getPrdStatusDate())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(DateUtil.format(sub.getPrdStatusDate()));
			cnBondItemMap1.put("ProductStatusReportingDate", cnBondItem4);
		}

		//备注
		if(StringUtil.isNotEmpty(sub.getRemark())){
			CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
			cnBondItem4.setValue(sub.getRemark());
			cnBondItemMap1.put("ProductStatusRegistrationDetails", cnBondItem4);
		}

		//登记流水号
		if(StringUtil.isNotEmpty(sub.getSubmitSeq())){
			CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
			cnBondItem19.setValue(sub.getSubmitSeq());
			cnBondItemMap1.put("RegistrationSerialNumber", cnBondItem19);
		}
	}
}
