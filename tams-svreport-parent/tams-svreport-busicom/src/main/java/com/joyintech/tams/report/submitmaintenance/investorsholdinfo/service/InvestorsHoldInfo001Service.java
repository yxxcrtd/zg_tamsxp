/*
 * 文件名：InvestorsHoldInfo001Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：天津滨海农商行理财资管系统
 * 修改人：dingzhipeng
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：天津滨海农商行理财资管系统
 */

package com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.entity.ChbFileReadMonitorInfo;
import com.joyintech.tams.common.base.entity.ChbInvestholdInfo;
import com.joyintech.tams.common.base.entity.ChbInvestholdInfoSub;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.chencknum.dao.CheckNumMapper;
import com.joyintech.tams.report.chencknum.dto.TotalAmountInfoDto;
import com.joyintech.tams.report.common.TxtParseUtil;
import com.joyintech.tams.report.common.XmlParseUtil;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.common.util.*;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.InterfConstant;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.report.investormerger.dao.ChbEquHoldCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dao.ChbInvestholdInfoCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dto.ChbInvestholdInfoCheckDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.FileIO;
import com.joyintech.tams.report.submitmaintenance.equityinfo.validate.EquityInfoValid;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dao.InvestorsHoldInfo001Mapper;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001FileReadParaDto;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dao.InvestorsHoldInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;
import com.joyintech.tams.report.system.proregistcodemanage.dao.ProRegistCodeManage001Mapper;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 投资者持有信息业务层service 〈功能详细描述〉
 *
 * @author dingzhipeng
 * @version 1.0 2018年5月8日
 * @see InvestorsHoldInfo001Service
 * @since 1.0
 */
@Service
//@Transactional(rollbackFor = Exception.class)
public class InvestorsHoldInfo001Service extends CommonExportService<InvestorsHoldInfo001Dto> {

	/**
	 * 未报送
	 */
	private static final String SEND_STATUS_01 = DictCodeListExt2.报送状态_未报送.getValue();
	/**
	 * 接口报送
	 */
	private static final String SEND_STATUS_02 = DictCodeListExt2.报送状态_接口报送.getValue();
	/**
	 * 手工处理
	 */
	private static final String SEND_STATUS_03 = DictCodeListExt2.报送状态_手工处理.getValue();
	private static int addPart = 1;
	private static String lastDate= "";
	/**
	 * bean
	 */
	@Autowired
	private InvestorsHoldInfo001Mapper investorsHoldInfo001Mapper;

	@Autowired
	private InvestorsHoldInfoRegist001Mapper investorsHoldInfoRegist001Mapper;
	/**
	 * 报送前检查mapper
	 */
	@SuppressWarnings("unused")
	@Autowired
	private CheckReport001Mapper chbReportCheckMapper;

	/**
	 * 产品登记编码管理mapper
	 */
	@Autowired
	private ProRegistCodeManage001Mapper proRegistCodeManageMapper;
	/**
	 * 投资者身份标识 合并处理
	 */
	@Autowired
	private InvestorsHoldInfoDistinctService investorsHoldInfoDistinctService;
	/**
	 * 投资者身份去重检查 临时表mapper
	 */
	@SuppressWarnings("unused")
	@Autowired
	private ChbEquHoldCheckDtoMapper chbEquHoldCheckDtoMapper;

	@Autowired
	private CheckNumMapper checkNumMapper;

	@Autowired
	private ChbInvestholdInfoCheckDtoMapper chbInvestholdInfoCheckDtoMapper;

	@Autowired
	private BatchInsertService batchInsertService;
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 上游接口解析读取文件处理最大线程数
	 */
	@Value("${application.interfMaxCorePoolSize}")
	private int interfMaxCorePoolSize;

	/**
	 * 上游接口解析读取文件处理最大线程数
	 */
	@Value("${application.maxDataCountOneTimeForInvestholdInfo}")
	private int maxDataCountOneTimeForInvestholdInfo;
	/**
	 * 长sql最大一次插入或者更新条数
	 */
	@Value("${application.reportInsertLongCount}")
	public  String reportInsertLongCount;
	/**
	 * sql最大一次最大删除条数
	 */
	@Value("${application.reportSelectOrDeleteCount}")
	public  String reportSelectOrDeleteCount;
	/**
	 * 短sql最大一次最大插入条数
	 */
	@Value("${application.reportInsertCount}")
	public  String reportInsertCount;
	/**
	 * 上游接口文件编码格式
	 */
	@Value("${application.reportInterfFileEncoding}")
	public String reportInterfFileEncoding;

	/**
	 * 上游接口解析读取文件处理,单线程处理数据条数
	 */
	@Value("${application.reportInterfOneTimeDataCount}")
	private int reportInterfOneTimeDataCount;

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
	 * 上游接口解析分割符
	 */
	@Value("${application.reportInterfSplitCharacter}")
	private String reportInterfSplitCharacter;

	/**
	 * sqlldr用数据库连接
	 */
	@Value("${spring.datasource.reportSqlldrDatabaseName}")
	private String reportSqlldrDatabaseName;

	/**
	 *
	 * 处理查询前的参数 <功能详细描述>
	 *
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public void dealSearchParam(Map<String, Object> pageParam) {

		// 将产品代码当做多选下拉框处理
		String prodId = (String) pageParam.get("prodId");
		if (StringUtil.isNotBlank(prodId)) {
			pageParam.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodId));
		}

		String orgCode = (String) pageParam.get("orgCode");
		// 将机构代码当做多选下拉框处理
		if (StringUtil.isNotBlank(orgCode)) {
			pageParam.put("orgCodeList", StringUtil.splitToList(GlobalConstant.COMMA, orgCode));
		}
		String prdRegistCode = (String) pageParam.get("prdRegistCode");
		if (StringUtil.isNotBlank(prdRegistCode)) {
			pageParam.put("prdRegistCodeList", StringUtil.splitToList(GlobalConstant.COMMA, prdRegistCode));
		}
		// 将数据状态当做多选下拉框处理
		if (pageParam.containsKey("status") && StringUtil.isNotEmpty(pageParam.get("status"))) {
			pageParam.put("statusList",
					StringUtil.splitToList(GlobalConstant.COMMA, pageParam.get("status").toString()));
		}
		// 处理日期
		if (pageParam.containsKey("holdDate") && StringUtil.isNotEmpty(pageParam.get("holdDate"))) {
			String holdDate = pageParam.get("holdDate").toString();
			pageParam.put("holdDateStart", DateUtil.parse(holdDate.substring(0, 10)));
			pageParam.put("holdDateEnd", DateUtil.parse(holdDate.substring(holdDate.length() - 10, holdDate.length())));
		}
		pageParam.put("DICT_STATUS", DictCode.维护数据状态.getValue());
		pageParam.put("DICT_CYY", DictCode.币种_中债.getValue());
		if (pageParam.containsKey("keyList")) {
			List<InvestorsHoldInfo001Dto> selectList = new ArrayList<>();
			String keyList = pageParam.get("keyList").toString();
			String[] keys = StringUtil.split(keyList, GlobalConstant.COMMA);
			for (String key : keys) {
				String[] keyItem = StringUtil.split(key, GlobalConstant.VERTICAL_LINE);
				/*
				 * v.registBankCode+"|"+v.prdRegistCode+"|"+v.indentifyMark+"|"+v.ccy+"|"+v.
				 * holdDate+"|" +v.status
				 */
				InvestorsHoldInfo001Dto dto = new InvestorsHoldInfo001Dto();
				dto.setRegistBankCode(keyItem[0]);
				// dto.setPrdRegistCode(keyItem[1]);
				dto.setProdid(keyItem[1]);
				dto.setIndentifyMark(keyItem[2]);
				dto.setCcy(keyItem[3]);
				dto.setHoldDate(DateUtil.parse(keyItem[4]));
				dto.setStatus(keyItem[5]);
				selectList.add(dto);
			}
			pageParam.put("keyList", selectList);
		}
	}

	/**
	 * 画面请求列表 <功能详细描述>
	 *
	 * @param pageParam
	 *            页面查询参数
	 * @return 返回到画面的数据
	 *             异常
	 * @see
	 * @since 1.0
	 */
	public Page<InvestorsHoldInfo001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		int page = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
		int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<InvestorsHoldInfo001Dto> pageList = queryForPage(
				page,legth,()->investorsHoldInfo001Mapper.selectAllData(pageParam));
		dealDataList(pageList.getRoot());
		return pageList;
	}

	/**
	 * 格式化查询结果
	 *
	 * @param resultList
	 *            查询结果
	 * @see
	 * @since 1.0
	 */
	private void dealDataList(List<InvestorsHoldInfo001Dto> resultList) {
		if (StringUtil.isEmptyList(resultList)) {
			return;
		}

		for (InvestorsHoldInfo001Dto investorsHoldInfo001Dto : resultList) {
			investorsHoldInfo001Dto.setStatus(
					SysDataUtil.getNameFromDataDict(DictCode.维护数据状态, investorsHoldInfo001Dto.getStatus()));
			investorsHoldInfo001Dto.setCcyCode(
					SysDataUtil.getNameFromDataDict(DictCode.币种_中债, investorsHoldInfo001Dto.getCcyCode()));
		}
	}

	// /**
	// * 处理查询结果
	// * <功能详细描述>
	// * @param list 待处理数据
	// * @param isExport 是否是导出
	// * @see
	// * @since 1.0
	// */
	// private void dealDataList(List<InvestorsHoldInfo001Dto> list, boolean
	// isExport) {
	// if(isExport){
	// list.forEach(item->{
	// item.setCcyCode(item.getCcy()+" "+item.getCcyCode());
	// });
	// }
	// else{
	//// list.forEach(item->{
	////
	//// });
	// }
	// }

	/**
	 * 查询所有的数据 <功能详细描述>
	 *
	 * @param pageParam
	 *            查询参数
	 * @return 查询结果
	 * @see
	 * @since 1.0
	 */
	public List<InvestorsHoldInfo001Dto> selectAllList(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		@SuppressWarnings("unchecked")
		List<InvestorsHoldInfo001Dto> dataList =  (List<InvestorsHoldInfo001Dto>) pageParam.get("keyList");

		List<InvestorsHoldInfo001Dto> dtoList = new ArrayList<InvestorsHoldInfo001Dto>();
		this.selectAllDataBatch(dataList, dtoList);
//				investorsHoldInfo001Mapper.selectAllData(pageParam);
		// dealDataList(dtoList, true);
		return dtoList;
	}

	/**
	 * 分批次执行selectAllDataBatch
	 * @param dataList 数据
	 * @param dtoList 返回结果集
	 * @see
	 * @since 1.0
	 */
	private void selectAllDataBatch(List<InvestorsHoldInfo001Dto> dataList,List<InvestorsHoldInfo001Dto> dtoList ) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			dtoList.addAll(investorsHoldInfo001Mapper.selectAllDataByList(dataList));
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			dtoList.addAll(investorsHoldInfo001Mapper.selectAllDataByList(newDataList.subList(0, maxDataCountOneTime)));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			dtoList.addAll(investorsHoldInfo001Mapper.selectAllDataByList(newDataList));
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		Map<String, Object> pageParam = exportParam.getSelectParam();
		String pageIds = (String) pageParam.get("pageIds");
		if (StringUtil.isNotEmpty(pageIds)) {
			List<String> pageNames = Arrays.asList(pageIds.split(GlobalConstant.COMMA));
			pageParam.replace("pageIds", pageIds, pageNames);
		} else {
			pageParam.replace("pageIds", pageIds, null);
			/***TISHKBS-2369  CLONE - UAT-投资者持有信息，已删除的数据能做导出，如果已删除数据与现有效数据是同一识别号，同一识别号可导出多条数据**/
			/***在不选择全部导出时候,限制只导出E状态的数据**/
			pageParam.put("status","E");
		}
		// 设置参数
		dealSearchParam(pageParam);


		//无查询参数的情况
		exportParam.config("/com/joyintech/tams/report/submitmaintenance/investorsholdinfo/conf/export_conf.xml")
				.select(() -> investorsHoldInfo001Mapper.selectAllData(pageParam));

	}

	@Override
	public com.joyintech.framework.grid.Page<InvestorsHoldInfo001Dto> selectPageData(Map<String, Object> pageParam) {
//		List<InvestorsHoldInfo001Dto> data = restTemplate.postForList(
//				SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfo/selectAllData", pageParam,
//				InvestorsHoldInfo001Dto.class);
		List<InvestorsHoldInfo001Dto> data =  selectAllData(pageParam);
		com.joyintech.framework.grid.Page<InvestorsHoldInfo001Dto> page = new com.joyintech.framework.grid.Page<InvestorsHoldInfo001Dto>();
		page.setRoot(data);
		//page.setTotal(data.size());
		return page;
	}

	public List<InvestorsHoldInfo001Dto> selectAllData(Map<String, Object> pageParam) {
		if(pageParam.get("holdDateStart")  instanceof Long) {
			pageParam.put("holdDateStart",new Date((Long)pageParam.get("holdDateStart")));
			pageParam.put("holdDateEnd",new Date((Long)pageParam.get("holdDateEnd")));
		}
		return investorsHoldInfo001Mapper.selectAllData(pageParam);
	}
	/**
	 * 删除操作 <功能详细描述>
	 *
	 * @param dto
	 *            数据
	 * @see
	 * @since 1.0
	 */@Transactional(rollbackFor = Exception.class)
	public void delete(InvestorsHoldInfo001Dto dto) {
		// 查询报送时间配置表
//		Map<String, Object> map = new HashMap<String, Object>();
//		/* map.put("reportCode", DictCodeListExt2.报表名称_投资者持有信息登记.getValue()); */
//		map.put("keyList", dto.getSelectedItem());
//		map.put("STATUS_D", DictCodeListExt1.维护数据状态_删除.getValue());
		List<InvestorsHoldInfo001Dto> keyList = dto.getSelectedItem();
		if (StringUtil.equals(keyList.get(0).getStatusCode(), DictCodeListExt1.维护数据状态_有效.getValue())) {
//			ChbInvestholdInfoSub chbInvesthold = null;
//			ChbInvestholdInfoSub chbInvestholdInfoSub = null;
			// 当前批次报送时点集合
			Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
//			List<Date> dateList = null;
			//investorsHoldInfo001Mapper.updateByKeyList(DictCodeListExt1.维护数据状态_删除.getValue(), keyList);
			//分批次将维护信息表中的数据更新为D
			logger.info("=======更新维护表开始======"+DateUtil.getCurrentDateTime());
			this.updateByKeyListBatch(DictCodeListExt1.维护数据状态_删除.getValue(), keyList);
			logger.info("=======更新维护表结束======"+DateUtil.getCurrentDateTime());
			//根据list查询库中所有满足条件的列表
			List<ChbInvestholdInfoSub> chbInvestList = new ArrayList<ChbInvestholdInfoSub>();
//					investorsHoldInfo001Mapper
//					.querySumbitInvestHoldByCodeBatch(keyList);
			this.querySumbitInvestHoldByCodeBatch(keyList, chbInvestList);
			Map<String, List<ChbInvestholdInfoSub>> investInfo001Map = chbInvestList.stream().collect(
					JoyinCollectors.groupingBy(
							x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE + x.getRegistBankCode()
									+GlobalConstant.UNDER_LINE + x.getCcy()+GlobalConstant.UNDER_LINE + DateUtil.format(x.getHoldDate())
									+GlobalConstant.UNDER_LINE + x.getPrdRegistCode()));
			//删除list
			List<ChbInvestholdInfoSub> delList = new ArrayList<ChbInvestholdInfoSub>();
			//更新list
			List<ChbInvestholdInfoSub> updateList = new ArrayList<ChbInvestholdInfoSub>();
			//插入list
			List<ChbInvestholdInfoSub> insertList = new ArrayList<ChbInvestholdInfoSub>();
			
			 List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, keyList.size());
				int index = 0;
			for (InvestorsHoldInfo001Dto investorsHoldInfo001Dto : keyList) {
				String key = investorsHoldInfo001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
						+ investorsHoldInfo001Dto.getRegistBankCode()+GlobalConstant.UNDER_LINE
						+ investorsHoldInfo001Dto.getCcy()+GlobalConstant.UNDER_LINE
						+ DateUtil.format(investorsHoldInfo001Dto.getHoldDate())+GlobalConstant.UNDER_LINE +
						investorsHoldInfo001Dto.getPrdRegistCode();
				List<ChbInvestholdInfoSub> chbInvest = investInfo001Map.get(key);
				if(CollectionUtils.isNotEmpty(chbInvest)){
					ChbInvestholdInfoSub chbInvesthold = new ChbInvestholdInfoSub();
					// 报送时点通用方法
					List<Date> dateList =  ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者持有信息登记.getValue(),
							DictCodeListExt2.报送数据状态_删除.getValue(), dateMap, DateUtil.now());
					if (CollectionUtils.isNotEmpty(dateList)) {
						chbInvesthold.setChbSubmitDeadline(dateList.get(0));
						chbInvesthold.setSubmitDeadline(dateList.get(1));
					}
					if (chbInvest != null && chbInvest.size() == 1) {
						ChbInvestholdInfoSub investorsHoldInfo = chbInvest.get(0);
						// 当登记表中只有一条数据，并且是未报送状态的，直接删除该条数据
						if (DictCodeListExt2.报送状态_未报送.getValue().equals(investorsHoldInfo.getSendStatus())) {
							// 用业务主键删除 包括识别标识、产品登记编码，币种、发送状态、持有日期、登记银行代码
							ChbInvestholdInfoSub chbInvestholdInfoSub = new ChbInvestholdInfoSub();
							chbInvestholdInfoSub.setIndentifyMark(investorsHoldInfo001Dto.getIndentifyMark());
							chbInvestholdInfoSub.setRegistBankCode(investorsHoldInfo001Dto.getRegistBankCode());
							chbInvestholdInfoSub.setSendStatus(investorsHoldInfo.getSendStatus());
							chbInvestholdInfoSub.setPrdRegistCode(investorsHoldInfo001Dto.getPrdRegistCode());
							chbInvestholdInfoSub.setCcy(investorsHoldInfo001Dto.getCcy());
							chbInvestholdInfoSub.setHoldDate(investorsHoldInfo001Dto.getHoldDate());
							delList.add(chbInvestholdInfoSub);
//							investorsHoldInfo001Mapper.deleteByPrimary(chbInvestholdInfoSub);
						}
						// 当登记表中只有一条数据，且报送状态为手工处理或者接口报送时，重新插入一条数据
						else if (DictCodeListExt2.报送状态_接口报送.getValue().equals(investorsHoldInfo.getSendStatus())
								|| DictCodeListExt2.报送状态_手工处理.getValue().equals(investorsHoldInfo.getSendStatus())) {
							chbInvesthold.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
							chbInvesthold.setIndentifyMark(investorsHoldInfo001Dto.getIndentifyMark());
							chbInvesthold.setRegistBankCode(investorsHoldInfo001Dto.getRegistBankCode());
							chbInvesthold.setSendStatus(investorsHoldInfo.getSendStatus());
							chbInvesthold.setRegistSeqno(
									//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
									CommonUtil.getSequenceStr(sequenceList, index)
									);
							index++;
							chbInvesthold.setHoldDate(investorsHoldInfo001Dto.getHoldDate());
							chbInvesthold.setPrdRegistCode(investorsHoldInfo001Dto.getPrdRegistCode());
							chbInvesthold.setCcy(investorsHoldInfo001Dto.getCcy());
							chbInvesthold.setProdid(investorsHoldInfo001Dto.getProdid());
							insertList.add(chbInvesthold);
//							investorsHoldInfo001Mapper.insertDeleteInvestReg(chbInvesthold);
						}
					}
					// 当登记表中存两条数据时，直接将未报送一条数据的数据状态更新为更新为数据删除
					else if (chbInvest != null && chbInvest.size() == 2) {
//						chbInvesthold.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
						chbInvesthold.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
						chbInvesthold.setIndentifyMark(investorsHoldInfo001Dto.getIndentifyMark());
						chbInvesthold.setRegistBankCode(investorsHoldInfo001Dto.getRegistBankCode());
						chbInvesthold.setHoldDate(investorsHoldInfo001Dto.getHoldDate());
						chbInvesthold.setPrdRegistCode(investorsHoldInfo001Dto.getPrdRegistCode());
						chbInvesthold.setCcy(investorsHoldInfo001Dto.getCcy());
						updateList.add(chbInvesthold);
						// chbInvesthold.setRegistSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//						investorsHoldInfo001Mapper.updateByPrimaryKeySelected(chbInvesthold);
					}
				}
			}
			//批量执行insert
			logger.info("=======执行insertDeleteInvestRegBatch开始===="+DateUtil.getCurrentDateTime());
			this.insertDeleteInvestRegBatch(insertList);
			logger.info("=======执行insertDeleteInvestRegBatch结束===="+DateUtil.getCurrentDateTime());
			//批量执行delete
			logger.info("=======执行deleteByPrimaryBatch开始===="+DateUtil.getCurrentDateTime());
			this.deleteByPrimaryBatch(delList);
			logger.info("=======执行deleteByPrimaryBatch结束===="+DateUtil.getCurrentDateTime());
			//批量执行update
			logger.info("=======执行updateByPrimaryKeySelectedBatch开始===="+DateUtil.getCurrentDateTime());
			this.updateByPrimaryKeySelectedBatch(DictCodeListExt2.报送数据状态_删除.getValue(), updateList);
			logger.info("=======执行updateByPrimaryKeySelectedBatch结束===="+DateUtil.getCurrentDateTime());
		}
		else if(StringUtil.equals(keyList.get(0).getStatusCode(), DictCodeListExt1.维护数据状态_待处理.getValue())){
			//investorsHoldInfo001Mapper.deleteByKeyList(keyList);
			//分批次物理删除持有信息维护表
			this.deleteByKeyListBatch(keyList);
		}
	}
	/**
	 * updateByPrimaryKeySelectedBatch 批量更新
	 * @param updateList 主键
	 * @param chbInvestList 结果集
	 */
	private void querySumbitInvestHoldByCodeBatch(List<InvestorsHoldInfo001Dto> keyList, List<ChbInvestholdInfoSub> chbInvestList){
		if(CollectionUtils.isEmpty(keyList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = keyList.size();

		if (dataCount <= maxDataCountOneTime) {
			chbInvestList.addAll(investorsHoldInfo001Mapper.querySumbitInvestHoldByCodeBatch(keyList));
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(keyList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			chbInvestList.addAll(investorsHoldInfo001Mapper.querySumbitInvestHoldByCodeBatch(newDataList.subList(0, maxDataCountOneTime)));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbInvestList.addAll(investorsHoldInfo001Mapper.querySumbitInvestHoldByCodeBatch(newDataList));
		}

	}
	/**
	 * updateByPrimaryKeySelectedBatch 批量更新
	 * @param updateList 主键
	 */
	private void updateByPrimaryKeySelectedBatch(String status,List<ChbInvestholdInfoSub> updateList){
		if(CollectionUtils.isEmpty(updateList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = updateList.size();

		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.updateByPrimaryKeySelectedBatch(status, updateList);
			return;
		}

		List<ChbInvestholdInfoSub> newDataList = new ArrayList<ChbInvestholdInfoSub>();
		newDataList.addAll(updateList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.updateByPrimaryKeySelectedBatch(status, newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.updateByPrimaryKeySelectedBatch(status, newDataList);
		}

	}
	/**
	 * insertDeleteInvestRegBatch 批量删除
	 * @param updateList 主键
	 */
	private void insertDeleteInvestRegBatch(List<ChbInvestholdInfoSub> insertList){
		if(CollectionUtils.isEmpty(insertList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportInsertLongCount, "1");
		int dataCount = insertList.size();

		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.insertDeleteInvestRegBatch(insertList);
			return;
		}

		List<ChbInvestholdInfoSub> newDataList = new ArrayList<ChbInvestholdInfoSub>();
		newDataList.addAll(insertList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.insertDeleteInvestRegBatch(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.insertDeleteInvestRegBatch(newDataList);
		}

	}
	/**
	 * deleteByPrimaryBatch 批量逻辑更新维护信息表
	 * @param delList 主键
	 */
	private void deleteByPrimaryBatch(List<ChbInvestholdInfoSub> delList){
		if(CollectionUtils.isEmpty(delList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = delList.size();

		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.deleteByPrimaryBatch(delList);
			return;
		}

		List<ChbInvestholdInfoSub> newDataList = new ArrayList<ChbInvestholdInfoSub>();
		newDataList.addAll(delList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.deleteByPrimaryBatch(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.deleteByPrimaryBatch(newDataList);
		}

	}
	/**
	 * updateByKeyListBatch 批量逻辑更新维护信息表
	 * @param stausD  删除的数据状态
	 * @param keyList 主键
	 */
	private void updateByKeyListBatch(String stausD, List<InvestorsHoldInfo001Dto> keyList){
		if(CollectionUtils.isEmpty(keyList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = keyList.size();

		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.updateByKeyList(stausD, keyList);
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(keyList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.updateByKeyList(stausD, newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.updateByKeyList(stausD, newDataList);
		}

	}
	/**
	 * deleteByKeyListBatch 批量逻辑更新维护信息表
	 * @param keyList 主键
	 */
	private void deleteByKeyListBatch(List<InvestorsHoldInfo001Dto> keyList){
		if(CollectionUtils.isEmpty(keyList)){
			return ;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = keyList.size();

		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.deleteByKeyList(keyList);
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(keyList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.deleteByKeyList(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.deleteByKeyList(newDataList);
		}

	}
	/**
	 * 将excel导入的数据处理为list统一验证 <功能详细描述>
	 *
	 * @param dataList
	 *            数据源
	 * @return 转换结果
	 * @see
	 * @since 1.0
	 */
	public List<Map<String, String>> listToMap(List<List<String>> dataList) {
		String[] fieldName = { "REGIST_BANK_CODE", "ACCOUNT", "INDENTIFY_MARK", "HOLD_DATE", "CCY", "QTY", "AMOUNT",
				"CNY_AMT", "AGENT" };
		List<Map<String, String>> result = new ArrayList<>();
		dataList.forEach(action -> {
			Map<String, String> temp = new HashMap<>();
			for (int i = 0; i < 9; i++) {
				temp.put(fieldName[i], action.get(i));
			}
			if(action.size()==10){
				temp.put("REG_CODE", action.get(action.size()-1));
			}
			result.add(temp);
		});
		return result;
	}

	/**
	 * 导入功能 <功能详细描述>
	 *
	 * @param fileName
	 *            文件名称
	 * @return check结果
	 *             异常
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> importData(List<List<String>> dataList,List<List<String>> tempList,String bankCode,String deptCode,String orgCode ) {
		if (!StringUtil.isEmptyList(dataList)) {
			List<String> headList = new ArrayList<String>();
			headList.add("登记银行代码");
			headList.add("产品代码");
			headList.add("识别标识");
			headList.add("持有日期");
			headList.add("币种");
			headList.add("持有份额");
			headList.add("持有金额");
			headList.add("折算人民币金额（元）");

			List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
			if (!StringUtil.isEmptyList(tempCheckResult)) {
				return tempCheckResult;
			}

			if (dataList.size() == 0) {
				return null;
			}
			List<Map<String, String>> mapList = listToMap(dataList);
			List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
			List<InvestorsHoldInfo001Dto> listA = new ArrayList<>();

			List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
			List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
			List<ExcelValidDto> checkResult = checkDataInfo(mapList, true, listE, listA, null, null,bankCode,deptCode,orgCode);
			if (checkResult.isEmpty()) {
				// 进行了数据检查与转化后 无错误信息可以进行后续操作
				InvestorsHoldInfo001FileReadParaDto paraDto = this.buildFileReadParaDto(mapList);
				mapList.stream().forEach(item->{
					String[] codeList = StringUtil.split(item.get("CCY"), StringUtil.SPACE);
					item.put("CCY", codeList[0]);
				});

				List<ChbInvestholdInfoCheckDto> mergelist = investorsHoldInfoDistinctService.checkInvestorsHoldInfolistBackNew(mapList,paraDto);
				afterCheckDealNew(listE, null, DictCodeListExt2.数据来源_EXCEL导入.getValue(), listIn, subListInsert, paraDto, mergelist);

				String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
				// 批量插入
				if (listIn != null && listIn.size() > 0) {
					if(listIn.size()<1000){
						investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listIn);
					}else{
						// 创建表
						ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
						table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));

						batchInsertService.exeBatchldr(tmpPath, listIn, table);
					}
				}

				if (subListInsert != null && subListInsert.size() > 0) {
					if(listIn.size()<1000) {
						insertChbSubHoldInfoDataByPart(subListInsert);
					}else{
						// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
						ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
						subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
						subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
						// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
						for (ImpColumn column : subTable.getColumns()) {
							if (StringUtil.equals(column.getName(), "STATUS")) {
								column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
							}
						}
						batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
					}
				}

				investorsHoldInfoDistinctService.mergeInvestorsHoldInfolistNew("02",DictCodeListExt2.数据来源_EXCEL导入.getValue());
			}
			return checkResult;
		}
		return null;
	}

	/**
	 * 对于校验通过的数据进行后续处理 <功能详细描述>
	 *
	 * @param listE
	 *            校验通过数据
	 * @param type
	 *            操作的类型
	 * @param subTable
	 *            子表
	 * @param table
	 *            表
	 * @param tmpPath
	 *            临时路径
	 * @param paraDto
	 *            投资者持有信息上游接口接入多线程处理，可以事先查询的内容dto
	 * @see
	 * @since 1.0
	 */
	public void afterCheckDeal(List<InvestorsHoldInfo001Dto> listE, List<InvestorsHoldInfo001Dto> listA, String type,
							   List<InvestorsHoldInfo001Dto> listIn, List<ChbInvestholdInfoSub> subListInsert, InvestorsHoldInfo001FileReadParaDto paraDto) {

		boolean isDB2DataBase = CommonUtil.isDB2DataBase();

		logger.info("afterCheckDeal待维护数据插入数据库处理开始 " + DateUtil.getCurrentDateTime());
		// 待维护数据插入数据库
		if (CollectionUtils.isNotEmpty(listA)) {
			listA.forEach(action -> {
				action.setDataSource(type);
				action.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
			});
			if(!StringUtil.isEmptyList(listA) && listA.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				// 先删后插
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listA);
				investorsHoldInfoDistinctService.deleteChbInvestholdInfoByTmp();
				// 插入
				investorsHoldInfoDistinctService.saveChbInvestholdInfo(listA);
			}else{
				investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listA);
			}
		}
		logger.info("afterCheckDeal待维护数据插入数据库处理结束 " + DateUtil.getCurrentDateTime());

		if (CollectionUtils.isEmpty(listE)) {
			return;
		}

		// 画面导入及新增修改时，处理数据库中存在的删除数据
		if (StringUtil.equals(type, DictCodeListExt2.数据来源_EXCEL导入.getValue())
				|| StringUtil.equals(type, DictCodeListExt2.数据来源_系统新建.getValue())) {
			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listE);

				investorsHoldInfo001Mapper.handleInfoDeleteDataNew();
			}else{
				investorsHoldInfoDistinctService.handleStatusDeleteData(listE);
			}
		}

		// 查询导入文件中的数据在数据库中已经存在的数据
		logger.info("selectAllHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
//		final Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = selectAllHoldInfoData(listE);
		Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = new HashMap<String, InvestorsHoldInfo001Dto>();
		if ((paraDto != null && paraDto.isTodayDataExist())||paraDto==null) {
//			allHoldInfoDataMap = selectAllHoldInfo(listE);
			//modify by liubin start 修改五要素为中债要求的五要素
//		    allHoldInfoDataMap = selectAllHoldInfoDataRegCodeKey(listE);
			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				allHoldInfoDataMap = selectAllHoldInfoDataForTmp();
			}else{
				allHoldInfoDataMap = selectAllHoldInfoDataMutiThread(listE);
			}
		    //modify by liubin end
		}
		logger.info("selectAllHoldInfoData结束 " + DateUtil.getCurrentDateTime());

		// 查询导入文件中的数据在数据库中报送表已经存在的数据
		logger.info("selectAllSubHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
		Map<String, ChbInvestholdInfoSub> allSubHoldInfoDataMap = new HashMap<String, ChbInvestholdInfoSub>();
		// 维护表中没有数据的情况，登记表肯定没有数据，就不用再查询了
		if (allHoldInfoDataMap.size() > 0) {
			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				allSubHoldInfoDataMap = selectAllSubHoldInfoDataForTmp();
			}else{
				allSubHoldInfoDataMap = selectAllSubHoldInfoData(listE);
			}
		}
		logger.info("selectAllSubHoldInfoData结束 " + DateUtil.getCurrentDateTime());

		// 存在维护表数据
		// 更新操作
		List<InvestorsHoldInfo001Dto> listUp = new ArrayList<>();
		// 更新操作
		List<ChbInvestholdInfoSub> subListUpdate = new ArrayList<>();

		logger.info("登记表数据整理处理开始 " + DateUtil.getCurrentDateTime());
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
		//对比临时dto
		ChbInvestholdInfoSub chbInvestholdInfoSubTemp = null;
		//获取多个序列 add by yanya on 20210520
		List<String> sequenceList = null;
		if(!"001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE")) && listE != null){
			sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, listE.size());
		}
		//当前序列的数标 add by yanya on 20210520
		int index = 0;
		for (InvestorsHoldInfo001Dto action : listE) {
			// 数据来源
			action.setDataSource(type);
			// 用来区分的key(登记表用)
			String dataKey = action.getRegistBankCode() + GlobalConstant.UNDER_LINE 
				   	+ action.getIndentifyMark()+ GlobalConstant.UNDER_LINE 
				   	//+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE
					+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE 
					+ action.getCcy()+ GlobalConstant.UNDER_LINE 
					+ DateUtil.format(action.getHoldDate());
			//数据对比，当无数据变化时，跳过不对登记表进行处理  begin
			chbInvestholdInfoSubTemp = allSubHoldInfoDataMap.get(dataKey+GlobalConstant.UNDER_LINE+DictCodeListExt2.报送状态_接口报送.getValue());
			if(chbInvestholdInfoSubTemp == null){
				chbInvestholdInfoSubTemp = allSubHoldInfoDataMap.get(dataKey+GlobalConstant.UNDER_LINE+DictCodeListExt2.报送状态_手工处理.getValue());
			}
			if(chbInvestholdInfoSubTemp != null && action.getAmount().compareTo(chbInvestholdInfoSubTemp.getAmount()) == 0
					&& action.getQty().compareTo(chbInvestholdInfoSubTemp.getQty()) == 0
					&& action.getCnyAmt().compareTo(chbInvestholdInfoSubTemp.getCnyAmt()) == 0){
				continue;
			}
			ChbInvestholdInfoSub chbInvestholdInfoSub = new ChbInvestholdInfoSub();
			BeanUtil.copyPropertiesIgnoreNull(chbInvestholdInfoSub, action);
			// 检查序列号设置初始值
			chbInvestholdInfoSub.setCheckSeqno("0");
			action.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
			 
			String subStatus = StringUtil.EMPTY;
			chbInvestholdInfoSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
			chbInvestholdInfoSub.setStatusDate(new Date());
			if (allHoldInfoDataMap.containsKey(dataKey)) {
				listUp.add(action);
			} else {
				listIn.add(action);
			}
			// add by zcw 持有不生成修改未报送数据 注释该逻辑 start
//			if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_02)
//					|| allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_03)) {
//				subStatus = DictCodeListExt2.报送数据状态_新建.getValue();
//			} else {
//				subStatus = DictCodeListExt2.报送数据状态_新建.getValue();
//			}
			// 数据状态
			chbInvestholdInfoSub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
			// add by zcw 持有不生成修改未报送数据 注释该逻辑 end
			// 产品代码
			chbInvestholdInfoSub.setProdid(action.getProdid());
			if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
				chbInvestholdInfoSub.setChbSubmitDeadline(DateCalcUtil.getEndOfDay(DateUtil.addDay(chbInvestholdInfoSub.getHoldDate(), 1)));
				chbInvestholdInfoSub.setSubmitDeadline(DateCalcUtil.getEndOfDay(DateUtil.addDay(chbInvestholdInfoSub.getHoldDate(), 1)));
			}else{
			// 当前批次报送时点集合
			List<Date> dateList = null;
			// 报送时点通用方法
			dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者持有信息登记.getValue(), subStatus, dateMap,
					chbInvestholdInfoSub.getHoldDate());
			if (CollectionUtils.isNotEmpty(dateList)) {
				chbInvestholdInfoSub.setChbSubmitDeadline(dateList.get(0));
				chbInvestholdInfoSub.setSubmitDeadline(dateList.get(1));
			}
			}
			if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01)) {
				chbInvestholdInfoSub.setRegistSeqno(allSubHoldInfoDataMap
						.get(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01).getRegistSeqno());

				// add by etliu 20180829 start
				chbInvestholdInfoSub.setSumitSeq(chbInvestholdInfoSub.getRegistSeqno());

				// add by etliu 20180829 end
				subListUpdate.add(chbInvestholdInfoSub);
			} else {
				if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
					chbInvestholdInfoSub.setRegistSeqno(getId());
				}else{
				chbInvestholdInfoSub.setRegistSeqno(//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
						CommonUtil.getSequenceStr(sequenceList, index)
						);
				index++;
				}
				// add by etliu 20180829 start
				chbInvestholdInfoSub.setSumitSeq(chbInvestholdInfoSub.getRegistSeqno());

				// add by etliu 20180829 end
				subListInsert.add(chbInvestholdInfoSub);
			}
		}
		logger.info("登记表数据整理处理结束 " + DateUtil.getCurrentDateTime());
		// 更新数据库
		if (subListUpdate != null && subListUpdate.size() > 0) {

			// 更新登记表信息
			logger.info("更新登记表信息处理开始 " + DateUtil.getCurrentDateTime());
			insertChbSubHoldInfoDataByPart(subListUpdate);
			logger.info("更新登记表信息处理结束 " + DateUtil.getCurrentDateTime());

		}
		if (listUp != null && listUp.size() > 0) {
		    logger.info("insertChbHoldInfoDataByPart处理开始 " + DateUtil.getCurrentDateTime());
			if(listUp.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				// 先删后插
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listUp);
				investorsHoldInfoDistinctService.deleteChbInvestholdInfoByTmp();
				// 插入
				investorsHoldInfoDistinctService.saveChbInvestholdInfo(listUp);
			}else{
				investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listUp);
			}
			logger.info("insertChbHoldInfoDataByPart处理结束  " + DateUtil.getCurrentDateTime());
		}
		chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
	}
	/**
	 * 对于校验通过的数据进行后续处理 <功能详细描述>
	 *
	 * @param listE
	 *            校验通过数据
	 * @param type
	 *            操作的类型
	 * @param subTable
	 *            子表
	 * @param table
	 *            表
	 * @param tmpPath
	 *            临时路径
	 * @param paraDto
     *            投资者持有信息上游接口接入多线程处理，可以事先查询的内容dto
	 * @see
	 * @since 1.0
	 */
    public void afterCheckDealNew(List<InvestorsHoldInfo001Dto> listE, List<InvestorsHoldInfo001Dto> listA, String type,
								  List<InvestorsHoldInfo001Dto> listIn, List<ChbInvestholdInfoSub> subListInsert,
								  InvestorsHoldInfo001FileReadParaDto paraDto,List<ChbInvestholdInfoCheckDto> mergelist) {

	    boolean isDB2DataBase = CommonUtil.isDB2DataBase();

	    logger.info("afterCheckDeal待维护数据插入数据库处理开始 " + DateUtil.getCurrentDateTime());
	    
	    Map<String, ChbInvestholdInfoCheckDto> allMergedMap =null;
	    
		// 待维护数据插入数据库
		if (CollectionUtils.isNotEmpty(listA)) {
		    
		    allMergedMap=  mergelist.stream().collect(JoyinCollectors.toMap(x -> x.getRegistBankCode()
				+ GlobalConstant.UNDER_LINE + x.getIndentifyMark() + GlobalConstant.UNDER_LINE
				+ x.getPrdRegistCode()  + GlobalConstant.UNDER_LINE + x.getCcy() + GlobalConstant.UNDER_LINE
				+ DateUtil.format(x.getHoldDate()), x -> x));
 
		    Iterator<InvestorsHoldInfo001Dto> iterator = listA.iterator();
		    
		      while(iterator.hasNext()){
			    InvestorsHoldInfo001Dto action = iterator.next();
			    //如果是识别标识合并的地方有了，则剔除。
			    String actionKey = action.getRegistBankCode() + GlobalConstant.UNDER_LINE
					+ action.getIndentifyMark()+ GlobalConstant.UNDER_LINE 
					+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE
					+ action.getCcy()+ GlobalConstant.UNDER_LINE 
					+ DateUtil.format(action.getHoldDate());
			    
			    if(allMergedMap.containsKey(actionKey)){
				iterator.remove();
			    }else{
			    action.setDataSource(type);
			    action.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
			    }
			}


			if(!StringUtil.isEmptyList(listA) && listA.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				// 先删后插
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listA);
				investorsHoldInfoDistinctService.deleteChbInvestholdInfoByTmp();
				// 插入
				investorsHoldInfoDistinctService.saveChbInvestholdInfo(listA);
			}else{
				investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listA);
			}
		}
		logger.info("afterCheckDeal待维护数据插入数据库处理结束 " + DateUtil.getCurrentDateTime());

		if (CollectionUtils.isEmpty(listE)) {
			return;
		}

		// 画面导入及新增修改时，处理数据库中存在的删除数据
		if (StringUtil.equals(type, DictCodeListExt2.数据来源_EXCEL导入.getValue())
		    || StringUtil.equals(type, DictCodeListExt2.数据来源_系统新建.getValue())) {

			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listE);

				investorsHoldInfo001Mapper.handleInfoDeleteDataNew();
				investorsHoldInfo001Mapper.handleSubDeleteDataNew();
			}else{
				investorsHoldInfoDistinctService.handleStatusDeleteData(listE);
			}
		}

		// 查询导入文件中的数据在数据库中已经存在的数据
        logger.info("selectAllHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
        Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = new HashMap<String, InvestorsHoldInfo001Dto>();
		if ((paraDto != null && paraDto.isTodayDataExist())||paraDto==null) {
			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				allHoldInfoDataMap = selectAllHoldInfoDataForTmp();
			}else{
				allHoldInfoDataMap = selectAllHoldInfoDataMutiThread(listE);
			}
		}
	    logger.info("selectAllHoldInfoData结束 " + DateUtil.getCurrentDateTime());

		// 查询导入文件中的数据在数据库中报送表已经存在的数据
	    logger.info("selectAllSubHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
		Map<String, ChbInvestholdInfoSub> allSubHoldInfoDataMap = new HashMap<String, ChbInvestholdInfoSub>();
		// 维护表中没有数据的情况，登记表肯定没有数据，就不用再查询了
		if (allHoldInfoDataMap.size() > 0) {
			if(!StringUtil.isEmptyList(listE) && listE.size()>1000){
				allSubHoldInfoDataMap = selectAllSubHoldInfoDataForTmp();
			}else{
				allSubHoldInfoDataMap = selectAllSubHoldInfoData(listE);
			}
		}
	    logger.info("selectAllSubHoldInfoData结束 " + DateUtil.getCurrentDateTime());

		// 存在维护表数据
		// 更新操作
		List<InvestorsHoldInfo001Dto> listUp = new ArrayList<>();
		// 更新操作
		List<ChbInvestholdInfoSub> subListUpdate = new ArrayList<>();

		logger.info("登记表数据整理处理开始 " + DateUtil.getCurrentDateTime());
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();

		List<String> sequenceList = null;
		
		if (listE != null && listE.size() > 0) {
		    sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, listE.size());
		    if (allMergedMap==null){//如果listA初始化过，就不再初始化，否则初始化
			 allMergedMap=  mergelist.stream().collect(JoyinCollectors.toMap(x -> x.getRegistBankCode()
						+ GlobalConstant.UNDER_LINE + x.getIndentifyMark() + GlobalConstant.UNDER_LINE
						+ x.getPrdRegistCode()  + GlobalConstant.UNDER_LINE + x.getCcy() + GlobalConstant.UNDER_LINE
						+ DateUtil.format(x.getHoldDate()), x -> x));
		    }
		}
		int index = 0;

		for (InvestorsHoldInfo001Dto action : listE) {
		   
			// 用来区分的key(登记表用)
			String dataKey = action.getRegistBankCode() + GlobalConstant.UNDER_LINE
				+ action.getIndentifyMark()+ GlobalConstant.UNDER_LINE 
				+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE
				//+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE 
				+ action.getCcy()+ GlobalConstant.UNDER_LINE 
				+ DateUtil.format(action.getHoldDate());
			
			 // 数据来源
		    	action.setDataSource(type);
		    	
		    	if(allMergedMap.containsKey(dataKey)){
		    	    continue;
		    	}
		    	
			//数据对比，当无数据变化时，跳过不对登记表进行处理  begin
			ChbInvestholdInfoSub chbInvestholdInfoSubTemp = allSubHoldInfoDataMap.get(dataKey+GlobalConstant.UNDER_LINE+DictCodeListExt2.报送状态_接口报送.getValue());
			if(chbInvestholdInfoSubTemp == null){
				chbInvestholdInfoSubTemp = allSubHoldInfoDataMap.get(dataKey+GlobalConstant.UNDER_LINE+DictCodeListExt2.报送状态_手工处理.getValue());
			}
			if(chbInvestholdInfoSubTemp != null && action.getAmount().compareTo(chbInvestholdInfoSubTemp.getAmount()) == 0
					&& action.getQty().compareTo(chbInvestholdInfoSubTemp.getQty()) == 0
					&& action.getCnyAmt().compareTo(chbInvestholdInfoSubTemp.getCnyAmt()) == 0){
				continue;
			}
 
			ChbInvestholdInfoSub chbInvestholdInfoSub = new ChbInvestholdInfoSub();
			BeanUtil.copyPropertiesIgnoreNull(chbInvestholdInfoSub, action);
			// 检查序列号设置初始值
			chbInvestholdInfoSub.setCheckSeqno("0");
			action.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
			 
			String subStatus = StringUtil.EMPTY;
			chbInvestholdInfoSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
			chbInvestholdInfoSub.setStatusDate(new Date());
			if (allHoldInfoDataMap.containsKey(dataKey)) {
				listUp.add(action);
			} else {
				listIn.add(action);
			}
			// add by zcw 持有不生成修改未报送数据 注释该逻辑 start
//			if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_02)
//					|| allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_03)) {
//				subStatus = DictCodeListExt2.报送数据状态_修改.getValue();
//			} else {
//				subStatus = DictCodeListExt2.报送数据状态_新建.getValue();
//			}
			// 数据状态
			chbInvestholdInfoSub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
			// add by zcw 持有不生成修改未报送数据 注释该逻辑 end

			// 产品代码
			chbInvestholdInfoSub.setProdid(action.getProdid());
			if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
				chbInvestholdInfoSub.setChbSubmitDeadline(DateCalcUtil.getEndOfDay(DateUtil.addDay(chbInvestholdInfoSub.getHoldDate(), 1)));
				chbInvestholdInfoSub.setSubmitDeadline(DateCalcUtil.getEndOfDay(DateUtil.addDay(chbInvestholdInfoSub.getHoldDate(), 1)));
			}else{
			// 当前批次报送时点集合
			List<Date> dateList = null;
			// 报送时点通用方法
			dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者持有信息登记.getValue(), subStatus, dateMap,
					chbInvestholdInfoSub.getHoldDate());
			if (CollectionUtils.isNotEmpty(dateList)) {
				chbInvestholdInfoSub.setChbSubmitDeadline(dateList.get(0));
				chbInvestholdInfoSub.setSubmitDeadline(dateList.get(1));
			}
			}
			if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01)) {
				chbInvestholdInfoSub.setRegistSeqno(allSubHoldInfoDataMap
						.get(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01).getRegistSeqno());

				// add by etliu 20180829 start
				chbInvestholdInfoSub.setSumitSeq(chbInvestholdInfoSub.getRegistSeqno());

				// add by etliu 20180829 end
				subListUpdate.add(chbInvestholdInfoSub);
			} else {
				if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
					chbInvestholdInfoSub.setRegistSeqno(getId());
				}else{
				chbInvestholdInfoSub.setRegistSeqno(//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
						CommonUtil.getSequenceStr(sequenceList, index)
						);
				index++;
				}
				chbInvestholdInfoSub.setSumitSeq(chbInvestholdInfoSub.getRegistSeqno());

				subListInsert.add(chbInvestholdInfoSub);
				}
			}
		logger.info("登记表数据整理处理结束 " + DateUtil.getCurrentDateTime());
		// 更新数据库
		if (subListUpdate != null && subListUpdate.size() > 0) {
			logger.info("更新登记表信息处理开始 " + DateUtil.getCurrentDateTime());
			insertChbSubHoldInfoDataByPart(subListUpdate);
			logger.info("更新登记表信息处理结束 " + DateUtil.getCurrentDateTime());
 
		}
		if (listUp != null && listUp.size() > 0) {
			logger.info("insertChbHoldInfoDataByPart处理开始 " + DateUtil.getCurrentDateTime());
			if(listUp.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				// 先删后插
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpByInfo(listUp);
				investorsHoldInfoDistinctService.deleteChbInvestholdInfoByTmp();
				// 插入
				investorsHoldInfoDistinctService.saveChbInvestholdInfo(listUp);
			}else{
				investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listUp);
			}
			logger.info("insertChbHoldInfoDataByPart处理结束  " + DateUtil.getCurrentDateTime());
		}
		chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
	}

	/**
	 *
	 * 比较修改数据是否发生变化
	 *
	 * @param dto
	 * @return
	 * @see
	 * @since 1.0
	 */
	@SuppressWarnings("unused")
	private boolean queryInvestHoldInfoSub(ChbInvestholdInfoSub dto) {
		ChbInvestholdInfoSub sub = investorsHoldInfo001Mapper.selectSendDataToCompare(dto);
		if (sub != null && sub.getAmount().compareTo(dto.getAmount()) == 0
				&& sub.getCnyAmt().compareTo(dto.getCnyAmt()) == 0 && sub.getQty().compareTo(dto.getQty()) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据最新导入的有效的产品登记编码数据，联动处理持有者信息待处理数据
	 *
	 * @param prdresistCodeList
	 *            最新导入的有效的产品登记编码数据
	 * @see
	 * @since 1.0
	 */@Transactional(rollbackFor = Exception.class)
	public void refreshStatusAData(List<ChbPrdresistCode> prdresistCodeList,String bankCode,String deptCode,String orgCode) {
		if (StringUtil.isEmptyList(prdresistCodeList)) {
			return;
		}

		for (ChbPrdresistCode chbPrdresistCode : prdresistCodeList) {
			logger.info("checkDataInfo处理"+chbPrdresistCode.getProdid()+"开始 " + DateUtil.getCurrentDateTime());
			// 根据产品代码和机构代码，查询持有者信息有效数据
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("prodId", chbPrdresistCode.getProdid());
			paramMap1.put("orgCode", orgCode);
			paramMap1.put("regCode", chbPrdresistCode.getRegCode());
			//修改重新更新有效的产品登记编码代码 start
//			List<InvestorsHoldInfo001Dto> statusAInvestholdInfoList1 = investorsHoldInfo001Mapper
//					.selectStatusEInvestholdInfoData(paramMap1);
//			if (!StringUtil.isEmptyList(statusAInvestholdInfoList1)
//					&& "01".equals(SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_REP_IS_COVER))) {
//				// 将产品登记编码设值并转化为List<Map<String, String>>
//				// List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
//				for (ChbInvestholdInfo chbInvestholdInfo : statusAInvestholdInfoList1) {
//					chbInvestholdInfo.setPrdRegistCode(chbPrdresistCode.getRegCode());
//				}
//
//				// List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
//				// List<InvestorsHoldInfo001Dto> listA = new ArrayList<>();
//
//				// 数据检查
//				// checkDataInfo(dataMapList, false, listE, listA, null, null);
//
//				// 入库处理
//				this.refreshStatusEDataToDb(statusAInvestholdInfoList1);
//				this.refreshStatusEDataToDb1(statusAInvestholdInfoList1);
//			}
			if ("01".equals(SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_REP_IS_COVER))) {
				investorsHoldInfo001Mapper.updateInfoRegCodeByProdid(paramMap1);
				investorsHoldInfo001Mapper.updateRegistRegCodeByProdid(paramMap1);
			}
			//修改重新更新有效的产品登记编码代码 end
			// 根据产品代码和机构代码，查询持有者信息待处理数据（待处理，且产品登记编码为空）
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("prodId", chbPrdresistCode.getProdid());
			paramMap.put("orgCode", orgCode);
			paramMap.put("isCover", SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_REP_IS_COVER));
			List<ChbInvestholdInfo> statusAInvestholdInfoList = investorsHoldInfo001Mapper
					.selectStatusAInvestholdInfoData(paramMap);

			if (!StringUtil.isEmptyList(statusAInvestholdInfoList)) {
				// 将产品登记编码设值并转化为List<Map<String, String>>
				List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
				for (ChbInvestholdInfo chbInvestholdInfo : statusAInvestholdInfoList) {
					chbInvestholdInfo.setPrdRegistCode(chbPrdresistCode.getRegCode());

					dataMapList.add(this.dtoToMap(chbInvestholdInfo));
				}

				List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
				List<InvestorsHoldInfo001Dto> listA = new ArrayList<>();

				// 数据检查
				checkDataInfo(dataMapList, false, listE, listA, null, null,bankCode,deptCode,orgCode);

				// 入库处理
				this.refreshStatusADataToDb(listE, listA);
			}
			logger.info("checkDataInfo处理"+chbPrdresistCode.getProdid()+"结束 " + DateUtil.getCurrentDateTime());
		}
	}

	/**
	 * 根据最新导入的有效的产品登记编码数据，联动处理持有者信息待处理数据 入库处理
	 *
	 * @param listE
	 *            校验通过数据
	 * @param listA
	 *            校验不通过数据
	 * @see
	 * @since 1.0
	 */
	private void refreshStatusADataToDb(List<InvestorsHoldInfo001Dto> listE, List<InvestorsHoldInfo001Dto> listA) {
		// 待维护数据插入数据库
		if (CollectionUtils.isEmpty(listE) && CollectionUtils.isEmpty(listA)) {
			return;
		}

		if (!StringUtil.isEmptyList(listA)) {
			this.updateChbInvestholdInfoDataByPart(listA, listA.get(0).getPrdRegistCode(),
					DictCodeListExt1.维护数据状态_待处理.getValue());
		}

		if (!StringUtil.isEmptyList(listE)) {
			this.updateChbInvestholdInfoDataByPart(listE, listE.get(0).getPrdRegistCode(),
					DictCodeListExt1.维护数据状态_有效.getValue());

			// 处理登记数据
			// 查询导入文件中的数据在数据库中报送表已经存在的数据
			  Map<String, ChbInvestholdInfoSub> allSubHoldInfoDataMap = selectAllSubHoldInfoData(listE);
			// 插入操作
			List<ChbInvestholdInfoSub> insertSubList = new ArrayList<>();
			// 更新操作
			List<ChbInvestholdInfoSub> updateSubList = new ArrayList<>();
			Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
			//增加批量序列 modify by yanya on 20210520
			List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, listE.size());
			int index = 0;
			for (InvestorsHoldInfo001Dto action : listE) {
				// 数据来源
				action.setDataSource(DictCodeListExt2.数据来源_系统新建.getValue());
				// 用来区分的key(登记表用)
				String dataKey = action.getRegistBankCode() + GlobalConstant.UNDER_LINE 
					+ action.getIndentifyMark()+ GlobalConstant.UNDER_LINE 
					//+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE
					+ action.getPrdRegistCode() + GlobalConstant.UNDER_LINE 
					+ action.getCcy()+ GlobalConstant.UNDER_LINE 
					+ DateUtil.format(action.getHoldDate());

				ChbInvestholdInfoSub chbInvestholdInfoSub = new ChbInvestholdInfoSub();
				BeanUtil.copyPropertiesIgnoreNull(chbInvestholdInfoSub, action);
				action.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
				String subStatus = StringUtil.EMPTY;
				chbInvestholdInfoSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
				chbInvestholdInfoSub.setStatusDate(new Date());
				if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_02)
						|| allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_03)) {
					subStatus = DictCodeListExt2.报送数据状态_修改.getValue();
				} else {
					subStatus = DictCodeListExt2.报送数据状态_新建.getValue();
				}
				// 数据状态
				chbInvestholdInfoSub.setStatus(subStatus);
				// 当前批次报送时点集合
				List<Date> dateList = null;
				// 报送时点通用方法
				dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者持有信息登记.getValue(), subStatus,
						dateMap, chbInvestholdInfoSub.getHoldDate());
				if (CollectionUtils.isNotEmpty(dateList)) {
					chbInvestholdInfoSub.setChbSubmitDeadline(dateList.get(0));
					chbInvestholdInfoSub.setSubmitDeadline(dateList.get(1));
				}
				if (allSubHoldInfoDataMap.containsKey(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01)) {
					chbInvestholdInfoSub.setRegistSeqno(allSubHoldInfoDataMap
							.get(dataKey + GlobalConstant.UNDER_LINE + SEND_STATUS_01).getRegistSeqno());
					updateSubList.add(chbInvestholdInfoSub);
				} else {
					chbInvestholdInfoSub.setRegistSeqno(//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
							CommonUtil.getSequenceStr(sequenceList, index)
							);
					index++;
					insertSubList.add(chbInvestholdInfoSub);
				}
			}

			// 登记表入库
			if (insertSubList != null && insertSubList.size() > 0) {
				insertChbSubHoldInfoDataByPart(insertSubList);
			}
			if (updateSubList != null && updateSubList.size() > 0) {
				insertChbSubHoldInfoDataByPart(updateSubList);
			}
		}
	}

	/**
	 * 更新投资者持有信息维护表的产品登记编码和数据状态字段
	 *
	 * @param dataList
	 *            数据列表
	 * @see
	 * @since 1.0
	 */
	private void updateChbInvestholdInfoDataByPart(List<InvestorsHoldInfo001Dto> dataList, String prdRegistCode,
												   String statusToUpdate) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		if (maxDataCountOneTime < 1) {
			maxDataCountOneTime = 1000;
		}
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.updateChbInvestholdInfoData(dataList, prdRegistCode, statusToUpdate);
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("更新updateChbInvestholdInfoData开始"+DateUtil.getCurrentDateTime());
			investorsHoldInfo001Mapper.updateChbInvestholdInfoData(newDataList.subList(0, maxDataCountOneTime),
					prdRegistCode, statusToUpdate);
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("更新updateChbInvestholdInfoData结束"+DateUtil.getCurrentDateTime());
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("更新updateChbInvestholdInfoData开始"+DateUtil.getCurrentDateTime());
			investorsHoldInfo001Mapper.updateChbInvestholdInfoData(newDataList, prdRegistCode, statusToUpdate);
			logger.info("更新updateChbInvestholdInfoData结束"+DateUtil.getCurrentDateTime());
		}
	}

	/**
	 * dto转化为map
	 *
	 * @param chbInvestholdInfo
	 *            dto
	 * @return map
	 * @see
	 * @since 1.0
	 */
	private Map<String, String> dtoToMap(ChbInvestholdInfo chbInvestholdInfo) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (chbInvestholdInfo == null) {
			return dataMap;
		}

		dataMap.put("REGIST_BANK_CODE", chbInvestholdInfo.getRegistBankCode());
		dataMap.put("INDENTIFY_MARK", chbInvestholdInfo.getIndentifyMark());
		dataMap.put("PRD_REGIST_CODE", chbInvestholdInfo.getPrdRegistCode());
		dataMap.put("HOLD_DATE", DateUtil.format(chbInvestholdInfo.getHoldDate()));
		dataMap.put("CCY", chbInvestholdInfo.getCcy());
		if (chbInvestholdInfo.getAmount() != null) {
			dataMap.put("AMOUNT", chbInvestholdInfo.getAmount().toString());
		}
		if (chbInvestholdInfo.getQty() != null) {
			dataMap.put("QTY", chbInvestholdInfo.getQty().toString());
		}
		if (chbInvestholdInfo.getCnyAmt() != null) {
			dataMap.put("CNY_AMT", chbInvestholdInfo.getCnyAmt().toString());
		}
		dataMap.put("ORG_CODE", chbInvestholdInfo.getOrgCode());
		// add by etliu 20181017 start
		dataMap.put("DEPT_CODE", chbInvestholdInfo.getDeptCode());
		// add by etliu 20181017 end
		dataMap.put("AGENT", chbInvestholdInfo.getAgent());
		dataMap.put("ACCOUNT", chbInvestholdInfo.getProdid());

		return dataMap;
	}

	/**
	 * 分批登记数据插入数据库 <功能详细描述>
	 *
	 * @param subList
	 *            待处理数据
	 * @see
	 * @since 1.0
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void insertChbSubHoldInfoDataByPart(List<ChbInvestholdInfoSub> subList) {
		 //如果反馈状态是通过，修改持有信息时，且数据没有任何变化，则登记表不生成未报送数据
//		List<ChbInvestholdInfoSub> subListNew = new ArrayList<ChbInvestholdInfoSub>();
//		for(ChbInvestholdInfoSub sub : subList){
//			if("02".equals(sub.getStatus())){
//				subListNew.add(sub);
//			}
//		}
//

		List<ChbInvestholdInfoSub> subListNew = subList.stream().filter(sub -> "02".equals(sub.getStatus())).collect(Collectors.toList());


		if (CollectionUtils.isNotEmpty(subListNew)) {
			List<InvestorsHoldInfoRegist001Dto> submitFeedbackStatusList = this.getSubmitFeedbackStatusList(subListNew);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			if (CollectionUtils.isNotEmpty(submitFeedbackStatusList)) {
				for (InvestorsHoldInfoRegist001Dto action : submitFeedbackStatusList) {
					if ("03".equals(action.getSubmitFeedbackStatus())) {
						if (CollectionUtils.isNotEmpty(subList)) {
							Iterator<ChbInvestholdInfoSub> it = subList.iterator();
							while (it.hasNext()) {
								ChbInvestholdInfoSub dto = it.next();
								if (dto.getRegistBankCode().equals(action.getRegistBankCode())
										&& dto.getProdid().equals(action.getProdid())
										&& dto.getCcy().equals(action.getCcy())
										&& dto.getIndentifyMark().equals(action.getIndentifyMark())
										&& sdf.format(dto.getHoldDate()).equals(sdf.format(action.getHoldDate()))
										&& (dto.getAmount().compareTo(action.getAmount()) == 0)
										&& (dto.getQty().compareTo(action.getQty()) == 0)
										&& (dto.getCnyAmt().compareTo(action.getCnyAmt()) == 0)) {
									it.remove();
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(subList)) {
			if(subList.size()>1000){
				chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
				// 先删后插
				investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpBySub(subList);

				investorsHoldInfoDistinctService.deleteChbInvestholdInfoSubByTmp();
				// 插入
				investorsHoldInfoDistinctService.saveChbInvestholdInfoSub(subList);
			}else {
				int maxDataCountOneTime = this.parsToInt(reportInsertLongCount, "1");
				int dataCount = subList.size();
				if (dataCount <= maxDataCountOneTime) {
					logger.info("单次执行insertChbSubHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
					investorsHoldInfoDistinctService.insertChbSubHoldInfoData(subList);
					logger.info("单次执行insertChbSubHoldInfoData处理结束 " + DateUtil.getCurrentDateTime());
					return;
				}

				List<ChbInvestholdInfoSub> newDataList = new ArrayList<ChbInvestholdInfoSub>();
				newDataList.addAll(subList);

				// 分批处理
				int part = dataCount / maxDataCountOneTime; // 分批数
				for (int i = 0; i < part; i++) {
					logger.info("单次执行insertChbSubHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
					investorsHoldInfoDistinctService.insertChbSubHoldInfoData(newDataList.subList(0, maxDataCountOneTime));
					newDataList.subList(0, maxDataCountOneTime).clear();
					logger.info("单次执行insertChbSubHoldInfoData处理结束 " + DateUtil.getCurrentDateTime());
				}
				// 最后剩下的数据
				if (CollectionUtils.isNotEmpty(newDataList)) {
					logger.info("单次执行insertChbSubHoldInfoData处理开始 " + DateUtil.getCurrentDateTime());
					investorsHoldInfoDistinctService.insertChbSubHoldInfoData(newDataList);
					logger.info("单次执行insertChbSubHoldInfoData处理结束 " + DateUtil.getCurrentDateTime());
				}
			}
		}
	}

	/**
	 * 查询维护表中存在的相关数据 <功能详细描述>
	 *
	 * @param listE
	 *            数据集合
	 * @return 匹配的数据
	 * @see
	 * @since 1.0
	 */
	private Map<String, ChbInvestholdInfoSub> selectAllSubHoldInfoData(List<InvestorsHoldInfo001Dto> listE) {
		// 查询导入文件中的数据在数据库中已经存在的数据
		List<ChbInvestholdInfoSub> allDataList = new ArrayList<ChbInvestholdInfoSub>();

		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
//		if (maxDataCountOneTime < 1) {
//			maxDataCountOneTime = 1000;
//		}
		int dataCount = listE.size();
		if (dataCount <= maxDataCountOneTime) {
			allDataList = investorsHoldInfo001Mapper.selectAllSubHoldInfoData(listE);
		} else {
			List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
			newKeyList.addAll(listE);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				logger.info("查询selectAllSubHoldInfoData开始"+DateUtil.getCurrentDateTime());
				allDataList.addAll(investorsHoldInfo001Mapper
						.selectAllSubHoldInfoData(newKeyList.subList(0, maxDataCountOneTime)));
				newKeyList.subList(0, maxDataCountOneTime).clear();
				logger.info("查询selectAllSubHoldInfoData结束"+DateUtil.getCurrentDateTime());
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				logger.info("查询selectAllSubHoldInfoData开始"+DateUtil.getCurrentDateTime());
				allDataList.addAll(investorsHoldInfo001Mapper.selectAllSubHoldInfoData(newKeyList));
				logger.info("查询selectAllSubHoldInfoData结束"+DateUtil.getCurrentDateTime());
			}
		}

		Map<String, ChbInvestholdInfoSub> allHoldInfoDataMap = StringUtil.isEmptyList(allDataList)
				? new HashMap<String, ChbInvestholdInfoSub>()
				: allDataList.stream()
						.collect(JoyinCollectors.toMap(x -> x.getRegistBankCode() + GlobalConstant.UNDER_LINE
								+ x.getIndentifyMark() + GlobalConstant.UNDER_LINE 
								//+ x.getPrdRegistCode()+ GlobalConstant.UNDER_LINE 
								+ x.getPrdRegistCode() + GlobalConstant.UNDER_LINE
								+ x.getCcy() + GlobalConstant.UNDER_LINE
								+ DateUtil.format(x.getHoldDate())+ GlobalConstant.UNDER_LINE
								+ x.getSendStatus(), x -> x));

		return allHoldInfoDataMap;
	}

	private Map<String, ChbInvestholdInfoSub> selectAllSubHoldInfoDataForTmp() {
		List<ChbInvestholdInfoSub> allDataList =investorsHoldInfo001Mapper.selectAllSubHoldInfoDataNew();

		Map<String, ChbInvestholdInfoSub> allHoldInfoDataMap = StringUtil.isEmptyList(allDataList)
				? new HashMap<String, ChbInvestholdInfoSub>()
				: allDataList.stream()
				.collect(JoyinCollectors.toMap(x -> x.getRegistBankCode() + GlobalConstant.UNDER_LINE
						+ x.getIndentifyMark() + GlobalConstant.UNDER_LINE
						//+ x.getPrdRegistCode()+ GlobalConstant.UNDER_LINE
						+ x.getPrdRegistCode() + GlobalConstant.UNDER_LINE
						+ x.getCcy() + GlobalConstant.UNDER_LINE
						+ DateUtil.format(x.getHoldDate())+ GlobalConstant.UNDER_LINE
						+ x.getSendStatus(), x -> x));

		return allHoldInfoDataMap;
	}

	/**
	 *  多线程查询 50最优
	 * @param list
	 * @return
	 */
	private Map<String, InvestorsHoldInfo001Dto> selectAllHoldInfoDataForTmp() {

		List<InvestorsHoldInfo001Dto> resultList = investorsHoldInfo001Mapper.selectAllHoldInfoDataNew() ;

		Map<String, InvestorsHoldInfo001Dto> resMap = StringUtil.isEmptyList(resultList)
				? new HashMap<String, InvestorsHoldInfo001Dto>()
				: resultList.stream().collect(JoyinCollectors.toMap(
				x -> x.getRegistBankCode() + GlobalConstant.UNDER_LINE
						+ x.getIndentifyMark()+ GlobalConstant.UNDER_LINE
						+ x.getPrdRegistCode() + GlobalConstant.UNDER_LINE
						+ x.getCcy() + GlobalConstant.UNDER_LINE
						+ DateUtil.format(x.getHoldDate()),
				x -> x));

		resultList.clear();
		return resMap;
	}

	/**
	 *  多线程查询 50最优
	 * @param list
	 * @return
	 */
	private Map<String, InvestorsHoldInfo001Dto> selectAllHoldInfoDataMutiThread(List<InvestorsHoldInfo001Dto> list) {

		List<InvestorsHoldInfo001Dto> resultList =
					Collections.synchronizedList(new ArrayList<InvestorsHoldInfo001Dto>());
		
		if (null != list && !list.isEmpty()) {

			List<Callable<Boolean>> tasks = new ArrayList<>();
			ExecutorService service = new JoyinThreadPoolExecutor(4);
			int start = 0;
			int end = 0;
			int size = list.size();
			int max = (int) Math.ceil((double) size / 50);
			// 信号量
			CountDownLatch countDown = new CountDownLatch(max);

			for (int i = 0; i < max; i++) {
				start = end;

				end = Math.min(size, 50 * (i + 1));

				List<InvestorsHoldInfo001Dto> subList = list.subList(start, end);

				// 提交任务
				tasks.add(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						resultList.addAll(investorsHoldInfo001Mapper.selectAllHoldInfoData(subList));
						countDown.countDown();// 个数-1
						return true;
					}
				});
			}
			// 添加线程等待转换线执行
			try {
				service.invokeAll(tasks);
				countDown.await();
				service.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Map<String, InvestorsHoldInfo001Dto> resMap = StringUtil.isEmptyList(resultList)
				? new HashMap<String, InvestorsHoldInfo001Dto>()
				: resultList.stream().collect(JoyinCollectors.toMap(
								x -> x.getRegistBankCode() + GlobalConstant.UNDER_LINE 
								+ x.getIndentifyMark()+ GlobalConstant.UNDER_LINE 
								+ x.getPrdRegistCode() + GlobalConstant.UNDER_LINE
								+ x.getCcy() + GlobalConstant.UNDER_LINE 
								+ DateUtil.format(x.getHoldDate()),
								x -> x));

		resultList.clear();
		return resMap;
	}
	/**
	 * 查询维护表中存在的相关数据 <功能详细描述>
	 *
	 * @param listE
	 *            数据集合
	 * @return 匹配的数据
	 * @see
	 * @since 1.0
	 */
	public Map<String, InvestorsHoldInfo001Dto> selectAllHoldInfoDataRegCodeKey(List<InvestorsHoldInfo001Dto> listE) {
		// 查询导入文件中的数据在数据库中已经存在的数据
		List<InvestorsHoldInfo001Dto> allDataList = new ArrayList<InvestorsHoldInfo001Dto>();

		int maxDataCountOneTime = 50;
//		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
//		if (maxDataCountOneTime < 1) {
//			maxDataCountOneTime = 1000;
//		}
		int dataCount = listE.size();
		if (dataCount <= maxDataCountOneTime) {
			allDataList.addAll(investorsHoldInfo001Mapper.selectAllHoldInfoData(listE));
		} else {
			List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
			newKeyList.addAll(listE);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				allDataList.addAll(
						investorsHoldInfo001Mapper.selectAllHoldInfoData(newKeyList.subList(0, maxDataCountOneTime)));
				newKeyList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				allDataList.addAll(investorsHoldInfo001Mapper.selectAllHoldInfoData(newKeyList));
			}
		}
		Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = StringUtil.isEmptyList(allDataList)
				? new HashMap<String, InvestorsHoldInfo001Dto>()
				: allDataList.stream().collect(JoyinCollectors.toMap(x -> x.getRegistBankCode()
						+ GlobalConstant.UNDER_LINE + x.getIndentifyMark() + GlobalConstant.UNDER_LINE
						// +x.getPrdRegistCode()+GlobalConstant.UNDER_LINE+x.getPrdRegistCode()
						+ x.getPrdRegistCode()  + GlobalConstant.UNDER_LINE + x.getCcy() + GlobalConstant.UNDER_LINE
						+ DateUtil.format(x.getHoldDate()), x -> x));
				allDataList.clear();	
		return allHoldInfoDataMap;
	}

	/**
	 * 检查数据是否合法 <功能详细描述>
	 *
	 * @param mapList
	 *            待检查数据
	 * @param isExcel
	 *            区分数据源
	 * @param listE
	 *            用来存储数据（检查通过的容器）
	 * @param listA
	 *            用来存储数据（检查不通过待处理的容器）
	 * @param prodRegInfoList
	 *            用来存储数据（系统中不存在的产品登记编码信息）
	 * @param paraDto
	 *            paraDto
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> checkDataInfo(List<Map<String, String>> mapList, boolean isExcel,
											 List<InvestorsHoldInfo001Dto> listE, List<InvestorsHoldInfo001Dto> listA,
											 List<ChbPrdresistCode> prodRegInfoList, InvestorsHoldInfo001FileReadParaDto paraDto,String bankCodeA,String deptCode,String orgCode ) {

		logger.info("checkDataInfo处理开始 " + DateUtil.getCurrentDateTime());
		//add by liubin start 将接口接入的数据输出到日志文件中
		String checkDataLogFilePath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, InterfConstant.SQLLDR_LOG_FILEPATH);
		File file = new SafelyFile(checkDataLogFilePath+File.separator+"check_data_log.txt");
		if(file.exists()){
			file = new SafelyFile(checkDataLogFilePath+File.separator+"check_data_log_"+System.currentTimeMillis()+".txt");
		}
		StringBuffer messageInfo = new StringBuffer();
		//add by liubin end


		List<ExcelValidDto> result = new ArrayList<>();
		if (mapList.isEmpty()) {
			result.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
					this.getMessage("REPORT_COMMON_FILE_E_0016")));
		} else {
			if (isExcel) {
				//判断导入的数据是否存在重复的主键
				for(int i = 0; i < mapList.size(); i++) {
					Map<String, String> checkMapI = mapList.get(i);
					if(StringUtil.isEmpty(checkMapI.get("REGIST_BANK_CODE"))||StringUtil.isEmpty(checkMapI.get("ACCOUNT"))
							||StringUtil.isEmpty(checkMapI.get("INDENTIFY_MARK"))||StringUtil.isEmpty(checkMapI.get("HOLD_DATE"))
							||StringUtil.isEmpty(checkMapI.get("CCY"))){
						continue;
					}
					if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
						if(checkMapI.get("ORG_CODE")!=null) {
							checkMapI.put("ORG_CODE", "Z70024");
						}
					}
					for(int j = i + 1; j< mapList.size(); j++) {
						Map<String, String> checkMapJ = mapList.get(j);
						if(checkMapI.get("REGIST_BANK_CODE").equals(checkMapJ.get("REGIST_BANK_CODE")) && checkMapI.get("ACCOUNT").equals(checkMapJ.get("ACCOUNT"))
								&& checkMapI.get("INDENTIFY_MARK").equals(checkMapJ.get("INDENTIFY_MARK"))
								&& checkMapI.get("HOLD_DATE").equals(checkMapJ.get("HOLD_DATE"))
								&& checkMapI.get("CCY").equals(checkMapJ.get("CCY"))) {
							result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j + 2),
									this.getMessage("REPORT_COMMON_FILE_E_0027", String.valueOf(i + 2)+","+String.valueOf(j + 2))));
							return result;
						}
					}
				}
			}

			String bankCode;
			if (isExcel) {
				bankCode = bankCodeA;
			} else {
				bankCode = "";
			}

			boolean isNeed = true;

			// 登记银行编码和产品登记编码的数据，在上游接口的情况，预先查询好传进来，避免重复查询
			// //获取所有的登记银行编码
			// Map<String,String> registMap =
			// investorsHoldInfo001Mapper.getAllRegistBankCodeSet().stream().collect(JoyinCollectors.toMap(x->x.get("REGIST_BANK_CODE"),
			// x->x.get("ORG_CODE")));
			// //获取所有的产品登记编码
			// Map<String,String> prdRegistMap =
			// investorsHoldInfo001Mapper.getAllRegistProdCodeSet().stream().collect(JoyinCollectors.toMap(x->x.get("PRODID"),
			// x->x.get("REG_CODE")));
			// 获取所有的登记银行编码
			Map<String, String> registMap = new HashMap<String, String>();
			// 获取所有的产品登记编码
			Map<String, String> prdRegistMap = new HashMap<String, String>();
			if (paraDto != null && paraDto.getRegistMap() != null) {
				registMap = paraDto.getRegistMap();
			} else {
				registMap = investorsHoldInfo001Mapper.getAllRegistBankCodeSet().stream()
						.collect(JoyinCollectors.toMap(x -> x.get("REGIST_BANK_CODE"), x -> x.get("ORG_CODE")));
			}
			if (paraDto != null && paraDto.getPrdRegistMap() != null) {
				prdRegistMap = paraDto.getPrdRegistMap();
			} else {
				prdRegistMap = investorsHoldInfo001Mapper.getAllRegistProdCodeSet().stream()
						.collect(JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));
			}

			// //获取所有的有效的投资者
			// Map<String,String> investorMap =
			// investorsHoldInfo001Mapper.getAllInvestorSet().stream().collect(JoyinCollectors.toMap(x->x.get("REGIST_BANK_CODE")+GlobalConstant.VERTICAL_LINE+x.get("INDENTIFY_MARK"),
			// x->x.get("INDENTIFY_MARK")));
			// 汇率mapper
			Map<String, BigDecimal> rateMap = new HashMap<>();
			int rowId = 1;
			// add by wangmin 2018-07-11 start
			// prodRegInfoList若为null，下方synchronized（prodRegInfoList） 方法报错
			if (prodRegInfoList == null) {
				prodRegInfoList = new ArrayList<ChbPrdresistCode>();
			}
			// add by wangmin 2018-07-11 end
			// prodRegInfoList若为null，下方synchronized（prodRegInfoList） 方法报错

			// add by yangxianjiu 2021-12-23 end
			// investorsHoldInfo001Mapper.countInvestorByIndentifyMark(indentifyMark)==0 优化导入
			List<String> allInvestor = new ArrayList<String>();
			if(isExcel){
				allInvestor = investorsHoldInfo001Mapper.selectAllInvestor();
			}
			for (Map<String, String> item : mapList) {
				List<String> message = new ArrayList<>();
				//String checkMess="";
				InvestorsHoldInfo001Dto dto = new InvestorsHoldInfo001Dto();
				String registBankCode = item.get("REGIST_BANK_CODE");
				// 登记银行代码校验
				if (StringUtil.isEmpty(registBankCode)) {
					message.add(this.getMessage("REPORT_COMMON_E_0057", "登记银行代码"));
					isNeed = false;
				}
				// excel中的登记银行代码必须与当前导入人的登记银行代码一致!
				if (isExcel) {
					if (!StringUtil.equals(bankCode, registBankCode)) {
						message.add("登记银行代码必须与当前用户的登记银行代码一致!");
					}
					// add by etliu 20180912 start
					else if (EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, registBankCode)) {
						message.add(this.getMessage("CHB_EQUITYINFO_E_035", "登记银行代码"));
					}
					// add by etliu 20180912 end
					else {
						dto.setRegistBankCode(registBankCode);
						dto.setOrgCode(orgCode);
					}
				}
				// 上游接口 为空或不存在直接移除
				else {
					if (!registMap.containsKey(registBankCode)) {
						isNeed = false;
						message.add("登记银行代码在机构管理中不存在！");
					} else {
						dto.setRegistBankCode(registBankCode);
						if (item.containsKey("ORG_CODE") && StringUtil.isNotEmpty(item.get("ORG_CODE"))) {
							dto.setOrgCode(item.get("ORG_CODE"));
						} else {
							// 未传机构代码 数据字段遗失不保存
							isNeed = false;
						}
					}
				}

				// 识别标识校验
				String indentifyMark = item.get("INDENTIFY_MARK");
				if (StringUtil.isEmpty(indentifyMark)) {
					isNeed = false;
					message.add(this.getMessage("REPORT_COMMON_E_0057", "识别标识"));
				} else if (!Pattern.compile("[^\u4e00-\u9fa5]+").matcher(indentifyMark).matches()) {
					isNeed = false;
					message.add(this.getMessage("CHB_EQUITYINFO_E_012", "识别标识"));
//				} else if (INDENTIFY_MARK.length() > 30) {
				} else if (EquityInfoCheckUtil.getByteLength(indentifyMark) > 30) {
					isNeed = false;
					message.add(this.getMessage("REPORT_COMMON_E_0002", "识别标识", 30));
				}
				//add by  liubin 20181108 start 判断是否是excel导入并且识别标识不存在，直接拦截
				else if(isExcel && (!allInvestor.contains(indentifyMark))){
//				else if(isExcel && (mapInvestor.get(indentifyMark) == null) ){
//				else if(isExcel && (investorsHoldInfo001Mapper.countInvestorByIndentifyMark(indentifyMark)==0)){
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_INDENTIFYMARK"));
				}
				//add by liubin 20181108 end
				// add by etliu 20180912 start
				else if (EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, indentifyMark)) {
					message.add(this.getMessage("CHB_EQUITYINFO_E_035", "识别标识"));
				}
				// add by etliu 20180912 end
				else {
					dto.setIndentifyMark(indentifyMark);
				}
				// //判断系统是否存在该投资者的信息
				// if(StringUtil.isNotEmpty(dto.getRegistBankCode())&&StringUtil.isNotEmpty(dto.getIndentifyMark())&&
				// !investorMap.containsKey(dto.getRegistBankCode()+GlobalConstant.VERTICAL_LINE+dto.getIndentifyMark())){
				// message.add(this.getMessage("REPORT_E_0015", "识别标识"));
				// }
				// 持有日期校验
				String holdDate = item.get("HOLD_DATE");
				//判断持有日期是否符合中债要求yyyy-MM-dd
//				try {
//					SimpleDateFormat df = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT_EN);
//					Date temp = df.parse(HOLD_DATE);
//				} catch (Exception e) {
//					isNeed = false;
//					message.add(this.getMessage("CHB_INVESTHOLD_HOLDDATE_CHECK"));
//				}


				Date currDate = DateUtil.now();//当前日期
				if (StringUtil.isEmpty(holdDate)) {
					isNeed = false;
					message.add(this.getMessage("REPORT_COMMON_E_0057", "持有日期"));
				} else if (holdDate.length() < 8) {
					isNeed = false;
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0013", "持有日期"));
				} else if (!DateUtil.isDateStr(holdDate, DateUtil.DEFAULT_OTHER_FORMAT,
						DateUtil.DEFAULT_ANOTHER_FORMAT, DateUtil.DEFAULT_DATE_FORMAT_EN)) {
					isNeed = false;
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0013", "持有日期"));
				} else if (DateUtil.isDateStr(holdDate, DateUtil.DEFAULT_OTHER_FORMAT)
						&& !isDateE(holdDate, DateUtil.DEFAULT_OTHER_FORMAT)) {
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKHOLDDATE"));
				} else if (DateUtil.isDateStr(holdDate, DateUtil.DEFAULT_ANOTHER_FORMAT)
						&& !isDateE(holdDate, DateUtil.DEFAULT_ANOTHER_FORMAT)) {
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKHOLDDATE"));
				} else if (DateUtil.isDateStr(holdDate, DateUtil.DEFAULT_DATE_FORMAT_EN)
						&& !isDateE(holdDate, DateUtil.DEFAULT_DATE_FORMAT_EN)) {
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKHOLDDATE"));
				}
				// add by etliu 20180912 start
				else if (EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, holdDate)) {
					message.add(this.getMessage("CHB_EQUITYINFO_E_035", "持有日期"));
				}
				// add by etliu 20180912 end
				else if(DateUtil.compareDate(currDate, DateUtil.parse(holdDate))<0){
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKHOLDDATE_2"));
				}else {
					dto.setHoldDate(DateUtil.parse(holdDate));
				}

				// 币种校验
				String ccy = item.get("CCY");
				if (StringUtil.isEmpty(ccy)) {
					isNeed = false;
					message.add(getMessage("REPORT_COMMON_E_0001", "币种"));
				} else {
					Map<String, String> dictCodeMap = SysDataUtil.getSysDictDataMap(DictCode.币种_中债);
					//直接判断
//					if(!dictCodeMap.containsKey(CCY)){
//						isNeed = false;
//						message.add(getMessage("CHBCONNECT_E_0002", "币种"));
//					}else{
//						dto.setCcy(CCY);
//					}
					String[] codeList = StringUtil.split(ccy, StringUtil.SPACE);
					if (!dictCodeMap.containsKey(codeList[0])) {
						isNeed = false;
						message.add(getMessage("CHBCONNECT_E_0002", "币种"));
					}
					// add by etliu 20180912 start
					else if (EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, ccy)) {
						message.add(this.getMessage("CHB_EQUITYINFO_E_035", "币种"));
					}
					// add by etliu 20180912 end
					else {
						dto.setCcy(codeList[0]);
					}
				}
				// 持有份额
				String qty = item.get("QTY");
				String qtyInfo = checkMoney(qty, "持有份额", 13, 5, true);
				if (StringUtil.isNotEmpty(qtyInfo)) {
					message.add(qtyInfo);
				} else {
					dto.setQty(new BigDecimal(qty));
				}

				// 持有金额
				String amount = item.get("AMOUNT");
				String amountInfo = checkMoney(amount, "持有金额", 13, 2, true);
				if (StringUtil.isNotEmpty(amountInfo)) {
					message.add(amountInfo);
				} else {
					dto.setAmount(new BigDecimal(amount));
				}

				// 折算人民币金额（元）
				if (item.containsKey("CNY_AMT")) {
					String cnyAmt = item.get("CNY_AMT");
					String cnyAmtInfo = checkMoney(cnyAmt, "折算人民币金额（元）", 13, 2, false);
					if (StringUtil.isNotEmpty(cnyAmtInfo)) {
						message.add(cnyAmtInfo);
					} else if (StringUtil.isNotEmpty(cnyAmt)) {
						dto.setCnyAmt(new BigDecimal(cnyAmt));
					}
				}
				//如果币种是人民币时，持有金额和折算人民币金额必须保持一致
				if (isExcel) {
					try {
						String[] codeList1 = StringUtil.split(ccy, StringUtil.SPACE);
						BigDecimal amount1 = new BigDecimal(item.get("AMOUNT"));
						BigDecimal cnyAmt1 = new BigDecimal(item.get("CNY_AMT"));
//						if("CNY".equals(codeList1[0])){
						if (StringUtil.equals(codeList1[0], DictCodeListExt1.币种_中债_人民币.getValue())) {
							if(amount1 != null || cnyAmt1 != null){
								if (cnyAmt1.compareTo(amount1) != 0) {
									message.add(getMessage("REPORT_COMMON_FILE_E_0025"));
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}

				/**机构和部门代码 为空时，给默认值  add by 徽商个性化 chaowei 2019-12-03 begin**/
				if (StringUtil.isEmpty(item.get("ORG_CODE"))) {
					item.put("ORG_CODE", SvrDataUtil.getSysParam("CHB_ORG_CODE"));
				}
				if (StringUtil.isEmpty(item.get("DEPT_CODE"))) {
					item.put("DEPT_CODE", SvrDataUtil.getSysParam("CHB_DEPT_CODE"));
				}
				/**机构和部门代码 为空时，给默认值  add by 徽商个性化 chaowei 2019-12-03 end**/

				// 部门代码
				if (item.containsKey("DEPT_CODE") || StringUtil.isNotEmpty(item.get("DEPT_CODE"))) {
					dto.setDeptCode(item.get("DEPT_CODE"));
				}
				// 经办人
				if (item.containsKey("AGENT") || StringUtil.isNotEmpty(item.get("AGENT"))) {
					dto.setAgent(item.get("AGENT"));
				}
				// 创建部门
				if (isExcel) {
					String createDept = deptCode != null ? deptCode
							: orgCode;
					dto.setCreateDept(createDept);
					dto.setDeptCode(deptCode);
				} else {
					String createDept = StringUtil.isNotEmpty(item.get("DEPT_CODE")) ? item.get("DEPT_CODE")
							: item.get("ORG_CODE");
					dto.setCreateDept(createDept);
				}
				String account = item.get("ACCOUNT");

				//新增产品登记编码格式校验
				String regCode = item.get("REG_CODE");
				if (StringUtil.isNotEmpty(regCode) && !regCode.matches("^[0-9a-zA-Z]{8}[ABC]?[0-9]{6}")){
//					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKREGCODE"));
				}
				// add by etliu 20180912 start
				else if (StringUtil.isNotEmpty(regCode) && EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, regCode)) {
					message.add(this.getMessage("CHB_EQUITYINFO_E_035", "产品登记编码"));
				}
				// add by etliu 20180912 end

				// 产品编码校验
//              String ACCOUNT = item.get("ACCOUNT");

				// 新加产品代码字段
				dto.setProdid(account);

				if (StringUtil.isEmpty(account)) {
					isNeed = false;
					message.add(this.getMessage("CHB_INVESTHOLD_CHECKPROD"));
				}
				else {
					if (!prdRegistMap.containsKey(account)) {
						// if(StringUtil.isNotEmpty(item.get("REG_CODE"))){
						dto.setPrdRegistCode(item.get("REG_CODE"));
//                        boolean flag = true;
//
//                        // 多线程情况下需要加锁，否则报错concurrentmodificationexception
//                        synchronized (prodRegInfoList) {
//                            if (CollectionUtils.isNotEmpty(prodRegInfoList)) {
//                                for (ChbPrdresistCode chbPrdresistCode : prodRegInfoList) {
//                                    if (StringUtil.equals(ACCOUNT, chbPrdresistCode.getProdid())) {
//                                        flag = false;
//                                        break;
//                                    }
//                                }
//                            }
//                            if (flag) {
//                                // 产品登记编码信息
//                                ChbPrdresistCode prodRegInfoDto = new ChbPrdresistCode();
//                                prodRegInfoDto.setProdid(ACCOUNT);
//                                prodRegInfoDto.setRegCode(item.get("REG_CODE"));
//                                prodRegInfoDto.setOrgCode(item.get("ORG_CODE"));
//                                // prodRegInfoDto.setDeptCode(dto.getDeptCode());
//                                // prodRegInfoDto.setCreateDept(dto.getCreateDept());
//                                prodRegInfoDto.setDeptCode(null);
//                                prodRegInfoDto.setCreateDept(null);
//                                prodRegInfoList.add(prodRegInfoDto);
//                            }
//                        }
						// }
						// else{
						// isNeed = false;
						// message.add(this.getMessage("REPORT_E_0015", "产品登记编码"));
						// }
						if (!StringUtil.isNotEmpty(item.get("REG_CODE"))) {
							message.add(this.getMessage("CHB_INVESTHOLD_CHECKPROD"));
						} else if (StringUtil.isNotEmpty(item.get("REG_CODE")) && item.get("REG_CODE").length() != 14
								&& item.get("REG_CODE").length() != 15) {
							message.add("产品登记编码的长度为14或15位！");
						} else if(StringUtil.isNotEmpty(item.get("REG_CODE")) && !Pattern.compile("^[0-9a-zA-Z]{8}[ABC]?[0-9]{6}").matcher(item.get("REG_CODE")).matches()){
							message.add(this.getMessage("REPORT_COMMON_E_0067", "产品登记编码"));
						}
					} else {
						if (StringUtil.isEmpty(prdRegistMap.get(account)) && StringUtil.isEmpty(item.get("REG_CODE"))) {
							message.add(this.getMessage("CHB_INVESTHOLD_CHECKPROD"));
						} else if (StringUtil.isNotEmpty(item.get("REG_CODE")) && item.get("REG_CODE").length() != 14
								&& item.get("REG_CODE").length() != 15) {
							message.add("产品登记编码的长度为14或15位！");
						} else if (StringUtil.equals(prdRegistMap.get(account), item.get("REG_CODE"))
								|| StringUtil.isEmpty(item.get("REG_CODE"))) {
							dto.setPrdRegistCode(prdRegistMap.get(account));
						} else if (StringUtil.isEmpty(prdRegistMap.get(account))) {
							dto.setPrdRegistCode(item.get("REG_CODE"));
						} else {
							dto.setPrdRegistCode(item.get("REG_CODE"));
							message.add(this.getMessage("REPORT_REG_MISMACTH"));
						}
					}
				}

				dto.setRowId(String.valueOf(rowId));
				// 判断为空check校验通过
//				if (message.isEmpty()) {
//					dto.setInterfCheckResult(" ");
				// 折算人民币处理
				if (dto.getCnyAmt() == null) {
					if (StringUtil.equals(dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue())) {
						dto.setCnyAmt(dto.getAmount());
					} else {
						String key = DateUtil.format(dto.getHoldDate()) + dto.getCcy();
						if (rateMap.containsKey(key)) {
							dto.setCnyAmt(ArithmeticUtil.multiply(rateMap.get(key), dto.getAmount()));
						} else {
							Map<String, Object> temp = new HashMap<>();
							// 原币种
							temp.put("CCY_ORIGINAL", dto.getCcy());
							// 持有日期
							temp.put("HOLDDATE", dto.getHoldDate());
							// 折算币种
							temp.put("CCY_CONVERT", DictCodeListExt1.币种_中债_人民币.getValue());
							// 汇率计算规则
							temp.put("pricetype",SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE));
							BigDecimal rate = investorsHoldInfo001Mapper.getRateNearByDate(temp);
							if (rate != null) {
								dto.setCnyAmt(ArithmeticUtil.multiply(rate, dto.getAmount()));
								dto.setCnyAmt(dto.getCnyAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
								rateMap.put(key, rate);
							} else {
								message.add("汇率不存在！");
							}
						}
					}
				}
//				}
				//反馈状态是发送成功不能修改
				if (isExcel) {
					try {
						List<ChbInvestholdInfoSub> subListUp = new ArrayList<>();
						ChbInvestholdInfoSub subDto = new ChbInvestholdInfoSub();
						String[] codeList = StringUtil.split(ccy, StringUtil.SPACE);
						subDto.setIndentifyMark(item.get("INDENTIFY_MARK"));
						subDto.setProdid(item.get("ACCOUNT"));
						subDto.setCcy(codeList[0]);
						subDto.setHoldDate(DateUtil.parse(item.get("HOLD_DATE")));
						subDto.setRegistBankCode(item.get("REGIST_BANK_CODE"));
						subListUp.add(subDto);
						List<InvestorsHoldInfoRegist001Dto> submitFeedbackStatusList = this
								.getSubmitFeedbackStatusList(subListUp);
						if (CollectionUtils.isNotEmpty(submitFeedbackStatusList)) {
							for (InvestorsHoldInfoRegist001Dto action : submitFeedbackStatusList) {
								if ("01".equals(action.getSubmitFeedbackStatus())
										|| "02".equals(action.getSubmitFeedbackStatus())
										|| "05".equals(action.getSubmitFeedbackStatus())
										|| StringUtil.isBlank(action.getSubmitFeedbackStatus())) {
									message.add("该数据存在中债报送流程中的数据，不允许修改，请稍后再试！");
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				// 判断为空check校验通过
				if (message.isEmpty()) {
					dto.setInterfCheckResult(" ");
					listE.add(dto);
				} else {
					// 存在错误信息 判断业务主键是否完整
					if (isNeed && !isExcel) {
						try {
							String checkMess = "";
							for (String msg : message) {
								checkMess += msg;
							}
							if (checkMess.length() > 200) {
								checkMess = checkMess.substring(0, 200);
							}
							dto.setInterfCheckResult(checkMess);
						}
						catch (Exception e) {
							logger.error(e.getMessage(), e);
						}

						listA.add(dto);
					}

					// 打印检查MESSAGE
					try {
//						System.out.println("识别标识[" + INDENTIFY_MARK + "] 产品[" + ACCOUNT + "]检查错误消息如下：");
						logger.error("识别标识[{}] 产品[{}]检查错误消息如下：" , indentifyMark,account);
						messageInfo.append("识别标识[" + indentifyMark + "] 产品[" + account + "]检查错误消息如下：").append("\n");
						for (String msg : message) {
							//checkMess+=msg;
//							System.out.println(msg);
							logger.error(msg);
							//add by liubin start 仅为接口接数时才将检查错误日志输出到日志中
							if(!isExcel){
								messageInfo.append(msg).append("\n");
							}
							//add by liubin end
						}
//						messageInfo.append("\n");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					// 生成错误信息提醒
					if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
						result.add(new ExcelValidDto(item.get("INDENTIFY_MARK")+"|"+item.get("ACCOUNT")
								+"|"+item.get("HOLD_DATE")
								+"|"+item.get("CCY")+"|"+item.get("QTY")+"|"+item.get("AMOUNT"), String.valueOf(rowId+1),
								StringUtil.joinString(message, "<br>")));
					}else{
						result.add(new ExcelValidDto(indentifyMark, String.valueOf(rowId + 1),
								StringUtil.joinString(message, "<br>")));
					}
				}
				rowId++;
			}
		}

		//add by liubin start 输出文件
		OutputStreamWriter osw = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			osw = new OutputStreamWriter(fout);
			osw.append(messageInfo.toString()).append("\n");
			osw.flush();
		} catch (Exception e1) {
//			e1.printStackTrace();
			logger.error(e1.getMessage(),e1);
		}
		finally{
			if(fout !=null ){
				try {
					fout.close();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}

			if(osw!=null){
				try {
					osw.close();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		//add by liubi end


		logger.info("checkDataInfo处理结束 " + DateUtil.getCurrentDateTime());

		return result;
	}

	/**
	 * 判断代码是否合法，返回错误信息
	 *
	 * @param code
	 *            代码
	 * @param intlen
	 *            长度
	 * @param declen
	 *            长度
	 * @param cloumnTitle
	 *            列名
	 * @param isRequired
	 *            是否必须
	 * @return 错误信息
	 */
	private String checkMoney(String code, String cloumnTitle, int intlen, int declen, boolean isRequired) {
		String message = null;
		// 必须验证
		if (StringUtil.isEmpty(code)) {
			if (isRequired) {
				message = this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
				return message;
			} else {
				return null;
			}
		}
		// 属性验证
		if (!CommonUtil.isFloatNumeric(code)) {
			message = this.getMessage("REPORT_COMMON_E_0003", cloumnTitle);
		}
		// add by etliu 20180912 start
		else if (StringUtil.isNotEmpty(code)
				&& EquityInfoCheckUtil.findPatern(EquityInfoValid.PATTERN_ALL, code)) {
			message = this.getMessage("CHB_EQUITYINFO_E_035", cloumnTitle);
		}
		// add by etliu 20180912 end
		else {
			BigDecimal num = new BigDecimal(code);
			if (num.compareTo(BigDecimal.ZERO) <= 0) {
				message = this.getMessage("REPORT_COMMON_E_0024", cloumnTitle, "0");
			}

			// 长度验证
			if ((num.precision() - num.scale()) > intlen || num.scale() > declen) {
				message = this.getMessage("REPORT_ASSETBOND_E_001", cloumnTitle, intlen, declen);
			}
		}
		return message;
	}

	/**
	 * 投资者持有信息上游接口接入多线程处理，可以事先查询的内容预加载，避免循环中重复查询
	 *
	 * @param equityList 文件中的数据
	 * @return InvestorsHoldInfo001FileReadParaDto
	 * @see
	 * @since 1.0
	 */
	private InvestorsHoldInfo001FileReadParaDto buildFileReadParaDto(List<Map<String, String>> equityList) {
		InvestorsHoldInfo001FileReadParaDto paraDto = new InvestorsHoldInfo001FileReadParaDto();

		// 获取所有的登记银行编码
		Map<String, String> registMap = investorsHoldInfo001Mapper.getAllRegistBankCodeSet().stream()
				.collect(JoyinCollectors.toMap(x -> x.get("REGIST_BANK_CODE"), x -> x.get("ORG_CODE")));
		paraDto.setRegistMap(registMap);

		// 获取所有的产品登记编码
		Map<String, String> prdRegistMap = investorsHoldInfo001Mapper.getAllRegistProdCodeSet().stream()
				.collect(JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));
		paraDto.setPrdRegistMap(prdRegistMap);

		// 当天的持有数据是否存在
		boolean isTodayDataExist = true;
		try {
		    if (!StringUtil.isEmptyList(equityList)) {
		        Map<String, String> firstDataMap = equityList.get(0);
		        String holdDatestr = firstDataMap.get("HOLD_DATE");
		        Date holdDate = DateUtil.parse(holdDatestr);

		        if (StringUtil.isNotEmpty(holdDate)) {
                    int  count = investorsHoldInfo001Mapper.selectInvestHoldInfoDataCount(holdDate);
                    if (count <= 0) {
                        isTodayDataExist = false;
                    }
		        }
	        }
        }
        catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
		paraDto.setTodayDataExist(isTodayDataExist);

		return paraDto;
	}

	/**
	 * 获取文件中的所有产品代码与产品登记编码的对应关系
	 *
	 * @param equityList 文件中的数据
	 * @param paraDto paraDto
	 * @see
	 * @since 1.0
	 */
	private List<ChbPrdresistCode> getProdRegInfoListFromFile(List<Map<String, String>> equityList, InvestorsHoldInfo001FileReadParaDto paraDto) {
		List<ChbPrdresistCode> prodRegInfoList = new ArrayList<>();

		// 获取系统中存在的所有的产品登记编码
		Map<String, String> prdRegistMap = paraDto.getPrdRegistMap();

		Map<String, String> tempDataKeyMap = new HashMap<String, String>();
		for (Map<String, String> dataMap : equityList) {
			String account = dataMap.get("ACCOUNT");
			String regCode = dataMap.get("REG_CODE");
			String orgCode="";
			if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
				orgCode = dataMap.get("ORG_CODE")==null ? "C10403" :dataMap.get("ORG_CODE");
			}else{
				/*add by xuyang for 持有信息解析时，产品登记编码入库机构代码为空给默认值  徽商个性化 20200822 begin*/
				orgCode = dataMap.get("ORG_CODE")==null ? CacheUtil.getSysParam("CHB_ORG_CODE") :dataMap.get("ORG_CODE");
			}
			/*add by xuyang for 持有信息解析时，产品登记编码入库机构代码为空给默认值  徽商个性化 20200822 end*/
			if (StringUtil.isNotBlank(account)) {
				if (!prdRegistMap.containsKey(account) && !tempDataKeyMap.containsKey(account)) {
					// 产品登记编码信息
					ChbPrdresistCode prodRegInfoDto = new ChbPrdresistCode();
					prodRegInfoDto.setProdid(account);
					prodRegInfoDto.setRegCode(regCode);
					prodRegInfoDto.setOrgCode(orgCode);
					prodRegInfoDto.setDeptCode(null);
					prodRegInfoDto.setCreateDept(null);
					prodRegInfoList.add(prodRegInfoDto);

					tempDataKeyMap.put(account, account);
				}
			}
		}

		return prodRegInfoList;
	}

	/**
	 * 从接口中获取投资者持有信息（数据开关关闭不覆盖）
	 * <ul>
	 * <li>1.调用共通方法解析传入的接口文件</li>
	 * <li>2.遍历解析后的数据，逐条插入验证（暂定）</li>
	 * <li>3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态</li>
	 * <li>4.验证通过后，判断是否新增数据
	 * <ul>
	 * <li>4.1 若为新增数据，则直接insert</li>
	 * <li>4.2 若非新增数据，则需读取是否覆盖开关</li>
	 * <li>4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2</li>
	 * </ul>
	 * </li>
	 * <li>若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新</li>此条规则去掉
	 * </ul>
	 *
	 * @param in
	 *            输入流
	 * @param interType
	 *            来源系统
	 * @return string 处理成功失败
	 * @param tmpPath
	 *            临时存放路径
	 * @param suffix
	 *            文件后缀名
	 * @param monitorInfoDto
	 *            读取上游文件监控信息dto
	 */
	public String getInvestorsHoldInfoByInterfYC(InputStream in, String interType, String tmpPath, String suffix,
												 ChbFileReadMonitorInfo monitorInfoDto,String realName) {
		// 如果接口尚未处理完成，则不处理
		// if (investorsHoldInfo001Mapper.selectTmpCount() > 0) {
		// return null;
		// }

		List<Map<String, String>> equityList = null;
		if ("xml".equals(suffix)) {
			try {
				equityList = XmlParseUtil.parse(in);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error(e.getMessage(),e);
				return "fail";
			}
		} else if ("txt".equals(suffix)) {
			try {
				equityList = TxtParseUtil.parse(in, reportInterfFileEncoding, reportInterfSplitCharacter);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error(e.getMessage(),e);
				return "fail";
			}
		}
		TotalAmountInfoDto totalAmountInfoDto = new TotalAmountInfoDto();
		totalAmountInfoDto.setId(UUID.randomUUID().toString());
		totalAmountInfoDto.setReportDate(realName.substring(37, 45));
		totalAmountInfoDto.setInterfaceType("2");
		totalAmountInfoDto.setFileName(realName);
		totalAmountInfoDto.setFileCount(equityList.size()+"");

		logger.info("解析xml文件存入List完成后equityList长度："+equityList.size());
		// String[] result = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
		// CHB_INVESTHOLD_INFO_NAME).split(GlobalConstant.COMMA);
		String[] result = {};
		// 解析成功或失败的标识
		boolean success = true;

		Map<String,String> resultMap = new TreeMap<String,String>();

		// 产品代码信息集合（用于重复性过滤）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date bdate=new Date();
		Long bt=bdate.getTime();
		logger.info("持有信息入库处理开始："+sdf.format(bdate));
		if (CollectionUtils.isNotEmpty(equityList)) {
			monitorInfoDto.setFileDataCount(BigDecimal.valueOf(equityList.size()));
			DataCheckService.checkDuplicatedData(equityList, "REGIST_BANK_CODE", "ACCOUNT","INDENTIFY_MARK","HOLD_DATE","CCY");
			investorsHoldInfoDistinctService.checkInvestorsHoldInfolist(equityList,"02");
			for(Map<String, String> map:equityList){
				map.remove(null);
			}
			// 投资者持有信息上游接口接入多线程处理，可以事先查询的内容预加载，避免循环中多次处理
			InvestorsHoldInfo001FileReadParaDto paraDto = this.buildFileReadParaDto(equityList);
			List<ChbPrdresistCode> prodRegInfoList = this.getProdRegInfoListFromFile(equityList, paraDto);

//			if (equityList.size() <= 1000) {
//				success = excuteInvestorsHoldInfoByStep(equityList, interType, result, null, null, null,
//						prodRegInfoList, paraDto);
//			} else {
			// 创建表
			ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
			ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
			subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
			subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
			// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
			for (ImpColumn column : subTable.getColumns()) {
				if (StringUtil.equals(column.getName(), "STATUS")) {
					column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
				}
			}
			// 使用线程安全list
			List<InvestorsHoldInfo001Dto> listE = new CopyOnWriteArrayList <>();
			List<InvestorsHoldInfo001Dto> listA = new CopyOnWriteArrayList <>();
			// 创建一个线程池
			if (interfMaxCorePoolSize < 1) {
				interfMaxCorePoolSize = 20;
			}
			ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
			List<Callable<Map<String,String>>> tasks = new ArrayList<>();
			if (null != equityList) {
				int start = 0;
				int end = 0;
				int size = equityList.size();
				int max = (int)Math.ceil((double)size / reportInterfOneTimeDataCount);
				for (int i = 0; i < max; i++ ) {
					start = end;
					end = Math.min(size, reportInterfOneTimeDataCount * (i + 1));
					List<Map<String, String>> subList = equityList.subList(start, end);
					logger.info("循环截取equityList处理：i="+i+"；start="+start+";end="+end+"subList.size="+subList.size());
					tasks.add(new Callable<Map<String,String>>() {

						@Override
						public Map<String,String> call() {
							return excuteInvestorsHoldInfoByStepYC(subList, interType, result,
									tmpPath, table, subTable, prodRegInfoList, paraDto,listA,listE);
						}
					});
				}
			}

			// 为线程池添加任务
			List<Future<Map<String,String>>> taskResult;
			try {
				taskResult = service.invokeAll(tasks);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
				Thread.currentThread().interrupt();
				return "fail";
			}
			for (Future<Map<String,String>> res : taskResult) {
				try {
//                    success = success && res.get();
					resultMap.putAll(res.get());
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage(),e);
					Thread.currentThread().interrupt();
					return "fail";
				}
			}
			// 任务结束，停止线程池的所有线程
			service.shutdown();
			// 多线程处理sqlldr不安全  单独处理
			List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
			List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
			logger.info("listE.size:"+listE.size()+"###listA.size:"+listA.size());
			if (listE.size() > 0 || listA.size() > 0) {
				afterCheckDeal(listE, listA, interType, listIn, subListInsert, paraDto);
			}
			// 批量插入
			if (listIn != null && listIn.size() > 0) {
				if(listIn.size()<1000){
					investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listIn);
				}else{
					batchInsertService.exeBatchldr(tmpPath, listIn, table);
				}
			}

			if (subListInsert != null && subListInsert.size() > 0) {
				if(listIn.size()<1000) {
					insertChbSubHoldInfoDataByPart(subListInsert);
				}else{
					batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
				}
			}

			// 将库中不存在的产品信息录入表中
			if (CollectionUtils.isNotEmpty(prodRegInfoList)) {
//                investorsHoldInfo001Mapper.insertProdRegInfo(prodRegInfoList);
				this.insertProdRegInfoByPart(prodRegInfoList);
			}
			investorsHoldInfoDistinctService.mergeInvestorsHoldInfolist("01",interType);
		}
		/** 将校验结果与源数据进行拼接 add by xiewei 2017.11.27 start */
		int successCount=0;
		for (Map<String, String> sourceData : equityList) {
			String value = resultMap.get(sourceData.get("INDENTIFY_MARK")+"|"+sourceData.get("ACCOUNT")+"|"+sourceData.get("HOLD_DATE")+"|"+sourceData.get("CCY")+"|"+sourceData.get("QTY")+"|"+sourceData.get("AMOUNT"));
			if (value != null && !"".equals(value)) {
				sourceData.put(ReportGlobalConstant.RESULT_CODE,
						ReportGlobalConstant.ERROR_CODE);
				sourceData.put(ReportGlobalConstant.RESULT, value);
			} else {
				sourceData.put(ReportGlobalConstant.RESULT_CODE,
						ReportGlobalConstant.CORRECT_CODE);
				sourceData.put(ReportGlobalConstant.RESULT,
						ReportGlobalConstant.CORRECT_INFO);
				successCount++;
			}
		}
		//反馈equityList结果
		FileIO.sendResponseFile(equityList, realName);

		totalAmountInfoDto.setFileInData(successCount+"");
		List<TotalAmountInfoDto> totalAmountInfoDtoList=checkNumMapper.queryTotalAmountInfo(totalAmountInfoDto);
		if(totalAmountInfoDtoList.size()!=0){
			checkNumMapper.deltotalAmountInfoDto(totalAmountInfoDto);
		}
		checkNumMapper.insertTotalAmountInfo(totalAmountInfoDto);

		Date edate=new Date();
		Long et=edate.getTime();
		Long time=(et-bt)/1000;
		logger.info("持有信息入库处理结束:"+sdf.format(edate)+"耗时"+time+"秒");
//		}
		// 数据解析完成，并导入数据库成功后，将ftp文件修改名称
		if (success) {
			return "success";
		}

		//add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
		monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
		//add by liubin end
		return "fail";
	}

	/**
	 * 从接口中获取投资者持有信息（数据开关关闭不覆盖）
	 * <ul>
	 * <li>1.调用共通方法解析传入的接口文件</li>
	 * <li>2.遍历解析后的数据，逐条插入验证（暂定）</li>
	 * <li>3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态</li>
	 * <li>4.验证通过后，判断是否新增数据
	 * <ul>
	 * <li>4.1 若为新增数据，则直接insert</li>
	 * <li>4.2 若非新增数据，则需读取是否覆盖开关</li>
	 * <li>4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2</li>
	 * </ul>
	 * </li>
	 * <li>若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新</li>此条规则去掉
	 * </ul>
	 *
	 * @param in
	 *            输入流
	 * @param interType
	 *            来源系统
	 * @return string 处理成功失败
	 * @param tmpPath
	 *            临时存放路径
	 * @param suffix
	 *            文件后缀名
	 * @param monitorInfoDto
	 *            读取上游文件监控信息dto
	 */
	public String getInvestorsHoldInfoByInterf(InputStream in, String interType, String tmpPath, String suffix,
											   ChbFileReadMonitorInfo monitorInfoDto) {
		// 如果接口尚未处理完成，则不处理
		// if (investorsHoldInfo001Mapper.selectTmpCount() > 0) {
		// return null;
		// }

		List<Map<String, String>> equityList = null;
		if ("xml".equals(suffix)) {
			try {
				equityList = XmlParseUtil.parse(in);

				if(StringUtil.isEmptyList(equityList)){
					return "success";
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error(e.getMessage(),e);
				return "fail";
			}
		} else if ("txt".equals(suffix)) {
			try {
				equityList = TxtParseUtil.parse(in, reportInterfFileEncoding, reportInterfSplitCharacter);

				if(StringUtil.isEmptyList(equityList)){
					return "success";
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.error(e.getMessage(),e);
				return "fail";
			}
		}
		logger.info("解析xml文件存入List完成后equityList长度："+equityList.size());
		// String[] result = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
		// CHB_INVESTHOLD_INFO_NAME).split(GlobalConstant.COMMA);
		String[] result = {};
		// 解析成功或失败的标识
		boolean success = true;
		// 产品代码信息集合（用于重复性过滤）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
		logger.info("持有信息入库处理开始："+sdf.format(bdate));
		if (CollectionUtils.isNotEmpty(equityList)) {
			// 设置 文件数据条数
			monitorInfoDto.setFileDataCount(BigDecimal.valueOf(equityList.size()));
			//如果同一个文件里有如下主键重复的数据，就会被过滤，这得看情况。是否需要过滤，如果多个渠道数据都在一个文件里，且需要数据合并的话。
			
			//DataCheckService.checkDuplicatedData(equityList, "REGIST_BANK_CODE", "ACCOUNT","INDENTIFY_MARK","HOLD_DATE","CCY");
			
			equityList = equityList.stream().collect(Collectors.collectingAndThen(
	 				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.get("REGIST_BANK_CODE")+"|"+p.get("ACCOUNT")+"|"+p.get("INDENTIFY_MARK")+"|"+p.get("HOLD_DATE")+"|"+p.get("CCY")
	 					))),ArrayList::new));
			
			logger.info("buildFileReadParaDto -begin "+sdf.format(new Date()));
			InvestorsHoldInfo001FileReadParaDto paraDto = this.buildFileReadParaDto(equityList);
			logger.info("buildFileReadParaDto -end "+sdf.format(new Date()));
			//FIXME  ADD BY 
			logger.info("checkInvestorsHoldInfolistBack -begin "+sdf.format(new Date()));
//			investorsHoldInfoDistinctService.checkInvestorsHoldInfolist(equityList,"02");
			// 除了check数据
			List<ChbInvestholdInfoCheckDto> mergelist = investorsHoldInfoDistinctService.checkInvestorsHoldInfolistBackNew(equityList,paraDto);
			logger.info("checkInvestorsHoldInfolistBack -end "+sdf.format(new Date()));
			// 投资者持有信息上游接口接入多线程处理，可以事先查询的内容预加载，避免循环中多次处理
			
			logger.info("getProdRegInfoListFromFile -begin "+sdf.format(new Date()));
			// 通过文件或者产品登记编码
			List<ChbPrdresistCode> prodRegInfoList = this.getProdRegInfoListFromFile(equityList, paraDto);
			logger.info("getProdRegInfoListFromFile -end "+sdf.format(new Date()));
 
			// 创建表
            ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
            table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
            // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
            ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
            subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
            subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
            // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
            for (ImpColumn column : subTable.getColumns()) {
                if (StringUtil.equals(column.getName(), "STATUS")) {
                    column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
                }
            }
            
            getInvestorsHoldInfoByInterf1( equityList,   interType,   tmpPath, result,  table,  subTable, paraDto, prodRegInfoList, mergelist, success);

            // 将库中不存在的产品信息录入表中
            if (CollectionUtils.isNotEmpty(prodRegInfoList)) {
                this.insertProdRegInfoByPart(prodRegInfoList);
            }
           
            logger.info("investorsHoldInfoDistinctService.mergeInvestorsHoldInfolist -begin:"+sdf.format(new Date()));
              investorsHoldInfoDistinctService.mergeInvestorsHoldInfolist("01",interType);
           // investorsHoldInfoDistinctService.mergeInvestorsHoldInfolistOld("01",interType);
            logger.info("investorsHoldInfoDistinctService.mergeInvestorsHoldInfolist -end:"+sdf.format(new Date()));
        }
		 Date edate=new Date();
	        Long et=edate.getTime();
	        Long time=(et-bt)/1000;
	    	logger.info("持有信息入库处理结束:"+sdf.format(edate)+"耗时"+time+"秒");
//		}
		// 数据解析完成，并导入数据库成功后，将ftp文件修改名称
		if (success) {
			return "success";
		}
		//add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
		monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
		//add by liubin end
		return "fail";
	}
	
	
@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void getInvestorsHoldInfoByInterf1(List<Map<String, String>> equityList, String interType, String tmpPath,
			String[] result, ImpTable table, ImpTable subTable, InvestorsHoldInfo001FileReadParaDto paraDto,
			List<ChbPrdresistCode> prodRegInfoList, List<ChbInvestholdInfoCheckDto> mergelist, boolean success) {

	// 使用线程安全list
	List<InvestorsHoldInfo001Dto> listE = new CopyOnWriteArrayList <InvestorsHoldInfo001Dto>();
	List<InvestorsHoldInfo001Dto> listA = new CopyOnWriteArrayList <InvestorsHoldInfo001Dto>();
        // 创建一个线程池
        if (interfMaxCorePoolSize < 1) {
            interfMaxCorePoolSize = 20;
        }
        ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        if (null != equityList) {
            int start = 0;
            int end = 0;
            int size = equityList.size();
            int max = (int)Math.ceil((double)size / reportInterfOneTimeDataCount);
            for (int i = 0; i < max; i++ ) {
                start = end;
                end = Math.min(size, reportInterfOneTimeDataCount * (i + 1));
                List<Map<String, String>> subList = equityList.subList(start, end);
                logger.info("循环截取equityList处理：i="+i+"；start="+start+";end="+end+"subList.size="+subList.size());
                tasks.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() {
                        return excuteInvestorsHoldInfoByStep(subList, interType, result,
                            tmpPath, table, subTable, prodRegInfoList, paraDto,mergelist,listA,listE);
                    }
                });
            }
        }

        // 为线程池添加任务
        List<Future<Boolean>> taskResult = null;
        try {
            taskResult = service.invokeAll(tasks);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        for (Future<Boolean> res : taskResult) {
            try {
                success = success && res.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(),e);
                Thread.currentThread().interrupt();
            }
        }
        // 任务结束，停止线程池的所有线程
        service.shutdown();
		// 多线程处理sqlldr不安全  单独处理
		List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
		List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
		logger.info("listE.size:"+listE.size()+"###listA.size:"+listA.size());
		if (listE.size() > 0 || listA.size() > 0) {
			afterCheckDealNew(listE, listA, interType, listIn, subListInsert, paraDto,mergelist);
		}
		// 批量插入
		if (listIn != null && listIn.size() > 0) {
			if(listIn.size()<1000){
				investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listIn);
			}else{
				batchInsertService.exeBatchldr(tmpPath, listIn, table);
			}
		}

		if (subListInsert != null && subListInsert.size() > 0) {
			if(listIn.size()<1000) {
				insertChbSubHoldInfoDataByPart(subListInsert);
			}else{
				batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
			}
		}
	}

	/**
	 * 登录产品登记编码数据
	 *
	 * @param prodRegInfoList
	 * @see
	 * @since 1.0
	 */
	public void insertProdRegInfoByPart(List<ChbPrdresistCode> prodRegInfoList) {
        if (CollectionUtils.isNotEmpty(prodRegInfoList)) {
            int maxDataCountOneTime = this.parsToInt(reportInsertCount, "1");
            int dataCount = prodRegInfoList.size();
            if (dataCount <= maxDataCountOneTime) {
                investorsHoldInfo001Mapper.insertProdRegInfo(prodRegInfoList);
                return;
            }

			List<ChbPrdresistCode> newDataList = new ArrayList<ChbPrdresistCode>();
			newDataList.addAll(prodRegInfoList);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				investorsHoldInfo001Mapper.insertProdRegInfo(newDataList.subList(0, maxDataCountOneTime));
				newDataList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataList)) {
				investorsHoldInfo001Mapper.insertProdRegInfo(newDataList);
			}
		}
	}

	/**
	 * @param dataList
	 *            待处理列表
	 * @param type
	 *            数据来源
	 * @param result
	 *            参数
	 * @param tmpPath
	 *            生成的临时文件存放路径
	 * @param prodRegInfoList
	 *            产品代码信息list
	 * @param paraDto
	 *            投资者持有信息上游接口接入多线程处理，可以事先查询的内容dto
	 * @return 此次是否成功插入
	 */
	private Map<String, String> excuteInvestorsHoldInfoByStepYC(List<Map<String, String>> dataList, String type, String result[],
																String tmpPath, ImpTable table, ImpTable subTable, List<ChbPrdresistCode> prodRegInfoList,
																InvestorsHoldInfo001FileReadParaDto paraDto, List<InvestorsHoldInfo001Dto>listA,
																List<InvestorsHoldInfo001Dto> listE) {
		Map<String,String> tempItem =new HashMap<String, String>();;
		if (dataList != null && CollectionUtils.isNotEmpty(dataList)) {
			List<ExcelValidDto> ExcelValidDto=checkDataInfo(dataList, false, listE, listA, prodRegInfoList, paraDto,null,null,null);
			for(int i=0;i<ExcelValidDto.size();i++){
				tempItem.put(ExcelValidDto.get(i).getValidKey(), ExcelValidDto.get(i).getErrorInfo());
			}
			return tempItem;
		}
		return tempItem;
	}
	/**
	 * @param dataList
	 *            待处理列表
	 * @param type
	 *            数据来源
	 * @param result
	 *            参数
	 * @param tmpPath
	 *            生成的临时文件存放路径
	 * @param prodRegInfoList
	 *            产品代码信息list
	 * @param paraDto
	 *            投资者持有信息上游接口接入多线程处理，可以事先查询的内容dto
	 * @return 此次是否成功插入
	 */
	private boolean excuteInvestorsHoldInfoByStep(List<Map<String, String>> dataList, String type, String result[],
												  String tmpPath, ImpTable table, ImpTable subTable, List<ChbPrdresistCode> prodRegInfoList,
												  InvestorsHoldInfo001FileReadParaDto paraDto,List<ChbInvestholdInfoCheckDto>  mergelist,
												  List<InvestorsHoldInfo001Dto>listA,
												  List<InvestorsHoldInfo001Dto> listE) {
		logger.info("excuteInvestorsHoldInfoByStep  -begin "+new Date());
		if (dataList != null && CollectionUtils.isNotEmpty(dataList)) {
			logger.info("checkDataInfo  -begin "+new Date());
			checkDataInfo(dataList, false, listE, listA, prodRegInfoList, paraDto,null,null,null);
			logger.info("checkDataInfo  -end "+new Date());
			return true;
		}
		logger.info("excuteInvestorsHoldInfoByStep  -end "+new Date());
		return true;
	}

	/**
	 * 删除临时表数据 <功能详细描述>
	 *
	 * @see
	 * @since 1.0
	 */
	public void deleteImportTmpData() {
		investorsHoldInfo001Mapper.deleteTmpTable();
	}

	/**
	 * 创建数据文件
	 *
	 * @param path
	 *            文件路径
	 * @param list
	 *            对象列表
	 * @param subList
	 *            对象列表
	 * @param columns
	 *            数据库表对应的列
	 * @param subColumns
	 */
	private void createDataFile(String path, List<InvestorsHoldInfo001Dto> list, List<ChbInvestholdInfoSub> subList,
								List<ImpColumn> columns, List<ImpColumn> subColumns) {
		// 生成txt文件CHB_INVESTHOLD_INFO_SUB
		List<String> lines = new ArrayList<>(list.size());
		List<String> subLines = new ArrayList<>(subList.size());
		list.forEach(item -> lines.add(ImportGenerator.buildTxt(item, columns)));
		// subLines.forEach(item -> subLines.add(ImportGenerator.buildTxt(item,
		// subColumns)));
		subList.forEach(item -> subLines.add(ImportGenerator.buildTxt(item, subColumns)));
		try {
			if (list.size() > 0) {
				FileUtils.writeLines(new SafelyFile(path + "/CHB_INVESTHOLD_INFO" + GlobalConstant.UNDER_LINE
						+ UUID.randomUUID().toString() + ".tmp"), "UTF-8", lines, true);
			}
			if (subList.size() > 0) {
				FileUtils.writeLines(new SafelyFile(path + "/CHB_INVESTHOLD_INFO_SUB" + GlobalConstant.UNDER_LINE
						+ UUID.randomUUID().toString() + ".tmp"), "UTF-8", subLines, true);
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}

	/**
	 * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
	 *
	 * @param fileName
	 *            导入的文件名名
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList,String bankCode,String deptCode,String orgCode) {
		AjaxResult webResult = new AjaxResult();
		if (!StringUtil.isEmptyList(dataList)) {
		    try {
//	          toChbList(dataList);
	            List<Map<String, String>> mapList = listToMap(dataList);
	            if (CollectionUtils.isNotEmpty(dataList)) {
	                List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
	                List<ExcelValidDto> checkResult = checkDataInfo(mapList, true, listE, null, null, null,bankCode,deptCode,orgCode);
	                if (checkResult.size() > 0) {
	                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
	                    return webResult;
	                }
	                Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = investorsHoldInfoDistinctService.selectAllHoldInfoDataRegCodeKey(listE);
	                if (allHoldInfoDataMap != null && allHoldInfoDataMap.size() > 0) {
	                    // 将序号放入list中
	                    List<String> excelNumList = new ArrayList<String>();
	                    List<Integer> excelNumIntList = new ArrayList<Integer>();
	                    List<String> prodidList = new ArrayList<String>();
	                    for (InvestorsHoldInfo001Dto info001Dto : listE) {
	                    	//modify by liubin start 将主键修改为中债五要素字段
	                    	String key = "";
	                    	List<Map<String, String>> prdresistCodeList = proRegistCodeManageMapper.queryRegistCodeByProdId(info001Dto.getProdid());
	                    	if(prdresistCodeList.size()>0 && (prdresistCodeList.get(0).get("REGCODE")!=null || !"".equals(prdresistCodeList.get(0).get("REGCODE")))){
	                    		key = info001Dto.getRegistBankCode() + GlobalConstant.UNDER_LINE
		                                + info001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
		                                // +info001Dto.getPrdRegistCode()+GlobalConstant.UNDER_LINE+info001Dto.getPrdRegistCode()
		                                +prdresistCodeList.get(0).get("REGCODE")  + GlobalConstant.UNDER_LINE + info001Dto.getCcy()
		                                + GlobalConstant.UNDER_LINE + DateUtil.format(info001Dto.getHoldDate());
	                    		if (allHoldInfoDataMap.containsKey(key)) {
		                            // excelNumList.add(info001Dto.getRowId());
		                            excelNumIntList.add(Integer.valueOf(info001Dto.getRowId()));
		                        }
	                    	}else{
	                    		//根据产品代码未查询到产品登记编码，则说明未维护，弹出检查结果
	                    		prodidList.add(info001Dto.getProdid());
//	                    		webResult.setMessage(this.getMessage("CHB_NO_REGCODEINFO",info001Dto.getProdid()));
							}
//	                        String key = info001Dto.getRegistBankCode() + GlobalConstant.UNDER_LINE
//	                                + info001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
//	                                // +info001Dto.getPrdRegistCode()+GlobalConstant.UNDER_LINE+info001Dto.getPrdRegistCode()
//	                                + info001Dto.getProdid() + GlobalConstant.UNDER_LINE + info001Dto.getCcy()
//	                                + GlobalConstant.UNDER_LINE + DateUtil.format(info001Dto.getHoldDate());

						}
						// 将list进行排序，并转化为string
						// Collections.sort(excelNumList);
						Collections.sort(excelNumIntList);
						// String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
						//modify by liubin start 若有产品未维护产品登记编码，则直接返回
						for (Integer rowNo : excelNumIntList) {
							excelNumList.add(String.valueOf(rowNo));
						}
						if(prodidList.size()>0 && excelNumIntList.size() == 0){
							webResult.setMessage(this.getMessage("CHB_NO_REGCODEINFO",StringUtil.joinString(prodidList,GlobalConstant.COMMA )),MessageType.确认消息);
						}else if(excelNumIntList.size()>0 && prodidList.size()==0){
							webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",
									StringUtil.joinString(excelNumList, GlobalConstant.COMMA)), MessageType.确认消息);
						}else if(excelNumIntList.size()>0 && prodidList.size()>0){
							webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",
									StringUtil.joinString(excelNumList, GlobalConstant.COMMA))+"\n"
									+this.getMessage("CHB_NO_REGCODEINFO",StringUtil.joinString(prodidList,GlobalConstant.COMMA )), MessageType.确认消息);
						}
						//modify by liubin end


					} else {
						// 防止在进行导入前check时进行了两次的弹框
						webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
					}
				}
				else{
					// 防止在进行导入前check时进行了两次的弹框
					webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				}
			}
			catch (Exception e) {
				// 防止在进行导入前check时进行了两次的弹框
				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
			}
		}
		return webResult;
	}

	/**
	 *
	 * 获取利率 <功能详细描述>
	 *
	 * @return 获取到的结果
	 * @see
	 * @since 1.0
	 */
	public BigDecimal getRate(Map<String, Object> map) {
		return investorsHoldInfo001Mapper.getRateNearByDate(map);
	}

	/**
	 * 统计产品登记代码 <功能详细描述>
	 *
	 * @param prdRegistCode
	 * @return
	 * @see
	 * @since 1.0
	 */
	public int countProdReg(String prdRegistCode) {
		int result = 0;
		try {
			result = investorsHoldInfo001Mapper.countProdReg(prdRegistCode);
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return result;

//		return investorsHoldInfo001Mapper.countProdReg(prdRegistCode);
	}

	/**
	 * 根据产品登记编码获取产品代码
	 *
	 * @param prdRegistCode
	 *            产品登记编码
	 * @return 产品代码
	 * @see
	 * @since 1.0
	 */
	public String getProdidByPrdRegistCode(String prdRegistCode) {
		return investorsHoldInfo001Mapper.getProdidByPrdRegistCode(prdRegistCode);
	}

	/**
	 * 设置单元格格式
	 */
	@Override
	public void setCellValue(String beanName, Cell cell, String propName, Object value) {
		if (StringUtil.startsWith(propName, "holdDate")) {
			if(value instanceof Long){
				Date cellValue = new Date((long) value);
				cell.setCellValue(DateUtil.format(cellValue));
			}
		}
		if (StringUtil.startsWith(propName, "qty")) {
			  BigDecimal cellValue = new BigDecimal((String) value);
              cell.setCellValue(cellValue.setScale(5).toString());
		}
	}

	/**
	 * 统计 <功能详细描述>
	 *
	 * @param map
	 * @return
	 * @see
	 * @since 1.0
	 */
	public int countInvestor(Map<String, String> map) {
		int result = 0;
		try {
			result = investorsHoldInfo001Mapper.countInvestor(map);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 统计主键是否重复
	 * @param map
	 * @return
	 */
	public int countPrimaryKey(Map<String, String> map){
//		return investorsHoldInfo001Mapper.countPrimaryKey(map);

		int result = 0;
		try {
			result = investorsHoldInfo001Mapper.countPrimaryKey(map);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 导入代销数据功能 <功能详细描述>
	 *
	 * @param fileName
	 *            文件名称
	 * @return check结果
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> importChbData(List<List<String>> dataList,List<List<String>> tempList,String bankCode,String deptCode,String orgCode) {
		if (!StringUtil.isEmptyList(dataList)) {
			//add by liubin start 增加模版校验
			List<String> headList = new ArrayList<String>();
			headList.add("登记银行代码");
			headList.add("产品登记编码");
			headList.add("识别标识");
			headList.add("持有日期");
			headList.add("币种");
			headList.add("持有份额");
			headList.add("持有金额");
			headList.add("折算人民币金额（元）");

			List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
			if (!StringUtil.isEmptyList(tempCheckResult)) {
				return tempCheckResult;
			}
			//add by liubin end

			if (dataList.size() == 0) {
				return null;
			}
			// 将中债数据格式转换成符合本系统的格式
			toChbList(dataList);

			List<Map<String, String>> mapList = listToMap(dataList);
			List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
			List<InvestorsHoldInfo001Dto> listA = new ArrayList<>();
			List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
			List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
			List<ExcelValidDto> checkResult = checkDataInfo(mapList, true, listE, listA, null, null,bankCode,deptCode,orgCode);
			if (checkResult.isEmpty()) {
				// 进行了数据检查与转化后 无错误信息可以进行后续操作
				InvestorsHoldInfo001FileReadParaDto paraDto = this.buildFileReadParaDto(mapList);
				List<ChbInvestholdInfoCheckDto> mergelist = investorsHoldInfoDistinctService.checkInvestorsHoldInfolistBackNew(mapList,paraDto);
				afterCheckDealNew(listE, null, DictCodeListExt2.数据来源_EXCEL导入.getValue(), listIn, subListInsert, paraDto, mergelist);

				String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
				// 批量插入
				if (listIn != null && listIn.size() > 0) {
					if(listIn.size()<1000){
						investorsHoldInfoDistinctService.insertChbHoldInfoDataByPart(listIn);
					}else{
						// 创建表
						ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
						table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));

						batchInsertService.exeBatchldr(tmpPath, listIn, table);
					}
				}

				if (subListInsert != null && subListInsert.size() > 0) {
					if(listIn.size()<1000) {
						insertChbSubHoldInfoDataByPart(subListInsert);
					}else{
						// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
						ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
						subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
						subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
						// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
						for (ImpColumn column : subTable.getColumns()) {
							if (StringUtil.equals(column.getName(), "STATUS")) {
								column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
							}
						}
						batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
					}
				}

				investorsHoldInfoDistinctService.mergeInvestorsHoldInfolistNew("02",DictCodeListExt2.数据来源_EXCEL导入.getValue());
			}
			return checkResult;
		}
		return null;
	}

	/**
	 * 导入代销数据功能
	 *
	 * @param datalist
	 *            中债数据格式
	 * @return
	 * @throws @see
	 * @since 1.0
	 */
	private void toChbList(List<List<String>> dataList) {
		// 通过代销数据的产品登记编码查找产品代码
		for (int i = 0; i < dataList.size(); i++) {
			String prdRegistCode = dataList.get(i).get(1);
			String prodid = investorsHoldInfo001Mapper.getProdidByPrdRegistCode(prdRegistCode);
			dataList.get(i).set(1, prodid);
			// 经办人一栏设置为空
			dataList.get(i).add("");
			dataList.get(i).add(prdRegistCode);
		}
	}

	/**
	 * 导入代销数据文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
	 *
	 * @param fileName
	 *            导入的文件名名
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public AjaxResult checkConfirmMsgForChbImportData(List<List<String>> dataList,List<List<String>> tempList,String bankCode,String deptCode,String orgCode) {
		AjaxResult webResult = new AjaxResult();
		if (!StringUtil.isEmptyList(dataList)) {
			//add by liubin start 增加校验
			List<String> headList = new ArrayList<String>();
			headList.add("登记银行代码");
			headList.add("产品登记编码");
			headList.add("识别标识");
			headList.add("持有日期");
			headList.add("币种");
			headList.add("持有份额");
			headList.add("持有金额");
			headList.add("折算人民币金额（元）");

			List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
			if (!StringUtil.isEmptyList(tempCheckResult)) {
				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				webResult.setMessage("导入模板不一致，请重新选择文件！");
//            	webResult.setData("导入模板不一致，请重新选择文件！");
				return webResult;
			}
			//add by liubin end

			// 将中债数据格式转换成符合本系统的格式
			toChbList(dataList);
			List<Map<String, String>> mapList = listToMap(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {
				List<InvestorsHoldInfo001Dto> listE = new ArrayList<>();
				List<ExcelValidDto> checkResult = checkDataInfo(mapList, true, listE, null, null, null,bankCode,deptCode,orgCode);
				if (checkResult.size() > 0) {
					webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				}
//				Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = selectAllHoldInfoDataRegCodeKey(listE);
				Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap =selectAllHoldInfoDataMutiThread(listE);
				if (allHoldInfoDataMap != null && allHoldInfoDataMap.size() > 0) {
					// 将序号放入list中
					List<String> excelNumList = new ArrayList<String>();
					List<Integer> excelNumIntList = new ArrayList<Integer>();
					for (InvestorsHoldInfo001Dto info001Dto : listE) {
//						String key = info001Dto.getRegistBankCode() + GlobalConstant.UNDER_LINE
//								+ info001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
//								// +info001Dto.getPrdRegistCode()+GlobalConstant.UNDER_LINE+info001Dto.getPrdRegistCode()
//								+ info001Dto.getProdid() + GlobalConstant.UNDER_LINE + info001Dto.getCcy()
//								+ GlobalConstant.UNDER_LINE + DateUtil.format(info001Dto.getHoldDate());
						String key = info001Dto.getRegistBankCode() + GlobalConstant.UNDER_LINE
								+ info001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
								// +info001Dto.getPrdRegistCode()+GlobalConstant.UNDER_LINE+info001Dto.getPrdRegistCode()
								+ info001Dto.getPrdRegistCode()+ GlobalConstant.UNDER_LINE + info001Dto.getCcy()
								+ GlobalConstant.UNDER_LINE + DateUtil.format(info001Dto.getHoldDate());
						if (allHoldInfoDataMap.containsKey(key)) {
							// excelNumList.add(info001Dto.getRowId());
							excelNumIntList.add(Integer.valueOf(info001Dto.getRowId())+1);
						}
					}
					// 将list进行排序，并转化为string
					// Collections.sort(excelNumList);
					Collections.sort(excelNumIntList);
					// String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
					for (Integer rowNo : excelNumIntList) {
						excelNumList.add(String.valueOf(rowNo));
					}
					// add by weiyuqiang 2019-04-17 修复持有信息前台导入不显示excel内重复数据，第几行问题 start
					if (CollectionUtils.isNotEmpty(excelNumIntList)){
						webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",
								StringUtil.joinString(excelNumList, GlobalConstant.COMMA)), MessageType.确认消息);
					}
					// add by weiyuqiang 2019-04-17 修复持有信息前台导入不显示excel内重复数据，第几行问题 end
				} else {
					// 防止在进行导入前check时进行了两次的弹框
					webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				}
			}
		}
		return webResult;
	}

	/**
	 * 传入日期，日期是否符合日期规范
	 *
	 * @param Date
	 *            传入日期
	 * @param pattern
	 *            日期转换
	 * @return
	 */
	public static boolean isDateE(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			format.setLenient(false);
			format.parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据最新导入的有效的产品登记编码数据，联动处理持有信息有效的数据 入库处理
	 *
	 * @param listE
	 *            校验通过数据
	 * @see
	 * @since 1.0
	 */
//	private void refreshStatusEDataToDb(List<InvestorsHoldInfo001Dto> listE) {
//		// 待维护数据插入数据库
//		if (CollectionUtils.isEmpty(listE)) {
//			return;
//		}
//		// 修改产品登记编码更新持有信息维护表数据
//		if (!StringUtil.isEmptyList(listE)) {
//			if (StringUtil.isEmptyList(listE)) {
//				return;
//			}
//
//			int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
////			if (maxDataCountOneTime < 1) {
////				maxDataCountOneTime = 1000;
////			}
//			int dataCount = listE.size();
//			if (dataCount <= maxDataCountOneTime) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoData2(listE, listE.get(0).getPrdRegistCode());
//				return;
//			}
//
//			List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
//			newDataList.addAll(listE);
//
//			// 分批处理
//			int part = dataCount / maxDataCountOneTime; // 分批数
//			for (int i = 0; i < part; i++) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoData2(newDataList.subList(0, maxDataCountOneTime),
//						listE.get(0).getPrdRegistCode());
//				newDataList.subList(0, maxDataCountOneTime).clear();
//			}
//			// 最后剩下的数据
//			if (CollectionUtils.isNotEmpty(newDataList)) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoData2(newDataList, listE.get(0).getPrdRegistCode());
//			}
//		}
//	}

	/**
	 * 根据最新导入的有效的产品登记编码数据，联动处理持有信息登记表数据 入库处理
	 *
	 * @param listE
	 *            校验通过数据
	 * @see
	 * @since 1.0
	 */
//	private void refreshStatusEDataToDb1(List<InvestorsHoldInfo001Dto> listE) {
//		// 待维护数据插入数据库
//		if (CollectionUtils.isEmpty(listE)) {
//			return;
//		}
//		// 修改产品登记编码更新持有信息维护表数据
//		if (!StringUtil.isEmptyList(listE)) {
//			if (StringUtil.isEmptyList(listE)) {
//				return;
//			}
//
//			int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
////			if (maxDataCountOneTime < 1) {
////				maxDataCountOneTime = 1000;
////			}
//			int dataCount = listE.size();
//			if (dataCount <= maxDataCountOneTime) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoSubData(listE, listE.get(0).getPrdRegistCode());
//				return;
//			}
//
//			List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
//			newDataList.addAll(listE);
//
//			// 分批处理
//			int part = dataCount / maxDataCountOneTime; // 分批数
//			for (int i = 0; i < part; i++) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoSubData(newDataList.subList(0, maxDataCountOneTime),
//						listE.get(0).getPrdRegistCode());
//				newDataList.subList(0, maxDataCountOneTime).clear();
//			}
//			// 最后剩下的数据
//			if (CollectionUtils.isNotEmpty(newDataList)) {
//				investorsHoldInfo001Mapper.updateChbInvestholdInfoSubData(newDataList, listE.get(0).getPrdRegistCode());
//			}
//		}
//	}
	/**
	 * 查询持有信息登记中对应的中债反馈状态为中债退回的数据
	 * @param map
	 * @return
	 */
	public List<InvestorsHoldInfo001Dto> selectInvestHoldInfoFeedBackData(Map<String, Object> map){
		return investorsHoldInfo001Mapper.selectInvestHoldInfoFeedBackData(map);
	};

	public List<InvestorsHoldInfo001Dto> selectAllInvestHoldInfoFeedBackData(Map<String, Object> map){
		return investorsHoldInfo001Mapper.selectAllInvestHoldInfoFeedBackData(map);
	};
	/**
	 * 配置项中非空处理
	 * @param Count
	 * @param flag
	 * @return
	 */
	public int parsToInt(String count,String flag){
		if(StringUtil.isNotEmpty(count)&&Integer.parseInt(count)<=0){
			if("1".equals(flag)){
				return 20;
			}
			else if("1".equals(flag)){
				return 500;
			}
			else {
				return 100;
			}
		}
		else{
			return Integer.parseInt(count);
		}
	}

	/**
	 * 持有信息修改之前查询反馈状态
	 * <功能详细描述>
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public List<InvestorsHoldInfoRegist001Dto>  getSubmitFeedbackStatusList(List<ChbInvestholdInfoSub> subListUp){
		if(!StringUtil.isEmptyList(subListUp) && subListUp.size()>1000){
			chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
			investorsHoldInfoDistinctService.saveChbInvestholdInfoCheckTmpBySub(subListUp);
			return investorsHoldInfo001Mapper.getSubmitFeedbackStatusListNew();
		}else{
			return investorsHoldInfo001Mapper.getSubmitFeedbackStatusList(subListUp);
		}
	}
	/**
	 * 导出功能查询
	 *
	 * @param pageParam 查询参数
	 * @return 结果集
	 * @see
	 * @since 1.0
	 */
	public List<InvestorsHoldInfoRegist001Dto> selectAllList2(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		List<InvestorsHoldInfoRegist001Dto> dtoList = investorsHoldInfoRegist001Mapper.selectAllData(
				pageParam);
		if (StringUtil.isNotEmpty(pageParam.get("flag"))
				&& !StringUtil.equals(pageParam.get("flag"), "0")& !StringUtil.equals(pageParam.get("flag"), "2")) {
			dealDataList(dtoList, true);
		}
		return dtoList;
	}
	/**
	 * 处理查询结果 <功能详细描述>
	 *
	 * @param list 待处理数据
	 * @param isExport 是否是导出
	 * @see
	 * @since 1.0
	 */
	private void dealDataList(List<InvestorsHoldInfoRegist001Dto> list, boolean isExport) {
//       list.forEach(item -> {
		for(int i=0;i<list.size();i++){
			InvestorsHoldInfoRegist001Dto item=list.get(i);
			item.setSendStatusCode(item.getSendStatus());
			item.setStatusCode(item.getStatus());
			item.setCcyCode(item.getCcy());
			item.setStatus(
					SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
			item.setSendStatus(
					SysDataUtil.getNameFromDataDict(DictCode.报送状态, item.getSendStatus()));
			item.setCcy(SysDataUtil.getNameFromDataDict(DictCode.币种, item.getCcy()));
		}
//       });
	}
	public static synchronized String getId() {
		Long nowStr = Long.valueOf(System.currentTimeMillis());
		if (lastDate.equals(nowStr.toString())) {
			addPart += 1;
		} else {
			addPart = 1;
			lastDate = nowStr.toString();
		}

		return "R0" + nowStr.toString() + String.format("%05d", new Object[] { Integer.valueOf(addPart) });
	}

	/**
	 * 获取序列
	 * @param list
	 * @param index
	 * @return
	 */
	public String getSequenceStr(List<String> list, int index) {
		if(StringUtil.isEmptyList(list) || index >= list.size()) {
			return getId();
		}
		else {
			return list.get(index);
		}
	}

}
