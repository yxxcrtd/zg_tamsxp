package com.joyintech.tams.svreport.pbc.classic.prdassetdebt.service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.svreport.pbc.assetdebt.dao.PbcReportAssetDebtDetailMapper;
import com.joyintech.tams.svreport.pbc.assetdebt.dto.PbcLoanDetailInfoSubReportVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.pbc.assetdebt.service.PbcReportAssetDebtService;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.service.PbcBeforeSubCheck;
import com.joyintech.tams.svreport.pbc.report.dao.PbcMonthReportMapper;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;
/**
 * 资产负债信息service
 * @author liuyu
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PbcPrdAssetDebtSubService001 extends CommonExportService<PbcMonthReport> {
	/**
	 * 人行月报Mapper
	 */
	@Autowired
	private PbcMonthReportMapper pbcMonthReportMapper;
	@Autowired
	private CheckReport001Mapper checkReport001Mapper;
	@Autowired
	private PbcBeforeSubCheck pbcBeforeSubCheck;
	@Autowired
	private PbcReportAssetDebtDetailMapper pbcReportAssetDebtDetailMapper;

	@Autowired
	private PbcReportAssetDebtService pbcReportAssetDebtService;

	/**
	 * 日志.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询产品资金募集信息
	 *
	 * @param searchParam
	 * @return
	 */
	public Page<PbcMonthReport> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
		int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		String submitDateStart = (String) searchParam.get("submitDateStart");
		if (!StringUtil.isEmpty(submitDateStart)
				&& submitDateStart.length() >= 7){
			submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
			searchParam.put("submitDateStart", submitDateStart);
		}
		String submitDateEnd = (String) searchParam.get("submitDateEnd");
		if (!StringUtil.isEmpty(submitDateEnd)
				&& submitDateEnd.length() >= 7){
			submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
			searchParam.put("submitDateEnd", submitDateEnd);
		}
		Page<PbcMonthReport> page = queryForPage(pagenum,length,()->pbcMonthReportMapper.searchPageForOnePrd(searchParam));
//		dealResult(page);
		return page;

	}
	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcMonthReport> page) {
		List<PbcMonthReport> resultList = page.getRoot();
		for(PbcMonthReport result : resultList){
//			result.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, result.getStatus()));
//			result.setSendStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态, result.getSendStatus()));
//			result.setSubmitFeedbackStatus(SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态,result.getSubmitFeedbackStatus()));
		}

	}
	/**
	 * 查询参数处理
	 * @param searchParam
	 */
	private void deaSearchParam(Map<String, Object> searchParam) {
		if(searchParam.containsKey("keyList")){
			searchParam.put("keyList",
					StringUtil.splitToList(",",(String) searchParam.get("keyList")));
		}
		searchParam.put("statusList",
				StringUtil.splitToList(",",(String) searchParam.get("status")));
		searchParam.put("sendStatusList",
				StringUtil.splitToList(",",(String) searchParam.get("sendStatus")));
		searchParam.put("submitFeedbackStatusList",
				StringUtil.splitToList(",",(String) searchParam.get("submitFeedbackStatus")));
		searchParam.put("operateModeList",
				StringUtil.splitToList(",",(String) searchParam.get("operateMode")));
		searchParam.put("ivstorientationList",
				StringUtil.splitToList(",",(String) searchParam.get("ivstorientation")));
		if (StringUtil.isNotEmpty(searchParam.get("prodId"))){
			searchParam.put("prodIdList",
					StringUtil.splitToList(",",(String) searchParam.get("prodId")));
		} else {
			searchParam.put("prodIdList",null);
		}
		if (StringUtil.isNotEmpty(searchParam.get("pbcProdId") )){
			searchParam.put("pbcProdIdList",
					StringUtil.splitToList(",",(String) searchParam.get("pbcProdId")));
		} else {
			searchParam.put("pbcProdIdList",null);
		}
		if (StringUtil.isNotEmpty(searchParam.get("pbcAccountId"))){
			searchParam.put("pbcAccountIdList",
					StringUtil.splitToList(",",(String) searchParam.get("pbcAccountId")));
		} else {
			searchParam.put("pbcAccountIdList",null);
		}
	}

	/**
	 * 设置导出文件参数
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config(
						"/com/joyintech/tams/svreport/pbc/classic/prdassetdebt/conf/export_conf.xml")
				.dataSource(DataSourceType.MASERT);
	}
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcMonthReport> selectPageData(Map<String, Object> selectParam) {
		return searchPage(selectParam);
	}

	/**
	 * 手工处理
	 * @param updateList
	 */
	@SuppressWarnings("unchecked")
	public void doManul(Map<String,Object> map) {
		logger.debug("手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcMonthReport> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			try {
				infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcMonthReport.class);
			} catch (IOException e) {
				logger.debug("数据转换异常",e.getMessage(),e);
				return ;
			}
		}else{
			Map<String,Object> pageParam;
			if("dat".equals(flag)){
				infoList = pbcMonthReportMapper.searchPageForOnePrd(map);
			}else{
				pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
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
				infoList = pbcMonthReportMapper.searchPage(pageParam);
			}

		}
		// His插入
		Iterator<PbcMonthReport> it = infoList.iterator();
		while(it.hasNext()){
			PbcMonthReport info = it.next();
			if(SysDataUtil.getNameFromDataDict(DictCode.报送状态, "03").equals(info.getSendStatus())){
				it.remove();
			}
//			if(StringUtils.isNotBlank(info.getReportuuid())) {
//				List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList = pbcReportAssetDebtDetailMapper.selectDetailInfoSubByReportUuid(info.getReportuuid());
//				List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList2 = pbcReportAssetDebtDetailMapper.selectCapitalTrustDetailByReportUuid(info.getReportuuid());
//				if(CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList) && CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList2)){
//					it.remove();
//				}
//			}
		}
		if(infoList.isEmpty())
			return ;
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcMonthReport> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				//已报送数据进留痕表
				//pbcMonthReportMapper.insertReportHis(list1);
				//删除正式表已报送数据
				//pbcMonthReportMapper.deleteLastSubmitRegist(list1);
				pbcMonthReportMapper.updateInterfSubmitAndSeqBatch(DictCodeListExt2.报送状态_手工处理.getValue(), list1,
						map.get("submitadd")==null?null:map.get("submitadd").toString());
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcMonthReport> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			//已报送数据进留痕表
			//pbcMonthReportMapper.insertReportHis(list2);
			//删除正式表已报送数据
			//pbcMonthReportMapper.deleteLastSubmitRegist(list2);
			pbcMonthReportMapper.updateInterfSubmitAndSeqBatch(DictCodeListExt2.报送状态_手工处理.getValue(), list2,
					map.get("submitadd")==null?null:map.get("submitadd").toString());
		}
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 end */
		logger.debug("手工处理结束:------------------------------"+new Date());

	}
	/**
	 * 取消手工处理
	 * @param updateList
	 */
	@SuppressWarnings("unchecked")
	public String doCancelManul(Map<String,Object> map) {
		logger.debug("取消手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcMonthReport> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			infoList  = (List<PbcMonthReport>) map.get("infoList");
		}else{
			// 查询方式
//			Map<String,Object> pageParam = RequestUtil.getPageParam();
//			infoList = searchPage(pageParam).getRoot();
			deaSearchParam(map);
			infoList = pbcMonthReportMapper.searchPageForOnePrd(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcMonthReportMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcMonthReport> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				//				pbcMonthReportMapper.cancelMannultRegist(list1);
//		 		// 将履历表中履历号最大的数据挪回登记表
//		 		pbcMonthReportMapper.insertReportDataFromHis(list1);
//		 		// 删除履历表中移除数据
//		 		pbcMonthReportMapper.deleteLastSubmitHis(list1);
				pbcMonthReportMapper.cancelMannultRegistForOnePrd(list1);
				// 将履历表中履历号最大的数据挪回登记表
				//pbcMonthReportMapper.insertReportDataFromHisForOnePrd(list1);
				// 删除履历表中移除数据
				//pbcMonthReportMapper.deleteLastSubmitHisForOnePrd(list1);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcMonthReport> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			pbcMonthReportMapper.cancelMannultRegistForOnePrd(list2);
			// 将履历表中履历号最大的数据挪回登记表
			//pbcMonthReportMapper.insertReportDataFromHisForOnePrd(list2);
			// 删除履历表中移除数据
			//pbcMonthReportMapper.deleteLastSubmitHisForOnePrd(list2);
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 end */
		return "success";
	}
	public List<String> getCheckRepList(Map<String, Object> param) {
		List<String> keylist = new ArrayList<String>();
		deaSearchParam(param);
		String submitDateStart = (String) param.get("submitDateStart");
		if (!StringUtil.isEmpty(submitDateStart)
				&& submitDateStart.length() >= 7){
			submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
			param.put("submitDateStart", submitDateStart);
		}
		String submitDateEnd = (String) param.get("submitDateEnd");
		if (!StringUtil.isEmpty(submitDateEnd)
				&& submitDateEnd.length() >= 7){
			submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
			param.put("submitDateEnd", submitDateEnd);
		}
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
					exc.setValidKey("报告期数:"+key);
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
	private List<CheckReport001Dto> check(Date date,List<String> checkKeys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		if(date!=null){
			checkdate=date;
		}
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_人行月度统计报表.getValue(),checkdate,checkKeys);

		//检查数据
		List<CheckReport001Dto> resultList = pbcBeforeSubCheck.checkPbcPbcPrdAssetDebtSub(checkdate);
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


	/**
	 * 根据条件查询数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public List<PbcMonthReport> searchByCondition(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
		int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		String submitDateStart = (String) searchParam.get("submitDateStart");
		if (!StringUtil.isEmpty(submitDateStart)
				&& submitDateStart.length() >= 7){
			submitDateStart = submitDateStart.substring(0,4)+submitDateStart.substring(5,7);
			searchParam.put("submitDateStart", submitDateStart);
		}
		String submitDateEnd = (String) searchParam.get("submitDateEnd");
		if (!StringUtil.isEmpty(submitDateEnd)
				&& submitDateEnd.length() >= 7){
			submitDateEnd = submitDateEnd.substring(0,4)+submitDateEnd.substring(5,7);
			searchParam.put("submitDateEnd", submitDateEnd);
		}

		List<PbcMonthReport> reportList = pbcMonthReportMapper.searchPageForOnePrd(searchParam);
		return reportList;
	}



	/**
	 * 	导出
	 * @param pageId
	 * @param selectParam
	 * @param datas
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {

		deaSearchParam(selectParam);
		// 导出文件
		List<File> sourceFiles = new ArrayList<>();

		List<PbcMonthReport> dataList=new ArrayList<PbcMonthReport>();

		if (datas != null){
			dataList=JsonUtils.json2List(datas,PbcMonthReport.class);
		}else{
			//不选报表导出所有未报送的记录
			dataList= searchByCondition(selectParam);
			//针对全导出时，对于贷款内容和资金信托份额内容都为空时，删除该条记录
			/*Iterator<PbcMonthReport> ii=dataList.iterator();
			while (ii.hasNext()){//判断集合内是否还有元素
				PbcMonthReport o=ii.next();//获取该元素
				if(StringUtils.isNotBlank(o.getReportuuid())) {
					List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList = pbcReportAssetDebtDetailMapper.selectDetailInfoSubByReportUuid(o.getReportuuid());
					List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList2 = pbcReportAssetDebtDetailMapper.selectCapitalTrustDetailByReportUuid(o.getReportuuid());
					if(CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList) && CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList2)){
						ii.remove();//【使用迭代器】删除当前指向的元素
					}
				}
			}*/
		}

		Map<String, List<PbcMonthReport>> dataParam = new HashMap<String, List<PbcMonthReport>>();
		// 单选导出
		for (int i =0 ;i < dataList.size();i++){

			List<PbcMonthReport> reprotList = new ArrayList<PbcMonthReport>();
			reprotList.add(dataList.get(i));

			String pbcAccountIdShow=dataList.get(i).getPbcAccountIdShow();
			String termid=dataList.get(i).getTermid();
			String code= pbcAccountIdShow + '|' + termid;
			//获取dataParam这个集合中是否有这个code的值
			if(dataParam.get(code)!=null){

				String sendStatus=dataList.get(i).getSendStatus();
				String status=dataList.get(i).getStatus();
				if(sendStatus.equals(DictCodeListExt2.报送状态_未报送.getValue()) && status.equals(DictCodeListExt2.报送数据状态_修改.getValue())){
					//dataParam.put(dataList.get(i).getPbcProdId(), dataList.get(i));
					dataParam.put(code, reprotList);
				}
			} else {
				dataParam.put(code, reprotList);
			}
		}
		// excel计数器
		int count = 1;
		Format f2 = new DecimalFormat("00");
		Map<String,Object> data  = new HashMap<String,Object>();
		Map<String, Integer> nameMap = new HashMap<String, Integer>();
		Iterator<Map.Entry<String,List<PbcMonthReport>>> iterator = dataParam.entrySet().iterator();
		while (iterator.hasNext()) {
			//List<String> sheetList = createSheet();
			Map.Entry<String, List<PbcMonthReport>> entry = iterator.next();
			for(PbcMonthReport dto : entry.getValue()){
				data  = new HashMap<String,Object>();

				//获取报表类型  根据不同的报表类型 查询不同的明细和对应的 模板数据
				String reporttype=dto.getReporttype();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("reportuuid", dto.getReportuuid());
				//String account_id=dto.getPbcAccountId();
				String account_id=dto.getPbcAccountIdShow();

				//模板名
				String name=dto.getPbcCode();
				if(StringUtils.isEmpty(name)){
					name = account_id;
				}
				param.put("pbcAccountId",name);
				for (Map.Entry<Object, Object> entity : pbcReportAssetDebtService.createDataForExport(param).entrySet()) {
					data.put(entity.getKey().toString(), entity.getValue());
				}
				//data.put("dataList", new ArrayList<PbcLoanDetailInfoSubReportVo>());
				//data.put("data2List", new ArrayList<PbcLoanDetailInfoSubReportVo>());
				PbcLoanDetailInfoSubReportVo pbc=new PbcLoanDetailInfoSubReportVo();
				List<PbcLoanDetailInfoSubReportVo> tempList=new ArrayList<PbcLoanDetailInfoSubReportVo>();
				tempList.add(pbc);
				data.put("dataList", tempList);
				data.put("data2List", tempList);
				// 获取sheet2贷款内容和sheet3资金信托份额内容
				if(StringUtils.isNotBlank(dto.getReportuuid())) {
					List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList = pbcReportAssetDebtDetailMapper.selectDetailInfoSubByReportUuid(dto.getReportuuid());
					if(CollectionUtils.isNotEmpty(pbcLoanDetailInfoSubReportVoList)){
						pbcLoanDetailInfoSubReportVoList.forEach(item->{
							item.setDateLoanIssuanceStr(DateUtil.format(item.getDateLoanIssuance(), "yyyy-MM-dd"));
							item.setLoanMaturityDateStr(DateUtil.format(item.getLoanMaturityDate(), "yyyy-MM-dd"));
							item.setMaturityDateLoanRolloverStr(DateUtil.format(item.getMaturityDateLoanRollover(), "yyyy-MM-dd"));
						});
						data.put("dataList", pbcLoanDetailInfoSubReportVoList);
					}
					List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList2 = pbcReportAssetDebtDetailMapper.selectCapitalTrustDetailByReportUuid(dto.getReportuuid());
					if(CollectionUtils.isNotEmpty(pbcLoanDetailInfoSubReportVoList2)) {
						data.put("data2List", pbcLoanDetailInfoSubReportVoList2);
					}
				}
				//生成文件
				if (nameMap.get(name) != null) {
					nameMap.put(name, nameMap.get(name) + 1);
					name = name  + "(" + nameMap.get(name) + ")";
				} else {
					nameMap.put(name, 1);
				}
				String fileName = "PVD_"+name+".xls";
				File depositfile = TisUtil.createFile(data, "pbc/" +"PbcReport_OneprdAssetDebt_export", fileName);
				sourceFiles.add(depositfile);
				count++;
			}
		}
		// 如果没有未报送的记录，生成空模板
		if (sourceFiles.isEmpty()){
//			data.put("dataList", new ArrayList<PbcLoanDetailInfoSubReportVo>());
//	    	data.put("data2List", new ArrayList<PbcLoanDetailInfoSubReportVo>());
			String fileName = "资产负债信息"+DateUtil.format(new Date(), "yyyy-MM-dd")+".xls";
			File depositfile = TisUtil.createFile(data, "pbc/" +"PbcReport_OneprdAssetDebt_export", fileName);
			sourceFiles.add(depositfile);
		}

		// 如果生成excel文件个数大于1，生成压缩文件
		File outFile = null;
		if (sourceFiles.size() > 1) {
			String exportFilePath = SvrDataUtil.getSysParam(
					GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//            String zipFileName = FileNameUtil.createFileName("zip");
			String zipFileName = "PbocAssetDebt("+DateUtil.format(new Date(), "yyyy-MM-dd")+").zip";
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
	 * 	导出
	 * @param pageId
	 * @param selectParam
	 * @param datas
	 * @return
	 * @throws IOException
	 */
	public  List<PbcMonthReport> searchNoDxAndXt(Map<String, Object> params) throws IOException {
		deaSearchParam(params);
		List<PbcMonthReport> dataList=new ArrayList<PbcMonthReport>();
		String datas=(String)params.get("datasExpArr");
		String flag=params.get("flag").toString();;
		if (datas != null){
			dataList=JsonUtils.json2List(datas,PbcMonthReport.class);
		}else if(StringUtil.isNotEmpty(flag) && flag.equals("0")){
			//不选报表导出所有未报送的记录
			dataList= searchByCondition(params);
		}
		List<PbcMonthReport> list=new ArrayList<PbcMonthReport>();
		for (PbcMonthReport dto: dataList){
			if(StringUtils.isNotBlank(dto.getReportuuid())) {
				List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList = pbcReportAssetDebtDetailMapper.selectDetailInfoSubByReportUuid(dto.getReportuuid());
				List<PbcLoanDetailInfoSubReportVo> pbcLoanDetailInfoSubReportVoList2 = pbcReportAssetDebtDetailMapper.selectCapitalTrustDetailByReportUuid(dto.getReportuuid());
				if(CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList) && CollectionUtils.isEmpty(pbcLoanDetailInfoSubReportVoList2)){
					list.add(dto);
				}
			}

		}
		return list;
	}

}
