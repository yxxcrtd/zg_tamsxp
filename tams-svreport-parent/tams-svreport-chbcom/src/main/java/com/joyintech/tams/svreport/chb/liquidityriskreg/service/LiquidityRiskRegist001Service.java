package com.joyintech.tams.svreport.chb.liquidityriskreg.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.joyintech.background.component.common.pack.common.util.JsonUtils;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.system.institution.service.InstitutionService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.svreport.chb.liquidityriskreg.dao.LiquidityRiskRegist001Mapper;
import com.joyintech.tams.svreport.chb.liquidityriskreg.dto.LiquidityRiskRegist001Dto;
import com.joyintech.tams.svreport.chb.monthreport.service.XlsxToHtml;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.common.base.dao.BaseMapper;

@Service(value="svrLiquidityRiskRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class LiquidityRiskRegist001Service extends CommonExportService<LiquidityRiskRegist001Dto> {

	@Autowired
	private LiquidityRiskRegist001Mapper liquidityRiskRegist001Mapper;

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 机构管理service
	 */
	@Autowired(required = false)
	private InstitutionService institutionService;

	/**
	 * 查询页面数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public Page<LiquidityRiskRegist001Dto> searchPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		//将发送状态改为空
		int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
		int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<LiquidityRiskRegist001Dto> page = queryForPage(
				pagenum,legth,()->liquidityRiskRegist001Mapper.searchPage(pageParam));
		dealSearchResult(page.getRoot());
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
				List<LiquidityRiskRegist001Dto> astValueList = JsonUtils.json2List(keyList,
						LiquidityRiskRegist001Dto.class);
				pageParam.put("reportList", astValueList);
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 数据状态
			String status = (String) pageParam.get("status");
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");


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

		}
	}

	/**
	 * 手工处理
	 * <一句话功能简述> <功能详细描述>
	 * @see
	 * @since 1.0
	 */
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<LiquidityRiskRegist001Dto> chbRegistList  = new ArrayList<LiquidityRiskRegist001Dto>();
		if(map.get("flag") != null && "01".equals(map.get("flag"))){
			//按条件查询全部
			chbRegistList = liquidityRiskRegist001Mapper.searchPage(map);
		}else{
			chbRegistList = (List<LiquidityRiskRegist001Dto>)map.get("reportList");
		}
		Iterator<LiquidityRiskRegist001Dto> it=chbRegistList.iterator();
		while(it.hasNext()){
			LiquidityRiskRegist001Dto reportList =  it.next();
			if(!reportList.getSendStatus().equals("01")){
				it.remove();
			}
		}
		if (!chbRegistList.isEmpty() && chbRegistList.size() != 0) {
			for (int i = 0 ; i < chbRegistList.size() ; i++) {
				String itemId = chbRegistList.get(i).getItemId();
				liquidityRiskRegist001Mapper.updateInterfSubmitAndSeqBatch(itemId);
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
		List<LiquidityRiskRegist001Dto> list = new ArrayList<LiquidityRiskRegist001Dto>();
		if(map.get("flag") != null && "01".equals(map.get("flag"))){
			list = liquidityRiskRegist001Mapper.searchPage(map);
		}else{
			list = (List<LiquidityRiskRegist001Dto>) map.get("reportList");
		}
		if (!list.isEmpty() && list.size() != 0) {
			for (int i = 0 ; i < list.size() ; i++) {
				String itemId = list.get(i).getItemId();
				liquidityRiskRegist001Mapper.cancelMannultRegist(itemId);
			}
		}
	}

	/**
	 * 处理查询结果
	 * @param list
	 */
	public void dealSearchResult(List<LiquidityRiskRegist001Dto> list) {
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for(LiquidityRiskRegist001Dto dto:list){
			if(StringUtil.isNotEmpty(dto.getStatus())){
				dto.setStatusStr(dto.getStatus() + " " + SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, dto.getStatus()));
			}
			if(StringUtil.isNotEmpty(dto.getSendStatus())){
				dto.setSendStatusStr(dto.getSendStatus() + " " + SvrDataUtil.getNameFromDataDict(DictCode.报送状态, dto.getSendStatus()));
			}
			if(StringUtil.isNotEmpty(dto.getSubmitFeedbackStatus())){
				dto.setSubmitFeedbackStatusStr(dto.getSubmitFeedbackStatus() + " " + SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态, dto.getSubmitFeedbackStatus()));
			}
		}
	}

	/**
	 * 预览
	 * @param reportName
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public String priview(String reportName,String itemId) throws Exception {
		// 导出文件
		Map<String, Object> data = new HashMap<String, Object>();
		//获取本期数明细表内容
		List<LiquidityRiskRegist001Dto> detailList = liquidityRiskRegist001Mapper.selectDetailData(itemId);
		//获取机构信息
		String orgCode = CommonUtil.selectBankCode();
		String orgName = "";
		if(StringUtil.isNotEmpty(orgCode)){
			// 取得系统中所有机构
			List<SuggestDto> institutionList = institutionService.getAllInstitutionCode(null);
			Map<String, String> institutionMap = new HashMap<String, String>();
			if (!StringUtil.isEmptyList(institutionList)) {
				institutionMap = institutionList.stream().collect(
						JoyinCollectors.toMap(x -> x.getCode(), x -> x.getName()));
			}
			String orgId = liquidityRiskRegist001Mapper.selectOrgCode(orgCode);
			orgName = institutionMap.get(orgId);
		}
		//联系人姓名
		String contactName=SvrDataUtil.getSysParam(SVReportGlobalConstant.COUNTACT_NAME);
		if(StringUtil.isEmpty(contactName)){
			contactName = " ";
		}
		//联系人电话
		String contactPhoneNum=SvrDataUtil.getSysParam(SVReportGlobalConstant.COUNTACT_PHONE_NUM);
		if(StringUtil.isEmpty(contactPhoneNum)){
			contactPhoneNum = " ";
		}
		//组装表头信息
		LiquidityRiskRegist001Dto basics = new LiquidityRiskRegist001Dto();
		List<LiquidityRiskRegist001Dto> basicsList = new ArrayList<>();
		basics.setContactName(contactName);
		basics.setContactPhoneNum(contactPhoneNum);
		basics.setOrgCode(orgCode);
		basics.setOrgName(orgName);
		basics.setItemId(itemId);
		basicsList.add(basics);
		data.put("data",basicsList);
		//组装主体信息
		List<LiquidityRiskRegist001Dto> dataList = new ArrayList<>();
		dealDateList(dataList,detailList);
		data.put("dataList",dataList);
		File depositfile = TisUtil.createFile(data, "LiquidityRiskRegist001ForViwe", reportName + ".xls");
		return this.priView(depositfile, true);
	}

	//预览导出
	public String priView( File downloadFile, boolean deleteAfterExport){
		String rs =  XlsxToHtml.readExcelToHtml(downloadFile,true);
		if (deleteAfterExport) {
			downloadFile.delete();
		}
		return rs;
	}

	/**
	 * 导出中债Excel
	 * @param pageId
	 * @param selectParam
	 * @param datas
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam, String datas) throws IOException {
		// 导出文件
		List<File> sourceFiles = new ArrayList<>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<LiquidityRiskRegist001Dto> resultList = new ArrayList<>();
		try {
			resultList = com.joyintech.framework.util.JsonUtils.json2List(datas, LiquidityRiskRegist001Dto.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		String reportName = resultList.get(0).getItemName();
		//期数
		String itemId = resultList.get(0).getItemId();
		//获取本期数明细表内容
		List<LiquidityRiskRegist001Dto> detailList = liquidityRiskRegist001Mapper.selectDetailData(itemId);
		//获取机构信息
		String orgCode = CommonUtil.selectBankCode();
		String orgName = "";
		if(StringUtil.isNotEmpty(orgCode)){
			// 取得系统中所有机构
			List<SuggestDto> institutionList = institutionService.getAllInstitutionCode(null);
			Map<String, String> institutionMap = new HashMap<String, String>();
			if (!StringUtil.isEmptyList(institutionList)) {
				institutionMap = institutionList.stream().collect(
						JoyinCollectors.toMap(x -> x.getCode(), x -> x.getName()));
			}
			String orgId = liquidityRiskRegist001Mapper.selectOrgCode(orgCode);
			orgName = institutionMap.get(orgId);
		}
		//联系人姓名
		String contactName=SvrDataUtil.getSysParam(SVReportGlobalConstant.COUNTACT_NAME);
		if(StringUtil.isEmpty(contactName)){
			contactName = " ";
		}
		//联系人电话
		String contactPhoneNum=SvrDataUtil.getSysParam(SVReportGlobalConstant.COUNTACT_PHONE_NUM);
		if(StringUtil.isEmpty(contactPhoneNum)){
			contactPhoneNum = " ";
		}
		//组装表头信息
		LiquidityRiskRegist001Dto basics = new LiquidityRiskRegist001Dto();
		List<LiquidityRiskRegist001Dto> basicsList = new ArrayList<>();
		basics.setContactName(contactName);
		basics.setContactPhoneNum(contactPhoneNum);
		basics.setOrgCode(orgCode);
		basics.setOrgName(orgName);
		basics.setItemId(itemId);
		basicsList.add(basics);
		data.put("data",basicsList);
		//组装主体信息
		List<LiquidityRiskRegist001Dto> dataList = new ArrayList<>();
		dealDateList(dataList,detailList);
		data.put("dataList",dataList);
		File depositfile = TisUtil.createFile(data, "LiquidityRiskRegist001", reportName + ".xls");
		sourceFiles.add(depositfile);
		File outFile = null;
		outFile = sourceFiles.get(0);
		return ExportUtil.export(outFile.getName(), outFile, true);
	}


	/**
	 * 组装主体信息
	 * @param dataList
	 * @param detailList
	 */
	public void dealDateList(List<LiquidityRiskRegist001Dto> dataList,List<LiquidityRiskRegist001Dto> detailList) {
		if(CollectionUtils.isEmpty(detailList)){
			LiquidityRiskRegist001Dto data = new LiquidityRiskRegist001Dto();
			dataList.add(data);
			return;
		}
		int cishuNum03 = 0;
		int cishuNum04 = 0;
		int cishuNum12 = 0;
		int cishuNum13 = 0;
		int cishuNum14 = 0;
		int cishuNum15 = 0;
		int cishuNum16 = 0;
		int cishuNum17 = 0;
		int cishuNum99 = 0;
		int zhishuNum01 = 0;
		int zhishuNum02 = 0;
		int zhishuNum11 = 0;
		//记录产品只数
		Map<String,List<LiquidityRiskRegist001Dto>> zhishuNumMap = new HashMap<>();
		Map<String,LiquidityRiskRegist001Dto> SolutionsMap = new HashMap<>();
		for(LiquidityRiskRegist001Dto detail:detailList){
			//产品只数map处理记录
			if(CollectionUtils.isNotEmpty(zhishuNumMap.get(detail.getRegCode()))){
				zhishuNumMap.get(detail.getRegCode()).add(detail);
			}else{
				List<LiquidityRiskRegist001Dto> zhishu = new ArrayList<>();
				zhishu.add(detail);
				zhishuNumMap.put(detail.getRegCode(),zhishu);
			}
			//处理发生次数
			if(StringUtil.equals(detail.getSolutions(),"03")){
				cishuNum03 = cishuNum03 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"04")){
				cishuNum04 = cishuNum04 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"12")){
				cishuNum12 = cishuNum12 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"13")){
				cishuNum13 = cishuNum13 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"14")){
				cishuNum14 = cishuNum14 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"15")){
				cishuNum15 = cishuNum15 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"16")){
				cishuNum16 = cishuNum16 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"17")){
				cishuNum17 = cishuNum17 + 1;
			}else if(StringUtil.equals(detail.getSolutions(),"99")){
				cishuNum99 = cishuNum99 + 1;
			}
			if(StringUtil.equals(detail.getSolutions(),"01")||StringUtil.equals(detail.getSolutions(),"02")||StringUtil.equals(detail.getSolutions(),"11")){
				if(SolutionsMap.get(detail.getSolutions()) == null){
					if(StringUtil.isEmpty(detail.getRemark())){
						detail.setRemark("");
					}
					SolutionsMap.put(detail.getSolutions(),detail);
				}else{
					if(StringUtil.isNotEmpty(detail.getDescribed())) {
						SolutionsMap.get(detail.getSolutions()).setDescribed(SolutionsMap.get(detail.getSolutions()).getDescribed() + "\n" + detail.getDescribed());
					}else{
						SolutionsMap.get(detail.getSolutions()).setDescribed(SolutionsMap.get(detail.getSolutions()).getDescribed() + "\n" + " ");
					}
					if(StringUtil.isNotEmpty(detail.getDisposeResult())) {
						SolutionsMap.get(detail.getSolutions()).setDisposeResult(SolutionsMap.get(detail.getSolutions()).getDisposeResult() + "\n" + detail.getDisposeResult());
					}else{
						SolutionsMap.get(detail.getSolutions()).setDisposeResult(SolutionsMap.get(detail.getSolutions()).getDisposeResult() + "\n" + " ");
					}
					if(StringUtil.isNotEmpty(detail.getRemark())) {
						SolutionsMap.get(detail.getSolutions()).setRemark(SolutionsMap.get(detail.getSolutions()).getRemark() + "\n" + detail.getRemark());
					}else{
						SolutionsMap.get(detail.getSolutions()).setRemark(SolutionsMap.get(detail.getSolutions()).getRemark() + "\n" + " ");
					}
				}
				if(StringUtil.equals(detail.getSolutions(),"01")){
					zhishuNum01 = zhishuNum01 + 1;
				}else if(StringUtil.equals(detail.getSolutions(),"02")){
					zhishuNum02 = zhishuNum02 + 1;
				}else if(StringUtil.equals(detail.getSolutions(),"11")){
					zhishuNum11 = zhishuNum11 + 1;
				}
			}else{
				dataList.add(detail);
			}
		}
		//塞入处理后的01,02,11
		if(SolutionsMap.size()>0){
			for(String key :SolutionsMap.keySet()){
				dataList.add(SolutionsMap.get(key));
			}
		}
		//塞入发生次数和产品只数
		for(LiquidityRiskRegist001Dto data:dataList){
			//塞入发生次数
			if(StringUtil.equals(data.getSolutions(),"01")||StringUtil.equals(data.getSolutions(),"02")||StringUtil.equals(data.getSolutions(),"11")){
				data.setFrequency(null);
				//处理产品登记编码，风险发生日期，风险应对日期
				data.setRegCode(null);
				data.setOccurDate(null);
				data.setReplyDate(null);
			}else{
				if(StringUtil.equals(data.getSolutions(),"03")){
					data.setFrequency(Integer.toString(cishuNum03));
				}else if(StringUtil.equals(data.getSolutions(),"04")){
					data.setFrequency(Integer.toString(cishuNum04));
				}else if(StringUtil.equals(data.getSolutions(),"12")){
					data.setFrequency(Integer.toString(cishuNum12));
				}else if(StringUtil.equals(data.getSolutions(),"13")){
					data.setFrequency(Integer.toString(cishuNum13));
				}else if(StringUtil.equals(data.getSolutions(),"14")){
					data.setFrequency(Integer.toString(cishuNum14));
				}else if(StringUtil.equals(data.getSolutions(),"15")){
					data.setFrequency(Integer.toString(cishuNum15));
				}else if(StringUtil.equals(data.getSolutions(),"16")){
					data.setFrequency(Integer.toString(cishuNum16));
				}else if(StringUtil.equals(data.getSolutions(),"17")){
					data.setFrequency(Integer.toString(cishuNum17));
				}else if(StringUtil.equals(data.getSolutions(),"99")){
					data.setFrequency(Integer.toString(cishuNum99));
				}
				//塞入产品只数
				int i = zhishuNumMap.get(data.getRegCode()).size();
				data.setProdParameter(Integer.toString(i));
			}
		}

		for(LiquidityRiskRegist001Dto data:dataList){
			if(StringUtil.isEmpty(data.getProdParameter())){
				int i = zhishuNumMap.size();
				data.setProdParameter(Integer.toString(i));
			}
			data.setSolutions(data.getSolutions() + " " + SvrDataUtil.getNameFromDataDict(DictCode.流动性风险应对措施, data.getSolutions()));
			data.setOccurDateStr(DateUtil.format(data.getOccurDate()));
			data.setReplyDateStr(DateUtil.format(data.getReplyDate()));
		}
	}

}