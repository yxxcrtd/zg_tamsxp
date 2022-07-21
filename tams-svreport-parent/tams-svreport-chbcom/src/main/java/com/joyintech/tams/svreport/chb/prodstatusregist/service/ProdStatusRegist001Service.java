package com.joyintech.tams.svreport.chb.prodstatusregist.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.svreport.chb.prodstatusregist.dao.ProdStatusRegist001Mapper;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
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
 * @Description 产品状态登记service
 * @author zhoubin
 * @version 3.0 2022年5月28日
 * @since 3.0
 */
@Service(value="svrProdStatusRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class ProdStatusRegist001Service extends CommonExportService<ProdStatusRegist001Dto> {

	@Autowired
	private ProdStatusRegist001Mapper prodStatusRegist001Mapper;
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

    
	/**
	 * @Description 产品状态登记查询
	 * @param pageParam
	 * @return Page<ProdStatusRegist001Dto>
	 * @version 3.0 2022年5月28日
	 */
	public Page<ProdStatusRegist001Dto> selectForPage(Map<String, Object> pageParam) {
		dealSearchParam(pageParam);
		Page<ProdStatusRegist001Dto> page;
		
		if(pageParam.get(GlobalConstant.PARAM_PAGE)!=null){
			int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
	        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
	        page = queryForPage(
					pagenum,legth,()->prodStatusRegist001Mapper.selectAllData(pageParam));
		}else{
			page = queryForPage(ProdStatusRegist001Mapper.class,"selectAllData",pageParam);
		}
		return page;
	}


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
				List<ProdStatusRegist001Dto> investList = JsonUtils.json2List(keyList,
						ProdStatusRegist001Dto.class);
				pageParam.put("investList", investList);
			} catch (IOException e) {
	            logger.error(e.getMessage(),e);
			}
		} else {
			// 查询参数条件
			// 将产品代码当做多选下拉框处理
			String prodId = (String) pageParam.get("prodId");
			if (StringUtil.isNotBlank(prodId)) {
				pageParam.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodId));
			}
			//产品登记编码
			String prdRegistCode = (String) pageParam.get("prdRegistCode");
			if (StringUtil.isNotBlank(prdRegistCode)) {
				pageParam.put("prdRegistCodeList", StringUtil.splitToList(GlobalConstant.COMMA, prdRegistCode));
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
			// 反馈状态
			String status = (String) pageParam.get("status");
			// 将反馈状态当做多选下拉框处理
			if (StringUtil.isNotBlank(status)) {
				pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
			}
		}
	}

	public List<ProdStatusRegist001Dto> selectForExport(Map<String, Object> pageParam) {

		List<ProdStatusRegist001Dto> dtoList = new LinkedList<>();
		this.selectAllDataBatch(pageParam, dtoList);
		return dtoList;
	}
	
	private void selectAllDataBatch(Map<String, Object> pageParam, List<ProdStatusRegist001Dto> dtoList) {

       dtoList.addAll(prodStatusRegist001Mapper.selectAllDataByList(pageParam));

	}
	
	/**
     * 配置项中非空处理
     * @param count
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
		exportParam.config("/com/joyintech/tams/svreport/chb/prodstatusregist/conf/export_conf.xml")
		.select(() -> prodStatusRegist001Mapper.selectAllData(pageParam));
	}


    public List<ProdStatusRegist001Dto> selectAllData(Map<String, Object> pageParam){
        return prodStatusRegist001Mapper.selectAllData(pageParam);
    }


	public List<ProdStatusRegist001Dto> selcteDataByIds(String  batchdate, List<String> prodIdList){
		return prodStatusRegist001Mapper.selcteDataByIds(batchdate,prodIdList);
	}

    @Override
    public Page<ProdStatusRegist001Dto> selectPageData(Map<String, Object> pageParam) {
        List<ProdStatusRegist001Dto> data = restTemplate.postForList(
                    SvrServiceApplication.CHB_SERVICE_NAME + "/ProdTerminationRegist/selectAllData",pageParam,
				ProdStatusRegist001Dto.class);
        Page<ProdStatusRegist001Dto> page = new Page<>();
        page.setRoot(data);
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
		List<ProdStatusRegist001Dto> prodTerminationList = new ArrayList<>();
		//选择的手动处理
		List<ProdStatusRegist001Dto> subList = (List<ProdStatusRegist001Dto>) map.get("investList");
		if(!CollectionUtils.isEmpty(subList)){
			for(ProdStatusRegist001Dto dto : subList){
				prodTerminationList.add(dto);
			}
		}else{
			//未选中数据
        	prodTerminationList = prodStatusRegist001Mapper.selectAllData(map);
		}
        if(!CollectionUtils.isEmpty(prodTerminationList)){
        	// 每批处理的数据条数
        	int partSize = 500;
        	// 存放业务数据的临时list，数据量不超过partSize
        	List<ProdStatusRegist001Dto> tmpList = new LinkedList<>();
        	// 分批次处理
        	for (int j = 0; j < prodTerminationList.size(); j++) {
        		tmpList.add(prodTerminationList.get(j));
        		if (partSize == tmpList.size() || j == prodTerminationList.size() - 1) {
        			//更新sub表数据（中债通过、手工处理）
                	this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), tmpList, false);
        			tmpList.clear();
        		}
        	}
        }
	}
	/**
	 * @Description 分批次执行updateInterfSubmitAndSeq
	 * @param sendStatus
	 * @param prodTerminationList
	 * @param isSendAllData
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	private void updateInterfSubmitAndSeq(String sendStatus, List<ProdStatusRegist001Dto> prodTerminationList, boolean isSendAllData) {
		if (StringUtil.isEmptyList(prodTerminationList)) {
			return;
		}
		int maxDataCountOneTime = Integer.parseInt(reportInsertLongCount);
		int dataCount = prodTerminationList.size();
		if (dataCount <= maxDataCountOneTime) {
			prodStatusRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, prodTerminationList);
			return;
		}
		// 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
		if (isSendAllData) {
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
			prodStatusRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
		    logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");
		    return;
		}
		List<ProdStatusRegist001Dto> newDataList = new LinkedList<>();
		newDataList.addAll(prodTerminationList);
		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			logger.info("单次更新登记表start===");
			prodStatusRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
                newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
			logger.info("单次更新登记表end===");
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("单次更新登记表start===");
			prodStatusRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
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
		List<ProdStatusRegist001Dto> prodTerminationList = new LinkedList<>();
		//选择的手动处理
		prodTerminationList = (List<ProdStatusRegist001Dto>) map.get("investList");
        if (StringUtil.isEmptyList(prodTerminationList)) {
        	//未选中数据
        	map.put("sendStatus", "03");
        	prodTerminationList = prodStatusRegist001Mapper.selectAllData(map);
		}
        if(!CollectionUtils.isEmpty(prodTerminationList)){

        	updateTerminationRegist(prodTerminationList);

        }
        return "success";
	}
	
	private void updateTerminationRegist(List<ProdStatusRegist001Dto> prodTerminationList){
		// 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<Date> registSeqList = new ArrayList<>(1);
		for(ProdStatusRegist001Dto m : prodTerminationList){
			registSeqList.add(m.getPrdStatusDate());
		}
		if (!StringUtil.isEmptyList(registSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 10;
            int dataCount = registSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
				prodStatusRegist001Mapper.updateTerminationRegist(registSeqList);
            }
            else {
                List<Date> newDataList = new ArrayList<>(1);
                newDataList.addAll(registSeqList);
                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
					prodStatusRegist001Mapper.updateTerminationRegist(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
					prodStatusRegist001Mapper.updateTerminationRegist(newDataList);
                }
            }
        }
	}
	
	private List<List<String>> createStringList(List<String> targe,int size) {
		List<List<String>> listArr = new LinkedList<>();
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
	* @Description: 查询产品终止登记待检查数据
	*
	* @param: 查询条件
	* @return：待检查数据
	*
	* @author: 王冠/wangguan
	* @date: 2019年6月25日 上午10:40:07 
	*/
	public List<String> getCheckList(Map<String, Object> param) {
		dealSearchParam(param);
		List<String> keylist = new ArrayList<String>();
		List<ProdStatusRegist001Dto> list = prodStatusRegist001Mapper.selectAllData(param);
		if(CollectionUtils.isNotEmpty(list)){
			for(ProdStatusRegist001Dto dto : list){
				keylist.add(dto.getRegistSeqno());
			}
		}
		return keylist;
	}
	
	/**   
	* @Function: ProdTerminationRegist001Service.java
	* @Description: 产品终止登记数据检查功能实现
	*
	* @param: 待检查数据
	* @return：检查结果
	*
	* @author: 王冠/wangguan
	* @date: 2019年6月25日 上午10:41:53 
	*/
	public Map<String,Object> checkData(List<String> checkKeys){
		Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
		Map<String,ProdStatusRegist001Dto> endmap = new HashMap<>();
		Map<String,Object> returnMap = new HashMap<>();
		List<ProdStatusRegist001Dto> endlist = prodStatusRegist001Mapper.selectAllDataByKeyList(checkKeys);
		endmap = endlist.stream().collect(Collectors.toMap(ProdStatusRegist001Dto::getRegistSeqno,(p)->p,(key1,key2)->key1));
		List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
		List<CheckReport001Dto> resultlist  = new ArrayList<CheckReport001Dto>();
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
					exc.setValidKey("产品登记编码:"+endmap.get(key).getPrdRegistCode());
					exc.setCheckResult("01");
					result.add(exc);
				}
			}
			returnMap.put("excelValidDtoList",result);
		}
		returnMap.put("resultList",endlist);
		return returnMap;
	}
	
	/**   
	* @Function: ProdTerminationRegist001Service.java
	* @Description: 产品终止登记数据检查功能实现
	*
	* @return：检查结果
	*
	* @author: 王冠/wangguan
	* @date: 2019年6月25日 上午11:03:20 
	*/
	public List<CheckReport001Dto> check(Date batchDate,List<String> keys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 供跑批使用
		if (StringUtil.isNotEmpty(batchDate)){
			checkdate = batchDate;
		}
		// 删除今天所有已检查的数据
		//checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品终止登记.getValue(),checkdate);
		//增加例外情况过滤
        List<ChbCheckRuleout> listPrdEndRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_END_SUB);
        Map<String, List<ChbCheckRuleout>> mapPrdEndRuleout = listPrdEndRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));

		//报送前强制的错误检查
        List<CheckReport001Dto> resultList = beforeSubErrorCheck.checkProdEndReg(checkdate,keys,mapPrdEndRuleout);
		//警告逻辑检查
		resultList.addAll(beforeSubCheck.checkProdEndReg(checkdate,mapPrdEndRuleout,keys));

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
}
