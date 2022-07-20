/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.submitmanagement.investorsholdinforegist.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.joyintech.system.batch.batchstart.service.SysBatchStart001Service;
import com.alibaba.fastjson.JSON;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.dao.ChbInvestholdInfoSubMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.common.base.entity.ChbSubmitStatus;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.cnbond.dto.CnBondInterfItem;
import com.joyintech.tams.report.interf.cnbond.dto.InterfCnBondSubmitPara;
import com.joyintech.tams.report.interf.cnbond.service.CnBondInterfService;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfoDistinctService;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dao.InvestorsHoldInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.validate.InvestorsHoldInfoRegist001Validate;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 投资者持有信息登记service
 *
 * @author zuoyinyi
 * @version 1.0 2018年5月8日
 * @see InvestorsHoldInfoRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InvestorsHoldInfoRegist001Service extends CommonExportService<InvestorsHoldInfoRegist001Dto> {

	/**
	 * 投资者持有信息登记mapper
	 */
	@Autowired
	private InvestorsHoldInfoRegist001Mapper investorsHoldInfoRegist001Mapper;
	
	@Autowired
	private CnBondInterfService cnBondInterfService;

	@Autowired
    private SysBatchStart001Service sysBatchStart001Service;
	/**
	 * 数据校验
	 */
	@Autowired
	private InvestorsHoldInfoRegist001Validate validate;

	@Autowired
	private SqlSessionTemplate template;

	/**
	 * 投资者持有信息登记基础mapper
	 */
	@Autowired
	private ChbInvestholdInfoSubMapper chbInvestholdInfoSubMapper;

	/**
	 * 报送前检查mapper
	 */
	@Autowired
	private CheckReport001Mapper checkReport001Mapper;

	@Autowired
	private InvestorsHoldInfoDistinctService investorsHoldInfoDistinctService;

	@Autowired
	private ChbBatchParaMapper chbBatchParaMapper;

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
	 * 短sql最大一次最大插入条数
	 */
	@Value("${application.reportSelectOrDeleteCountForShenFen}")
	public  int reportSelectOrDeleteCountForShenFen;

	/**
	 * 检查通过标志
	 */
	private static final String IS_SUCESS_INVESTORS_CHECK = "01";

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 查询页面数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public Page<InvestorsHoldInfoRegist001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		//将发送状态改为空
//		pageParam.put("sendStatus", "");
		int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
		int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<InvestorsHoldInfoRegist001Dto> page = queryForPage(
				pagenum,legth,()->investorsHoldInfoRegist001Mapper.selectAllData(pageParam));
		dealDataList(page.getRoot(), false);
		return page;
	}

	/**
	 * 查询数据（无分页）
	 * @param param
	 * @return
	 */
	public List<InvestorsHoldInfoRegist001Dto> queryForHoldInfoRegist(Map<String, Object> param) {
		dealSearchParam(param);
		List<InvestorsHoldInfoRegist001Dto> list = new ArrayList<>();
		list = investorsHoldInfoRegist001Mapper.selectAllData(param);
		dealDataList(list, false);
		return list;
	}

	/**
	 * 处理查询前的参数 <功能详细描述>
	 *
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<InvestorsHoldInfoRegist001Dto> investList = JsonUtils.json2List(keyList,
						InvestorsHoldInfoRegist001Dto.class);
				pageParam.put("investList", investList);
			} catch (IOException e) {
//				logger.error(e.getMessage(),e);
				logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 数据状态
			String status = (String) pageParam.get("status");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 报送状态
			String orgCode = (String) pageParam.get("orgCode");
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 产品登记编码
			String prdRegistCode = (String) pageParam.get("prdRegistCode");
			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList",
						StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			// 将机构代码当做多选下拉框处理
			if (StringUtil.isNotBlank(orgCode)) {
				pageParam.put("orgCodeList", StringUtil.splitToList(GlobalConstant.COMMA, orgCode));
			}
			// 将产品登记编码当做多选下拉框处理
			if (StringUtil.isNotBlank(prdRegistCode)) {
				pageParam.put("prdRegistCodeList", StringUtil.splitToList(GlobalConstant.COMMA, prdRegistCode));
			}
		}
	}

	/**
	 * 处理查询结果 <功能详细描述>
	 *
	 * @param list
	 *            待处理数据
	 * @param isExport
	 *            是否是导出
	 * @see
	 * @since 1.0
	 */
	private void dealDataList(List<InvestorsHoldInfoRegist001Dto> list, boolean isExport) {
		list.forEach(item -> {
			item.setSendStatusCode(item.getSendStatus());
			item.setStatusCode(item.getStatus());
			item.setCcyCode(item.getCcy());
			item.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
			item.setSendStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态, item.getSendStatus()));
			if(!isExport){
				item.setCcy(SysDataUtil.getNameFromDataDict(DictCode.币种_中债, item.getCcy()));
			}
		});
	}

	/**
	 * excel导入功能
	 *
	 * @param fileName
	 *            文件名
	 * @return 错误信息
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> importData(List<List<String>> dataList,String orgCode) {
		List<ExcelValidDto> checkResult = new ArrayList<ExcelValidDto>();
		if (!StringUtil.isEmptyList(dataList)) {
			if (CollectionUtils.isNotEmpty(dataList)) {
				List<InvestorsHoldInfoRegist001Dto> entities = new ArrayList<>(dataList.size());
				checkResult = convertToEntity(dataList, entities,orgCode);
				if (StringUtil.isEmptyList(checkResult)) {
					batchInsertData(entities);
				}
				return checkResult;
			}
		}
		checkResult.add(
				new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), this.getMessage("REPORT_COMMON_FILE_E_0016")));
		return checkResult;
	}

	/**
	 * 批量插入投资者持有信息登记表
	 *
	 * @param list
	 *            数据集
	 * @see
	 * @since 1.0
	 */
	public void batchInsertData(List<InvestorsHoldInfoRegist001Dto> list) {
		if (CollectionUtils.isNotEmpty(list)) {

			List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, list.size());

			// list.forEach(item -> {

			for (int i = 0; i < list.size(); i++) {
				InvestorsHoldInfoRegist001Dto item = list.get(i);

				item.setRegistSeqno(// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
						CommonUtil.getSequenceStr(sequenceList, i));
				Map<String, Object> map = new HashMap<>();
				map.put("prdRegistCode", item.getPrdRegistCode());
				map.put("registBankCode", item.getRegistBankCode());
				map.put("ccy", item.getCcy());
				map.put("indentifyMark", item.getIndentifyMark());
				map.put("holdDate", DateUtil.format(item.getHoldDate()));
				Integer num = investorsHoldInfoRegist001Mapper.selectIsExsit(map);
				if (num != 0) {
					chbInvestholdInfoSubMapper.updateByPrimaryKeySelective(item);
				} else {
					chbInvestholdInfoSubMapper.insertSelective(item);
				}
			}
		
		}
	}

	/**
	 * excel数据转为java对象
	 * <p>
	 * 将excel导入的数据，按行转为EquityInfoRegist001Dto对象，同时验证各字段值
	 * </p>
	 *
	 * @param dataList
	 *            excel导入的数据
	 * @param entities
	 *            转化后的列表
	 * @return 验证信息
	 */
	private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,
												List<InvestorsHoldInfoRegist001Dto> entities,String orgCode) {
		List<ExcelValidDto> checkResult = new ArrayList<>();

		List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, dataList.size());

		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			if (null == list || list.size() != 8) {
				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
						this.getMessage("REPORT_COMMON_FILE_E_0021", "投资者持有信息登记导入")));
				continue;
			}
			InvestorsHoldInfoRegist001Dto entity = new InvestorsHoldInfoRegist001Dto();
			entity.setRegistBankCode(list.get(0));
			entity.setPrdRegistCode(list.get(1));
			entity.setIndentifyMark(list.get(2));
			entity.setHoldDate(DateUtil.parse(list.get(3)));
			entity.setCcy(list.get(4));
			entity.setQty(new BigDecimal(list.get(5)));
			entity.setAmount(new BigDecimal(list.get(6)));
			entity.setCnyAmt(new BigDecimal(list.get(7)));
			entity.setSendStatus(DictCodeListExt2.报送状态_手工处理.getValue());
			entity.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
			entity.setOrgCode(orgCode);
			entity.setSumitSeq(// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
					CommonUtil.getSequenceStr(sequenceList, i));
			entities.add(entity);
		}
		return checkResult;
	}

	/**
	 * 导出功能查询
	 *
	 * @param pageParam
	 *            查询参数
	 * @return 结果集
	 * @see
	 * @since 1.0
	 */

	@SuppressWarnings("unchecked")
	public List<InvestorsHoldInfoRegist001Dto> selectAllList(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		List<InvestorsHoldInfoRegist001Dto> dataList =  (List<InvestorsHoldInfoRegist001Dto>) pageParam.get("investList");// 查询参数列表

		List<InvestorsHoldInfoRegist001Dto> dtoList = new ArrayList<InvestorsHoldInfoRegist001Dto>();// 存放查询结果集
		if(CollectionUtils.isNotEmpty(dataList)){
			selectAllDataBach(dataList,dtoList);
		}
		else{
			dtoList = investorsHoldInfoRegist001Mapper.selectAllData(pageParam);
		}

//		if (StringUtil.isNotEmpty(pageParam.get("flag"))
//				&& !StringUtil.equals(pageParam.get("flag"), "0") & !StringUtil.equals(pageParam.get("flag"), "2")) {
//			dealDataList(dtoList, true);
//		}
		return dtoList;
	}

	/**
	 * 分批次执行selectAllDataBatch
	 *
	 * @param dataList  数据
	 * @param dtoList 返回结果集
	 * @see
	 * @since 1.0
	 */
	private void selectAllDataBachSend(List<InvestorsHoldInfoRegist001Dto> dataList, List<InvestorsHoldInfoRegist001Dto> dtoList) {
		if(StringUtil.isEmptyList(dataList)) {
			return;
		}
		int maxDataCountOneTime=this.parsToInt(reportSelectOrDeleteCount, "2");
		//查询条数
		int count=dataList.size();
		if(count <= maxDataCountOneTime) {
			logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据开始start===");
//			dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataByList(true,dataList));
			dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataKeyByList(dataList));
			logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据结束End===");
			return;
		}

		// 获取所有持有日期
		Map<String, String> holdDateMap = new HashMap<String, String>();
		for (InvestorsHoldInfoRegist001Dto tempDataDto : dataList) {
			String holdDateStr = DateUtil.format(tempDataDto.getHoldDate());
			if (!holdDateMap.containsKey(holdDateStr)) {
				holdDateMap.put(holdDateStr, holdDateStr);
			}
		}

		int dataCount = 0;
		for (String holdDateStr : holdDateMap.keySet()) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("holdDateStr", holdDateStr);
			dataCount = dataCount + investorsHoldInfoRegist001Mapper.selectAlreadySubmitDataCount(paramMap);
		}

		if (dataCount == 0) {
			return;
		}

		List<InvestorsHoldInfoRegist001Dto> allSendDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		for (String holdDateStr : holdDateMap.keySet()) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("holdDateStr", holdDateStr);
			allSendDataList.addAll(investorsHoldInfoRegist001Mapper.selectAlreadySubmitData(paramMap));
		}

		if (allSendDataList.size() == 0) {
			return;
		}

		// 报送数据key
		Map<String, String> dataKeyMap = dataList.stream().collect(
				JoyinCollectors.toMap(x -> x.getRegistBankCode()
								+ GlobalConstant.UNDER_LINE + x.getPrdRegistCode()
								+ GlobalConstant.UNDER_LINE + x.getIndentifyMark()
								+ GlobalConstant.UNDER_LINE + x.getCcy()
								+ GlobalConstant.UNDER_LINE + DateUtil.format(x.getHoldDate()),
						x -> x.getRegistBankCode()
								+ GlobalConstant.UNDER_LINE + x.getPrdRegistCode()
								+ GlobalConstant.UNDER_LINE + x.getIndentifyMark()
								+ GlobalConstant.UNDER_LINE + x.getCcy()
								+ GlobalConstant.UNDER_LINE + DateUtil.format(x.getHoldDate())));
		for (InvestorsHoldInfoRegist001Dto x : allSendDataList) {
			String dataKey = x.getRegistBankCode()
					+ GlobalConstant.UNDER_LINE + x.getPrdRegistCode()
					+ GlobalConstant.UNDER_LINE + x.getIndentifyMark()
					+ GlobalConstant.UNDER_LINE + x.getCcy()
					+ GlobalConstant.UNDER_LINE + DateUtil.format(x.getHoldDate());

			if (dataKeyMap.containsKey(dataKey)) {
				dtoList.add(x);
			}
		}

//		List<InvestorsHoldInfoRegist001Dto> newDataList=new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		newDataList.addAll(dataList);
//		// 分批数
//		int part=count/maxDataCountOneTime;
//
//        for (int i = 0; i < part; i++ ) {
//            logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据开始start===");
//        	dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataKeyByList(newDataList.subList(0, maxDataCountOneTime)));
//        	logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据结束End");
//            newDataList.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(newDataList)) {
//            logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据开始start===");
//        	dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataKeyByList(newDataList));
//        	logger.info("investorsHoldInfoRegist001Mapper.selectAllDataKeyByList单次查询数据结束End");
//        }
	}
	/**
	 * 分批次执行selectAllDataBatch
	 *
	 * @param dataList  数据
	 * @param dtoList 返回结果集
	 * @see
	 * @since 1.0
	 */
	private void selectAllDataBach(List<InvestorsHoldInfoRegist001Dto> dataList, List<InvestorsHoldInfoRegist001Dto> dtoList) {
		if(StringUtil.isEmptyList(dataList)) {
			return;
		}
		int maxDataCountOneTime=this.parsToInt(reportSelectOrDeleteCount, "2");
		//查询条数
		int count=dataList.size();
		if(count<=maxDataCountOneTime) {
			dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataByList(false,dataList));
			return;
		}

		List<InvestorsHoldInfoRegist001Dto> newDataList=new ArrayList<InvestorsHoldInfoRegist001Dto>();
		newDataList.addAll(dataList);
		// 分批数
		int part=count/maxDataCountOneTime;

		for (int i = 0; i < part; i++ ) {
			dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataByList(false,newDataList.subList(0, maxDataCountOneTime)));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			dtoList.addAll(investorsHoldInfoRegist001Mapper.selectAllDataByList(false,newDataList));
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
		}

		// 设置查询参数
		dealSearchParam(pageParam);

		String flag = (String) pageParam.get("flag");
		String configFile = null;
		// 导出中债excel
		if (StringUtil.equals("0", flag)) {
			configFile = "/com/joyintech/tams/report/submitmanagement/investorsholdinforegist/conf/export_conf.xml";
		} else if (StringUtil.equals("1", flag)) {
			configFile = "/com/joyintech/tams/report/submitmanagement/investorsholdinforegist/conf/export_conf_all.xml";
		} else if (StringUtil.equals("2", flag)) {
			configFile = "/com/joyintech/tams/report/submitmanagement/investorsholdinforegist/conf/export_conf_del.xml";
		}


		exportParam.config(configFile).select(() -> investorsHoldInfoRegist001Mapper.selectAllData(pageParam));

	}

	@Override
	public com.joyintech.framework.grid.Page<InvestorsHoldInfoRegist001Dto> selectPageData(Map<String, Object> pageParam) {
		List<InvestorsHoldInfoRegist001Dto> data = simpleRestTemplate.postForList(
				SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfoRegist/selectAllData",pageParam,
				InvestorsHoldInfoRegist001Dto.class);
		com.joyintech.framework.grid.Page<InvestorsHoldInfoRegist001Dto> page = new com.joyintech.framework.grid.Page<InvestorsHoldInfoRegist001Dto>();
		page.setRoot(data);
//        page.setTotal(data.size());
//        return  selectForPage(pageParam);
		return page;
	}

	public List<InvestorsHoldInfoRegist001Dto> selectAllData(Map<String,Object> map){
		return investorsHoldInfoRegist001Mapper.selectAllData(map);
	}


	@Override
	protected void beforeCreateExcel(List<InvestorsHoldInfoRegist001Dto> contents) {
		String flag = (String) RequestUtil.getPageParam().get("flag");
		if (StringUtil.isNotEmpty(flag) && !StringUtil.equals(flag, "0") & !StringUtil.equals(flag, "2")) {
			dealDataList(contents, true);
		}
		if (StringUtil.isNotEmpty(flag) && StringUtil.equals(flag, "0") ) {
			dealDataList(contents, true);
			dealBigDecmailData(contents);
		}
	}
	private void dealBigDecmailData(List<InvestorsHoldInfoRegist001Dto> contents) {
		contents.forEach(item -> {
			item.setAmountExportExcel((item.getAmount()==null?null:item.getAmount().setScale(2,RoundingMode.HALF_UP).toString()));
			item.setQtyExportExcel((item.getQty()==null?null:item.getQty().setScale(5,RoundingMode.HALF_UP).toString()));
			item.setCnyAmtExportExcel((item.getCnyAmt()==null?null:item.getCnyAmt().setScale(2,RoundingMode.HALF_UP).toString()));
		});
	}

	/**
	 * 报送前检查
	 *
	 * @param checkKeys
	 *            检查数据的关键字
	 * @return 检查结果
	 * @see
	 * @since 1.0
	 */
	public List<ExcelValidDto> sendBeforeCheck(Map<String, Object> map,boolean flag) {
		List<ExcelValidDto> checkResult = new ArrayList<>();
		dealSearchParam(map);
		@SuppressWarnings("unchecked")
		List<InvestorsHoldInfoRegist001Dto> paramList = (List<InvestorsHoldInfoRegist001Dto>) map.get("investList");
		List<String> message = new ArrayList<>();
		Integer i = 1;
		if (CollectionUtils.isNotEmpty(paramList)) {
			for (InvestorsHoldInfoRegist001Dto dto : paramList) {
				if (StringUtil.isEmpty(dto.getPrdRegistCode())) {
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "产品登记编码"));
				}
				if (StringUtil.isEmpty(dto.getRegistBankCode())) {
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "登记银行代码"));
				}
				if (StringUtil.isEmpty(dto.getIndentifyMark())) {
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "识别标识"));
				}
				if (StringUtil.isEmpty(dto.getCcy())) {
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "币种"));
				}
				if (dto.getHoldDate() == null) {
					message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "持有日期"));
				}
				if (CollectionUtils.isNotEmpty(message)) {
					checkResult.add(new ExcelValidDto(dto.getIndentifyMark(), String.valueOf(i),
							StringUtil.joinString(message, " "), null));
					return checkResult;
				}
				i++;
			}
		}
		map.put("sendStatus", DictCodeListExt2.报送状态_未报送.getValue());
		// 所有需要进行数据检查的结果list
		List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList  = new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		= investorsHoldInfoRegist001Mapper
//				.selectAllData(map);
		if(CollectionUtils.isNotEmpty(paramList)){
			selectAllDataBach(paramList,investorsHoldInfoRegistList);
		}
		else{
			// modify by etliu 20180818 start
//			investorsHoldInfoRegistList = investorsHoldInfoRegist001Mapper.selectAllData(map);
			@SuppressWarnings("unchecked")
			List<InvestorsHoldInfoRegist001Dto> batchExeDataList = (List<InvestorsHoldInfoRegist001Dto>) map.get("batchExeDataList");
			if (StringUtil.isEmptyList(batchExeDataList)) {
				investorsHoldInfoRegistList = investorsHoldInfoRegist001Mapper.selectAllData(map);
			} else {
				investorsHoldInfoRegistList = batchExeDataList;
			}
			// modify by etliu 20180818 end
		}
		// 存储已经批量进行过数据检查的数据
		List<String> isCheckedDataList = new ArrayList<>();
		// 存储没有进行数据检查的数据
		List<InvestorsHoldInfoRegist001Dto> isNeedCheckDataList = new ArrayList<>();
		// 存储存在逻辑检查的数据，已删除报送前检查中存在的结果
		List<String> logicCheckResult = new ArrayList<>();
		if (CollectionUtils.isEmpty(investorsHoldInfoRegistList) && !StringUtil.equals("02", map.get("isCheck"))) {
			checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据", null));
		} else {
			for (InvestorsHoldInfoRegist001Dto dto : investorsHoldInfoRegistList) {
				if (!StringUtil.equals("0", dto.getCheckSeqno()) && StringUtil.isNotEmpty(dto.getCheckSeqno())
						&& !StringUtil.equals(dto.getIsLogicCheck(), DictCodeList.是否_中债_是.getValue())) {
					isCheckedDataList.add(dto.getCheckSeqno());
				} else {
					if (StringUtil.equals(dto.getIsLogicCheck(), DictCodeList.是否_中债_是.getValue())) {
						logicCheckResult.add(dto.getCheckSeqno());
					}
					isNeedCheckDataList.add(dto);
				}
			}
			int maxCount=this.parsToInt(reportInsertCount, "3");
			logger.info("查询已检查的检查结果开始====="+DateUtil.getCurrentDate());
			// 已检查的检查结果直接在CHB_REPORT_CHECK中查询
			if (CollectionUtils.isNotEmpty(isCheckedDataList)) {
				// 查询分批处理（每次最多处理100条）
				if (isCheckedDataList.size() < maxCount) {
					checkResult = checkReport001Mapper.selectByCheckSeqno(isCheckedDataList);
				} else {
					int part = isCheckedDataList.size() / maxCount;
					for (int j = 0; j < part; j++) {
						checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList.subList(0, maxCount)));
						isCheckedDataList.subList(0, maxCount).clear();
					}
					if (CollectionUtils.isNotEmpty(isCheckedDataList)) {
						checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList));
					}
				}
//				checkResult = checkReport001Mapper.selectByCheckSeqno(isCheckedDataList);
			}
			logger.info("查询已检查的检查结果结束====="+DateUtil.getCurrentDate());
			// 对已经检查过的并且存在逻辑检查的删除在CHB_REPORT_CHECK中的信息
			logger.info("对已经检查过的并且存在逻辑检查的删除开始====="+logicCheckResult.size());
			if(flag){
				checkReport001Mapper.delReportAllCheckDatas(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
			}
			else {
				if (CollectionUtils.isNotEmpty(logicCheckResult)) {
					// 删除报送前检查的数据
					if (CollectionUtils.isNotEmpty(logicCheckResult)) {
						if (logicCheckResult.size() < maxCount) {
							checkReport001Mapper.delReportCheckDatas(logicCheckResult);
						} else {
							int part = logicCheckResult.size() / maxCount;
							for (int k = 0; k < part; k++) {
								checkReport001Mapper.delReportCheckDatas(logicCheckResult.subList(0, maxCount));
								logicCheckResult.subList(0, maxCount).clear();
							}
							if (CollectionUtils.isNotEmpty(logicCheckResult)) {
								checkReport001Mapper.delReportCheckDatas(logicCheckResult);
							}
						}
					}
				}
			}
			logger.info("对已经检查过的并且存在逻辑检查的删除结束====="+logicCheckResult.size());
			// 进行数据检查并对结果进行相应处理
			if (CollectionUtils.isNotEmpty(isNeedCheckDataList)) {
				logger.info("进行数据检查开始====="+isNeedCheckDataList.size());
				List<ExcelValidDto> newCheckedInfo = validate.checkData(isNeedCheckDataList,
						(String) map.get("isCheck"));
				logger.info("进行数据检查结束====="+isNeedCheckDataList.size());
				checkResult.addAll(newCheckedInfo);
				// 组装结果集
				List<CheckReport001Dto> resultList = new ArrayList<>();
				// 投资者持有信息
				List<InvestorsHoldInfoRegist001Dto> investRegistUpdateList = new ArrayList<>();
				newCheckedInfo.forEach(item -> {
					if(flag&&!IS_SUCESS_INVESTORS_CHECK.equals
							(SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()))){
						CheckReport001Dto chbReportCheck = new CheckReport001Dto();
						BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
						//chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
						chbReportCheck.setCheckSeqno(IDGenerator.generate());
						chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
						chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者持有信息登记.getCodeName());
						chbReportCheck.setCheckResult(
								SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
						resultList.add(chbReportCheck);

						// 更新检查序列号对应的数据
						InvestorsHoldInfoRegist001Dto dto = new InvestorsHoldInfoRegist001Dto();
						List<String> keyList = StringUtil.splitToList(GlobalConstant.COMMA, item.getValidKey());
						dto.setPrdRegistCode(keyList.get(0).substring(6).trim());
						dto.setIndentifyMark(keyList.get(1).substring(4).trim());
						dto.setHoldDate(DateUtil.parse(keyList.get(2).substring(4)));
						dto.setCcy(keyList.get(3).substring(2).trim());
						dto.setRegistBankCode(keyList.get(4).substring(6).trim());
						dto.setCheckSeqno(chbReportCheck.getCheckSeqno().trim());
						investRegistUpdateList.add(dto);
					}
					else if(!flag){
						CheckReport001Dto chbReportCheck = new CheckReport001Dto();
						BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
						//chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
						chbReportCheck.setCheckSeqno(IDGenerator.generate());
						chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
						chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者持有信息登记.getCodeName());
						chbReportCheck.setCheckResult(
								SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
						resultList.add(chbReportCheck);

						// 更新检查序列号对应的数据
						InvestorsHoldInfoRegist001Dto dto = new InvestorsHoldInfoRegist001Dto();
						List<String> keyList = StringUtil.splitToList(GlobalConstant.COMMA, item.getValidKey());
						dto.setPrdRegistCode(keyList.get(0).substring(6).trim());
						dto.setIndentifyMark(keyList.get(1).substring(4).trim());
						dto.setHoldDate(DateUtil.parse(keyList.get(2).substring(4)));
						dto.setCcy(keyList.get(3).substring(2).trim());
						dto.setRegistBankCode(keyList.get(4).substring(6).trim());
						dto.setCheckSeqno(chbReportCheck.getCheckSeqno().trim());
						investRegistUpdateList.add(dto);
					}
				});
				logger.info("进行数据存储开始====="+resultList.size()+"====时间===="+DateUtil.getCurrentDate());
				// 由于resultList与equityInfoRegistUpdateList的size相同，故size只判断一个
				if (CollectionUtils.isNotEmpty(resultList)) {
					// 查询分批处理（每次最多处理maxCount条）
					if (resultList.size() < maxCount) {
						// 将新生成的检查信息插入报送前检查表
						checkReport001Mapper.batchInsert(resultList);
						// 批量更新投资者身份信息检查序列号
						investorsHoldInfoRegist001Mapper.updateInvestRegistCheckSeqno(investRegistUpdateList);
					} else {
						int part = resultList.size() / maxCount;
						for (int j = 0; j < part; j++) {
							// 将新生成的检查信息插入报送前检查表
							checkReport001Mapper.batchInsert(resultList.subList(0, maxCount));
							// 批量更新投资者身份信息检查序列号
							investorsHoldInfoRegist001Mapper.updateInvestRegistCheckSeqno(investRegistUpdateList.subList(0, maxCount));
							resultList.subList(0, maxCount).clear();
							investRegistUpdateList.subList(0, maxCount).clear();
						}
						if (CollectionUtils.isNotEmpty(resultList)) {
							// 将新生成的检查信息插入报送前检查表
							checkReport001Mapper.batchInsert(resultList);
							// 批量更新投资者身份信息检查序列号
							investorsHoldInfoRegist001Mapper.updateInvestRegistCheckSeqno(investRegistUpdateList);
						}
					}
				}
				logger.info("进行数据存储结束====="+resultList.size()+"====时间===="+DateUtil.getCurrentDate());
				// 将新生成的检查信息插入报送前检查表
				// checkReport001Mapper.batchInsert(resultList);
				// // 批量更新投资者持有信息检查序列号
				// checkReport001Mapper.updateInvestRegistCheckSeqno(investRegistUpdateList);
			}
		}

		/**
		 * add by dengyu 无用代码
		 * 
		 * List<InvestorsHoldInfoRegist001Dto>
		 * cklist=investorsHoldInfoDistinctService.checkBeforeSubmit(null,"01");
		 * if(cklist!=null&&!cklist.isEmpty()){
		 * for(InvestorsHoldInfoRegist001Dto single:cklist){
		 * for(Iterator<ExcelValidDto> it=checkResult.iterator();it.hasNext();){
		 * ExcelValidDto excelValidDto=it.next(); String validKey = "产品登记编码
		 * "+single.getPrdRegistCode()+GlobalConstant.COMMA +"识别标识
		 * "+single.getIndentifyMark()+GlobalConstant.COMMA +"持有日期
		 * "+DateUtil.format(single.getHoldDate(),"yyyy-MM-dd")+GlobalConstant.COMMA
		 * +"币种 "+single.getCcy()+GlobalConstant.COMMA +"登记银行代码
		 * "+single.getRegistBankCode();
		 * if(excelValidDto.getValidKey().equals(validKey)){ it.remove(); } } }
		 * for(InvestorsHoldInfoRegist001Dto single:cklist){ String validKey =
		 * "产品登记编码 "+single.getPrdRegistCode()+GlobalConstant.COMMA +"识别标识
		 * "+single.getIndentifyMark()+GlobalConstant.COMMA +"持有日期
		 * "+DateUtil.format(single.getHoldDate(),"yyyy-MM-dd")+GlobalConstant.COMMA
		 * +"币种 "+single.getCcy()+GlobalConstant.COMMA +"登记银行代码
		 * "+single.getRegistBankCode(); ExcelValidDto ed=new ExcelValidDto();
		 * ed.setValidKey(validKey); ed.setErrorInfo("检查不通过，该投资者存在多个识别标识！");
		 * checkResult.add(ed); } }
		 */

		investorsHoldInfoDistinctService.checkBeforeSubmit();

		if (StringUtil.isEmpty((String) map.get("isCheck"))) {
			if (CollectionUtils.isNotEmpty(checkResult)) {
				checkResult = checkResult.parallelStream().filter(dto -> {
					return StringUtil.equals(dto.getCheckResult(), DictCodeListExt2.检查结果_检查不通过.getCodeName());
				}).collect(Collectors.toList());
			}
		}
		return checkResult;
	}

	/**
	 * 获取报送列表
	 *
	 * @param param
	 *            界面查询条件
	 * @return map
	 * @see
	 * @since 1.0
	 */
	public Map<String, List<InvestorsHoldInfoRegist001Dto>> getSubmitList(Map<String, Object> map) {
		dealSearchParam(map);
		@SuppressWarnings("unchecked")
		List<InvestorsHoldInfoRegist001Dto> paramList = (List<InvestorsHoldInfoRegist001Dto>) map.get("investList");

		List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		= investorsHoldInfoRegist001Mapper
//				.selectAllData(map);
		if(CollectionUtils.isNotEmpty(paramList)){
			selectAllDataBach(paramList,investorsHoldInfoRegistList);
		}
		else{
			// modify by etliu 20180818 start
//			investorsHoldInfoRegistList = investorsHoldInfoRegist001Mapper.selectAllData(map);
			@SuppressWarnings("unchecked")
			List<InvestorsHoldInfoRegist001Dto> batchExeNeedSendDataList = (List<InvestorsHoldInfoRegist001Dto>) map.get("batchExeNeedSendDataList");
			if (StringUtil.isEmptyList(batchExeNeedSendDataList)) {
				investorsHoldInfoRegistList = investorsHoldInfoRegist001Mapper.selectAllData(map);
			} else {
				investorsHoldInfoRegistList = batchExeNeedSendDataList;
			}
			// modify by etliu 20180818 end
		}

		Map<String, List<InvestorsHoldInfoRegist001Dto>> resultMap = new HashMap<>();
		List<InvestorsHoldInfoRegist001Dto> resultList = null;
		if (CollectionUtils.isNotEmpty(investorsHoldInfoRegistList)) {
			format(investorsHoldInfoRegistList);
			for (InvestorsHoldInfoRegist001Dto item : investorsHoldInfoRegistList) {
				if (CollectionUtils.isEmpty(resultMap.get(item.getRegistBankCode()))) {
					resultList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
				} else {
					resultList = resultMap.get(item.getRegistBankCode());
				}
				resultList.add(item);
				resultMap.put(item.getRegistBankCode(), resultList);
			}
			;
		}
		return resultMap;
	}

	/**
	 * 数据字典信息格式化
	 *
	 * @param investorsHoldInfoRegistList
	 *            数据来源
	 * @see
	 * @since 1.0
	 */
	private void format(List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList) {
		investorsHoldInfoRegistList.forEach(item -> {
			item.setSendStatus(getDictCodeAndName(DictCode.报送状态, item.getSendStatus()));
		});
	}

	/**
	 * 转换成key+value形式
	 *
	 * @param DictCode
	 *            字典表中数据类型
	 * @param value
	 *            需要换换的值
	 * @return key+value
	 * @see
	 * @since 1.0
	 */
	public static String getDictCodeAndName(DictCode dictCode, String value) {
		if (StringUtil.isNotEmpty(value)) {
			return value + " " + SysDataUtil.getNameFromDataDict(dictCode, value);
		}
		return "";
	}




	/**
	 * 分批次执行updateInterfSeq
	 *
	 * @param investList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void updateInterfSeqBatch(List<InvestorsHoldInfoRegist001Dto> investList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(investList)) {
			return;
		}

		int maxDataCountOneTime = Integer.parseInt(reportInsertCount);
		int dataCount = investList.size();
		if (dataCount <= maxDataCountOneTime) {
			// modify by etliu 20180818 start
//			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeq(sendStatus, investList);
//			;
			investorsHoldInfoRegist001Mapper.updateInterfSeq(investList);
			// modify by etliu 20180818 end
			return;
		}


		List<InvestorsHoldInfoRegist001Dto> newDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		newDataList.addAll(investList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			// modify by etliu 20180818 start
//			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeq(sendStatus,
//					newDataList.subList(0, maxDataCountOneTime));
			logger.info("单次更新登记表start===");
			investorsHoldInfoRegist001Mapper.updateInterfSeq(newDataList.subList(0, maxDataCountOneTime));
			// modify by etliu 20180818 end
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			// modify by etliu 20180818 start
			logger.info("单次更新登记表start===");
			investorsHoldInfoRegist001Mapper.updateInterfSeq(newDataList);
			logger.info("单次更新登记表end===");

//			;
//		    investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			// modify by etliu 20180818 end
		}
	}
	/**
	 * 分批次执行updateInterfSubmitAndSeq
	 *
	 * @param investList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<InvestorsHoldInfoRegist001Dto> investList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(investList)) {
			return;
		}

		int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
		int dataCount = investList.size();
		if (dataCount <= maxDataCountOneTime) {
			// modify by etliu 20180818 start
//			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeq(sendStatus, investList);
//			;
			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
			// modify by etliu 20180818 end
			return;
		}

		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		if (isSendAllData) {
			logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
			investorsHoldInfoRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
			logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");

			return;
		}

		List<InvestorsHoldInfoRegist001Dto> newDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		newDataList.addAll(investList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			// modify by etliu 20180818 start
//			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeq(sendStatus,
//					newDataList.subList(0, maxDataCountOneTime));
			logger.info("单次更新登记表start===");
			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
					newDataList.subList(0, maxDataCountOneTime));
			// modify by etliu 20180818 end
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			// modify by etliu 20180818 start
			logger.info("单次更新登记表start===");
			investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			logger.info("单次更新登记表end===");

//			;
//		    investorsHoldInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			// modify by etliu 20180818 end
		}
	}

	/**
	 * 获取报送列表
	 *
	 * @param equiytRegistList
	 *            条件
	 * @see
	 * @since 1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void doUpdateSubmtitStatus(List<InvestorsHoldInfoRegist001Dto> investList, boolean isSendAllData, List<InvestorsHoldInfoRegist001Dto> unSeqList) {
		// modfiy by etliu 20180818 start
		// 检查报送的数据中是否存在已报送数据
//		Map<String, Object> map = new HashMap<>();
//		map.put("investList", investList);
//		map.put("checkFlag", true);
//		List<InvestorsHoldInfoRegist001Dto> list = new ArrayList<InvestorsHoldInfoRegist001Dto>();
////		= investorsHoldInfoRegist001Mapper.selectAllData(map);
//		this.selectAllDataBachSend(investList,list);
//		if (CollectionUtils.isNotEmpty(list)) {
//			// 将第一次报送的数据数据履历表
//			//investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(list);
//			this.insertInvestorsHoldInfoRegistHisBatch(list);
//			// 删除已报送数据
//			//investorsHoldInfoRegist001Mapper.deleteSubmitRegist(list);
//			this.deleteSubmitRegistBatch(list);
//		}
//		List<InvestorsHoldInfoRegist001Dto> unSumitList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		for (InvestorsHoldInfoRegist001Dto investorsHoldInfoRegist001Dto : investList) {
//			 if(StringUtil.isEmpty(investorsHoldInfoRegist001Dto.getSubmitFeedbackStatus())){
//				 unSumitList.add(investorsHoldInfoRegist001Dto);
//			 }
//		}
//	    this.deleteSubmitRegistBatch(investList);
//		logger.info("selectAllDataBachSend查询数据开始start===");
//		List<InvestorsHoldInfoRegist001Dto> list = new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		this.selectAllDataBachSend(unSumitList, list);
//		logger.info("selectAllDataBachSend查询数据结束end===");
//		if (CollectionUtils.isNotEmpty(list)) {
//			logger.info("deleteSubmitRegistBatch登记表数据删除start===");
//			this.deleteSubmitRegistBatch(list);
//			logger.info("deleteSubmitRegistBatch登记表数据删除end===");
//		}
		// modfiy by etliu 20180818 end
		if (isSendAllData) {
			logger.info("deleteAllAlreadySubmitRegistData删除重复已报送数据start===");
			investorsHoldInfoRegist001Mapper.deleteAllAlreadySubmitRegistData();
			logger.info("deleteAllAlreadySubmitRegistData删除重复已报送数据end===");
		}
		else {
			List<InvestorsHoldInfoRegist001Dto> unSumitList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
			for (InvestorsHoldInfoRegist001Dto investorsHoldInfoRegist001Dto : investList) {
				if (StringUtil.isEmpty(investorsHoldInfoRegist001Dto.getSubmitFeedbackStatus())) {
					unSumitList.add(investorsHoldInfoRegist001Dto);
				}
			}
			logger.info("selectAllDataBachSend查询数据开始start===");
			List<InvestorsHoldInfoRegist001Dto> list = new ArrayList<InvestorsHoldInfoRegist001Dto>();
			this.selectAllDataBachSend(unSumitList, list);
			logger.info("selectAllDataBachSend查询数据结束end===");
			if (CollectionUtils.isNotEmpty(list)) {
				logger.info("deleteSubmitRegistBatch登记表数据删除start===");
				this.deleteSubmitRegistBatch(list);
				logger.info("deleteSubmitRegistBatch登记表数据删除end===");
			}
		}

		// 批量更新报送状态
		// investorsHoldInfoRegist001Mapper.updateInterfSubmit("02", investList);
		// 批量更新报送状态与报送流水号
//		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_接口报送.getValue(), investList);
		logger.info("updateInterfSubmitAndSeq更新登记表start===");
		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_接口报送.getValue(), investList, isSendAllData);
		logger.info("updateInterfSubmitAndSeq更新登记表end===");
		//更新报送流水号为空或者重新报送的报送流水号
		logger.info("updateInterfSeq更新登记表start===");
		this.updateInterfSeqBatch(unSeqList, isSendAllData);
		logger.info("updateInterfSeq更新登记表end===");
	}
	/**
	 * 分批次执行deleteSubmitRegist
	 *
	 * @param investList
	 *            数据
	 * @see
	 * @since 1.0
	 */
	private void deleteSubmitRegistBatch(List<InvestorsHoldInfoRegist001Dto> investList) {
		if (StringUtil.isEmptyList(investList)) {
			return;
		}

		int maxDataCountOneTime = this.parsToInt(reportInsertCount, "3");
		int dataCount = investList.size();
		if (dataCount <= maxDataCountOneTime) {
//			investorsHoldInfoRegist001Mapper.deleteSubmitRegist(investList);
			investorsHoldInfoRegist001Mapper.deleteSubmitRegistByPK(investList);
			return;
		}

		List<InvestorsHoldInfoRegist001Dto> newDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		newDataList.addAll(investList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
//			investorsHoldInfoRegist001Mapper.deleteSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
			investorsHoldInfoRegist001Mapper.deleteSubmitRegistByPK(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
//			investorsHoldInfoRegist001Mapper.deleteSubmitRegist(newDataList);
			investorsHoldInfoRegist001Mapper.deleteSubmitRegistByPK(newDataList);
		}
	}
	/**
	 * 分批次执行insertInvestorsHoldInfoRegistHis
	 *
	 * @param investList
	 *            数据
	 * @see
	 * @since 1.0
	 */
//	private void insertInvestorsHoldInfoRegistHisBatch(List<InvestorsHoldInfoRegist001Dto> investList) {
//		if (StringUtil.isEmptyList(investList)) {
//			return;
//		}
//
//		int maxDataCountOneTime = this.parsToInt(reportInsertCount, "3");
//		int dataCount = investList.size();
//		if (dataCount <= maxDataCountOneTime) {
//			investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(investList);
//			return;
//		}
//
//		List<InvestorsHoldInfoRegist001Dto> newDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
//		newDataList.addAll(investList);
//
//		// 分批处理
//		int part = dataCount / maxDataCountOneTime; // 分批数
//		for (int i = 0; i < part; i++) {
//			investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(newDataList.subList(0, maxDataCountOneTime));
//			newDataList.subList(0, maxDataCountOneTime).clear();
//		}
//		// 最后剩下的数据
//		if (CollectionUtils.isNotEmpty(newDataList)) {
//			investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(newDataList);
//		}
//	}
	/**
	 * <一句话功能简述> <功能详细描述>
	 *
	 * @param upKey
	 * @see
	 * @since 1.0
	 */
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		// 权益人报送表主键
		@SuppressWarnings("unchecked")
		List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList = (List<InvestorsHoldInfoRegist001Dto>) map
				.get("investList");
		// 报送状态表主键
		ChbSubmitStatus chbSubmitStatus = null;
		List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
        List<InvestorsHoldInfoRegist001Dto> alreadySendList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
        List<InvestorsHoldInfoRegist001Dto> notSendList = new ArrayList<InvestorsHoldInfoRegist001Dto>();

		List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理,
				investorsHoldInfoRegistList.size());
		int index = 0;
		for (InvestorsHoldInfoRegist001Dto dto : investorsHoldInfoRegistList) {
			// 报送状态表插入组装
			chbSubmitStatus = new ChbSubmitStatus();
			if (StringUtil.isEmpty(dto.getSumitSeq())) {
				chbSubmitStatus.setSeqno(
						// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
						CommonUtil.getSequenceStr(sequenceList, index));
				index++;
				dto.setSumitSeq(chbSubmitStatus.getSeqno());
			} else {
				chbSubmitStatus.setSeqno(dto.getSumitSeq());
			}
			chbSubmitStatus.setSubmitFeedbackStatus(DictCodeListExt2.报送状态_手工处理.getValue());
			chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());

			if (StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(), dto.getSendStatus())) {
				listStatus.add(chbSubmitStatus);
				notSendList.add(dto);
			}
			else {
				sumitSeqList.add(dto.getSumitSeq());
				alreadySendList.add(dto);
			}
		}
		// 检查报送的数据中是否存在已报送数据
		map.put("checkFlag", true);

		// 未报送的数据 插入报送状态表
		if (!StringUtil.isEmptyList(listStatus)) {
			// 分批处理
			int maxDataCountOneTime = 50;
			int dataCount = listStatus.size();
			if (dataCount <= maxDataCountOneTime) {
				investorsHoldInfoRegist001Mapper.insertSubmitStatus(listStatus);
			}
			else {
				List<ChbSubmitStatus> newDataList = new ArrayList<ChbSubmitStatus>();
				newDataList.addAll(listStatus);

				// 分批处理
				int part = dataCount / maxDataCountOneTime; // 分批数
				for (int i = 0; i < part; i++ ) {
					investorsHoldInfoRegist001Mapper.insertSubmitStatus(newDataList.subList(0, maxDataCountOneTime));
					newDataList.subList(0, maxDataCountOneTime).clear();
				}
				// 最后剩下的数据
				if (CollectionUtils.isNotEmpty(newDataList)) {
					investorsHoldInfoRegist001Mapper.insertSubmitStatus(newDataList);
				}
			}
		}

		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
		if (!StringUtil.isEmptyList(sumitSeqList)) {
			// 分批处理
			int maxDataCountOneTime = 300;
			int dataCount = sumitSeqList.size();
			if (dataCount <= maxDataCountOneTime) {
				investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
			}
			else {
				List<String> newDataList = new ArrayList<String>();
				newDataList.addAll(sumitSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
                }
            }
        }

		if (CollectionUtils.isNotEmpty(notSendList)) {
			// 讲第一次报送的数据数据履历表
			investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHisBetter(notSendList);
			// 删除已报送数据
			investorsHoldInfoRegist001Mapper.deleteSubmitRegistBetter(notSendList);
		}
		// add by zcw 优化end
		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), investorsHoldInfoRegistList, false);

	}
	
	/**
	 * 手工处理符合查询条件的全部数据
	 * @param map
	 * @author liubin
	 */
	@SuppressWarnings("unchecked")
	public void updateSumStausAll(Map<String, Object> map){
		//先查询出参数表的参数
		List<ChbBatchPara> chbBatchParas=chbBatchParaMapper.selectAllData();
		if(!StringUtil.isEmptyList(chbBatchParas)){
			for(ChbBatchPara single:chbBatchParas){
				if(single.getTaskId().equals(BatchBeanName.投资者持有信息登记手工处理.getTaskId())){
					Map<String,Object> ssmap=(Map<String,Object>)JSON.parse(single.getParas());
					map=ssmap;
				}
			}
		}

		dealSearchParam(map);

		List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		// add by zcw 优化start
		// investorsHoldInfoRegistList =
		// investorsHoldInfoRegist001Mapper.selectAllData(map);

    template.select(
				"com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dao.InvestorsHoldInfoRegist001Mapper.selectAllDataNoOrder",
				map, new ResultHandler() {
					@Override
					public void handleResult(ResultContext resultContext) {
						investorsHoldInfoRegistList
								.add((InvestorsHoldInfoRegist001Dto) resultContext.getResultObject());
					}
				});
//		  investorsHoldInfoRegistList =
//    investorsHoldInfoRegist001Mapper.selectAllDataNoOrder(map);
		// add by zcw 优化end
		// 报送状态表主键
		ChbSubmitStatus chbSubmitStatus = null;
		List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
        List<InvestorsHoldInfoRegist001Dto> alreadySendList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
        List<InvestorsHoldInfoRegist001Dto> notSendList = new ArrayList<InvestorsHoldInfoRegist001Dto>();

		List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理,
				investorsHoldInfoRegistList.size());
		int index = 0;

		for (InvestorsHoldInfoRegist001Dto dto : investorsHoldInfoRegistList) {
			// 报送状态表插入组装
			chbSubmitStatus = new ChbSubmitStatus();
			if (StringUtil.isEmpty(dto.getSumitSeq())) {
				chbSubmitStatus.setSeqno(
						// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
						CommonUtil.getSequenceStr(sequenceList, index));
				index++;
				dto.setSumitSeq(chbSubmitStatus.getSeqno());
			} else {
				chbSubmitStatus.setSeqno(dto.getSumitSeq());
			}
			chbSubmitStatus.setSubmitFeedbackStatus(DictCodeListExt2.报送状态_手工处理.getValue());
			chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());

			if (StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(), dto.getSendStatus())) {
				listStatus.add(chbSubmitStatus);
				notSendList.add(dto);
			} else {
				sumitSeqList.add(dto.getSumitSeq());
				alreadySendList.add(dto);
			}
		}
		// 检查报送的数据中是否存在已报送数据
		map.put("checkFlag", true);

		// 未报送的数据 插入报送状态表
		if (!StringUtil.isEmptyList(listStatus)) {
			// add by DENGYU 改成批量插入 20210623 begin

            this.insertBatch(listStatus);

			// // 分批处理
			// int maxDataCountOneTime = 50;
			// int dataCount = listStatus.size();
			// if (dataCount <= maxDataCountOneTime) {
			// investorsHoldInfoRegist001Mapper.insertSubmitStatus(listStatus);
			// }
			// else {
			// List<ChbSubmitStatus> newDataList = new
			// ArrayList<ChbSubmitStatus>();
			// newDataList.addAll(listStatus);
			//
			// // 分批处理
			// int part = dataCount / maxDataCountOneTime; // 分批数
			// for (int i = 0; i < part; i++ ) {
			// investorsHoldInfoRegist001Mapper.insertSubmitStatus(newDataList.subList(0,
			// maxDataCountOneTime));
			// newDataList.subList(0, maxDataCountOneTime).clear();
			// }
			// // 最后剩下的数据
			// if (CollectionUtils.isNotEmpty(newDataList)) {
			// investorsHoldInfoRegist001Mapper.insertSubmitStatus(newDataList);
			// }
			// }

			// add by DENGYU 改成批量插入 20210623 end

		}

		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
		if (!StringUtil.isEmptyList(sumitSeqList)) {

			// add by DENGYU 改成批量插入 20210623 end
			// // 分批处理
			// int maxDataCountOneTime = 300;
			// int dataCount = sumitSeqList.size();
			// if (dataCount <= maxDataCountOneTime) {
			// investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
			// }
			// else {
			// List<String> newDataList = new ArrayList<String>();
			// newDataList.addAll(sumitSeqList);
			//
			// // 分批处理
			// int part = dataCount / maxDataCountOneTime; // 分批数
			// for (int i = 0; i < part; i++ ) {
			// investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(newDataList.subList(0,
			// maxDataCountOneTime));
			// newDataList.subList(0, maxDataCountOneTime).clear();
			// }
			// // 最后剩下的数据
			// if (CollectionUtils.isNotEmpty(newDataList)) {
			// investorsHoldInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
			// }
			// }
			this.updateBatch(sumitSeqList);

			// add by DENGYU 改成批量插入 20210623 end
		}
		/* 手工处理增加留痕处理以及已报送数据登记表删除 add by yuluan 2019-03-29 begin */
		// add by zcw 优化start

		List<InvestorsHoldInfoRegist001Dto> dataMutiThreadList = selectAllInvestorsHoldInfoRegistDataMutiThread(investorsHoldInfoRegistList);
		
		this.insertInvestorsHoldInfoRegistHisBatch(dataMutiThreadList);

		/**
		 * SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd"); //
		 * List<InvestorsHoldInfoRegist001Dto> list =
		 * investorsHoldInfoRegist001Mapper.selectAllData(map); List<String>
		 * keys1 = new ArrayList<>(); for(InvestorsHoldInfoRegist001Dto
		 * single:investorsHoldInfoRegistList){ String
		 * key=single.getRegistBankCode()+GlobalConstant.UNDER_LINE
		 * +single.getIndentifyMark() +GlobalConstant.UNDER_LINE
		 * +single.getPrdRegistCode() +GlobalConstant.UNDER_LINE
		 * +single.getCcy()+GlobalConstant.UNDER_LINE
		 * +sdf.format(single.getHoldDate());
		 * 
		 * keys1.add(key); }
		 * 
		 * //investorsHoldInfoRegistList.stream().map(x ->
		 * x.getRegistBankCode()).collect(Collectors.toList());
		 * 
		 * 
		 * 
		 * if (CollectionUtils.isNotEmpty(investorsHoldInfoRegistList)) { int
		 * maxDataCountOneTime = 300; int dataCount = keys1.size(); if
		 * (dataCount <= maxDataCountOneTime) { // 讲第一次报送的数据数据履历表
		 * investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(keys1);
		 * // 删除已报送数据
		 * investorsHoldInfoRegist001Mapper.deleteSubmitRegist(keys1);
		 * 
		 * }else { List<String> newDataList = new ArrayList<String>();
		 * newDataList.addAll(keys1); // 分批处理 int part = dataCount /
		 * maxDataCountOneTime; // 分批数 for (int i = 0; i < part; i++ ) { //
		 * 讲第一次报送的数据数据履历表
		 * investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(newDataList.subList(0,
		 * maxDataCountOneTime)); // 删除已报送数据
		 * investorsHoldInfoRegist001Mapper.deleteSubmitRegist(newDataList.subList(0,
		 * maxDataCountOneTime)); newDataList.subList(0,
		 * maxDataCountOneTime).clear(); } // 最后剩下的数据 if
		 * (CollectionUtils.isNotEmpty(newDataList)) { // 讲第一次报送的数据数据履历表
		 * investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(newDataList);
		 * // 删除已报送数据
		 * investorsHoldInfoRegist001Mapper.deleteSubmitRegist(newDataList); } }
		 * }
		 * 
		 */
		/*
		 * if (CollectionUtils.isNotEmpty(investorsHoldInfoRegistList)) { int
		 * maxDataCountOneTime = 300; int dataCount =
		 * investorsHoldInfoRegistList.size(); if (dataCount <=
		 * maxDataCountOneTime) { // 将第一次报送的数据数据履历表
		 * investorsHoldInfoRegist001Mapper.
		 * insertInvestorsHoldInfoRegistHisBetter(investorsHoldInfoRegistList);
		 * // 删除已报送数据 investorsHoldInfoRegist001Mapper.deleteSubmitRegistBetter(
		 * investorsHoldInfoRegistList);
		 * 
		 * }else { List<InvestorsHoldInfoRegist001Dto> newDataList = new
		 * ArrayList<InvestorsHoldInfoRegist001Dto>();
		 * newDataList.addAll(investorsHoldInfoRegistList); // 分批处理 int part =
		 * dataCount / maxDataCountOneTime; // 分批数 for (int i = 0; i < part; i++
		 * ) { // 讲第一次报送的数据数据履历表 investorsHoldInfoRegist001Mapper.
		 * insertInvestorsHoldInfoRegistHisBetter(newDataList.subList(0,
		 * maxDataCountOneTime)); // 删除已报送数据
		 * investorsHoldInfoRegist001Mapper.deleteSubmitRegistBetter(newDataList
		 * .subList(0, maxDataCountOneTime)); newDataList.subList(0,
		 * maxDataCountOneTime).clear(); } // 最后剩下的数据 if
		 * (CollectionUtils.isNotEmpty(newDataList)) { // 讲第一次报送的数据数据履历表
		 * investorsHoldInfoRegist001Mapper.
		 * insertInvestorsHoldInfoRegistHisBetter(newDataList); // 删除已报送数据
		 * investorsHoldInfoRegist001Mapper.deleteSubmitRegistBetter(newDataList
		 * ); } } }
		 */
		// add by zcw 优化end
		/* 手工处理增加留痕处理以及已报送数据登记表删除 add by yuluan 2019-03-29 end */
		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), investorsHoldInfoRegistList, false);

		if(!StringUtil.isEmptyList(chbBatchParas)){
			for(ChbBatchPara single:chbBatchParas){
				chbBatchParaMapper.deleteByPrimaryKey(single.getParaId());
			}
		}
	}

	/**
	 * 先查出来需要入his表，及删除的数据，最后再做删除，一般情况下持有很少有修改的数据。
	 * 
	 * @param list
	 * @return
	 */

	private List<InvestorsHoldInfoRegist001Dto> selectAllInvestorsHoldInfoRegistDataMutiThread(List<InvestorsHoldInfoRegist001Dto> list) {

		List<InvestorsHoldInfoRegist001Dto> resultList = Collections.synchronizedList(new ArrayList<InvestorsHoldInfoRegist001Dto>());

		if (null != list && !list.isEmpty()) {

			List<Callable<Boolean>> tasks = new ArrayList<>();
			ExecutorService service = new JoyinThreadPoolExecutor(10);
			int start = 0;
			int end = 0;
			int size = list.size();
			int max = (int) Math.ceil((double) size / 50);
			// 信号量
			//CountDownLatch countDown = new CountDownLatch(max);

			for (int i = 0; i < max; i++) {
				start = end;
				end = Math.min(size, 50 * (i + 1));
				List<InvestorsHoldInfoRegist001Dto> subList = list.subList(start, end);
				// 提交任务
				tasks.add(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						resultList.addAll(investorsHoldInfoRegist001Mapper.selectAllNeedToHisData(subList));
						//countDown.countDown();// 个数-1
						return true;
					}
				});
			}
			// 添加线程等待转换线执行
			try {
				service.invokeAll(tasks);
				//countDown.await();
				service.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return resultList;
	}

	public void insertInvestorsHoldInfoRegistHisBatch(List<InvestorsHoldInfoRegist001Dto> list) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 批量插入mapper
		InvestorsHoldInfoRegist001Mapper mapper = session.getMapper(InvestorsHoldInfoRegist001Mapper.class);
		try {
			int count = 0;
			for (InvestorsHoldInfoRegist001Dto model : list) {
				mapper.insertInvestorsHoldInfoRegistHisBatch(model);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}

/**
 * 
 * @param list
 */
  public void insertBatch(List<ChbSubmitStatus> list) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 批量插入mapper
    InvestorsHoldInfoRegist001Mapper mapper = session.getMapper
				(InvestorsHoldInfoRegist001Mapper.class);
		try {
			int count = 0;
			for (ChbSubmitStatus model : list) {
				mapper.insertSubmitStatusDto(model);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}

	public void updateBatch(List<String> list) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// 批量插入mapper
    InvestorsHoldInfoRegist001Mapper mapper = session.getMapper(
    		InvestorsHoldInfoRegist001Mapper.class);
		try {
			int count = 0;
			for (String seqno : list) {
				mapper.updateSubmitStatusToSuccess(seqno);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} finally {
			session.close();
		}
	}

	/**
	 * 对于未报送的数据进行手工处理数据库处理
	 *
	 * @param equiytRegistList 未报送的数据
	 * @see
	 * @since 1.0
	 */
//    private void doMannulForNotSubmitData(Map<String, Object> map) {
//        List<InvestorsHoldInfoRegist001Dto> list = investorsHoldInfoRegist001Mapper.selectAllData(map);
//        if (CollectionUtils.isNotEmpty(list)) {
//            // 讲第一次报送的数据数据履历表
//            investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(list);
//            // 删除已报送数据
//            investorsHoldInfoRegist001Mapper.deleteSubmitRegist(list);
//        }
//        // investorsHoldInfoRegist001Mapper.updateInterfSubmit(DictCodeListExt2.报送状态_手工处理.getValue(),
//        // investorsHoldInfoRegistList);
////        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), investorsHoldInfoRegistList, false);
//    }

	/**
	 * 校验是否所有的数据都符合 <功能详细描述>
	 *
	 * @param upKey
	 *            参数
	 * @return error 列表
	 * @see
	 * @since 1.0
	 */
	public List<ValidMessage> checkIsNotSubmit(Map<String, Object> map) {
		List<ValidMessage> errorList = new ArrayList<ValidMessage>();
		dealSearchParam(map);
		if (investorsHoldInfoRegist001Mapper.isNotSubmitP(map) != 0) {
			errorList.add(new ValidMessage("存在未报送的数据。"));
		}
		return errorList;
	}

	/**
	 * 已手工处理的数据将报送状态改为未报送
	 *
	 * @param upKey
	 *            传入主键
	 * @see
	 * @since 1.0
	 */
	public void cancelMannul(Map<String, Object> map) {

		List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
		dealSearchParam(map);
		@SuppressWarnings("unchecked")
		List<InvestorsHoldInfoRegist001Dto> list = (List<InvestorsHoldInfoRegist001Dto>) map.get("investList");
		for (InvestorsHoldInfoRegist001Dto dto : list) {
			if (StringUtil.isNotEmpty(dto.getSumitSeq())) {
				ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
				chbSubmitStatus.setSeqno(dto.getSumitSeq());
				listStatus.add(chbSubmitStatus);
			}
		}
		investorsHoldInfoRegist001Mapper.updateInvestRegist(list);
		// 将履历表中履历号最大的数据挪回登记表
		investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegist(list);
		investorsHoldInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
	}

	/**
	 * 默认取消手工处理全部
	 * @param map
	 */
	public void cancelMannulAll(Map<String, Object> map){
		List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
		map.put("sendStatus", "03");
		dealSearchParam(map);
		List<InvestorsHoldInfoRegist001Dto> investorsHoldInfoRegistList = investorsHoldInfoRegist001Mapper.selectAllData(map);
		for (InvestorsHoldInfoRegist001Dto dto : investorsHoldInfoRegistList) {
			if (StringUtil.isNotEmpty(dto.getSumitSeq())) {
				ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
				chbSubmitStatus.setSeqno(dto.getSumitSeq());
				listStatus.add(chbSubmitStatus);
			}
		}
		//分批次处理

		int maxDataCountOneTime = 50;
		int dataCount = listStatus.size();
		int dataInvestorsHoldInfoCount = investorsHoldInfoRegistList.size();
		if(dataInvestorsHoldInfoCount <= maxDataCountOneTime){
			investorsHoldInfoRegist001Mapper.updateInvestRegist(investorsHoldInfoRegistList);
			// 将履历表中履历号最大的数据挪回登记表
			investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegist(investorsHoldInfoRegistList);
		}else{
			List<InvestorsHoldInfoRegist001Dto> newDataInvestorsHoldInfoList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
			newDataInvestorsHoldInfoList.addAll(investorsHoldInfoRegistList);
			// 分批处理
			int part = dataInvestorsHoldInfoCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++ ) {
				investorsHoldInfoRegist001Mapper.updateInvestRegist(newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime));
				investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegist(newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime));
				newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataInvestorsHoldInfoList)) {
				investorsHoldInfoRegist001Mapper.updateInvestRegist(newDataInvestorsHoldInfoList);
				investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegist(newDataInvestorsHoldInfoList);
			}
		}
		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
		}
		else {
			List<ChbSubmitStatus> newDataList = new ArrayList<ChbSubmitStatus>();
			newDataList.addAll(listStatus);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++ ) {
				investorsHoldInfoRegist001Mapper.deleteMannulSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
				newDataList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataList)) {
				investorsHoldInfoRegist001Mapper.deleteMannulSubmitRegist(newDataList);
			}
		}
//		investorsHoldInfoRegist001Mapper.updateInvestRegist(investorsHoldInfoRegistList);
//		// 将履历表中履历号最大的数据挪回登记表
//		investorsHoldInfoRegist001Mapper.insertInvestorsHoldInfoRegist(investorsHoldInfoRegistList);
//		investorsHoldInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
	}
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
	 * 发送某登记银行代码的数据
	 *
	 * @param registList
	 *            发送的某个机构的投资者身份信息
	 * @see
	 * @since 1.0
	 */
	public void sendInvestorsHoldCHB(List<InvestorsHoldInfoRegist001Dto> registList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(registList)) {
			return;
		}
		int sendCbMaxDataCount = Integer.valueOf(
				SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));

		int dataCount = registList.size();
		if (dataCount <= sendCbMaxDataCount) {
			this.sendInvestorsHoldCHBOneFile(registList, isSendAllData);
			return;
		}

		// 分批处理
		List<InvestorsHoldInfoRegist001Dto> newDataList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
		newDataList.addAll(registList);

		int part = dataCount / sendCbMaxDataCount; // 分批数
		for (int i = 0; i < part; i++ ) {
			this.sendInvestorsHoldCHBOneFile(newDataList.subList(0, sendCbMaxDataCount), isSendAllData);
			newDataList.subList(0, sendCbMaxDataCount).clear();
		}
		// 最后剩下的数据
		if (!StringUtil.isEmptyList(newDataList)) {
			this.sendInvestorsHoldCHBOneFile(newDataList, isSendAllData);
		}
	}




	/**
	 * 发送某登记银行代码的数据(单文件最大1000000条数据)
	 *
	 * @param registList
	 *            发送的某个机构的投资者身份信息
	 * @see
	 * @since 1.0
	 */
	private void sendInvestorsHoldCHBOneFile(List<InvestorsHoldInfoRegist001Dto> registList, boolean isSendAllData) {

        // 元素名称
        String title = "wemax_RegistrationBankCode|wemax_ProductRegistrationCode|wemax_DiscriminationId|wemax_HoldingDate|wemax_Currency|wemax_InvestedUnits|wemax_InvestedAmount|wemax_InvestedAmountCny|wemax_RegistrationSerialNumber";
        // 元素字符串结果集
        StringBuffer info = new StringBuffer("\n" + "    <![CDATA[" + '\n');
        info.append(title);
        info.append('\n');
        List<String> registSeq = new ArrayList<String>();
        Map<String, String> registSeqKeyMap = new HashMap<String, String>();
        List<InvestorsHoldInfoRegist001Dto> unSubmitSeqList = new ArrayList<InvestorsHoldInfoRegist001Dto>();
        if (CollectionUtils.isNotEmpty(registList)) {
			List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理,
					registList.size());
			int index = 0;
			for (InvestorsHoldInfoRegist001Dto dto : registList) {
				// 针对取消手工处理再报送
				String registSeqNo = "";
				String currRegistSeq = "";
				if (DictCodeListExt2.报送反馈状态_发送失败.getValue().equals(dto.getSubmitFeedbackStatus())
						|| DictCodeListExt2.报送反馈状态_中债退回.getValue().equals(dto.getSubmitFeedbackStatus())) {
					// registSeqNo =
					// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
					registSeqNo = CommonUtil.getSequenceStr(sequenceList, index);
					index++;
					// 将报送流水为空的放入list
					currRegistSeq = registSeqNo;
					dto.setSumitSeq(currRegistSeq);
					unSubmitSeqList.add(dto);
				} else if (StringUtil.isEmpty(dto.getSumitSeq())) {
					// registSeqNo =
					// CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
					registSeqNo = CommonUtil.getSequenceStr(sequenceList, index);
					index++;
					dto.setSumitSeq(registSeqNo);
					currRegistSeq = registSeqNo;
					unSubmitSeqList.add(dto);
				} else {
					registSeqNo = dto.getSumitSeq();
					currRegistSeq = registSeqNo;
				}
				registSeq.add(registSeqNo);
				registSeqKeyMap.put(currRegistSeq, dto.getOrgCode() + "," + dto.getIndentifyMark() + ","
						+ dto.getPrdRegistCode() + "," + DateUtil.format(dto.getHoldDate()) + "," + dto.getCcy());

                String value = "    " + dto.getRegistBankCode() + "|" + dto.getPrdRegistCode() + "|"
                        + dto.getIndentifyMark() + "|" + DateUtil.format(dto.getHoldDate()) + "|" + dto.getCcy() + "|"
                        + dto.getQty() + "|" + dto.getAmount() + "|" + dto.getCnyAmt() + "|" + registSeqNo;
                info.append(value);
                info.append('\n');
            }
        }
        info.append("    ]]>");
        info.append('\n');
        // 更新报送状态表
        // modify by etliu 20181011 start
//        this.doUpdateSubmtitStatus(registList, isSendAllData, unSubmitSeqList);
        if (!isSendAllData) {
     	   this.doUpdateSubmtitStatus(registList, isSendAllData, unSubmitSeqList);
        }
        // modify by etliu 20181011 end
        // 将格式转换为中债报送的固定格式
        List<InterfCnBondSubmitPara> paras = new ArrayList<InterfCnBondSubmitPara>();
        InterfCnBondSubmitPara it = new InterfCnBondSubmitPara();
        List<Map<String, CnBondInterfItem>> listMap = new ArrayList<Map<String, CnBondInterfItem>>();
        CnBondInterfItem cnBondInterfItem = new CnBondInterfItem();
        cnBondInterfItem.setValue(info.toString());
        Map<String, CnBondInterfItem> maps = new HashMap<String, CnBondInterfItem>();
        maps.put("InvestorsInvestedInformationRegistrationCsvDataExplanatory", cnBondInterfItem);
        listMap.add(maps);
        it.setDealType(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
        it.setInfos(listMap);
        it.setSysDate(DateUtil.now());
        it.setRegistSeq(registSeq);
        it.setRegistBankCode(registList.get(0).getRegistBankCode());
        it.setRegistSeqKeyMap(registSeqKeyMap);
        paras.add(it);
         cnBondInterfService.sendCnBond(paras);
    }
    
    
    /**
     * 检查对应的task是否有未执行或正在执行中的批处理任务 <功能详细描述>
     * 
     * @param beanname
     * @return false未执行或正在执行中的任务 true 未检查到对应的未执行或正在执行中的任务
     */
    public boolean checkIfInBatchByTaskBeanName(String beanname) {
    	InvestorsHoldInfoRegist001Service chbAssetInfoInterf = SpringContextUtil.getBean(
        		InvestorsHoldInfoRegist001Service.class);
        int count = chbAssetInfoInterf.getNoendCountByTaskBeanName(beanname);
        if (count > 0) {
            return false;
        }
        return true;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.OTHER)
    public int getNoendCountByTaskBeanName(String beanname) {
        return investorsHoldInfoRegist001Mapper.selectNoendCountByTaskBeanName(beanname);
    }
    
    /**
     * 导出功能查询
     *
     * @param pageParam 查询参数
     * @return 结果集
     * @see
     * @since 1.0
     */
    public int countExportNum(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        int dataNum = investorsHoldInfoRegist001Mapper.countExportNum(pageParam);
        return dataNum;
    }
    
    /**
     * 启动批处理导出
     * @param param
     */
 	public AjaxResult batchExport(Map<String, Object> param) {
 		AjaxResult ajaxResult = new AjaxResult();
 		// 是否未执行或正在执行中
 		if (!checkIfInBatchByTaskBeanName(
            BatchBeanName.投资者持有信息登记导出.getTaskId())) {
            ajaxResult.setMessage("投资者持有信息登记导出批处理正在执行中或待执行！", MessageType.错误消息);
            return ajaxResult;
        }
 		ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
 		log.setBatchExeId(IDGenerator.generate());
 		log.setTaskId(BatchBeanName.投资者持有信息登记导出.getTaskId());
 		log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
 		log.setOperator(LoginUserUtil.getUserName());
 		log.setFileType(DictCodeListExt3.报送文件类别_投资者持有信息登记.getValue());
 		log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
 		log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
 		log.setCreateUser(LoginUserUtil.getUserName());
 		log.setCreateTime(new Date());
 		log.setUpdateUser(LoginUserUtil.getUserName());
 		log.setUpdateTime(new Date());
 		// 日志存表
 		simpleRestTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
 				ChbSubinfoBatchLog.class);
 		// 启动批处理
 		sysBatchStart001Service.executeBatch();
 		// 批处理启动成功，可在批处理监控页面查看进度
// 		return new AjaxResult("导出excel文件批处理启动成功，可在批处理监控页面查看进度");
 		ajaxResult.setData(true);
 		return ajaxResult;
 	}
 	
 	 /**
     * 启动批处理导出
     * @param param
     */
 	public AjaxResult batchExportAnddoManual(Map<String, Object> param) {
 		AjaxResult ajaxResult = new AjaxResult();
 		// 是否未执行或正在执行中
 		if (!checkIfInBatchByTaskBeanName(
            BatchBeanName.投资者持有信息登记导出.getTaskId())) {
            ajaxResult.setMessage("投资者持有信息登记导出批处理正在执行中或待执行！", MessageType.错误消息);
            return ajaxResult;
        }
 		if (!checkIfInBatchByTaskBeanName(
 	            BatchBeanName.投资者持有信息登记手工处理.getTaskId())) {
 	            ajaxResult.setMessage("投资者持有信息登记手工处理批处理正在执行中或待执行！", MessageType.错误消息);
 	            return ajaxResult;
 	        }
 		ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
 		log.setBatchExeId(IDGenerator.generate());
 		log.setTaskId(BatchBeanName.投资者持有信息登记导出.getTaskId());
 		log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
 		log.setOperator(LoginUserUtil.getUserName());
 		log.setFileType(DictCodeListExt3.报送文件类别_投资者持有信息登记.getValue());
 		log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
 		log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
 		log.setCreateUser(LoginUserUtil.getUserName());
 		log.setCreateTime(new Date());
 		log.setUpdateUser(LoginUserUtil.getUserName());
 		log.setUpdateTime(new Date());
 		// 日志存表
 		simpleRestTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
 				ChbSubinfoBatchLog.class);
 		//手工处理
		ChbBatchPara record = new ChbBatchPara();
	    record.setTaskId(BatchBeanName.投资者持有信息登记手工处理.getTaskId());
	    record.setParaId(IDGenerator.generate());
	    if (param.get("key") != null) {
	        record.setParas(param.get("key").toString());
	        simpleRestTemplate.postForObject(
		       		SvrServiceApplication.CHB3_SERVICE_NAME + "/InvestorsHoldInfoRegist/insert", record,
		       		Integer.class);
	    }
 		// 启动批处理
 		sysBatchStart001Service.executeBatch();
 		// 批处理启动成功，可在批处理监控页面查看进度
// 		return new AjaxResult("导出excel文件批处理启动成功，可在批处理监控页面查看进度");
 		ajaxResult.setData(true);
 		return ajaxResult;
 	}
}
