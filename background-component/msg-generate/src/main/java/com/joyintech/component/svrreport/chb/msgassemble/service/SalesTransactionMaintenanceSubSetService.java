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

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.*;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设置 销售结算资金交易登记报送
 *
 * @since 1.0.0
 * @version 1.0.0
 * @author sjj
 * @创建时间： 2022年10月13日
 */
@Service
public class SalesTransactionMaintenanceSubSetService {

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
	public ChbMsgBuildInfoDto setMsg(List<SalesTransactionMaintenanceSubDto> datas) throws Exception {
		logger.debug("开始组装【销售结算资金交易登记】报送报文");
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装【销售结算资金交易登记】报送报文");

		return chbMsgBuildInfoDto;
	}

	/**
	 * 生成标准接口的数据结构
	 *
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<SalesTransactionMaintenanceSubDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();

		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();

		List<String> registSeq = new ArrayList<String>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		// 元素名称
		String title = "wemax_SalesFundTransferorSettlementInstitutionCode|wemax_ProductRegistrationCode|wemax_SalesFundTransferAccountNumber|wemax_SalesFundTransferAccountName|wemax_SalesFundTransferSerialNumber|wemax_SalesFundFlowType|wemax_SalesFundTransferType|wemax_SalesFundTransferCounterpartyAccountNumber|wemax_SalesFundTransferCounterpartyAccountName|wemax_BankNameofSalesFundTransferCounterpartyAccount|wemax_BankIdentificationCodeofSalesFundTransferCounterpartyAccount|wemax_SalesFundTransferCurrency|wemax_SalesFundTransferAmount|wemax_SalesFundTransferDate|wemax_SalesFundTransferTime|wemax_SalesFundTransferPostscript|wemax_Details|wemax_RegistrationSerialNumber";
		// 元素字符串结果集
		StringBuffer info = new StringBuffer("\n" + "    <![CDATA[" + '\n');
		info.append(title);
		info.append('\n');
		datas.forEach(item -> {
			//利率统一除以100
			ChbXmlUtil.rateFormat(item);

			Map<String, CnBondInterfItemDto> cnBondItemMap = new LinkedHashMap<String, CnBondInterfItemDto>();
			String currRegistSeq = "";
			//重新发送不需要重生生成流水号
			if (StringUtil.isNotEmpty(item.getSumitSeq())){
				registSeq.add(item.getSumitSeq());
				registSeqKeyMap.put(item.getSumitSeq(), item.getPrdRegistCode() + ',' + item.getInstitutionCode()
						+ ',' + item.getAccountNo() + ',' + item.getTradeNo());
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSumitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				registSeqKeyMap.put(item.getSumitSeq(), item.getPrdRegistCode() + ',' + item.getInstitutionCode()
						+ ',' + item.getAccountNo() + ',' + item.getTradeNo());
			}

			String value = item.getInstitutionCode()+"|"+item.getPrdRegistCode()+"|"+item.getAccountNo()+"|"+item.getAccountName()
					+ "|"+ item.getTradeNo()+ "|"+ item.getTradePs()+ "|"+ item.getTradeType()+ "|" + item.getTradeCounterAccNo()
					+ "|"+ item.getTradeCcy() + "|" +item.getTradeCounterAccName()+ "|" +item.getTradeCounterAccBankName() + "|" +item.getTradeCounterAccBankNo()
					+ "|" + item.getTradeAmt().setScale(2,RoundingMode.HALF_UP) + "|" + sdf.format(item.getTradeVdate())+ "|" + item.getTradeTime()
					+ "|"+ item.getRemark()+ "|"+ item.getSumitSeq();
			info.append(value);
			info.append('\n');
		});
		info.append("    ]]>");
		info.append('\n');
		CnBondInterfItemDto cnBondInterfItem = new CnBondInterfItemDto();
		cnBondInterfItem.setValue(info.toString());

		Map<String, CnBondInterfItemDto> maps = new HashMap<String, CnBondInterfItemDto>();
		maps.put("SalesFundTransferOrSettlementRegistrationCsvDataExplanator", cnBondInterfItem);
		infos.add(maps);

		res.setInfos(infos);
		//在para里面设置唯一标识符唯一标识符
		res.setRegistSeqKeyMap(registSeqKeyMap);

		res.setDealType(DictCodeList.报表名称_销售结算资金交易信息登记.getValue());
		res.setSysDate(DateUtil.now());
		res.setRegistSeq(registSeq);
		res.setRegistBankCode(datas.get(0).getRegistBankCode());
		return res;
	}
}
