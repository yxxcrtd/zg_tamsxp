/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.pbc.report.service;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.tams.svreport.pbc.assetdebt.service.*;
import com.joyintech.tams.svreport.pbc.classic.pbcNewDat.service.PbcNewReportPrdDatFileService;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.pbc.assetposition.service.PbcMonthAssetPositionService;
import com.joyintech.tams.svreport.pbc.assetposition.service.PbcMonthAssetReportService;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportType;
import com.joyintech.tams.svreport.pbc.common.constants.PbcTemplateName;
import com.joyintech.tams.svreport.pbc.common.util.PbcExcelUtil;
import com.joyintech.tams.svreport.pbc.common.util.XlsxToHtml;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.service.PbcBeforeSubCheck;
import com.joyintech.tams.svreport.pbc.prod.service.PbcMonthProdPositionService;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub001Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub002Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub003Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub004Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub005Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd001Sub006Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd003Sub001Service;
import com.joyintech.tams.svreport.pbc.prod.service.PbcReportProd003Sub002Service;
import com.joyintech.tams.svreport.pbc.report.dao.PbcMonthReportMapper;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;

import net.sf.jxls.transformer.XLSTransformer;
/**
 * 
 * 功能:〈银行月度报表登记service
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年5月30日
 * @修改记录：
 */
@Service
public class PbcMonthReport001Service extends CommonExportService<PbcMonthReport> {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private PbcReportProd001Sub001Service pbcReportProd001Sub001Service;
	@Autowired
	private PbcReportProd001Sub002Service pbcReportProd001Sub002Service;
	@Autowired
	private PbcReportProd001Sub003Service pbcReportProd001Sub003Service;
	@Autowired
	private PbcReportProd001Sub004Service pbcReportProd001Sub004Service;
	@Autowired
	private PbcReportProd001Sub005Service pbcReportProd001Sub005Service;
	@Autowired
	private PbcReportProd001Sub006Service pbcReportProd001Sub006Service;
	@Autowired
	private PbcReportProd003Sub001Service pbcReportProd003Sub001Service;
	@Autowired
	private PbcReportProd003Sub002Service pbcReportProd003Sub002Service;
	@Autowired
	private PbcReportAssetDebtService pbcReportAssetDebtService;
	@Autowired
	private PbcReportCompanyService pbcReportCompanyService;
	@Autowired
	private PbcReportAreaService pbcReportAreaService;
	@Autowired
	private PbcMonthReportMapper pbcMonthReportMapper;
	@Autowired
	private PbcReportCompanyBondService pbcReportCompanyBondService;
	@Autowired
	private PbcReportAssetIncomeDetailService pbcReportAssetIncomeDetailService;
	@Autowired
	private PbcReportDebtTermDetailService pbcReportDebtTermDetailService;
    @Autowired
    private PbcMonthProdPositionService pbcMonthProdPositionService;
    @Autowired
    private PbcMonthAssetPositionService pbcAssetPositionInterf;
    @Autowired
    private PbcMonthAssetReportService pbcMonthAssetPositionService;
    @Autowired
	private CheckReport001Mapper checkReport001Mapper;
    @Autowired
	private PbcBeforeSubCheck pbcBeforeSubCheck;
	@Autowired
	private PbcNewReportPrdDatFileService pbcNewReportPrdDatFileService;
	@Autowired
	private PbcReportAssetConfigurationService pbcReportAssetConfigurationService;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public  int reportSelectOrDeleteCountForShenFen;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 人行导出模版sheet页名称
     */
    private final static String SHEET_1_1 = "表1-1资管产品募集余额";
    
    private final static String SHEET_1_2 = "表1-2募集及兑付发生额";
    
    private final static String SHEET_1_3 = "表1-3只数情况";
    
    private final static String SHEET_1_4 = "表1-4境内募集余额分地区";
    
    private final static String SHEET_1_5 = "表1-5提前及延期兑付";
    
    private final static String SHEET_1_6 = "表1-6资管产品到期未兑付";
    
    private final static String SHEET_2_1 = "表2-1资产负债";
    
    private final static String SHEET_2_2 = "表2-2除回购和拆借外贷款按行业、企业";
    
    private final static String SHEET_2_3 = "表2-3除回购和拆借外贷款分地区";
    
    private final static String SHEET_2_4 = "表2-4资产收益权按基础资产投向";
    
    private final static String SHEET_2_5 = "表2-5企业债券按行业、企业";
    
    private final static String SHEET_3_1 = "表3-1存续产品分期限募集余额";
    
    private final static String SHEET_3_2 = "表3-2新发产品分期限募集金额";
    
    private final static String SHEET_3_3 = "表3-3资产负债剩余期限";

	private final static String SHEET_4_1 = "表4-1资管产品资产配置情况";
    
	/**
	 * 查询页面数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public Page<PbcMonthReport> searchPage(Map<String, Object> pageParam) {
		dealPageSearchParam(pageParam);
		//将发送状态改为空

        if (pageParam.get(GlobalConstant.PARAM_PAGE) != null
                && pageParam.get(GlobalConstant.PARAM_LENGTH) != null){
            int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
            Page<PbcMonthReport> page = queryForPage(
                    pagenum,legth,()->pbcMonthReportMapper.searchPage(pageParam));
            Collections.sort(page.getRoot());
            return page;
        } else {
        	Page<PbcMonthReport> page = queryForPage(PbcMonthReportMapper.class, "searchPage",
    				pageParam);
        	Collections.sort(page.getRoot());
            return page;
        }
//		Page<PbcMonthReport> page = queryForPage(PbcMonthReportMapper.class, "searchPage",
//				pageParam);
//		return page;
	}

    /**
     * 查询页面数据
     *
     * @param pageParam
     *            查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public PbcMonthReport searchPageBysubmitDate(Map<String, Object> pageParam) {
        Map<String, Object> param = new HashMap<String, Object>();
        String submitDateStart = (String) pageParam.get("submitDateStart");
        if (!StringUtil.isEmpty(submitDateStart)
                && submitDateStart.length() >= 7){
            submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
            param.put("submitDateStart", submitDateStart);
        }
        String submitDateEnd = (String) pageParam.get("submitDateEnd");
        if (!StringUtil.isEmpty(submitDateEnd)
                && submitDateEnd.length() >= 7){
            submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
            param.put("submitDateEnd", submitDateEnd);
        }
        List<PbcMonthReport> pbcMonthReportList = pbcMonthReportMapper.searchPage(param);
        PbcMonthReport repos = new PbcMonthReport();
        for (PbcMonthReport report : pbcMonthReportList){
            if (repos.getStartdate() == null){
                repos.setStartdate(report.getStartdate());
            } 
            // 取最小的开始日期
            if (repos.getStartdate().compareTo(report.getStartdate()) > 0){
                repos.setStartdate(report.getStartdate());
            }
			repos.setSendStatus(report.getSendStatus());
        }
        return repos;
    }
    
    /**
     * 根据条件查询数据
     *
     * @param pageParam
     *            查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public List<PbcMonthReport> searchByCondition(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        //将发送状态改为空
        pageParam.put("sendStatus", "");
        String submitDateStart = (String) pageParam.get("submitDateStart");
        if (!StringUtil.isEmpty(submitDateStart)
                && submitDateStart.length() >= 7){
            submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
            pageParam.put("submitDateStart", submitDateStart);
        }
        String submitDateEnd = (String) pageParam.get("submitDateEnd");
        if (!StringUtil.isEmpty(submitDateEnd)
                && submitDateEnd.length() >= 7){
            submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
            pageParam.put("submitDateEnd", submitDateEnd);
        }
        List<PbcMonthReport> reportList = pbcMonthReportMapper.searchPage(pageParam);
        return reportList;
    }

	/**
	 * 处理查询前的参数 <功能详细描述>
	 *
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public void dealSearchParam(Map<String, Object> pageParam) {

		pageParam.put("sendStatus", "");
		String submitDateStart = (String) pageParam.get("submitDateStart");
		if (!StringUtil.isEmpty(submitDateStart)
				&& submitDateStart.length() >= 7){
			submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
			pageParam.put("submitDateStart", submitDateStart);
		}
		String submitDateEnd = (String) pageParam.get("submitDateEnd");
		if (!StringUtil.isEmpty(submitDateEnd)
				&& submitDateEnd.length() >= 7){
			submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
			pageParam.put("submitDateEnd", submitDateEnd);
		}


		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<PbcMonthReport> astValueList = JsonUtils.json2List(keyList,
						PbcMonthReport.class);
				pageParam.put("reportList", astValueList);
				if (!CollectionUtils.isEmpty(astValueList)){
					pageParam.put("reportuuid", astValueList.get(0).getReportuuid());
				}
			} catch (IOException e) {
//				logger.error(e.getMessage(),e);
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 数据状态
			String status = (String) pageParam.get("status");
			String reporttypes = (String) pageParam.get("reporttypes");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
			if (StringUtil.isNotBlank(reporttypes)) {
				pageParam.put("reporttypeList", StringUtil.splitToList(GlobalConstant.COMMA, reporttypes));
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

		}
	}

	/**
	 * 处理查询前的参数 <功能详细描述>
	 *
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	public void dealPageSearchParam(Map<String, Object> pageParam) {

		String submitDateStart = (String) pageParam.get("submitDateStart");
		if (!StringUtil.isEmpty(submitDateStart)
				&& submitDateStart.length() >= 7){
			submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
			pageParam.put("submitDateStart", submitDateStart);
		}
		String submitDateEnd = (String) pageParam.get("submitDateEnd");
		if (!StringUtil.isEmpty(submitDateEnd)
				&& submitDateEnd.length() >= 7){
			submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
			pageParam.put("submitDateEnd", submitDateEnd);
		}


		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<PbcMonthReport> astValueList = JsonUtils.json2List(keyList,
						PbcMonthReport.class);
				pageParam.put("reportList", astValueList);
				if (!CollectionUtils.isEmpty(astValueList)){
					pageParam.put("reportuuid", astValueList.get(0).getReportuuid());
				}
			} catch (IOException e) {
//				logger.error(e.getMessage(),e);
				logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 数据状态
			String status = (String) pageParam.get("status");
			String reporttypes = (String) pageParam.get("reporttypes");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
			if (StringUtil.isNotBlank(reporttypes)) {
				pageParam.put("reporttypeList", StringUtil.splitToList(GlobalConstant.COMMA, reporttypes));
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

		}
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
	public List<PbcMonthReport> selectAllList(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		List<PbcMonthReport> dataList =  (List<PbcMonthReport>) pageParam.get("astValueList");// 查询参数列表

		List<PbcMonthReport> dtoList = new ArrayList<PbcMonthReport>();// 存放查询结果集
		if(CollectionUtils.isNotEmpty(dataList)){
//			selectAllDataBach(dataList,dtoList);
		}
		else{
			dtoList = pbcMonthReportMapper.searchPage(pageParam);
		}


		return dtoList;
	}
	
	
    /**
 	 * <一句话功能简述> <功能详细描述>
 	 *
 	 * @param upKey
 	 * @see
 	 * @since 1.0
 	 */
	@Transactional(rollbackFor = Exception.class)
 	@SuppressWarnings("unchecked")
 	public void updateSumStaus(Map<String, Object> map) {
 		dealSearchParam(map);
 		List<PbcMonthReport> chbBotAstPositionRegistList  = new ArrayList<PbcMonthReport>();
 		if(map.get("flag") != null && "01".equals(map.get("flag"))){
 			//按条件查询全部
 			chbBotAstPositionRegistList = pbcMonthReportMapper.searchPage(map);
 		}else{
 			chbBotAstPositionRegistList = (List<PbcMonthReport>)map.get("reportList");
 		}

        List<PbcMonthReport> reportList  = new ArrayList<PbcMonthReport>();
        for(PbcMonthReport m : chbBotAstPositionRegistList){
            // 手工处理只处理未报送的
            if (DictCodeListExt2.报送状态_未报送.getValue().equals(m.getSendStatus())){
                reportList.add(m);

            }
        }
 		
// 		for (PbcMonthReport dto : chbBotAstPositionRegistList) {
//// 			// 报送状态表插入组装
//// 			if (StringUtil.isEmpty(dto.getSumitSeq())) {
//// 				dto.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//// 			} 
// 			dto.setSubmitFeedbackStatus(DictCodeListExt2.报送反馈状态_中债通过.getValue());
// 		}
 		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), reportList);

 	}
 	
 	/**
 	 * 分批次执行updateInterfSubmitAndSeq
 	 *
 	 * @param investList
 	 *            数据
 	 * @see
 	 * @since 1.0
 	 */
 	@Transactional(rollbackFor = Exception.class)
 	public void updateInterfSubmitAndSeq(String sendStatus, List<PbcMonthReport> investList) {
 		if (StringUtil.isEmptyList(investList)) {
 			return;
 		}
 		int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
 		int dataCount = investList.size();
 		if (dataCount <= maxDataCountOneTime) {
            //已报送数据进留痕表
 		    pbcMonthReportMapper.insertReportHis(investList);
            //删除正式表已报送数据
			investList.get(0).getStatusDate();
// 		    pbcMonthReportMapper.deleteLastSubmitRegist(investList);
 			pbcMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList,null);
 			pbcMonthReportMapper.updateDoMannultSendZipDatFile(investList);

 			return;
 		}
 		List<PbcMonthReport> newDataList = new ArrayList<PbcMonthReport>();
 		newDataList.addAll(investList);

 		// 分批处理
 		int part = dataCount / maxDataCountOneTime; // 分批数
 		for (int i = 0; i < part; i++) {
 			logger.info("单次更新登记表start===");
            //已报送数据进留痕表
            pbcMonthReportMapper.insertReportHis(newDataList.subList(0, maxDataCountOneTime));
            //删除正式表已报送数据
//            pbcMonthReportMapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
 			pbcMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus,
                 newDataList.subList(0, maxDataCountOneTime),null);
			pbcMonthReportMapper.updateDoMannultSendZipDatFile(newDataList.subList(0, maxDataCountOneTime));
 			newDataList.subList(0, maxDataCountOneTime).clear();
 			logger.info("单次更新登记表end===");
 		}
 		// 最后剩下的数据
 		if (CollectionUtils.isNotEmpty(newDataList)) {
 			logger.info("单次更新登记表start===");
            //已报送数据进留痕表
            pbcMonthReportMapper.insertReportHis(newDataList.subList(0, maxDataCountOneTime));
            //删除正式表已报送数据
//            pbcMonthReportMapper.deleteLastSubmitRegist(newDataList.subList(0, maxDataCountOneTime));
 			pbcMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList,null);
			pbcMonthReportMapper.updateDoMannultSendZipDatFile(newDataList);

			logger.info("单次更新登记表end===");
 		}
 	}

 	
 	/**
 	 * 已手工处理的数据将报送状态改为未报送
 	 *
 	 * @param upKey
 	 *            传入主键
 	 * @see
     * @return boolean 是否同时存在已登记和未登记的数据 
 	 * @since 1.0
 	 */
 	@Transactional(rollbackFor = Exception.class)
 	@SuppressWarnings("unchecked")
 	public boolean cancelMannul(Map<String, Object> map) {
 		dealSearchParam(map);
 		List<PbcMonthReport> list = new ArrayList<PbcMonthReport>();
 		// 用于判断是否同时存在未报送和已报送的数据
        // 所有未登记的数据
        Map<String, Object> querymap = new HashMap<>();
        querymap.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, DictCodeListExt2.报送状态_未报送.getValue()));
        List<PbcMonthReport> pbcReportNotRegList = pbcMonthReportMapper.searchPage(querymap);
        Map<String,String> pbcReportNotRegMap = pbcReportNotRegList.stream().collect(Collectors.toMap(PbcMonthReport::getTermid,
                PbcMonthReport::getTermReport,(key1,key2)->key2));
 		if(map.get("flag") != null && "01".equals(map.get("flag"))){
 			list = pbcMonthReportMapper.searchPage(map);
 		}else{
 			list = (List<PbcMonthReport>) map.get("reportList");
 		}
 		// 循环要处理的数据判断是否过滤同时存在未报送和已报送的数据
        for(PbcMonthReport m : list){
            // 如果没有该产品的数据则插入如果有则判断是否同时存在未报送和已报送
            // 只要有未报送的登记数据就不允许取消手工处理
            if (DictCodeListExt2.报送状态_手工处理.getValue().equals(m.getSendStatus())
                    && pbcReportNotRegMap.get(m.getTermid()+m.getReporttype()) != null){
                return true;
            }
                    
        }
 		pbcMonthReportMapper.cancelMannultRegist(list);
 		// 将履历表中履历号最大的数据挪回登记表
// 		pbcMonthReportMapper.insertReportDataFromHis(list);
 		// 删除履历表中移除数据
 		pbcMonthReportMapper.deleteLastSubmitHis(list);

		pbcMonthReportMapper.updateCancelMannultSendZipDatFile(list);
 		return false;
 	}
 	
    @Override
    public ResponseEntity<byte[]> export(String pageId, Map<String, Object> selectParam) {
    	dealSearchParam(selectParam);
    	// 导出文件
        List<File> sourceFiles = new ArrayList<>();
    	@SuppressWarnings("unchecked")
        List<PbcMonthReport> dataList =  (List<PbcMonthReport>) selectParam.get("reportList");
		Set<String> termList = new HashSet<>();

		//设置单选多选都是全部导出
		if(null!=dataList&&dataList.size()>0){
			for(int i =0 ;i < dataList.size();i++){
				termList.add(dataList.get(i).getTermid());
			}
			selectParam.put("termidList",termList);
		}

		// 不选报表导出全部
		dataList = searchByCondition(selectParam);
    	Map<String, List<PbcMonthReport>> dataParam = new HashMap<String, List<PbcMonthReport>>();
    	// 单选导出
    	for (int i =0 ;i < dataList.size();i++){
    		if(dataParam.get(dataList.get(i).getTermid())!=null){
    			dataParam.get(dataList.get(i).getTermid()).add(dataList.get(i));
    		} else {
    			List<PbcMonthReport> reprotList = new ArrayList<PbcMonthReport>();
    			reprotList.add(dataList.get(i));
    			dataParam.put(dataList.get(i).getTermid(), reprotList);
    		}
    	}
    	// excel计数器
		int count = 1;
		Format f2 = new DecimalFormat("00");
    	Iterator<Map.Entry<String,List<PbcMonthReport>>> iterator = dataParam.entrySet().iterator();
		while (iterator.hasNext()) {
			List<String> sheetList = createSheet();
			Map.Entry<String, List<PbcMonthReport>> entry = iterator.next();
			Map<Object,Object> data  = new HashMap<Object,Object>();
			for(PbcMonthReport dto : entry.getValue()){
				//获取报表类型  根据不同的报表类型 查询不同的明细和对应的 模板数据
		    	String reporttype=dto.getReporttype();
		    	Map<String,Object> param = new HashMap<String,Object>();
		    	param.put("reportuuid", dto.getReportuuid());
		    	if(reporttype.equals(PbcReportType.PBC_REPORT_1_1)){
		    		pbcReportProd001Sub001Service.createDataForExp(param,data);
		    		sheetList.remove(SHEET_1_1);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_2)){
					pbcReportProd001Sub002Service.createDataForExp(param,data);
					sheetList.remove(SHEET_1_2);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_3)){
					pbcReportProd001Sub003Service.createDataForExp(param,data);
					sheetList.remove(SHEET_1_3);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_4)){
					pbcReportProd001Sub004Service.createDataForExp(param,data);
					sheetList.remove(SHEET_1_4);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_5)){
					pbcReportProd001Sub005Service.createDataForExp(param,data);
					sheetList.remove(SHEET_1_5);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_6)){
					pbcReportProd001Sub006Service.createDataForExp(param,data);
					sheetList.remove(SHEET_1_6);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_1)){
					pbcReportAssetDebtService.createDataForExp(param,data);
					sheetList.remove(SHEET_2_1);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_2)){
					pbcReportCompanyService.createDataForExp(param,data);
					sheetList.remove(SHEET_2_2);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_3)){
					pbcReportAreaService.createDataForExp(param,data);
					sheetList.remove(SHEET_2_3);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_4)){
					pbcReportAssetIncomeDetailService.createDataForExp(param,data);
					sheetList.remove(SHEET_2_4);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_5)){
					pbcReportCompanyBondService.createDataForExp(param,data);
					sheetList.remove(SHEET_2_5);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_1)){
					pbcReportProd003Sub001Service.createDataForExp(param,data);
					sheetList.remove(SHEET_3_1);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_2)){
					pbcReportProd003Sub002Service.createDataForExp(param,data);
					sheetList.remove(SHEET_3_2);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_3)){
					pbcReportDebtTermDetailService.createDataForExp(param,data);
					sheetList.remove(SHEET_3_3);
				}else if(reporttype.equals(PbcReportType.PBC_REPORT_4_1)){
					pbcReportAssetConfigurationService.createDataForExp(param,data);
					sheetList.remove(SHEET_4_1);
				}
			}
			PbcMonthReport pbcReport = (PbcMonthReport) data.get("report");
			//生成文件
			String fileName = DateUtil.format(pbcReport.getEnddate(),DateUtil.DEFAULT_OTHER_FORMAT)+"-"+f2.format(count)+"-"+pbcReport.getSocialCreditCode()+"-"+pbcReport.getReportOrgName() +".xls";
//			String fileName = DateUtil.format(DateUtil.getLastDay(new Date()), "yyyyMMdd")+"-"+f2.format(count)+"-"+pbcReport.getSocialCreditCode()+"-"+pbcReport.getReportOrgName() +".xls";
//			File depositfile = PbcExcelUtil.createFile(data, "PbcReport", fileName);
			File depositfile = TisUtil.createFile(PbcExcelUtil.convertMapKey2String(data), "pbc/PbcReport", fileName);
			// 删除多余的sheet页
			//PbcExcelUtil.deleteSheet(depositfile, sheetList);
			sourceFiles.add(depositfile);
			count++;
		}
    	
        // 如果生成excel文件个数大于1，生成压缩文件
        File outFile = null;
        if (sourceFiles.size() > 1 || sourceFiles.isEmpty()) {
            String exportFilePath = SvrDataUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//            String zipFileName = FileNameUtil.createFileName("zip");
            String zipFileName = "人行资管产品月度统计报表("+DateUtil.getSysDate()+").zip";
            outFile = new SafelyFile(exportFilePath + File.separator + zipFileName);
            // 导出路径不存在则重新生成
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            try {
                ZipFileUtil.compressZip(outFile, true, sourceFiles.toArray(new SafelyFile[] {}));
            }
            catch (Exception e) {
                // 压缩文件失败后，删除所有临时文件
                for (File file : sourceFiles) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                logger.error(e.getMessage(),e);
            }
        } else {//单个文件
            outFile = sourceFiles.get(0);
        }
		return ExportUtil.export(outFile.getName(), outFile, true);
    }
    
    /**
     * @Description 创建模版sheet页list
     * @return
     * @return List<String>
     * @version 1.7 2019年5月14日
     */
    private static List<String> createSheet(){
    	List<String> sheetList = new ArrayList<String>();
    	sheetList.add(SHEET_1_1);
    	sheetList.add(SHEET_1_2);
    	sheetList.add(SHEET_1_3);
    	sheetList.add(SHEET_1_4);
    	sheetList.add(SHEET_1_5);
    	sheetList.add(SHEET_1_6);
    	sheetList.add(SHEET_2_1);
    	sheetList.add(SHEET_2_2);
    	sheetList.add(SHEET_2_3);
    	sheetList.add(SHEET_2_4);
    	sheetList.add(SHEET_2_5);
    	sheetList.add(SHEET_3_1);
    	sheetList.add(SHEET_3_2);
    	sheetList.add(SHEET_3_3);
		sheetList.add(SHEET_4_1);
		return sheetList;
	}
    
    public Map<Object, Object> createData(Map<String, Object> selectParam){
    	dealSearchParam(selectParam);
    	Map<Object,Object> data  = new HashMap<Object,Object>();
    	@SuppressWarnings("unchecked")
		List<PbcMonthReport> dataList =  (List<PbcMonthReport>) selectParam.get("reportList");// 查询参数列表
    	String termid = "";
    	if(dataList!=null&&dataList.size()>0){
    		termid = dataList.get(0).getTermid();
    	}else{
    		termid = selectParam.get("termid").toString();
    	}
    	PbcMonthReport monthreport = pbcMonthReportMapper.selectByTermId(termid);
//    	if(monthreport==null)
    	data.put("report", monthreport);
    	data.put("termname", monthreport.getTermname());
    	selectParam.put("termid", monthreport.getTermid());
//		List<ChbMonthReportDetail> detailList  = chbMonthReportDetailMapper.selectByModel(selectParam);
//		for(ChbMonthReportDetail detail : detailList){
//			data.put(detail.getPropertycode(), divide10000(detail));
//		}
    	return  data;
    }
 	
	public ResponseEntity<byte[]>  exportMonthReportExcel(Map<Object,Object> data,String exceltemplate) throws IOException {
		String termname = data.get("termname").toString();
	    String flieName = "银行月度报表"+"_"+termname+".xls";
	    File depositfile = createFile(data,exceltemplate);
		return ExportUtil.export(flieName, depositfile, true);
	}
	public String priview(String pageid,Map<String, Object> selectParam) {
		//获取报表类型  根据不同的报表类型 查询不同的明细和对应的 模板数据
		String reporttype=selectParam.get("reporttype").toString(); 
		String message="";
		if(reporttype.equals(PbcReportType.PBC_REPORT_1_1)){
            // 设置table预览大小
            selectParam.put("tableWidth", 120);
			message=pbcReportProd001Sub001Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_2)){
            // 设置table预览大小
            selectParam.put("tableWidth", 200);
			message=pbcReportProd001Sub002Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_3)){
			message=pbcReportProd001Sub003Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_4)){
            // 设置table预览大小
            selectParam.put("tableWidth", 120);
			message=pbcReportProd001Sub004Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_5)){
            // 设置table预览大小
            selectParam.put("tableWidth", 160);
			message=pbcReportProd001Sub005Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_1_6)){
			message=pbcReportProd001Sub006Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_1)){
		    // 设置table预览大小
		    selectParam.put("tableWidth", 200);
			message=pbcReportAssetDebtService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_2)){
			message=pbcReportCompanyService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_3)){
			message=pbcReportAreaService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_4)){
            // 设置table预览大小
            selectParam.put("tableWidth", 120);
			message=pbcReportAssetIncomeDetailService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_2_5)){
			message=pbcReportCompanyBondService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_1)){
			message=pbcReportProd003Sub001Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_2)){
			message=pbcReportProd003Sub002Service.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_3_3)){
			message=pbcReportDebtTermDetailService.priview(selectParam);
		}else if(reporttype.equals(PbcReportType.PBC_REPORT_4_1)){
			message=pbcReportAssetConfigurationService.priview(selectParam);
		}
		else if(reporttype.equals(PbcReportType.PBC_REPORT_ONEPRD_ASSETDEBT)){
			 // 设置table预览大小
		    selectParam.put("tableWidth", 200);
		    selectParam.put("templateChange", PbcTemplateName.PBC_REPORT_ONEPRD_ASSET_DEBT);
			message=pbcReportAssetDebtService.priview(selectParam);
		}
		
//		Map<Object,Object> data = createData(selectParam);
//		
//		data.put("termname",data.get("termname").toString());
//	    File depositfile = createFile(data,exceltemplate);
//		return this.priView(depositfile, true);
		return message;
	}
    /**
     * 导出文件
     * 
     * @param downloadFileName 导出的文件名
     * @param downloadFile 需要被下载的文件
     * @param deleteAfterExport 下载后是否删除当前原文件
     * @return 返回responseEntity
     * @throws IOException 异常
     * @see
     * @since 1.0
     */
    public String priView( File downloadFile, boolean deleteAfterExport,int sheetNum,int tableWidth)
        throws IOException {
    	  String rs =  XlsxToHtml.readExcelToHtml(downloadFile,true,true,sheetNum,tableWidth);  
        if (deleteAfterExport) {
            downloadFile.delete();
        }
        return rs;
    }
	
	

	public File createFile(Map<Object,Object> data,String exceltemplate){
		String termname = data.get("termname").toString();
	    String flieName = exceltemplate+"_"+termname+".xls";
	    File depositfile = null;
		try{		
			
		// 关联模板
        XLSTransformer transformer = new XLSTransformer();
        
		String template  = null;
		//windows环境地址
        if(File.separator.equals("\\")){
        	template = getXmlPath(exceltemplate+".xls");
        }
        //linux环境地址
        if(File.separator.equals("/")){
        	template = getXmlPathLinux(exceltemplate+".xls");
        }
		//传放地址
		String depositfilePath = SvrDataUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		
		depositfile = new SafelyFile(depositfilePath + File.separator + flieName);
		if (!depositfile.getParentFile().exists()) {
			depositfile.getParentFile().mkdirs();
		}
		
		transformer.transformXLS(template, data, depositfilePath + File.separator +flieName);
	    }catch(InvalidFormatException|IOException e) {
			logger.info(exceltemplate + "生成失败！");
			logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
		return depositfile;
	}
	
	public static String getXmlPath(String filename) {
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI().getPath();
			path = path.replace('/', '\\'); // 将/换成\
			path = path.replace("file:", ""); // 去掉file:
			path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
			path += "tpl\\" + filename;
			path = path.replace("report-batch", "report-parent\\report");
			return path;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static String getXmlPathLinux(String filename) {
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI().getPath();
			path += "tpl/" + filename;
			path = path.replace("file:", ""); // 去掉file:
			path = path.replace("report-batch", "report-parent/report");
			return path;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * 功能:重新计算正式版
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年3月23日
	 * @param map 
	 * void
	 */
    @Transactional(rollbackFor = Exception.class)
    public String recalReport(Map<String, Object> map){
        // 报表开始日
        String submitDateStart = (String) map.get("submitDateStart");
        Date startDate = DateUtil.parse(submitDateStart);
        // 报表结束日
        String submitDateEnd = (String) map.get("submitDateEnd");
        Date endDate = DateUtil.parse(submitDateEnd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        // 获取开始日月份下个月的第一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date reportStartDate = calendar.getTime();
        // 获取开始日月份下个月的第一天
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date reportEndDate = calendar.getTime();
        // 从报告期开日月份的第一天跑到报告期结束日月份的第一天
        Date batchDate = reportStartDate;
        for(;batchDate.compareTo(reportEndDate)<=0;){
            Calendar calendarBatch = Calendar.getInstance();
            calendarBatch.setTime(batchDate);
            // 每月1日生成上月的数据
            if ( calendarBatch.get(Calendar.DATE)!=1 ) {  
                continue;
            }
            // 人行产品头寸
            // 已报送报送检查到第一条就不跑
            List<String> reporttypeProdList = new ArrayList<String>();
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品募集余额统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品募集及兑付发生额统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品只数情况统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品境内募集余额分地区统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品提前及延期兑付统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_资管产品到期未兑付统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_存续产品分合同期限募集余额统计表.getValue());
            reporttypeProdList.add(DictCodeListExt2.人行报表名称_新发产品分合同期限募集金额统计表.getValue());
            String msgProd = pbcMonthProdPositionService.checkSendStatus(batchDate,reporttypeProdList,CommonUtil.getOrgCode());
            if(msgProd != null && !StringUtil.isEmpty(msgProd)){
                return msgProd;
            } 
            //已报送报送检查
            List<String> reporttypeAssetList = new ArrayList<String>();
            reporttypeAssetList.add(DictCodeListExt2.人行报表名称_资管产品资产负债统计表.getValue());
            reporttypeAssetList.add(DictCodeListExt2.人行报表名称_资产收益权按基础资产投向分类统计表.getValue());
            reporttypeAssetList.add(DictCodeListExt2.人行报表名称_资管产品只数情况统计表.getValue());
            reporttypeAssetList.add(DictCodeListExt2.人行报表名称_资管产品资产负债剩余期限统计表.getValue());
			reporttypeAssetList.add(DictCodeListExt2.人行报表名称_资管产品资产配置情况表.getValue());
            String msgAsset = pbcMonthProdPositionService.checkSendStatus(batchDate,reporttypeAssetList,CommonUtil.getOrgCode());
            if(msgAsset != null&&!StringUtil.isEmpty(msgAsset)){
                return msgAsset;
            } 
            batchDate = DateUtil.addMonthDate(batchDate, 1);
        }
        for(;reportStartDate.compareTo(reportEndDate)<=0;){
            // 重新计算只跑人行月报头寸和报表生成
			if(MultilevelDataAuthorityUtil.isEnabled()) {
				Map<String,Object> maptemp =new HashMap<String,Object>();
				List<String> listProdtemp = pbcMonthReportMapper.findProductByName(maptemp);
				// 生成人行月报产品端数据
				pbcMonthProdPositionService.generateProdPositionData(reportStartDate,listProdtemp,false,true);
				// 生成人行月报资产头寸
				pbcAssetPositionInterf.generateAssetPositionData(reportStartDate, listProdtemp, null, false,true);
				// 生成人行月报产品头寸
				pbcMonthAssetPositionService.generateMonthAssetReport(reportStartDate, listProdtemp,true);
			}else{
				// 生成人行月报产品端数据
				pbcMonthProdPositionService.generateProdPositionData(reportStartDate,null,false,true);
				// 生成人行月报资产头寸
				pbcAssetPositionInterf.generateAssetPositionData(reportStartDate, null, null, false,true);
				// 生成人行月报产品头寸
				pbcMonthAssetPositionService.generateMonthAssetReport(reportStartDate, null,true);
			}
            reportStartDate = DateUtil.addMonthDate(reportStartDate, 1);
        }
        //添加生成新人行dat文件的方法 leipeng 20210827
		try {
			pbcNewReportPrdDatFileService.genDataFile(null, null, batchDate, null,CommonUtil.getOrgCode());
		}catch (Exception e){
			logger.error("生成dat文件错误",e);
			return "重新生成人行月报文件成功,但是生成新人行dat文件失败!";
		}

        return "success";
    }
   
	
    /**
     * 功能:重新计算demo版
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月23日
     * @param map 
     * void
     */
	@Transactional(rollbackFor = Exception.class)
 	public void recalReport(){
		try {
			runSqlBySpringUtils();
		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
            logger.error(e.getMessage(),e);
		}
		
		// 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
// 		String dateStr="2018-11-30";
//    	String endStr="2018-11-30";
// 		Date resultDate = null;
//		Date endDate = null;
//		dateStr=map.get("submitDateStart").toString();
//		endStr=map.get("submitDateEnd").toString();
//		try {
//			resultDate = sdf.parse(dateStr);
//			endDate = sdf.parse(endStr);
//			
//		} catch (ParseException e) {
//			logger.error(e.getMessage(),e);
//		}
 		
 	}
//	public ChbMonthReportDetail divide10000(ChbMonthReportDetail detail) {
//		BigDecimal tenthousand = new BigDecimal(10000);
//		if(detail.getBalance()!=null)//期末余额
//			detail.setBalance(detail.getBalance().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		if(detail.getBottombalance()!=null)//期末余额（穿透后）
//			detail.setBottombalance(detail.getBottombalance().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		if(detail.getBankprofit()!=null)//本期银行收益
//			detail.setBankprofit(detail.getBankprofit().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		if(detail.getCustomerprofit()!=null)//本期客户收益
//			detail.setCustomerprofit(detail.getCustomerprofit().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		if(detail.getProdamount()!=null)//本期募集金额
//			detail.setProdamount(detail.getProdamount().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		if(detail.getProdnetamount()!=null)//本期总募集金额
//			detail.setProdnetamount(detail.getProdnetamount().divide(tenthousand, 9, BigDecimal.ROUND_HALF_UP));
//		return detail;
//	}
	/**
     * 执行sql脚本文件 使用Spring工具类
     */
    public void runSqlBySpringUtils() {
    	SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection conn = sqlSession.getConnection();
        try {
//            ClassPathResource rc = new ClassPathResource("initReport.sql", PbcMonthReport001Service.class);
            File path = new SafelyFile(ResourceUtils.getURL("classpath:").getPath());
            File upload = new SafelyFile(path.getAbsolutePath(),"initReport.sql");
            FileSystemResource rc1 = new FileSystemResource(upload.getPath());
            EncodedResource er = new EncodedResource(rc1, "UTF-8");
            ScriptUtils.executeSqlScript(conn, er);
        } catch (Exception e) {
//           logger.error(e.getMessage(),e);
            logger.error(e.getMessage(),e);
            return;
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(),e);
				}
			if (sqlSession != null)
				sqlSession.close();
		}
    }
    public List<String> getCheckRepList(Map<String, Object> param) {
        List<String> keylist = new ArrayList<String>();
        dealSearchParam(param);
        List<PbcMonthReport> list = pbcMonthReportMapper.searchPage(param);
        if(CollectionUtils.isNotEmpty(list)){
            for(PbcMonthReport dto : list){
                keylist.add(dto.getTermid());
            }
        }
        return keylist;
    }
    public List<ExcelValidDto> checkData(List<String> checkKeys){
        Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
        Map<String,PbcMonthReport> repmap = new HashMap<String,PbcMonthReport>();
//        List<PbcMonthReport> issuelist = pbcMonthReportMapper.searchByKeys(checkKeys);
//        repmap = issuelist.stream().collect(Collectors.toMap(PbcMonthReport::getReportuuid,(p)->p,(key1,key2)->key1));
        List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
        List<CheckReport001Dto> resultlist  = new ArrayList<CheckReport001Dto>();
        // 检查登记表中小于今天所有未报送的数据
        Set<String>  checkKeyset=getNorepeat(checkKeys);
        
        if(CollectionUtils.isNotEmpty(checkKeyset)){
        	for(String timed:checkKeyset){
        		String cdateStr = timed+"01";
        		Date cdate=new Date();
        		try {
        			cdate = DateUtil.parseDate(cdateStr,"yyyyMMdd");
        		} catch (ParseException e) {
        			logger.error(e.getMessage(),e);
        		}
        		// 调用检查方法
        		resultlist.addAll(check(cdate,checkKeys));
        	}
            checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getRefseqno,(p)->p,(key1,key2)->key1));
            for(String key : checkKeyset){
                if(checkmap.get(key)!=null){
                    ExcelValidDto exc=new ExcelValidDto();
                    exc.setCheckResult(checkmap.get(key).getCheckResult());
                    exc.setErrorInfo(checkmap.get(key).getErrorInfo());
                    exc.setWarnInfo(checkmap.get(key).getWarnInfo());
                    exc.setValidKey(checkmap.get(key).getValidKey());
                    result.add(exc);
                }else{
                    ExcelValidDto exc=new ExcelValidDto();
                    exc.setValidKey("月报期数:"+key);
                    exc.setCheckResult("01");
                    result.add(exc);
                }
            }
        }
        return result;
    }
    Set<String> getNorepeat(List<String> checkKeys){
    	Set<String> result=new HashSet<String>();
    	for(String single:checkKeys){
    		result.add(single);
    	}
    	return result;
    }
    /**
     * @Description 检查登记表中小于今天所有未报送的数据
     * @return
     * @return List<CheckReport001Dto>
     * @version 1.7 2019年6月24日
     */
    private List<CheckReport001Dto> check(Date date,List<String> Keys){
        // 数据日期为系统时间
    	Date checkdate = DateUtil.parse(DateUtil.getSysDate());
    	if(date!=null){
    		checkdate=date;
    	}
        // 删除今天所有已检查的数据
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_人行月度统计报表.getValue(),checkdate,Keys);
       
        //检查数据
        List<CheckReport001Dto> resultList = pbcBeforeSubCheck.checkPbcReport(checkdate);
        //报错检查异常数据
        int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < resultList.size();) {
            if (batchLastIndex > resultList.size() - 1) {
                batchLastIndex = resultList.size();
                checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
                break;// 数据删除完毕,退出循环
            } else {
                checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index + batchCount;
            }
        }
        return resultList;
    }
}
