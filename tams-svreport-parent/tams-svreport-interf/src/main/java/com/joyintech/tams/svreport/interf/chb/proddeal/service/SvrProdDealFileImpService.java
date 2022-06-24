package com.joyintech.tams.svreport.interf.chb.proddeal.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.svreport.interf.chb.proddeal.dto.CheckReport001Dto;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfMappingConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbTypeMappingMapper;
import com.joyintech.tams.svreport.interf.chb.proddeal.dao.ChbPrdDealInterfMapper;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 产品交易接口文件入库
 * 
 * @author yuluan
 *
 */
@Service
public class SvrProdDealFileImpService extends FileImpService {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * CHB_PRD_DEAL_NAME
	 */
//	private static final String FILE_NAME_FX_CHB_PRD_FEEDEAL = "BTA_CLIENT_AREA";
	private static final String FILE_NAME_FX_CHB_PRD_FEEDEAL_DB = "SALE_CUST_SVRREPORT";
	/**
	 * CHB_PRD_DEAL_NAME
	 */
	private static final String BUSINESSKEY_CHB_PRD_FEEDEAL = "TRADE_ID";
	
	private static final String BUSINESSKEY_CHB_PRD_DEAL_1="finprodId";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_5="senddate";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_4="cusCode";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_2="areaCode";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_3="ccy";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_6="issuemember";
	private static final String BUSINESSKEY_CHB_PRD_DEAL_7="inoraboard";

	private static final String REFSEQNO_SPECIAL_SAMPE = "产品交易文件接入检查";

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
	/**
	 * 上游接口解析读取文件处理最大线程数
	 */
	@Value("${application.interfMaxCorePoolSize}")
	private int interfMaxCorePoolSize;
	/**
	 * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrDataCount}")
	private int reportInterfOneTimeSqlLdrDataCount;

	/**
	 * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
	private int reportInterfOneTimeSqlLdrCommitCount;
	/**
	 * sqlldr用数据库连接
	 */
	@Value("${spring.datasource.reportSqlldrDatabaseName}")
	/**
	 * 使用sqlloader入库的条数限制
	 */
	private static final int SQLLOADER_MAX_SIZE = 100000;
	private String reportSqlldrDatabaseName;

	@Autowired
	private ChbPrdDealInterfMapper chbPrdDealInterfMapper;
	@Autowired
	private ProdDealInterfService prodDealInterfService;

	@Autowired
	private ChbTypeMappingMapper chbTypeMappingMapper;

	 /**
     * mybatis的sqlsession管理核心
     */
    @Autowired
	private SqlSessionTemplate template;

	private boolean excuteProdBaseInfoByStep(List<Map<String, String>> baseinfoList, String tmpPath, ImpTable table,
			String saveFlag,String dataSource) {

		// 定义新增数据list-验证不通过数据
		// List<ChbPrdFeeDeal> listNA = new ArrayList<ChbPrdFeeDeal>();
		// 定义新增数据list-验证通过数据
		List<ChbPrdDeal> listNE = new ArrayList<ChbPrdDeal>();
		List<ChbPrdFeeDeal> listFeeE = new ArrayList<ChbPrdFeeDeal>();
		Date settdateDate = null;
		HashMap<String, ChbPrdDeal> dlMap = new HashMap<String, ChbPrdDeal>();
		String key = "";
		// modify by convertMapToEntity yangxianjiu 2022-01-18 start
		List<ChbPrdDeal> baseList = (List<ChbPrdDeal>) convertMapToEntity(baseinfoList,ChbPrdDeal.class, dataSource);
//		for (Map<String, String> map : baseinfoList) {
		for (ChbPrdDeal deal : baseList) {
//			ChbPrdDeal deal = dataConversionEntity(map);
			deal.setRedemptionFee(deal.getRedemptionFee()==null?BigDecimal.ZERO:deal.getRedemptionFee());
			deal.setTradeQty(deal.getTradeQty()==null?BigDecimal.ZERO:deal.getTradeQty());
			deal.setTradeCost(deal.getTradeCost()==null?BigDecimal.ZERO:deal.getTradeCost());
			deal.setIntAmt(deal.getIntAmt()==null?BigDecimal.ZERO:deal.getIntAmt());
			deal.setCustCounts(deal.getCustCounts()==null?BigDecimal.ZERO:deal.getCustCounts());

			deal.setTradePrince(deal.getTradePrince()==null?BigDecimal.ZERO:deal.getTradePrince().subtract(deal.getRedemptionFee()));
			// modify by convertMapToEntity yangxianjiu 2022-01-18 end
			if (settdateDate == null && deal.getSettdateDate() != null) {
				settdateDate = deal.getSettdateDate();
			}
			// deal.setDataSource(type); //
			deal.setSeqno(IDGenerator.generate(36));
			deal.setCreateUser(CommonUtil.getUserName());
			deal.setUpdateUser(CommonUtil.getUserName());

			if (null != deal.getRedemptionFee() && deal.getRedemptionFee().compareTo(BigDecimal.ZERO) > 0) {
				// 赎回费作为本行管理费插入费用交易表
				ChbPrdFeeDeal feeDeal = getFeeDealModel(deal);
				listFeeE.add(feeDeal);
			}
			// 该费用存入管理费表
			deal.setRedemptionFee(BigDecimal.ZERO);

			listNE.add(deal);
			// 认购数据需要更新要素表存续状态为01 存续，到期数据需要更新02 终止
			if (DictCodeListExt2.产品存续期登记交易类型_募集期认购.getValue().equals(deal.getTradeType())
					|| DictCodeListExt2.产品存续期登记交易类型_到期兑付.getValue().equals(deal.getTradeType())) {
				key = (null == deal.getFprodId() ? deal.getProdId() : deal.getFprodId());
				if (dlMap.containsKey(key)) {
					if (DictCodeListExt2.产品存续期登记交易类型_到期兑付.getValue().equals(deal.getTradeType())) {
						dlMap.put(key, deal);
					}
				} else {
					dlMap.put(key, deal);
				}
			}
		}
		if (listNE != null && listNE.size() > 0) {
			// 如果是增量文件则不需要删除
//			if (tmpPath == null) {
				// 插入维护信息表
//				if(saveFlag.equals(com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ALL)){
//					chbPrdDealInterfMapper.deletePrdDealByDate(settdateDate);
//				}
				
				insertDataMerge(listNE);
//				prodDealInterfService.insertPrdDealBatch(listNE);
//			} else {
//				if(saveFlag.equals(com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ADD)){
//					prodDealInterfService.deletePrdDealBatch(listNE);
//				}
//				createDataFile(tmpPath + "/CHB_PRD_DEAL", listNE, table.getColumns());
//			}
		}

		if (listFeeE != null && listFeeE.size() > 0) {
			// 如果是增量文件则不需要删除
			prodDealInterfService.deletePrdFeeDealBatch(listFeeE);
//			if (tmpPath == null) {
				// 插入维护信息表
				prodDealInterfService.insertPrdFeeDealBatch(listFeeE);
//			} else {
//				ImpTable feeTable = new ImpTable(InterfConstants.产品费用交易接口数据.getCode());
//				table.setColumns(ImportGenerator.createImpTableColumns(InterfConstants.产品费用交易接口数据.getCode()));
//				createDataFile(tmpPath + "/CHB_PRD_FEE_DEAL", listFeeE, feeTable.getColumns());
//			}
		}
		logger.debug("反向更新要素信息:----开始-----" + df.format(new Date()));
		// 反向更新要素信息
		ArrayList<ChbPrdDeal> updList = new ArrayList<ChbPrdDeal>();
		for (Map.Entry<String, ChbPrdDeal> entry : dlMap.entrySet()) {
			ChbPrdDeal dl = entry.getValue();
			if (null != dl.getFprodId()) {
				dl.setProdId(dl.getFprodId());
			}
			updList.add(dl);
		}

		if (!StringUtil.isEmptyList(updList)) {
			prodDealInterfService.updatePrdBaseInfo(updList);
		}
		logger.debug("反向更新要素信息:----结束-----" + df.format(new Date()));
		return CollectionUtils.isNotEmpty(listNE);

	}

	/**
	 * @param equityList
	 *            投资者列表
	 * @param type
	 *            数据来源
	 * @param result
	 *            参数
	 * @param tmpPath
	 *            生成的临时文件存放路径
	 * @return 此次是否成功插入
	 */
	private boolean excuteProdDealByStepFx(List<Map<String, String>> equityList,  
			String tmpPath, ImpTable table,String saveFlag,String dateSource) {
			
		// 投资者信息接口信息不为null处理
		if (equityList != null && equityList.size() > 0) {
			 logger.info("销售系统产品交易文件处理开始");
			// 定义新增数据list-验证不通过数据
			// 定义新增数据list-验证通过数据
			List<ChbPrdDeal> listNE = new ArrayList<ChbPrdDeal>();
			// 客户类别映射
			List<ChbTypeMapping> customerType2 = chbTypeMappingMapper
					.selectMappingBySrcDataType(InterfMappingConstants.MAAPING_CUSTOMER_GROUP_ID);
			Map<String, ChbTypeMapping> customerMap = customerType2.stream()
					.collect(JoyinCollectors.toMap(x -> x.getSrcdatatypeid(), x -> x));
			// 境外客户类别映射
			List<ChbTypeMapping> customerTypeJW = chbTypeMappingMapper
					.selectMappingBySrcDataType(InterfMappingConstants.MAAPING_CUSTOMER_GROUP_ID_JW);
			Map<String, ChbTypeMapping> customerMapJW = customerTypeJW.stream()
					.collect(JoyinCollectors.toMap(x -> x.getSrcdatatypeid(), x -> x));
			// 币种映射
			List<ChbTypeMapping> ccyTypes = chbTypeMappingMapper
					.selectMappingBySrcDataType(InterfMappingConstants.MAAPING_CCY);
			Map<String, String> ccyMap = ccyTypes.stream()
					.collect(JoyinCollectors.toMap(x -> x.getSrcdatatypeid(), x -> x.getObjdatatypeid()));
			// 查询产品要素 插入发行机构代码
			List<ChbPrdDeal> infoList = chbPrdDealInterfMapper.getAllPrdIssuePartyFromBaseInfo();
			Map<String, String> infoMap = infoList.stream()
					.collect(JoyinCollectors.toMap(x -> x.getProdId(), x -> x.getIssuePartyId()));
			HashMap<String,ChbPrdDeal> dlMap = new HashMap<String,ChbPrdDeal>(); 
			String key = "";
			Date settdateDate = null;
			for (Map<String, String> map : equityList) {
				List<ChbPrdDeal> deals = dataConversionEntityFx(map, customerMap,customerMapJW,infoMap, ccyMap);
				// // 定义循环投资者信息变量
				// ChbPrdDeal deal = null;
				for (ChbPrdDeal single : deals) {
					if (settdateDate == null && single.getSettdateDate() != null) {
						settdateDate = single.getSettdateDate();
					}
					single.setDataSource(dateSource);
					single.setSeqno(IDGenerator.generate(36));
					single.setCreateUser(
							CommonUtil.getUserName());
					single.setUpdateUser(
							CommonUtil.getUserName());

					single.setRedemptionFee(BigDecimal.ZERO);

					single.setIssuePartyId(CommonUtil.getUserBank().getRegistBankCode());

					listNE.add(single);
					//认购数据需要更新要素表存续状态为01 存续，到期数据需要更新02 终止
					if(DictCodeListExt2.产品存续期登记交易类型_募集期认购.getValue().equals(single.getTradeType())
							|| DictCodeListExt2.产品存续期登记交易类型_到期兑付.getValue().equals(single.getTradeType())){
							key = (null == single.getFprodId() ? single.getProdId() : single.getFprodId());
							if(dlMap.containsKey(key)){
								if(DictCodeListExt2.产品存续期登记交易类型_到期兑付.getValue().equals(single.getTradeType())){
									dlMap.put(key, single);
								}
							}else{
								dlMap.put(key, single);
							}
						}
				}

			}

			if (listNE != null && listNE.size() > 0) {
//				if (tmpPath == null) {
					// 插入维护信息表
//					if(saveFlag.equals(com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ALL)){
//						chbPrdDealInterfMapper.deletePrdDealByDate(settdateDate);
//					}
					insertDataMerge(listNE);
//				} else {
//					if(saveFlag.equals(com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ADD)){
//						prodDealInterfService.deletePrdDealBatch(listNE);
//					}else{
//						chbPrdDealInterfMapper.deletePrdDealByDate(settdateDate);
//					}
//					createDataFile(tmpPath + "/CHB_PRD_DEAL", listNE, table.getColumns());
//				}
			}

			logger.debug("反向更新要素信息:----开始-----" + df.format(new Date()));
			//反向更新要素信息
			ArrayList<ChbPrdDeal> updList = new ArrayList<ChbPrdDeal>();
			for(Map.Entry<String, ChbPrdDeal> entry: dlMap.entrySet()){
				ChbPrdDeal dl = entry.getValue();
				if(null != dl.getFprodId()){
					dl.setProdId(dl.getFprodId());
				}
				updList.add(dl);
	        }
			
			if(!StringUtil.isEmptyList(updList)){
				prodDealInterfService.updatePrdBaseInfo(updList);
			}
			logger.debug("反向更新要素信息:----结束-----" + df.format(new Date()));
			logger.info("销售系统产品交易文件处理结束");
			logger.info("处理条数listNE："+listNE.size());
			
			return  CollectionUtils.isNotEmpty(listNE);
					
		}
		return false;
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	private List<ChbPrdDeal> dataConversionEntityFx(Map<String, String> map, Map<String, ChbTypeMapping> customerMap,Map<String,ChbTypeMapping> customerMapJW,
			Map<String, String> infoMap, Map<String, String> ccyMap) {
		List<ChbPrdDeal> deals = new ArrayList<ChbPrdDeal>();
		ChbPrdDeal ibd = new ChbPrdDeal();
		List<String> message = new ArrayList<String>();
		// 处理是的map
		// System.out.println("处理时的map========================"+map.toString());
		// 上游流水号
		// if (StringUtil.isEmpty(map.get("TRADE_ID"))) {
		// message.add(this.getMessage("CHB_EQUITYINFO_E_001", "流水号"));
		// }

		// 产品代码校验
//		if (StringUtil.isEmpty(map.get("PROD_ID"))) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_001", "产品代码"));
//		} else if (EquityInfoCheckUtil.isExceedLength(map.get("PROD_ID"), 50)) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_010", "产品代码最大50", "50"));
//			ibd.setProdId(map.get("PROD_ID").substring(0, 50));
//		} else {
			ibd.setProdId(map.get("finprodId"));
//		}
		// 机构代码
		ibd.setIssuePartyId(infoMap.get(map.get("finprodId")));
		// 父产品代码
//		if (!StringUtil.isEmpty(map.get("FPROD_ID"))) {
//			if (EquityInfoCheckUtil.isExceedLength(map.get("FPROD_ID"), 50)) {
//				message.add(this.getMessage("CHB_EQUITYINFO_E_010", "父产品代码最大50", "50"));
//				ibd.setFprodId(map.get("FPROD_ID").substring(0, 50));
//			}
//		} else {
		if(!StringUtil.isBlank(map.get("FPROD_ID"))) {
			ibd.setFprodId(map.get("FPROD_ID"));
		}
//		}
			
		// 销售区域
		if (StringUtil.isEmpty(map.get("areaCode"))) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_001", "销售区域"));
		}
		// 确认日
//		if (StringUtil.isEmpty(map.get("PUB_DATE"))) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_001", "下发日"));
//		} else if (EquityInfoCheckUtil.isExceedLength(map.get("PUB_DATE"), 8)) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_010", "下发日", "8"));
//		} else {
			boolean flag = true;
			try {
				DateUtil.parse(map.get("senddate"));
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				flag = false;
//				message.add(this.getMessage("REPORT_COMMON_FILE_E_0013", "下发日不符合格式"));
			}
			if (flag) {
				ibd.setSettdateDate(DateUtil.parse(map.get("senddate")));
				ibd.setPayDate(DateUtil.parse(map.get("senddate")));
				ibd.setInterestDate(DateUtil.parse(map.get("senddate")));
			}
//		}

		// 币种
		String ccy = map.get("ccy");
		if (ccy != null) {
			ccy = ccyMap.get(ccy);
		}
		if (StringUtil.isEmpty(ccy)) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_001", "币种"));
		} 
//		else if (EquityInfoCheckUtil.isDictCodeKeyExist(DictCode.币种_中债, ccy)) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_004", "币种"));
//		}

		// 赎回费
		ibd.setTradePrince(BigDecimal.ZERO);

		ibd.setStatus("E");
		// 销售区域代码
		ibd.setBuyPlace(map.get("areaCode"));
		// 是否代销
//		ibd.setIsagency("02");
//		String isagency = map.get("ISAGENCY");
		String frh=map.get("issuemember");
		if(!StringUtil.isEmpty(frh)&&"A00".equals(frh)) {
			ibd.setIsagency("02");
		}else {
			ibd.setIsagency("01");
		}
		ibd.setCcy(ccy);
		ibd.setInterestCcy(ccy);
		String inoraboard = map.get("inoraboard");
		String c2 = "";
		String c1 = "";
		if("02".equals(inoraboard)) {//境外时，客户一级类型为C06，客户一级类型为C0600
//			c1 = "C06";
//			c2 = "C0600";

				c2 = customerMapJW.get(map.get("cusCode")).getObjdatatypeid();

			c1 = null;
			if (c2 != null && c2.length() > 3) {
				c1 = c2.substring(0, 3);
			}
		}else {//境内时，从映射表取值客户一级类型，客户一级类型

			  c2 = customerMap.get(map.get("cusCode")).getObjdatatypeid();

			c1 = null;
			if (c2 != null && c2.length() > 3) {
				c1 = c2.substring(0, 3);
			}
		}
		String custCountsS = map.get("saveCusNum");
		if (StringUtil.isEmpty(custCountsS)) {
//			message.add(this.getMessage("CHB_EQUITYINFO_E_001", "保有量客户数"));
		}
		BigDecimal custCounts=BigDecimal.ZERO;
		try{
			custCounts=new BigDecimal("".equals(custCountsS)?"0":custCountsS);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
//			message.add(this.getMessage("CHB_EQUITYINFO_E_003", "保有量客户数"));
		}
		ibd.setCustCounts(custCounts);
		ibd.setRemark(map.get("REMARK"));
		ibd.setCustomerType1(c1);
		ibd.setCustomerType2(c2);
		ibd.setIndentifyMark(ibd.getProdId() + "=" + c1 + "=" + c2);
		if (!message.isEmpty()) {
			ibd.setRemark(ibd.getRemark() + ";(数据校验结果:" + message + ")");
		}
		StringBuffer keyVFinal = new StringBuffer();
		keyVFinal.append(ibd.getProdId());
		keyVFinal.append(ibd.getCustomerType2());
		keyVFinal.append(ibd.getCcy());
		keyVFinal.append(ibd.getIsagency());
		keyVFinal.append(map.get("senddate"));
		keyVFinal.append(map.get("areaCode"));
		// 交易类型
		if (!StringUtil.isEmpty(map.get("prinamt")) && new BigDecimal(map.get("prinamt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("01");
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("prinamt"))?"0":map.get("prinamt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("prinamtConfirm"))?"0":map.get("prinamtConfirm")));
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("prinamt"))?"0":map.get("prinamt")));
				ibdt.setIntAmt(BigDecimal.ZERO);
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("sgAmt")) && new BigDecimal(map.get("sgAmt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("02");
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("sgAmt"))?"0":map.get("sgAmt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("sgConfirmAmt"))?"0":map.get("sgConfirmAmt")));
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("sgAmt"))?"0":map.get("sgAmt")));
				ibdt.setIntAmt(BigDecimal.ZERO);
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("shAmt")) && new BigDecimal(map.get("shAmt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("03");
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("shAmt"))?"0":map.get("shAmt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("shQty"))?"0":map.get("shQty")));
				ibdt.setIntAmt(new BigDecimal("".equals(map.get("shAmt"))?"0":map.get("shAmt")).subtract(new BigDecimal("".equals(map.get("shPrincipal"))?"0":map.get("shPrincipal"))));
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("shPrincipal"))?"0":map.get("shPrincipal")));
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("expireAmt")) && new BigDecimal(map.get("expireAmt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("16");
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("expireAmt"))?"0":map.get("expireAmt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("expireAmtQty"))?"0":map.get("expireAmtQty")));
				ibdt.setIntAmt(new BigDecimal("".equals(map.get("expireAmt"))?"0":map.get("expireAmt")).subtract(new BigDecimal("".equals(map.get("expireAmtPrincipal"))?"0":map.get("expireAmtPrincipal"))));
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("expireAmtPrincipal"))?"0":map.get("expireAmtPrincipal")));
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("rebonusAmt")) && new BigDecimal(map.get("rebonusAmt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("05");
				ibdt.setIntAmt(new BigDecimal("".equals(map.get("rebonusAmt"))?"0":map.get("rebonusAmt")));
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("rebonusAmt"))?"0":map.get("rebonusAmt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("rebonusAmtQty"))?"0":map.get("rebonusAmtQty")));
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("rebonusAmt"))?"0":map.get("rebonusAmt")));
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("bonusAmt")) && new BigDecimal(map.get("bonusAmt")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("04");
				ibdt.setIntAmt(new BigDecimal("".equals(map.get("bonusAmt"))?"0":map.get("bonusAmt")));
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("bonusAmt"))?"0":map.get("bonusAmt")));
				ibdt.setTradeQty(BigDecimal.ZERO);
				ibdt.setTradeCost(BigDecimal.ZERO);
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("qtyAddAmt")) && new BigDecimal(map.get("qtyAddAmt")).compareTo(BigDecimal.ZERO) != 0
				|| !StringUtil.isEmpty(map.get("qtyAdd")) && new BigDecimal(map.get("qtyAdd")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("14");
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("qtyAddAmt"))?"0":map.get("qtyAddAmt")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("qtyAdd"))?"0":map.get("qtyAdd")));
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("qtyAddAmt"))?"0":map.get("qtyAddAmt")));
				ibdt.setIntAmt(BigDecimal.ZERO);
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		if (!StringUtil.isEmpty(map.get("qtyMinusPrincipal")) && new BigDecimal(map.get("qtyMinusPrincipal")).compareTo(BigDecimal.ZERO) != 0
				|| !StringUtil.isEmpty(map.get("qtyMinus")) && new BigDecimal(map.get("qtyMinus")).compareTo(BigDecimal.ZERO) != 0) {
			try {
				ChbPrdDeal ibdt = (ChbPrdDeal) ibd.clone();
				ibdt.setTradeType("15");
				ibdt.setTradeCost(new BigDecimal("".equals(map.get("qtyMinusPrincipal"))?"0":map.get("qtyMinusPrincipal")));
				ibdt.setTradeQty(new BigDecimal("".equals(map.get("qtyMinus"))?"0":map.get("qtyMinus")));
				ibdt.setTradePrince(new BigDecimal("".equals(map.get("qtyMinusPrincipal"))?"0":map.get("qtyMinusPrincipal")));
				ibdt.setIntAmt(BigDecimal.ZERO);
				keyVFinal.append(ibdt.getTradeType());
				String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
				ibdt.setTradeId(md5Key);
				deals.add(ibdt);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage(), e);
				message.add(e.getMessage());
			}
		}
		return deals;
	}
	/**
	 * map转换成实体类
	 * 
	 * @param map
	 * @return
	 */
	ChbPrdDeal dataConversionEntity(Map<String, String> map) {
		ChbPrdDeal ibd = new ChbPrdDeal();
		// 上游流水号
		// 机构代码
		// 产品代码校验
		ibd.setProdId(map.get("PROD_ID"));

		// 父产品代码
		ibd.setFprodId(map.get("FPROD_ID"));

		// 识别标识
		// 获取投资者信息报送表中的数据
//		Map<String, String> indentifyMarkMap = chbPrdDealInterfMapper.getAllRegistBankCodeSet().stream()
//				.collect(JoyinCollectors.toMap(x -> x.get("INDENTIFY_MARK"), x -> x.get("ORG_CODE")));
		ibd.setIndentifyMark(map.get("INDENTIFY_MARK"));
		// 销售区域
		// 确认日
		ibd.setSettdateDate(DateUtil.parse(map.get("SETTDATE_DATE")));
		// 本金支付日
		ibd.setPayDate(DateUtil.parse(map.get("PAY_DATE")));
		// 利息支付日
		ibd.setInterestDate(DateUtil.parse(map.get("INTEREST_DATE")));
		if (StringUtil.isNotEmpty(map.get("TRADE_PRINCE"))) {
			ibd.setTradePrince(new BigDecimal(map.get("TRADE_PRINCE")));
		}else{
			ibd.setTradePrince(BigDecimal.ZERO);
		}
		// 交易份额
		if (StringUtil.isNotEmpty(map.get("TRADE_QTY"))) {
			ibd.setTradeQty(new BigDecimal(map.get("TRADE_QTY")));
		}else{
			ibd.setTradeQty(BigDecimal.ZERO);
		}
		//交易成本
		if (StringUtil.isNotEmpty(map.get("TRADE_COST"))) {
			ibd.setTradeCost(new BigDecimal(map.get("TRADE_COST")));
		}else{
			ibd.setTradeCost(BigDecimal.ZERO);
		}
		// 赎回费
		if(map.get("REDEMPTION_FEE")!=null&&!"".equals(map.get("REDEMPTION_FEE"))){
			ibd.setRedemptionFee(new BigDecimal(map.get("REDEMPTION_FEE")));
		}else{
			ibd.setRedemptionFee(BigDecimal.ZERO);
		}
		ibd.setTradePrince(ibd.getTradePrince().subtract(ibd.getRedemptionFee()));
		ibd.setRevDate(DateUtil.parse(map.get("REV_DATE")));
		// 客户一级类型
		// 客户二级类型
		ibd.setStatus(map.get("STATUS"));
		if (map.get("ERRMSG") != null && !"".equals(map.get("ERRMSG"))) {
			ibd.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
		}
		ibd.setTradeId(map.get("TRADE_ID"));
		ibd.setIssuePartyId(map.get("ISSUE_PARTY_ID"));
		ibd.setBuyPlace(map.get("BUY_PLACE"));
		ibd.setTradeType(map.get("TRADE_TYPE"));
		if (StringUtil.isNotEmpty(map.get("INT_AMT"))) {
			ibd.setIntAmt(new BigDecimal(map.get("INT_AMT")));
		}
		ibd.setCcy(map.get("CCY"));
		//保有客户数
		if (StringUtil.isNotEmpty(map.get("CUST_COUNTS"))) {
			ibd.setCustCounts(new BigDecimal(map.get("CUST_COUNTS")));
		}else{
			ibd.setCustCounts(BigDecimal.ZERO);
		}
		//是否代销
		ibd.setIsagency(map.get("ISAGENCY"));
		ibd.setInterestCcy(map.get("INTEREST_CCY"));
		ibd.setCustomerType1(map.get("CUSTOMER_TYPE1"));
		ibd.setCustomerType2(map.get("CUSTOMER_TYPE2"));
		ibd.setRemark(map.get("REMARK"));
		return ibd;

	}

	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag,String dateSource) {
		String dealFileType = SvrDataUtil.getSysParam("CHB_PRD_DEAL_FILE_TYPE");
		String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY,
				GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		boolean success = true;
		if (listData.size() <= SQLLOADER_MAX_SIZE || com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ADD.equals(saveFlag)) {
			if(dealFileType.equals(SVReportGlobalConstant.CHB_PRD_DEAL_FILE_TYPE_01)){
				success = excuteProdBaseInfoByStep(listData, null, null, saveFlag,dateSource);
			}else{
				success = excuteProdDealByStepFx(listData, null, null, saveFlag,dateSource);
			}
		} else {
			// listData= Collections.synchronizedList(listData);
			// 创建表
			ImpTable table = new ImpTable(InterfDefine.投资者交易信息接口数据.getCode());
			table.setColumns(ImportGenerator.createImpTableColumns(InterfDefine.投资者交易信息接口数据.getCode()));
			// 创建一个线程池
			if (interfMaxCorePoolSize < 1) {
				interfMaxCorePoolSize = 20;
			}
			ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
			List<Callable<Boolean>> tasks = new ArrayList<>();
			if (null != listData) {
				int start = 0;
				int end = 0;
				int size = listData.size();
				int max = (int) Math.ceil((double) size / 1000);
				for (int i = 0; i < max; i++) {
					start = end;
					end = Math.min(size, 1000 * (i + 1));
					List<Map<String, String>> subList = listData.subList(start, end);
					tasks.add(new Callable<Boolean>() {

						@Override
						public Boolean call() {
							if(dealFileType.equals(SVReportGlobalConstant.CHB_PRD_DEAL_FILE_TYPE_01)){
								return excuteProdBaseInfoByStep(subList, tmpPath, table, saveFlag,dateSource);
							}else{
								return excuteProdDealByStepFx(listData, null, null, saveFlag,dateSource);
							}
						}
					});
				}
			}

			// 为线程池添加任务
			List<Future<Boolean>> taskResult;
			try {
				taskResult = service.invokeAll(tasks);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				return "fail";
			}
			for (Future<Boolean> res : taskResult) {
				try {
					success = success && res.get();
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage(), e);
					return "fail";
				}
			}
			// 任务结束，停止线程池的所有线程
			service.shutdown();

			Collection<File> files = FileUtils.listFiles(new SafelyFile(tmpPath), new String[] { "tmp" }, false);
			List<String> content = new ArrayList<>(listData.size());
			String result = "";
			for (File f : files) {
				if (f.getName().startsWith(InterfDefine.投资者交易信息接口数据.getCode())) {
					try {
						content.addAll(FileUtils.readLines(f, Charset.forName("UTF-8")));
						logger.debug("查找sqlloder文件" + f);
					} catch (IOException e) {
						logger.debug("查找sqlloder文件异常");
						logger.error(e.getMessage(), e);
						result = "fail";
					} finally {
						f.delete();
					}
				}
			}
			if ("fail".equals(result)) {
				return result;
			}
			// 系统是否使用的db2数据库
			boolean isDB2DataBase = CommonUtil.isDB2DataBase();
			if (isDB2DataBase) {
				// db2数据库处理
				if (CollectionUtils.isNotEmpty(content)) {
					try {
						success = success && ImportGenerator.importToDBByPart(table, tmpPath, content,
								reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "del",
								reportSqlldrDatabaseName);
					} catch (IOException | InterruptedException e) {
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}
			} else {
				// oracle原逻辑
				if (CollectionUtils.isNotEmpty(content)) {
					try {
						success = success && ImportGenerator.importToDBByPart(table, tmpPath, content,
								reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "txt",
								reportSqlldrDatabaseName);
					} catch (IOException | InterruptedException e) {
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}
			}
		}
		// }
		// 数据解析完成，并导入数据库成功后，将ftp文件修改名称
		if (success) {
			return "success";
		}
		// add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
		// monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
		// add by liubin end
		return "fail";
	}

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
		String dealFileType = SvrDataUtil.getSysParam("CHB_PRD_DEAL_FILE_TYPE");
//		List<Map<String, String>> listEerro;
		String result="success";
		if(dealFileType.equals(SVReportGlobalConstant.CHB_PRD_DEAL_FILE_TYPE_01)){
//			listEerro = 
					checkData(listData, interfFile.getFilename(),
					InterfDefine.投资者交易信息接口数据.getCode(), InterfDefine.投资者交易信息接口数据.getCode(),
					BUSINESSKEY_CHB_PRD_FEEDEAL, BUSINESSKEY_CHB_PRD_FEEDEAL);
		}else{
//			listEerro = 
					checkData(listData, interfFile.getFilename(),
					 InterfDefine.投资者交易信息接口数据.getCode(), InterfDefine.投资者交易信息接口数据.getCode(),
					 null,BUSINESSKEY_CHB_PRD_DEAL_1,
					 BUSINESSKEY_CHB_PRD_DEAL_2,BUSINESSKEY_CHB_PRD_DEAL_3,
					 BUSINESSKEY_CHB_PRD_DEAL_4,BUSINESSKEY_CHB_PRD_DEAL_5,
					 BUSINESSKEY_CHB_PRD_DEAL_6,BUSINESSKEY_CHB_PRD_DEAL_7);
		}
//		listData.addAll(listEerro);
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.投资者交易信息接口数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        if(!listData.isEmpty()){
			//1、复制入库处理用的listData（List<map>，用作客户数检查数据。
			List<Map<String, String>> listdataCheck=listData;
			String checkDate="";

			for(Map<String, String> map1:listdataCheck){
				if(map1.get("checkDate")!=null){
					checkDate=map1.get("checkDate");
				}
			}
        	//设置系统参数
			String svrCusNumCheck = SvrDataUtil.getSysParam("SAVE_CUS_NUM_CHECK_01");
            for(int i=0;i<listData.size();i++){
             if(listData.get(i).containsKey("checkDate")){
				 listData.remove(i);
			 }
			}
        	result=saveData(listData, saveFlag, interfFile.getDataSource());
			//1、当系统参数设置为【是否做客户数检查 = 01：是】的时候，进行以下处理：

			if(StringUtil.equals("01",svrCusNumCheck)){
				cusNumChkInterf(listdataCheck,checkDate);
			}
        }
        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.投资者交易信息接口数据.getCode());
		return result;
	}

	/**
	 * 
	 * @param deal
	 * @return
	 */
	private ChbPrdFeeDeal getFeeDealModel(ChbPrdDeal deal) {
		ChbPrdFeeDeal feeDeal = new ChbPrdFeeDeal();
		feeDeal.setSeqno(IDGenerator.generate(36));
		feeDeal.setTradeId(deal.getTradeId());
		feeDeal.setProdId(deal.getProdId());
		feeDeal.setFprodId(deal.getFprodId());
		feeDeal.setFeeId("BJGGLF_JYSHF");
		feeDeal.setFeeName(DictCodeListExt2.费用二级类型_本机构管理费.getCodeName());
		feeDeal.setFeeType(DictCodeListExt2.费用类型_本机构费用.getValue());
		feeDeal.setFeeSectype(DictCodeListExt2.费用二级类型_本机构管理费.getValue());
		feeDeal.setCcy(deal.getCcy());
		feeDeal.setAmt(deal.getRedemptionFee());
		feeDeal.setPayDate(deal.getPayDate());
		feeDeal.setStatus(deal.getStatus());
		feeDeal.setDataSource(deal.getDataSource());
		feeDeal.setRevDate(deal.getRevDate());
		feeDeal.setCreateUser(SecurityUtil.getLoginUser() == null ? "system" : SecurityUtil.getLoginUser().getUid());
		feeDeal.setUpdateUser(SecurityUtil.getLoginUser() == null ? "system" : SecurityUtil.getLoginUser().getUid());
		feeDeal.setRemark("赎回费");

		return feeDeal;
	}
	 /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author yuluan 2019年5月5日 
     * @param assetInfoList 产品交易
     * @param 
     */
    public void insertDataMerge(List<ChbPrdDeal> prdDealList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("产品交易插入开始--------------" + new Date());
            ChbPrdDealInterfMapper batchMapper = session.getMapper(ChbPrdDealInterfMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < prdDealList.size();){
                if(batchLastIndex > prdDealList.size()-1){
                    batchLastIndex = prdDealList.size();
                    batchMapper.insertDataMerge(prdDealList.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.insertDataMerge(prdDealList.subList(index, batchLastIndex));
                    index = batchLastIndex;//设置下一批下标
                    batchLastIndex = index + batchCount;                     
                }
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("产品交易插入结束--------------" + new Date());
    }

	/**
	 * 检查客户数方法
	 * @param listData
	 * @param checkDate
	 */
	public void cusNumChkInterf(List<Map<String, String>> listData,String   checkDate) {
		//定义返回对象
		List<CheckReport001Dto> resultList = new ArrayList<>();


		//新建对象： map<string 产品ID，产品客户数信息DTO>
		HashMap<String, ChbPrdCustNumIfo> prdMap = new HashMap<>();

		//新建对象： 产品列表list<Stirng>  对象产品list
		Set<String> prdList = new HashSet<>();

		//以输入参数.ListData为基础、进行循环处理，生成产品维度的客户数数据
		for (Map<String, String> map : listData) {
			//以此条数据的产品代码为Key,进行是否认购数据判断，当此条数据为认购数据的情况下，进行以下处理：
			if (!StringUtil.isEmpty(map.get("prinamt")) && new BigDecimal(map.get("prinamt")).compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal cust_counts = new BigDecimal(map.get("saveCusNum"));
				// 销售区域为计划单列市的不统计（省份内已经统计）：210200大连市、330200宁波市、350200厦门市、370200青岛市、440300深圳市
				if("210200".equals(map.get("areaCode")) || "330200".equals(map.get("areaCode")) || "350200".equals(map.get("areaCode")) ||
						"370200".equals(map.get("areaCode")) || "440300".equals(map.get("areaCode"))) {
					cust_counts = BigDecimal.ZERO;
				}

				//在【产品信息map】中存在prodDealInterfService
				if (prdMap.containsKey(map.get("finprodId"))) {
					ChbPrdCustNumIfo custNumIfo = prdMap.get(map.get("finprodId"));
					custNumIfo.setCustHeldNum(custNumIfo.getCustHeldNum().add(cust_counts));
					// 产品信息map.产品客户数信息DTO.持有客户数  + 此条信息的.保有量客户数
					prdMap.put(map.get("finprodId"), custNumIfo);
				} else {
					//在【产品信息map】中不存在
					//以此条记录的信息，对【产品客户信息DTO】进行设值
					ChbPrdCustNumIfo custNumIfo = new ChbPrdCustNumIfo();
					custNumIfo.setPrdCode(map.get("finprodId"));
					custNumIfo.setInspectionResults(null);
					custNumIfo.setWarningInformation(null);
					//custNumIfo.setDataOfDate(map.get());
					custNumIfo.setCustHeldNum(cust_counts);
					custNumIfo.setThrCustNum(BigDecimal.ZERO);
					//往产品信息Map中，设值<此条记录的.产品代码，循环中新建的.产品客户信息DTO>
					prdMap.put(map.get("finprodId"), custNumIfo);
					//对象产品list.add(此条数据的产品ID）
					prdList.add(map.get("finprodId"));
				}
			}
		}
		List<String>  prList=new ArrayList<>();
        for(String se:prdList){
			prList.add(se);
		}
			//组装一二期客户数信息
			List<ChbPrdCustNumIfo> lsitOtbch =new ArrayList<ChbPrdCustNumIfo>(prdMap.values());

			// 2、以1中处理【对象产品List】为查询条件，查询三期数据、并进行比较处理
			List<ChbPrdCustNumIfo> lsitOtbchThree = chbPrdDealInterfMapper.getraiseCustNumThree(prList, com.joyintech.framework.util.DateUtil.parse(checkDate));

			//2.2 、比较一二期 与 三期的客户数

			//a、三期不存在、一二期存在  &&  c、三期存在、一二期存在，但是客户数不一致
			complareList(lsitOtbchThree, lsitOtbch, com.joyintech.framework.util.DateUtil.parse(checkDate), resultList, false);
			//b、三期存在，一二期不存在
			complareList(lsitOtbch, lsitOtbchThree, com.joyintech.framework.util.DateUtil.parse(checkDate), resultList, true);

			//3、检查结果数据、批量入库
            if(resultList!=null&&resultList.size()>0){
            	// add by zhangliang 20210325 先删后插  删除的记录只能是自己生成的记录 不能把报送前检查的信息给删了 start
				chbPrdDealInterfMapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_募集总量登记.getValue(),com.joyintech.framework.util.DateUtil.parse(checkDate), REFSEQNO_SPECIAL_SAMPE);
				// add by zhangliang 20210325 先删后插  删除的记录只能是自己生成的记录 不能把报送前检查的信息给删了 end
				int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
				int batchLastIndex = batchCount;// 每批最后一个的下标
				for (int index = 0; index < resultList.size();) {
					if (batchLastIndex > resultList.size() - 1) {
						batchLastIndex = resultList.size();
						chbPrdDealInterfMapper.batchInsert(resultList.subList(index, batchLastIndex));
						break;// 数据删除完毕,退出循环
					} else {
						chbPrdDealInterfMapper.batchInsert(resultList.subList(index, batchLastIndex));
						index = batchLastIndex;// 设置下一批下标
						batchLastIndex = index + batchCount;
					}
				}
			}


	}
	/**
	 * 比较2个list中是否相互存在
	 * otList  一二期的集合     thList  三期的集合
	 *
	 * @param otList
	 * @param thList
	 * @return
	 */
	public void complareList(List<ChbPrdCustNumIfo> otList, List<ChbPrdCustNumIfo> thList, Date checkdate,
							 List<CheckReport001Dto> returList, boolean flag) {
		Map<String, ChbPrdCustNumIfo> map = new HashMap<>();
		//otList 转为map
		if (otList != null) {
			map = otList.stream().collect(Collectors.toMap(ChbPrdCustNumIfo::getPrdCode, Function.identity(), (key1, key2) -> key2));
		}
		if (thList != null && thList.size() > 0) {
			for (ChbPrdCustNumIfo cinfo : thList) {
				if (!map.containsKey(cinfo.getPrdCode())) {
					CheckReport001Dto dto = new CheckReport001Dto();
					//检查序列号  自动生成
					dto.setCheckSeqno(IDGenerator.generate(32));
					//报表ID=DictCodeListExt2.报表名称_募集总量登记.getValue()
					dto.setRepCode(DictCodeListExt2.报表名称_募集总量登记.getValue());
					//报表名称
					dto.setRepName(DictCodeListExt2.报表名称_募集总量登记.getCodeName());
					//唯一标识="产品代码:" + 此条记录的.产品代码
					dto.setValidKey("产品代码:" + cinfo.getPrdCode());
					//检查结果
					dto.setCheckResult(DictCodeListExt2.检查结果_检查不通过.getValue());
					//警告信息
					if (flag) {
						dto.setErrorInfo("客户数不一致（交易入库检查），三期客户数：" + cinfo.getCustHeldNum() + "、一二期客户数：" + 0 + "；");
					} else {
						dto.setErrorInfo("客户数不一致（交易入库检查），三期客户数：" + 0 + "、一二期客户数：" + cinfo.getCustHeldNum() + "；");
					}
					//数据日期
					dto.setDataTime(checkdate);
					// 检查序列号 add by zhangliang 20210325 用于区分报送前检查check还是接入check
					dto.setRefseqno(REFSEQNO_SPECIAL_SAMPE);
					returList.add(dto);
				} else {
					//c、三期存在、一二期存在，但是客户数不一致
					if (cinfo.getCustHeldNum().compareTo(map.get(cinfo.getPrdCode()).getCustHeldNum()) != 0 &&
							!flag) {
						CheckReport001Dto dto = new CheckReport001Dto();
						//检查序列号  自动生成
						dto.setCheckSeqno(IDGenerator.generate(32));
						//报表ID=DictCodeListExt2.报表名称_募集总量登记.getValue()
						dto.setRepCode(DictCodeListExt2.报表名称_募集总量登记.getValue());
						//报表名称
						dto.setRepName(DictCodeListExt2.报表名称_募集总量登记.getCodeName());
						//唯一标识="产品代码:" + 此条记录的.产品代码
						dto.setValidKey("产品代码:" + cinfo.getPrdCode());
						//检查结果
						dto.setCheckResult(DictCodeListExt2.检查结果_检查不通过.getValue());
						//警告信息
						dto.setErrorInfo("客户数不一致（交易入库检查），三期客户数：" + map.get(cinfo.getPrdCode()).getCustHeldNum() + "、一二期客户数：" + cinfo.getCustHeldNum() + "；");
						//数据日期
						dto.setDataTime(checkdate);
						// 检查序列号 add by zhangliang 20210325 用于区分报送前检查check还是接入check
						dto.setRefseqno(REFSEQNO_SPECIAL_SAMPE);
						returList.add(dto);
					}
				}
			}

		}

	}

}
