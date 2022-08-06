package com.joyintech.tams.svreport.chb.astposregist.service;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.*;
import com.joyintech.system.batch.batchstart.service.SysBatchStart001Service;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.util.SvrMaptoEntityUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import com.joyintech.tams.svreport.chb.astposregist.dao.ChbAstPosRegist001Mapper;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.prodterminationregist.dao.ChbPrdEndSubHisMapper;
import com.joyintech.tams.svreport.chb.prodterminationregist.dao.ProdTerminationRegist001Mapper;
import com.joyintech.tams.svreport.chb.prodterminationregist.dto.ProdTerminationRegist001Dto;
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
 * @Description 资产持仓登记service
 */
@Service(value="svrChbAstPosRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class ChbAstPosRegist001Service extends CommonExportService<ChbAstPosRegist001Dto> {

	@Autowired
	private ChbAstPosRegist001Mapper chbAstPosRegist001Mapper;
	@Autowired
	private ChbBatchParaMapper chbBatchParaMapper;


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
    private ChbPrdEndSubHisMapper chbPrdEndSubHisMapper;
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    @Autowired
    private BeforeSubErrorCheck beforeSubErrorCheck;

	/**
	 * 预警校验
	 */
	@Autowired
    private BeforeSubCheck beforeSubCheck;



	private String tableName = "CHB_AST_POS_SUB";
    
	/**
	 * @Description 资产持仓登记查询
	 * @param pageParam
	 * @return Page<ChbAstPosRegist001Dto>
	 */
	public Page<ChbAstPosRegist001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		Page<ChbAstPosRegist001Dto> page;
		if(pageParam.get(GlobalConstant.PARAM_PAGE)!=null){
			int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
	        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
	        page = queryForPage(
					pagenum,legth,()->chbAstPosRegist001Mapper.selectAllData(pageParam));
		}else{
			page = queryForPage(ChbAstPosRegist001Mapper.class,"selectAllData",pageParam);
		}
		
		dealResultlist(page.getRoot());
		return page;
	}
	
	public void dealResultlist(List<ChbAstPosRegist001Dto> resultlist){
		if(!CollectionUtils.isEmpty(resultlist)){
			for(ChbAstPosRegist001Dto dto : resultlist){
				if(StringUtil.isNotEmpty(dto.getSubmitFeedbackStatus())){
					dto.setSubmitFeedbackStatusStr(SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,dto.getSubmitFeedbackStatus()));
				}
				if(StringUtil.isNotEmpty(dto.getCcy())){
					dto.setCcyStr(SvrDataUtil.getNameFromDataDict(DictCode.币种_中债,dto.getCcy()));
				}
				if(StringUtil.isNotEmpty(dto.getPosType())){
					dto.setPosTypeStr(dto.getPosType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.中债持仓类型,dto.getPosType()));
				}
				if(StringUtil.isNotEmpty(dto.getInterAssetType())){
					dto.setInterAssetTypeStr(dto.getInterAssetType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资产穿透情况,dto.getInterAssetType()));
				}
				if(StringUtil.isNotEmpty(dto.getSendStatus())){
					dto.setSendStatusStr(SvrDataUtil.getNameFromDataDict(DictCode.报送状态,dto.getSendStatus()));
				}
				if(StringUtil.isNotEmpty(dto.getStatus())){
					dto.setStatusStr(SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态,dto.getStatus()));
				}
			}
		}
	}


	/***0722升级**/
	public void dealResultlistSend(List<ChbAstPosRegist001Dto> resultlist){
		if(!CollectionUtils.isEmpty(resultlist)){
			for(ChbAstPosRegist001Dto dto : resultlist){
				if(StringUtil.isNotEmpty(dto.getSubmitFeedbackStatus())){
					dto.setSubmitFeedbackStatus(SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,dto.getSubmitFeedbackStatus()));
				}
				if(StringUtil.isNotEmpty(dto.getCcy())){
					dto.setCcy(SvrDataUtil.getNameFromDataDict(DictCode.币种_中债,dto.getCcy()));
				}
				if(StringUtil.isNotEmpty(dto.getPosType())){
					dto.setPosType(dto.getPosType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.中债持仓类型,dto.getPosType()));
				}
				if(StringUtil.isNotEmpty(dto.getInterAssetType())){
					dto.setInterAssetType(dto.getInterAssetType()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资产穿透情况,dto.getInterAssetType()));
				}
				if(StringUtil.isNotEmpty(dto.getSendStatus())){
					dto.setSendStatus(SvrDataUtil.getNameFromDataDict(DictCode.报送状态,dto.getSendStatus()));
				}
				if(StringUtil.isNotEmpty(dto.getStatus())){
					dto.setStatus(SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态,dto.getStatus()));
				}
			}
		}
	}
	/***0722升级**/
	/**
	 * @Description 查询前数据处理
	 * @param pageParam
	 * @return void
	 */
	private void dealSearchParam(Map<String, Object> pageParam) {
		// 选择行数据时
		if (pageParam.containsKey("keyList")) {
			String keyList = pageParam.get("keyList").toString();
			try {
				List<ChbAstPosRegist001Dto> investList = JsonUtils.json2List(keyList,
						ChbAstPosRegist001Dto.class);
				pageParam.put("investList", investList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 将资产代码当做多选下拉框处理
			String assetCode = (String) pageParam.get("assetCode");
			if (StringUtil.isNotBlank(assetCode)) {
				pageParam.put("assetIdList", StringUtil.splitToList(GlobalConstant.COMMA, assetCode));
			}
			//产品登记编码
			String regCode = (String) pageParam.get("regCode");
			if (StringUtil.isNotBlank(regCode)) {
				pageParam.put("registCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCode));
			}
			// 持仓类别
			String posType = (String) pageParam.get("posType");
			if (StringUtil.isNotBlank(posType)) {
				pageParam.put("posTypeList", StringUtil.splitToList(GlobalConstant.COMMA, posType));
			}
			//
			// 持仓类别
			String interAssetType = (String) pageParam.get("interAssetType");
			if (StringUtil.isNotBlank(interAssetType)) {
				pageParam.put("interAssetTypeList", StringUtil.splitToList(GlobalConstant.COMMA, interAssetType));
			}
			
			// 报送状态
			String sendStatus = (String) pageParam.get("sendStatus");
			// 将报送状态当做多选下拉框处理
			if (StringUtil.isNotBlank(sendStatus)) {
				pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
			}
			// 反馈状态
			String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(submitFeedbackStatus)) {
				pageParam.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
			}
			// 数据状态
			String status = (String) pageParam.get("status");
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
			// 会计科目名称
			String subjectName = (String) pageParam.get("subjectName");
			if (StringUtil.isNotBlank(subjectName)) {
				pageParam.put("subjectNameList", StringUtil.splitToList(GlobalConstant.COMMA, subjectName));
			}
		}
	}

	public List<ChbAstPosRegist001Dto> selectForExport(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		@SuppressWarnings("unchecked")
		List<ChbAstPosRegist001Dto> dataList =  (List<ChbAstPosRegist001Dto>) pageParam.get("investList");
		Map<String,List<ChbAstPosRegist001Dto>>  maptermid=dataList.stream().collect(Collectors.groupingBy(ChbAstPosRegist001Dto::getTermid));

		List<ChbAstPosRegist001Dto> dtoList = new ArrayList<ChbAstPosRegist001Dto>();
		this.selectAllDataBatch(new ArrayList<>(maptermid.keySet()), dtoList);
		dealResultlist(dtoList);
		return dtoList;
	}

	private void selectAllDataBatch(List<String> dataList, List<ChbAstPosRegist001Dto> dtoList) {
		if (StringUtil.isEmptyList(dataList)) {
			dtoList.addAll(chbAstPosRegist001Mapper.selectAllDataByList(dataList));
			return;
		}
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			dtoList.addAll(chbAstPosRegist001Mapper.selectAllDataByList(dataList));
			return;
		}

		List<String> newDataList = new ArrayList<String>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			dtoList.addAll(chbAstPosRegist001Mapper.selectAllDataByList(newDataList.subList(0, maxDataCountOneTime)));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			dtoList.addAll(chbAstPosRegist001Mapper.selectAllDataByList(newDataList));
		}
	}

	/**
     * 配置项中非空处理
     * @param
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
		exportParam.config("/com/joyintech/tams/svreport/chb/astposregist/conf/export_conf.xml")
		.select(() -> chbAstPosRegist001Mapper.selectAllData(pageParam));
	}


    public List<ChbAstPosRegist001Dto> selectAllData(Map<String, Object> pageParam){
		dealSearchParam(pageParam);
		List<ChbAstPosRegist001Dto> dtoList = chbAstPosRegist001Mapper.selectAllData(pageParam);
		dealResultlist(dtoList);
		return dtoList;
    }
    @Override
    public Page<ChbAstPosRegist001Dto> selectPageData(Map<String, Object> pageParam) {
        List<ChbAstPosRegist001Dto> data = restTemplate.postForList(
                    SvrServiceApplication.CHB_SERVICE_NAME + "/ChbAstPosRegist/selectAllData",pageParam,
				ChbAstPosRegist001Dto.class);

        Page<ChbAstPosRegist001Dto> page = new Page<ChbAstPosRegist001Dto>();
        page.setRoot(data);
        //page.setTotal(data.size());
		int pageSize = StringUtil.integerValueOf(pageParam.get(GlobalConstant.PARAM_LENGTH).toString(), StringUtil.integerValueOf(CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_ROW_COUNT),50000));

		if(data!=null && data.size()>0){
			page.setTotal(data.size()/pageSize+1);
		}else{
			page.setTotal(0);
		}

        return  page;
    }
	/**
     * 手动处理
     * @param map
     */
	@SuppressWarnings("unchecked")
	public void updateSumStaus(Map<String, Object> map) {
		dealSearchParam(map);
		List<ChbAstPosRegist001Dto> astPosSubist = new ArrayList<ChbAstPosRegist001Dto>();
		//选择的手动处理
		List<ChbAstPosRegist001Dto> subList = (List<ChbAstPosRegist001Dto>) map.get("investList");
		if(!CollectionUtils.isEmpty(subList)){
			for(ChbAstPosRegist001Dto dto : subList){
				astPosSubist.add(dto);
			}
		}else{
			//未选中数据
			astPosSubist = chbAstPosRegist001Mapper.selectAllData(map);
		}
        if(!CollectionUtils.isEmpty(astPosSubist)){
        	// 每批处理的数据条数
        	int partSize = 500;
        	// 存放业务数据的临时list，数据量不超过partSize
        	List<ChbAstPosRegist001Dto> tmpList = new ArrayList<ChbAstPosRegist001Dto>();
        	// 分批次处理
        	for (int j = 0; j < astPosSubist.size(); j++) {
        		tmpList.add(astPosSubist.get(j));
        		if (partSize == tmpList.size() || j == astPosSubist.size() - 1) {
        			//找sub表有没有已报送的数据
//        			List<String> regList = chbAstPosRegist001Mapper.selectRegistSeqnoList(tmpList,"02");
        			//更新sub表数据（中债通过、手工处理）
                	this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), tmpList, false);
//                	if(!CollectionUtils.isEmpty(regList)){
//        				List<List<String>> regseqnolist = createStringList(regList,20);
//        				for(int i=0;i<regseqnolist.size();i++){
//        					//将已报送的数据移至his表 insert select
//							chbAstPosRegist001Mapper.insertSubHisBatch(regseqnolist.get(i));
//        					//将已报送的数据从sub表物理删除
//							chbAstPosRegist001Mapper.deleteRegistSeqnoList(regseqnolist.get(i));
//        				}
//        			}
        			tmpList.clear();
        		}
        	}
        }
	}
	/**
	 * @Description 分批次执行updateInterfSubmitAndSeq
	 * @param sendStatus
	 * @param astposTerminationList
	 * @param isSendAllData
	 * @return void
	 * @version
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<ChbAstPosRegist001Dto> astposTerminationList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(astposTerminationList)) {
			return;
		}
		int maxDataCountOneTime = Integer.parseInt(reportInsertLongCount);
		int dataCount = astposTerminationList.size();
		if (dataCount <= maxDataCountOneTime) {
			chbAstPosRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, astposTerminationList);
			return;
		}
		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		if (isSendAllData) {
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
			chbAstPosRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");
		    return;
		}
		List<ChbAstPosRegist001Dto> newDataList = new ArrayList<ChbAstPosRegist001Dto>();
		newDataList.addAll(astposTerminationList);
		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
			chbAstPosRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
                newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
			chbAstPosRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
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
		List<ChbAstPosRegist001Dto> astposTerminationList = new ArrayList<ChbAstPosRegist001Dto>();
		//选择的手动处理
		astposTerminationList = (List<ChbAstPosRegist001Dto>) map.get("investList");
        if (StringUtil.isEmptyList(astposTerminationList)) {
        	//未选中数据
        	map.put("sendStatus", "03");
			astposTerminationList = chbAstPosRegist001Mapper.selectAllData(map);
		}
        if(!CollectionUtils.isEmpty(astposTerminationList)){
//        	//找sub表有没有未报送的数据
//			List<String> unregList = chbAstPosRegist001Mapper.selectRegistSeqnoList(astposTerminationList,"01");
//			if(!CollectionUtils.isEmpty(unregList)){
//				return "fail";
//			}
        	//查询his表，取最新的已报送数据
// 			List<String> seqnolist = chbAstPosRegist001Mapper.selectByprodId(astposTerminationList);
        	updateTerminationRegist(astposTerminationList);
//        	if(!CollectionUtils.isEmpty(seqnolist)){
//				List<List<String>> regList = createStringList(seqnolist, 20);
//				for(int i=0;i<regList.size();i++){
//					//将his表数据移至sub表
//					chbAstPosRegist001Mapper.insertSeletForSub(regList.get(i));
//					//his表数据物理删除
//					chbAstPosRegist001Mapper.deleteBySeqno(regList.get(i));
//				}
//			}
        }
        return "success";
	}

	private void updateTerminationRegist(List<ChbAstPosRegist001Dto> astposTerminationList){
		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> registSeqList = new ArrayList<String>();
		for(ChbAstPosRegist001Dto m : astposTerminationList){
			registSeqList.add(m.getTermid());
		}
		if (!StringUtil.isEmptyList(registSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 10;
            int dataCount = registSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
				chbAstPosRegist001Mapper.updateTerminationRegist(registSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(registSeqList);
                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
					chbAstPosRegist001Mapper.updateTerminationRegist(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
					chbAstPosRegist001Mapper.updateTerminationRegist(newDataList);
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

	/**
	* @Function: ProdTerminationRegist001Service.java
	* @Description: 查询资产持仓终止登记待检查数据
	*/
	public Map<String,Object> getCheckEndList(Map<String, Object> param) {
		Map<String,Object> map=new HashMap<>();
		dealSearchParam(param);
		List<String> keylist = new ArrayList<String>();
		List<String> keylistseq = new ArrayList<String>();

		//当勾选数据后，查询当期的月报数据
		if(param.containsKey("investList")){
			List<ChbAstPosRegist001Dto> investList=(List<ChbAstPosRegist001Dto>)param.get("investList");
			String termid=investList.get(0).getTermid();
			param.put("termid",termid);
		}
		List<ChbAstPosRegist001Dto> list= chbAstPosRegist001Mapper.selectAllData(param);
		Set<String> set1=new HashSet<>();
		if(CollectionUtils.isNotEmpty(list)){
			for(ChbAstPosRegist001Dto dto : list){
				//添加期数
				set1.add(dto.getTermid());
				keylistseq.add(dto.getRegistSeqno());
			}
//			dealResultlist(list);
		}
		keylist.addAll(set1);
		map.put("keylist",keylist);
		map.put("keylistseq",keylistseq);
		return map;
	}

	/**
	* @Function: ProdTerminationRegist001Service.java
	* @Description: 资产持仓登记数据检查功能实现
	*/
	public Map<String,Object> checkData(List<String> checkKeys,List<String> termidList){
		Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
		Map<String,ChbAstPosRegist001Dto> endmap = new HashMap<String,ChbAstPosRegist001Dto>();
		Map<String,Object> returnMap = new HashMap<>();
		List<ChbAstPosRegist001Dto> endlist = chbAstPosRegist001Mapper.selectAllDataByKeyList(termidList);
		//获取当前的月末时间
		Date checkDate=endlist.get(0).getValueDate();
		endmap = endlist.stream().collect(Collectors.toMap(ChbAstPosRegist001Dto::getRegistSeqno,(p)->p,(key1,key2)->key1));
		List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
		List<CheckReport001Dto> resultlist  = new ArrayList<CheckReport001Dto>();
		// 检查登记表中小于今天所有未报送的数据
		if(CollectionUtils.isNotEmpty(checkKeys)){
			// 调用检查方法
			resultlist.addAll(check(checkDate,checkKeys));
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
					exc.setValidKey("产品代码:"+endmap.get(key).getProdId()
							+ (StringUtil.isNotEmpty(endmap.get(key).getAssetId()) ? (";行内资产/负债编码:"+endmap.get(key).getAssetId()): "")
							+ (StringUtil.isNotEmpty(endmap.get(key).getInterAssetCode()) ? (";中间层行内资产/负债编码:"+endmap.get(key).getInterAssetCode()): "")
					);
					exc.setCheckResult("01");
					result.add(exc);
				}
			}
			returnMap.put("excelValidDtoList",result);
		}
		dealResultlistSend(endlist);
		returnMap.put("resultList",endlist);
		return returnMap;
	}

	/**
	* @Function:
	* @Description: 资产持仓登记数据检查功能实现
	* @date:
	*/
	public List<CheckReport001Dto> check(Date batchDate,List<String> keys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 供跑批使用
		if (StringUtil.isNotEmpty(batchDate)){
			checkdate = batchDate;
		}
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_资产持仓登记.getValue(),checkdate,null);

		/**TODO  数据检查功能待定*/
		//报送前强制的错误检查
        List<CheckReport001Dto> resultList = new ArrayList<>();
		resultList=beforeSubErrorCheck.checkChbAstPosReg(null,checkdate,keys,null);
		/**TODO  数据检查功能待定*/

        //报错检查异常数据
        int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		for (int index = 0; index < resultList.size();) {
			if (batchLastIndex > resultList.size() - 1) {
				batchLastIndex = resultList.size();
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				break;// 数据插入完毕,退出循环
			} else {
				checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		return resultList;
	}


	public int countExportNum(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		int dataNum = chbAstPosRegist001Mapper.countExportNum(pageParam);
		return dataNum;
	}



	/**
	 * add by zhoubin
	 * 将报送状态置为手工处理
	 * @param param
	 */
	@SuppressWarnings("unchecked")
	public void updateSumStausAll(Map<String, Object> param){

		//先查询出参数表的参数
		List<ChbBatchPara> chbBatchParas=chbBatchParaMapper.selectAllData();
		if(!StringUtil.isEmptyList(chbBatchParas)){
			for(ChbBatchPara single:chbBatchParas){
				if(single.getTaskId().equals(BatchBeanName.资产持仓登记手工处理.getTaskId())){
					Map<String,Object> ssmap=(Map<String,Object>) JSON.parse(single.getParas());
					param=ssmap;
				}
			}
		}
		List<ChbAstPosRegist001Dto> list= chbAstPosRegist001Mapper.selectAllData(param);

		this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), list, false);
		if(!StringUtil.isEmptyList(chbBatchParas)){
			for(ChbBatchPara single:chbBatchParas){
				chbBatchParaMapper.deleteByPrimaryKey(single.getParaId());
			}
		}
	}

}
