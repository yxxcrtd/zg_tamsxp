package com.joyintech.tams.svreport.chb.prodraisetotalregist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.util.SvrMaptoEntityUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.proddurationreg.dto.ProdDurationReg001Dto;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dao.ChbPrdRaisetotSubHisMapper;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dao.RaiseTotalRegist001Mapper;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dto.RaiseTotalRegist001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author jiangxiao
 * @version 3.0 2019年01月21日
 * @see RaiseTotalRegist001Service
 * @since 3.0
 */
@Service(value="svrRaiseTotalRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class RaiseTotalRegist001Service extends SvrCommonExportService<RaiseTotalRegist001Dto> {

	@Autowired
	private RaiseTotalRegist001Mapper raiseTotalRegist001Mapper;
	@Autowired
	private CheckReport001Mapper checkReport001Mapper;
	@Autowired
	private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
	@Autowired
	private BeforeSubCheck beforeSubCheck;

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
	 * sqlldr用数据库连接
	 */
	@Value("${spring.datasource.reportSqlldrDatabaseName}")
	private String reportSqlldrDatabaseName;
	@Autowired
	private ChbPrdRaisetotSubHisMapper chbPrdRaisetotSubHisMapper;
	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;
	@Autowired
	private SvrMaptoEntityUtil svrMaptoEntityUtil;
	private String tableName = "CHB_PRD_RAISETOT_SUB";

	@Autowired
	private BeforeSubErrorCheck beforeSubErrorCheck;

	/**
	 * @Description 产品募集总量分页查询
	 * @param pageParam
	 * @return Page<RaiseTotalRegist001Dto>
	 * @version 3.0 2019年1月22日
	 */
	public Page<RaiseTotalRegist001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);

		//add by leipeng 20200628
		if(CommonUtil.isBAmodel()){
			pageParam.put("model_type", "01");
		}

		if (null != pageParam.get(GlobalConstant.PARAM_PAGE)
				&& null != pageParam.get(GlobalConstant.PARAM_LENGTH)) {
			int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
			int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
			Page<RaiseTotalRegist001Dto> page = queryForPage(
					pagenum,legth,()->raiseTotalRegist001Mapper.selectAllData(pageParam));
			dealResultlist(page.getRoot());
			return page;
		} else {
			Page<RaiseTotalRegist001Dto> page = queryForPage(RaiseTotalRegist001Mapper.class,"selectAllData",pageParam);
			dealResultlist(page.getRoot());
			return page;
		}

	}

	private void dealResultlist(List<RaiseTotalRegist001Dto> resultlist){
		if(!CollectionUtils.isEmpty(resultlist)){
			for(RaiseTotalRegist001Dto dto : resultlist){
				dto.setProdRegCode(dto.getOrgCode());
				if(StringUtil.isNotEmpty(dto.getSubmitFeedbackStatus())){
					dto.setSubmitFeedbackStatus(SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,dto.getSubmitFeedbackStatus()));
				}
				if(StringUtil.isNotEmpty(dto.getIsConsign())){
					dto.setIsConsign(dto.getIsConsign()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债,dto.getIsConsign()));
				}
				dto.setProdRegCode(dto.getRegCode());
			}
		}
	}

	private void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<RaiseTotalRegist001Dto> investList = JsonUtils.json2List(keyList,
						RaiseTotalRegist001Dto.class);
				pageParam.put("investList", investList);
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 将产品代码当做多选下拉框处理
			String prodName = (String) pageParam.get("prodName");
			if (StringUtil.isNotBlank(prodName)) {
				pageParam.put("prodNameList", StringUtil.splitToList(GlobalConstant.COMMA, prodName));
			}
			//产品登记编码
			String regCode = (String) pageParam.get("regCode");
			if (StringUtil.isNotBlank(regCode)) {
				pageParam.put("regCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCode));
			}
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			String status = (String) pageParam.get("status");
			// 将数据状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
		}

	}

	public List<RaiseTotalRegist001Dto> selectForExport(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		@SuppressWarnings("unchecked")
		List<RaiseTotalRegist001Dto> dataList =  (List<RaiseTotalRegist001Dto>) pageParam.get("investList");

		List<RaiseTotalRegist001Dto> dtoList = new ArrayList<RaiseTotalRegist001Dto>();
		this.selectAllDataBatch(dataList, dtoList);
		return dtoList;
	}

	private void selectAllDataBatch(List<RaiseTotalRegist001Dto> dataList, List<RaiseTotalRegist001Dto> dtoList) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			dtoList.addAll(raiseTotalRegist001Mapper.selectAllDataByList(dataList));
			return;
		}

		List<RaiseTotalRegist001Dto> newDataList = new ArrayList<RaiseTotalRegist001Dto>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			dtoList.addAll(raiseTotalRegist001Mapper.selectAllDataByList(newDataList.subList(0, maxDataCountOneTime)));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			dtoList.addAll(raiseTotalRegist001Mapper.selectAllDataByList(newDataList));
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

		// 设置参数
		dealSearchParam(pageParam);
		//无查询参数的情况
		exportParam.config("/com/joyintech/tams/svreport/chb/prodraisetotalregist/conf/export_conf.xml")
				.select(() -> raiseTotalRegist001Mapper.selectAllDataForExport(pageParam));

	}
	public List<RaiseTotalRegist001Dto> selectAllData(Map<String, Object> pageParam){
		return raiseTotalRegist001Mapper.selectAllDataForExport(pageParam);
	}
	@Override
	public com.joyintech.framework.grid.Page<RaiseTotalRegist001Dto> selectPageData(Map<String, Object> pageParam) {
		List<RaiseTotalRegist001Dto> data = restTemplate.postForList(
				SvrServiceApplication.CHB_SERVICE_NAME + "/RaiseTotalRegist/selectAllData",pageParam,
				RaiseTotalRegist001Dto.class);
		com.joyintech.framework.grid.Page<RaiseTotalRegist001Dto> page = new com.joyintech.framework.grid.Page<RaiseTotalRegist001Dto>();
		page.setRoot(data);
		//page.setTotal(data.size());
		if(!CollectionUtils.isEmpty(page.getRoot())){
			for(RaiseTotalRegist001Dto dto : page.getRoot()){
				dto.setBelongInstitutionCode(null);
			}
		}
		int pageSize = StringUtil.integerValueOf(pageParam.get(GlobalConstant.PARAM_LENGTH).toString(), StringUtil.integerValueOf(CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_ROW_COUNT),50000));

		if(data!=null && data.size() > 0){
			page.setTotal(data.size()/pageSize+1);
		}else{
			page.setTotal(0);
		}

		return  page;
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
			else if("2".equals(flag)){
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
	 * 手动处理
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<RaiseTotalRegist001Dto> prodTerminationList = new ArrayList<RaiseTotalRegist001Dto>();
		//选择的手动处理
		List<RaiseTotalRegist001Dto> subList = (List<RaiseTotalRegist001Dto>) map.get("investList");
		if(!CollectionUtils.isEmpty(subList)){
			for(RaiseTotalRegist001Dto dto : subList){
				// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 start
//				if(DictCodeListExt2.报送状态_未报送.getValue().equals(dto.getSendStatus())){
//					prodTerminationList.add(dto);
//				}
				prodTerminationList.add(dto);
				// modify by zhangliang 20210309 手工处理与前台逻辑一致：只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和中债退回的数据进行手工处理 end
			}
		}else{
			//未选中数据
			prodTerminationList = raiseTotalRegist001Mapper.selectAllData(map);
		}
		if(!CollectionUtils.isEmpty(prodTerminationList)){
			// 每批处理的数据条数
			int partSize = 500;
			// 存放业务数据的临时list，数据量不超过partSize
			List<RaiseTotalRegist001Dto> tmpList = new ArrayList<RaiseTotalRegist001Dto>();
			// 分批次处理
			for (int j = 0; j < prodTerminationList.size(); j++) {
				tmpList.add(prodTerminationList.get(j));
				if (partSize == tmpList.size() || j == prodTerminationList.size() - 1) {
					//找sub表有没有已报送的数据
					List<String> regList = raiseTotalRegist001Mapper.selectRegistSeqnoList(tmpList,"02");
					//更新sub表数据（中债通过、手工处理）
					this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), tmpList, false);
					if(!CollectionUtils.isEmpty(regList)){
						List<List<String>> regseqnolist = createStringList(regList,20);
						for(int i=0;i<regseqnolist.size();i++){
							//将已报送的数据移至his表 insert select
							chbPrdRaisetotSubHisMapper.insertSubHisBatch(regseqnolist.get(i));
							//将已报送的数据从sub表物理删除
							raiseTotalRegist001Mapper.deleteRegistSeqnoList(regseqnolist.get(i));
						}
					}
					tmpList.clear();
				}
			}
		}
	}
	/**
	 * @Description 分批次执行updateInterfSubmitAndSeq
	 * @param sendStatus
	 * @param prodprodTerminationListList
	 * @param isSendAllData
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<RaiseTotalRegist001Dto> prodTerminationList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(prodTerminationList)) {
			return;
		}
		int maxDataCountOneTime = Integer.parseInt(reportInsertLongCount);
		int dataCount = prodTerminationList.size();
		if (dataCount <= maxDataCountOneTime) {
			raiseTotalRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, prodTerminationList);
			return;
		}

		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		if (isSendAllData) {
			logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
			raiseTotalRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
			logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");
			return;
		}

		List<RaiseTotalRegist001Dto> newDataList = new ArrayList<RaiseTotalRegist001Dto>();
		newDataList.addAll(prodTerminationList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
			raiseTotalRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
					newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
			raiseTotalRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
			logger.info("单次更新登记表end===");
		}
	}

	/**
	 * @Description 取消手工处理
	 * @param map
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	@SuppressWarnings("unchecked")
	public String cancelMannul(Map<String, Object> map) {
		dealSearchParam(map);
		List<RaiseTotalRegist001Dto> prodTerminationList = new ArrayList<RaiseTotalRegist001Dto>();
		//选择的手动处理
		prodTerminationList = (List<RaiseTotalRegist001Dto>) map.get("investList");
		if (StringUtil.isEmptyList(prodTerminationList)) {
			//未选中数据
			map.put("sendStatus", "03");
			prodTerminationList = raiseTotalRegist001Mapper.selectAllData(map);
		}
		if(!CollectionUtils.isEmpty(prodTerminationList)){
			//找sub表有没有未报送的数据
			List<String> unregList = raiseTotalRegist001Mapper.selectRegistSeqnoList(prodTerminationList,"01");
			if(!CollectionUtils.isEmpty(unregList)){
				return "fail";
			}
			//查询his表，取最新的已报送数据
			List<String> seqnolist = chbPrdRaisetotSubHisMapper.selectByprodId(prodTerminationList);
			cancelMannultRegist(prodTerminationList);
			if(!CollectionUtils.isEmpty(seqnolist)){
				List<List<String>> regList = createStringList(seqnolist, 20);
				for(int i=0;i<regList.size();i++){
					//将his表数据移至sub表
					raiseTotalRegist001Mapper.insertSeletForSub(regList.get(i));
					//his表数据物理删除
					chbPrdRaisetotSubHisMapper.deleteBySeqno(regList.get(i));
				}
			}
		}
		return "success";
	}

	private void cancelMannultRegist(List<RaiseTotalRegist001Dto> prodTerminationList){
		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
		List<String> registSeqList = new ArrayList<String>();
		for(RaiseTotalRegist001Dto m : prodTerminationList){
			registSeqList.add(m.getRegistSeqno());
		}
		if (!StringUtil.isEmptyList(registSeqList)) {
			// 分批处理
			int maxDataCountOneTime = 300;
			int dataCount = registSeqList.size();
			if (dataCount <= maxDataCountOneTime) {
				raiseTotalRegist001Mapper.updateRaiseTotalRegist(registSeqList);
			}
			else {
				List<String> newDataList = new ArrayList<String>();
				newDataList.addAll(registSeqList);
				// 分批处理
				int part = dataCount / maxDataCountOneTime; // 分批数
				for (int i = 0; i < part; i++ ) {
					raiseTotalRegist001Mapper.updateRaiseTotalRegist(newDataList.subList(0, maxDataCountOneTime));
					newDataList.subList(0, maxDataCountOneTime).clear();
				}
				// 最后剩下的数据
				if (CollectionUtils.isNotEmpty(newDataList)) {
					raiseTotalRegist001Mapper.updateRaiseTotalRegist(newDataList);
				}
			}
		}
	}

	private List<List<String>> createStringList(List<String> targe,int size) {
		List<List<String>> listArr = new ArrayList<List<String>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<String> sub = new ArrayList<String>();
			//把指定索引数据放入到list中
			for(int j = i*size;j <= size*(i+1)-1;j++) {
				if(j <= targe.size()-1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}

	public Map<String,Object> checkData(List<String> checkKeys){
		Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
		Map<String,RaiseTotalRegist001Dto> repmap = new HashMap<String,RaiseTotalRegist001Dto>();
		Map<String,Object> returnMap = new HashMap<>();
		List<RaiseTotalRegist001Dto> issuelist = raiseTotalRegist001Mapper.queryListByKeys(checkKeys);
		dealResultlist(issuelist);
		repmap = issuelist.stream().collect(Collectors.toMap(RaiseTotalRegist001Dto::getRegistSeqno,(p)->p,(key1,key2)->key1));
		List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
		List<CheckReport001Dto> resultlist = new ArrayList<CheckReport001Dto>();
		// 检查登记表中小于今天所有未报送的数据
		if(CollectionUtils.isNotEmpty(checkKeys)){
			// 调用检查方法
			resultlist.addAll(check(null,checkKeys));
			checkmap = resultlist.stream().collect(Collectors.toMap(CheckReport001Dto::getRefseqno,(p)->p,(key1,key2)->key1));
			for(String key : checkKeys){
				if(checkmap.get(key)!=null){
					ExcelValidDto exc=new ExcelValidDto();
					exc.setCheckResult(checkmap.get(key).getCheckResult());
					exc.setErrorInfo(checkmap.get(key).getErrorInfo());
					exc.setWarnInfo(checkmap.get(key).getWarnInfo());
					exc.setValidKey(checkmap.get(key).getValidKey());
					result.add(exc);
				}else{
					ExcelValidDto exc=new ExcelValidDto();
					exc.setValidKey("产品代码:"+repmap.get(key).getProdId());
					exc.setCheckResult("01");
					result.add(exc);
				}
			}
			returnMap.put("excelValidDtoList",result);
		}
		returnMap.put("resultList",issuelist);
		return returnMap;
	}

	/**
	 * @Description 检查登记表中小于今天所有未报送的数据
	 * @return
	 * @return List<CheckReport001Dto>
	 * @version 1.7 2019年6月24日
	 */
	public List<CheckReport001Dto> check(Date batchDate,List<String> keys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 供跑批使用
		if (StringUtil.isNotEmpty(batchDate)){
			checkdate = batchDate;
		}
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_募集总量登记.getValue(),checkdate,keys);
		//增加例外情况过滤
		List<ChbCheckRuleout> listRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_RAISETOT_SUB);
		Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream().collect(
				JoyinCollectors.groupingBy(x -> x.getProdId()));

		//检查数据
		//警告逻辑检查
		// List<CheckReport001Dto> resultList = beforeSubCheck.checkProdRaiseTotalReg(checkdate, mapRuleout);
		//报送前强制的错误检查
		List<CheckReport001Dto> resultList = beforeSubErrorCheck.checkProdRaiseTotalReg(checkdate,keys,mapRuleout);
//		//警告逻辑检查
//		resultList.addAll(beforeSubCheck.checkProdRaiseTotalReg(checkdate,mapRuleout));

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

	public List<String> getCheckRepList(Map<String, Object> param) {
		List<String> keylist = new ArrayList<String>();
		dealSearchParam(param);
		List<RaiseTotalRegist001Dto> list = raiseTotalRegist001Mapper.selectAllData(param);
		if(CollectionUtils.isNotEmpty(list)){
			for(RaiseTotalRegist001Dto dto : list){
				keylist.add(dto.getRegistSeqno());
			}
		}
		return keylist;
	}
}
