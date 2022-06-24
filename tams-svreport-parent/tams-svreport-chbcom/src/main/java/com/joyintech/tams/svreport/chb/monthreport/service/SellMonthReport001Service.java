
package com.joyintech.tams.svreport.chb.monthreport.service;


import com.joyintech.background.component.common.pack.common.util.JsonUtils;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.tams.svreport.chb.monthreport.dao.SellMonthReportDetailMapper;
import com.joyintech.tams.svreport.chb.monthreport.dto.SellMonthReportDetail;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.monthreport.dao.SellMonthReportMapper;
import com.joyintech.tams.svreport.chb.monthreport.dto.SellMonthReport;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
public class SellMonthReport001Service extends CommonExportService<SellMonthReport> {
	@Autowired
    private SellMonthReportMapper sellMonthReportMapper;
	@Autowired
	private SellMonthReportDetailMapper sellMonthReportDetailMapper;
	@Autowired
	private CheckReport001Mapper checkReport001Mapper;
	@Autowired
	private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
	@Autowired
	private BeforeSubCheck beforeSubCheck;
	@Autowired
    private MonthReportRegistBatchService monthReportRegistBatchService;

	/**
	 * 短sql最大一次最大插入条数
	 */
	@Value("${application.reportSelectOrDeleteCountForShenFen}")
	public  int reportSelectOrDeleteCountForShenFen;
	private static final String IS_CHECK_SUCESS="01";
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
	public Page<SellMonthReport> searchPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		//将发送状态改为空
		pageParam.put("sendStatus", "");
		int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<SellMonthReport> page = queryForPage(
				pagenum,legth,()->sellMonthReportMapper.searchPage(pageParam));
		return page;
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
				List<SellMonthReport> astValueList = JsonUtils.json2List(keyList,
						SellMonthReport.class);
				pageParam.put("reportList", astValueList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 数据状态
			String dataStatus = (String) pageParam.get("dataStatus");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");


			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(dataStatus)) {
				pageParam.put("dataStatusList", StringUtil.splitToList(GlobalConstant.COMMA, dataStatus));
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

	public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {
		// 导出文件
		List<File> sourceFiles = new ArrayList<>();
		List<SellMonthReport> dataList=new ArrayList<SellMonthReport>();
		dataList= com.joyintech.framework.util.JsonUtils.json2List(datas,SellMonthReport.class);
		SellMonthReport report = dataList.get(0);
		String reportuuid = report.getReportuuid();
		String reportName = report.getReportName();
		String termid = report.getTermid();
		Map<String,Object> data  = new HashMap<String,Object>();
		List<SellMonthReportDetail> tempList=new ArrayList<SellMonthReportDetail>();
		List<SellMonthReport> dateTempList=new ArrayList<SellMonthReport>();
		//查询时间 机构与机构名
		dateTempList = sellMonthReportMapper.selectByPrimaryKey(reportuuid);
		//获取机构名称及银行登记编码
		for(SellMonthReport Monthreport : dateTempList){
			String orgCode = CommonUtil.getOrgCode();
			String issuePartyId = sellMonthReportMapper.selectIssuePartyId(orgCode);
			String issuePartyName = sellMonthReportMapper.selectOrgName(orgCode);
			Monthreport.setIssuePartyName(issuePartyName);
			Monthreport.setIssuePartyId(issuePartyId);
		}
		String fileName = reportName +".xls";
		//查询当前期数的reportuuid主键
		List<SellMonthReport> reportuuidList = sellMonthReportDetailMapper.selectReportuuid(termid);
		if(reportuuidList.size() != 0){
		for(SellMonthReport monthReport :reportuuidList) {
				//分投资者分类
				if(monthReport.getReporttype().equals("1_1")) {
					tempList = sellMonthReportDetailMapper.selectReportDetail(monthReport.getReportuuid());
					if (!tempList.isEmpty()) {
						for (SellMonthReportDetail detail : tempList) {
							BigDecimal oneW = new BigDecimal("10000");
							BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
							BigDecimal valueAmountTotal = detail.getValueAmountTotal();
							BigDecimal balanceAmt = detail.getBalanceAmt();
							if(saleAmountTotal != null) {
								detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}else{
								detail.setSaleAmountTotal(BigDecimal.ZERO);
							}
							if(valueAmountTotal != null) {
								detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setValueAmountTotal(BigDecimal.ZERO);
							}
							if(balanceAmt != null) {
								detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setBalanceAmt(BigDecimal.ZERO);
							}
						}
					}
					data.put("dataList1", tempList);
					data.put("data", dateTempList);
				}
				//分地区
			if(monthReport.getReporttype().equals("1_2")) {
				tempList = sellMonthReportDetailMapper.selectReportDetail(monthReport.getReportuuid());
				if (!tempList.isEmpty()) {
					for (SellMonthReportDetail detail : tempList) {
						BigDecimal oneW = new BigDecimal("10000");
						BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
						BigDecimal valueAmountTotal = detail.getValueAmountTotal();
						BigDecimal balanceAmt = detail.getBalanceAmt();
						if(saleAmountTotal != null) {
							detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}else{
							detail.setSaleAmountTotal(BigDecimal.ZERO);
						}
						if(valueAmountTotal != null) {
							detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}
						else{
							detail.setValueAmountTotal(BigDecimal.ZERO);
						}
						if(balanceAmt != null) {
							detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}
						else{
							detail.setBalanceAmt(BigDecimal.ZERO);
						}
					}
				}
				data.put("dataList2", tempList);
				data.put("data", dateTempList);
			}
				//分销售渠道
			if(monthReport.getReporttype().equals("1_3")) {
				tempList = sellMonthReportDetailMapper.selectReportDetail(monthReport.getReportuuid());
				if (!tempList.isEmpty()) {
					for (SellMonthReportDetail detail : tempList) {
						BigDecimal oneW = new BigDecimal("10000");
						BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
						BigDecimal valueAmountTotal = detail.getValueAmountTotal();
						BigDecimal balanceAmt = detail.getBalanceAmt();
						if(saleAmountTotal != null) {
							detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}else{
							detail.setSaleAmountTotal(BigDecimal.ZERO);
						}
						if(valueAmountTotal != null) {
							detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}
						else{
							detail.setValueAmountTotal(BigDecimal.ZERO);
						}
						if(balanceAmt != null) {
							detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
						}
						else{
							detail.setBalanceAmt(BigDecimal.ZERO);
						}
					}
				}
				data.put("dataList3", tempList);
				data.put("data", dateTempList);
			}
		}
		}
		 else{
			data.put("data", dateTempList);
			data.put("dataList1", tempList);
			data.put("dataList2", tempList);
			data.put("dataList3", tempList);
		}
		File depositfile = TisUtil.createFile(data, "pbc/" +"sellMonthReport_export", fileName);
		sourceFiles.add(depositfile);
		File outFile = null;
		outFile = sourceFiles.get(0);
		return ExportUtil.export(outFile.getName(), outFile, true);
	}


	public String priview(String pageid,Map<String, Object> selectParam,String datas) throws Exception {
		//模板
		String temp = "";
		//月报主表UUID
		String reportuuid = selectParam.get("reportuuid").toString();
		//期数
		String termid = selectParam.get("termid").toString();
		//报表类型
		String reporttype = selectParam.get("reporttype").toString();
		Map<String,Object> data  = new HashMap<String,Object>();
		List<SellMonthReportDetail> tempList=new ArrayList<SellMonthReportDetail>();
		List<SellMonthReport> dateTempList=new ArrayList<SellMonthReport>();
		//查询时间
		dateTempList = sellMonthReportMapper.selectByPrimaryKey(reportuuid);
		//获取机构名称及银行登记编码
		for(SellMonthReport report : dateTempList){
			String orgCode = CommonUtil.getOrgCode();
			String issuePartyId = sellMonthReportMapper.selectIssuePartyId(orgCode);
			String issuePartyName = sellMonthReportMapper.selectOrgName(orgCode);
			report.setIssuePartyName(issuePartyName);
			report.setIssuePartyId(issuePartyId);
		}
		String fileName = "销售月度统计表"+ DateUtil.format(new Date(), "yyyy-MM-dd")+".xls";
				//分投资者分类
				if(reporttype.equals("1_1")) {
					temp = "sellMonthReport1_1";
					tempList = sellMonthReportDetailMapper.selectReportDetail(reportuuid);
					if (!tempList.isEmpty()) {
						for (SellMonthReportDetail detail : tempList) {
							BigDecimal oneW = new BigDecimal("10000");
							BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
							BigDecimal valueAmountTotal = detail.getValueAmountTotal();
							BigDecimal balanceAmt = detail.getBalanceAmt();
							if(saleAmountTotal != null) {
								detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}else{
								detail.setSaleAmountTotal(BigDecimal.ZERO);
							}
							if(valueAmountTotal != null) {
								detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setValueAmountTotal(BigDecimal.ZERO);
							}
							if(balanceAmt != null) {
								detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setBalanceAmt(BigDecimal.ZERO);
							}
						}
					}
					data.put("dataList1", tempList);
					data.put("data", dateTempList);
				}
				//分地区
				if(reporttype.equals("1_2")) {
					temp = "sellMonthReport1_2";
					tempList = sellMonthReportDetailMapper.selectReportDetail(reportuuid);
					if (!tempList.isEmpty()) {
						for (SellMonthReportDetail detail : tempList) {
							BigDecimal oneW = new BigDecimal("10000");
							BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
							BigDecimal valueAmountTotal = detail.getValueAmountTotal();
							BigDecimal balanceAmt = detail.getBalanceAmt();
							if(saleAmountTotal != null) {
								detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}else{
								detail.setSaleAmountTotal(BigDecimal.ZERO);
							}
							if(valueAmountTotal != null) {
								detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setValueAmountTotal(BigDecimal.ZERO);
							}
							if(balanceAmt != null) {
								detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setBalanceAmt(BigDecimal.ZERO);
							}
						}
					}
					data.put("dataList2", tempList);
					data.put("data", dateTempList);
				}
				//分销售渠道
				if(reporttype.equals("1_3")) {
					temp = "sellMonthReport1_3";
					tempList = sellMonthReportDetailMapper.selectReportDetail(reportuuid);
					if (!tempList.isEmpty()) {
						for (SellMonthReportDetail detail : tempList) {
							BigDecimal oneW = new BigDecimal("10000");
							BigDecimal saleAmountTotal = detail.getSaleAmountTotal();
							BigDecimal valueAmountTotal = detail.getValueAmountTotal();
							BigDecimal balanceAmt = detail.getBalanceAmt();
							if(saleAmountTotal != null) {
								detail.setSaleAmountTotal(saleAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}else{
								detail.setSaleAmountTotal(BigDecimal.ZERO);
							}
							if(valueAmountTotal != null) {
								detail.setValueAmountTotal(valueAmountTotal.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setValueAmountTotal(BigDecimal.ZERO);
							}
							if(balanceAmt != null) {
								detail.setBalanceAmt(balanceAmt.divide(oneW,6, BigDecimal.ROUND_HALF_UP));
							}
							else{
								detail.setBalanceAmt(BigDecimal.ZERO);
							}
						}
					}
					data.put("dataList3", tempList);
					data.put("data", dateTempList);
				}
		File depositfile = TisUtil.createFile(data, "pbc/" +temp, fileName);
		return this.priView(depositfile, true,reporttype);
	}
	//预览导出
	public String priView( File downloadFile, boolean deleteAfterExport,String reporttype){
		String rs =  XlsxToHtml.readExcelToHtmlThree(downloadFile,true);
		if (deleteAfterExport) {
			downloadFile.delete();
		}
		return rs;
	}

	/**
	 * 手工处理
	 * <一句话功能简述> <功能详细描述>
	 * @see
	 * @since 1.0
	 */
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<SellMonthReport> chbRegistList  = new ArrayList<SellMonthReport>();
		if(map.get("flag") != null && "01".equals(map.get("flag"))){
			//按条件查询全部
			chbRegistList = sellMonthReportMapper.searchPage(map);
		}else{
			chbRegistList = (List<SellMonthReport>)map.get("reportList");
		}
		Iterator<SellMonthReport> it=chbRegistList.iterator();
		while(it.hasNext()){
			SellMonthReport reportList =  it.next();
			if(!reportList.getSendStatus().equals("01")){
				it.remove();
			}
		}
		if (!chbRegistList.isEmpty() && chbRegistList.size() != 0) {
			for (int i = 0 ; i < chbRegistList.size() ; i++) {
				String termid = chbRegistList.get(i).getTermid();
				sellMonthReportMapper.updateInterfSubmitAndSeqBatch(termid);
			}
			}
	}

	/**
	 * 已手工处理的数据将报送状态改为未报送
	 *
	 * @see
	 * @since 1.0
	 */
	public void cancelMannul(Map<String, Object> map) {
		dealSearchParam(map);
		List<SellMonthReport> list = new ArrayList<SellMonthReport>();
		if(map.get("flag") != null && "01".equals(map.get("flag"))){
			list = sellMonthReportMapper.searchPage(map);
		}else{
			list = (List<SellMonthReport>) map.get("reportList");
		}
		if (!list.isEmpty() && list.size() != 0) {
			for (int i = 0 ; i < list.size() ; i++) {
				String termid = list.get(i).getTermid();
				sellMonthReportMapper.cancelMannultRegist(termid);
			}
		}
	}
}
