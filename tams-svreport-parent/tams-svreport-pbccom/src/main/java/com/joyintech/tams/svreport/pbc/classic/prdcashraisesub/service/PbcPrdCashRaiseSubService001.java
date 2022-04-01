package com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.joyintech.framework.file.SafelyFile;
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
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.pbc.assetdebt.dto.PbcReportAssetDebtDetailDto;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dao.PbcProdRaiseSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dto.PbcProdRaiseSub;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dto.PbcInitProdRaisingSubVo;
import com.joyintech.tams.svreport.pbc.datacheck.beforesub.service.PbcBeforeSubCheck;
import com.joyintech.tools.collectors.JoyinCollectors;
/**
 * 产品资金募集信息service
 * @author liuyu
 *
 */
@Service
@Transactional
public class PbcPrdCashRaiseSubService001 extends CommonExportService<PbcProdRaiseSub> {
	/**
	 * 产品资金募集信息mapper
	 */
	@Autowired
	private PbcProdRaiseSubMapper pbcProdRaiseSubMapper;
	/**
	 * 日志.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CheckReport001Mapper checkReport001Mapper;
	
	@Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
	
	@Autowired
    private PbcBeforeSubCheck pbcBeforeSubCheck;

	private DecimalFormat df = new DecimalFormat("0.00000");
    
    private DecimalFormat dt = new DecimalFormat("0.00");
	/**
	 * 页面查询
	 * 
	 * @param searchParam
	 * @return
	 */
	public Page<PbcProdRaiseSub> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcProdRaiseSub> page = queryForPage(pagenum,length,()->pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam));
		dealResult(page);
		return page;
	}
	
	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcProdRaiseSub> page) {
		List<PbcProdRaiseSub> resultList = page.getRoot();
		for(PbcProdRaiseSub result : resultList){
//			result.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, result.getStatus()));
//			result.setSendStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态, result.getSendStatus()));
//			result.setSubmitFeedbackStatus(SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态,result.getSubmitFeedbackStatus()));
            if (StringUtil.isNotEmpty(result.getBuyPlace())){
                result.setBuyPlace( SysDataUtil.getNameFromDataDict(DictCode.行政区划_省,result.getBuyPlace()));
            }
            if (StringUtil.isNotEmpty(result.getCustomerType1())){
                result.setCustomerType1( SysDataUtil.getNameFromDataDict(DictCode.人行客户类型,result.getCustomerType1()));
            }
			//			result.setCcy(SysDataUtil.getNameFromDataDict(DictCode.币种, result.getCcy()));
			result.setRaisingDateStr(DateUtil.format(result.getRaisingDate()));
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
        if (StringUtil.isNotEmpty(searchParam.get("vdateStart"))){
            searchParam.put("vdateStart",searchParam.get("vdateStart").toString().substring(0, 7));
        }
        if (StringUtil.isNotEmpty(searchParam.get("vdateEnd"))){
            searchParam.put("vdateEnd",searchParam.get("vdateEnd").toString().substring(0, 7));
        }
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
	            "/com/joyintech/tams/svreport/pbc/classic/prdcashraisesub/conf/export_conf.xml")
	            .dataSource(DataSourceType.MASERT);
	}
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcProdRaiseSub> selectPageData(Map<String, Object> selectParam) {
			return searchPage(selectParam);
	}
	

	/**
	 * 手工处理
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void doManul(Map<String,Object> map) {
		logger.debug("手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcProdRaiseSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			 try {
					infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcProdRaiseSub.class);
				} catch (IOException e) {
				 	logger.error(e.getMessage(),e);
					logger.debug("数据转换异常",e.getMessage(),e);
					return ;
				}
		}else{
			Map<String,Object> pageParam;
			if("dat".equals(flag)){
				infoList = pbcProdRaiseSubMapper.searchPageForOnePrd(map);
			}else{
				pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
				infoList = pbcProdRaiseSubMapper.searchPageForOnePrd(pageParam);
			}
			
		}
		// His插入
		Iterator<PbcProdRaiseSub> it = infoList.iterator();
		while(it.hasNext()){
			PbcProdRaiseSub info = it.next();
			if(SysDataUtil.getNameFromDataDict(DictCode.报送状态, "03").equals(info.getSendStatus())){
				it.remove();
			}
		}
		if(infoList.isEmpty())
			return ;
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcProdRaiseSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 历史表插入
				pbcProdRaiseSubMapper.insertHisBatchBySendStatus(list1);
				// 原始表删除
				pbcProdRaiseSubMapper.deleteBatchBySendStatus(list1);		
				// 原始表更新状态
				pbcProdRaiseSubMapper.doManul(list1,map.get("submitadd")==null?null:map.get("submitadd").toString());
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcProdRaiseSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 历史表插入
			pbcProdRaiseSubMapper.insertHisBatchBySendStatus(list2);
			// 原始表删除
			pbcProdRaiseSubMapper.deleteBatchBySendStatus(list2);		
			// 原始表更新状态
			pbcProdRaiseSubMapper.doManul(list2,map.get("submitadd")==null?null:map.get("submitadd").toString());
		}
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 end */
		logger.debug("手工处理结束:------------------------------"+new Date());
		
	}
	/**
	 * 取消手工处理
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public String doCancelManul(Map<String,Object> map) {
		logger.debug("取消手工处理开始:------------------------------"+new Date());
		String flag = map.get("flag").toString();
		List<PbcProdRaiseSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			 try {
					infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcProdRaiseSub.class);
				} catch (IOException e) {
				 	logger.error(e.getMessage(),e);
					logger.debug("数据转换异常",e.getMessage(),e);
					return "fail";
				}
		}else{
			// 查询方式
//			Map<String,Object> pageParam = RequestUtil.getPageParam();
//			infoList = searchPage(pageParam).getRoot();
			deaSearchParam(map);
			infoList = pbcProdRaiseSubMapper.searchPageForOnePrd(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcProdRaiseSubMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcProdRaiseSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 更新状态
				pbcProdRaiseSubMapper.cancelManul(list1);
				// His表数据插入sub表
				pbcProdRaiseSubMapper.insetBatchSubFromHisByUpdateTime(list1);
				// 删除his表中移走的数据
				pbcProdRaiseSubMapper.deleteBatchSubFromHisByUpdateTime(list1);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcProdRaiseSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 更新状态
			pbcProdRaiseSubMapper.cancelManul(list2);
			// His表数据插入sub表
			pbcProdRaiseSubMapper.insetBatchSubFromHisByUpdateTime(list2);
			// 删除his表中移走的数据
			pbcProdRaiseSubMapper.deleteBatchSubFromHisByUpdateTime(list2);
		}
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 end */
		logger.debug("取消手工处理结束:------------------------------"+new Date());
		return "success";
	}

	/**
	 * 获取登记序列列表
	 * @param param 页面参数
	 * @return 登记序列列表
	 */
	public List<String> getCheckRepList(Map<String, Object> param) {
		List<String> keylist = new ArrayList<String>();
		// 设置查询参数
		deaSearchParam(param);
		List<PbcProdRaiseSub> list = pbcProdRaiseSubMapper.searchPageForOnePrd(param);
		if(CollectionUtils.isNotEmpty(list)){
			for(PbcProdRaiseSub dto : list){
				keylist.add(dto.getRegistSeqno());
			}
		}
		return keylist;
	}
	/**
	    * 数据检查
	    * @param checkKeys 查询条件
	    * @return 检查结果
	    * @see
	    * @since 1.0
	    */
	public List<ExcelValidDto> checkData(List<String> checkKeys){
		Map<String,CheckReport001Dto> checkmap = new HashMap<String,CheckReport001Dto>();
		Map<String,PbcProdRaiseSub> repmap = new HashMap<String,PbcProdRaiseSub>();
		List<PbcProdRaiseSub> issuelist = pbcProdRaiseSubMapper.queryListByKeys(checkKeys);
		repmap = issuelist.stream().collect(Collectors.toMap(PbcProdRaiseSub::getRegistSeqno,(p)->p,(key1,key2)->key1));
		List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
		List<CheckReport001Dto> resultlist = new ArrayList<CheckReport001Dto>();
		// 检查登记表中小于今天所有未报送的数据
		if(CollectionUtils.isNotEmpty(checkKeys)){
			// 调用检查方法
			resultlist.addAll(check(checkKeys));
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
		}
		return result;
	}
	
	private List<CheckReport001Dto> check(List<String> checkKeys){
		// 数据日期为系统时间
		Date checkdate = DateUtil.parse(DateUtil.getSysDate());
		// 删除今天所有已检查的数据
		checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_产品资金募集信息.getValue(),checkdate,checkKeys);
		//增加例外情况过滤
        List<ChbCheckRuleout> listRuleout=chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.PBC_PROD_RAISING_SUB);
        Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream().collect(
                 JoyinCollectors.groupingBy(x -> x.getProdId()));
        //检查数据
        List<CheckReport001Dto> resultList = pbcBeforeSubCheck.checkPbcPrdRaisingSub(checkdate, mapRuleout);
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
	
	public List<PbcProdRaiseSub> searchByCondition(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		List<PbcProdRaiseSub> reportList = pbcProdRaiseSubMapper.searchPageForOnePrd(searchParam);
		return reportList;
		
	}
	
	
	 //处理导出人行excel的数据，金额保留两位小数，份额保留5位小数
    public void dealCashRaiseDateListForExport(PbcInitProdRaisingSubVo detail) {
        // 人民币金额
        if (detail.getRmbje() != null && BigDecimal.ZERO.compareTo(detail.getRmbje()) != 0) {
            detail.setRmbjeToString(dt.format(detail.getRmbje()));
        }
        //转人民币金额
        if (detail.getZrmb() != null && BigDecimal.ZERO.compareTo(detail.getZrmb()) != 0) {
        	detail.setZrmbToString(dt.format(detail.getZrmb()));
        }
        //金额
        if (detail.getJe() != null && BigDecimal.ZERO.compareTo(detail.getJe()) != 0) {
        	detail.setJeToString(dt.format(detail.getJe()));
        }
        // 人民币份额
        if (detail.getRmbfe() != null && BigDecimal.ZERO.compareTo(detail.getRmbfe()) != 0) {
            detail.setRmbfeToString(df.format(detail.getRmbfe()));
        }
        //份额
        if (detail.getFe() != null && BigDecimal.ZERO.compareTo(detail.getFe()) != 0) {
            detail.setFeToString(df.format(detail.getFe()));
        }
    }
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<byte[]> download(String pageId, Map<String, Object> selectParam,String datas) throws IOException {

		deaSearchParam(selectParam);
    	// 导出文件 	
        List<File> sourceFiles = new ArrayList<>();

		List<File> sourceFiles0 = new ArrayList<>();

		@SuppressWarnings("unchecked")
        //List<PbcPrdBaseinfoSub> dataList =  (List<PbcPrdBaseinfoSub>) selectParam.get("reportList");
    	List<PbcProdRaiseSub> dataList=new ArrayList<PbcProdRaiseSub>();
    	if (datas != null){
        	dataList=JsonUtils.json2List(datas,PbcProdRaiseSub.class);
    	}else{
    		//不选报表导出所有未报送的记录
    		
    		dataList= searchByCondition(selectParam);
    	}
    	
    	Map<String, PbcProdRaiseSub> dataParam = new HashMap<String, PbcProdRaiseSub>();
    	// 单选导出
    	for (int i =0 ;i < dataList.size();i++){
    		String pbcProdIdShow=dataList.get(i).getPbcProdIdShow();
    		String raisingDate=DateUtil.format(dataList.get(i).getRaisingDate(),"yyyyMMdd");
    		String code= pbcProdIdShow + '|' + raisingDate;
    		//获取dataParam这个集合中是否有这个code的值
    		if(null!=dataParam.get(code)){
    			
				String sendStatus=dataList.get(i).getSendStatus();
				String status=dataList.get(i).getStatus();
				if(sendStatus.equals(DictCodeListExt2.报送状态_未报送.getValue()) && status.equals(DictCodeListExt2.报送数据状态_修改.getValue())){
					//dataParam.put(dataList.get(i).getPbcProdId(), dataList.get(i));
					dataParam.put(code, dataList.get(i));
				}
			}else{
					dataParam.put(code, dataList.get(i));
			}
    		
    	}
    	// excel计数器
		int count = 1;
		Format f2 = new DecimalFormat("00");
    	String template="";
    	Map<String, Integer> nameMap = new HashMap<String, Integer>();
    	//生成人行Excel
    	for(Entry<String, PbcProdRaiseSub> dto : dataParam.entrySet()){
			
    		Map<String,Object> data  = new HashMap<String,Object>();
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	param.put("registSeqno", dto.getValue().getRegistSeqno());
	    	//param.put("pbcProdId", dto.getValue().getPbcProdId());
	    	param.put("pbcProdId", dto.getValue().getPbcProdIdShow());
	    	param.put("raisingDate", dto.getValue().getRaisingDate());
	    	param.put("sendStatus", dto.getValue().getSendStatus());	/*报送状态*/
	    	param.put("status", dto.getValue().getStatus());			/*数据状态*/
	    	param.put("statusDate", dto.getValue().getStatusDate());
	    	param.put("operateModeK",DictCodeListExt3.人行产品运作模式_开放式净值型.getValue());

	    	String pbcProdId=dto.getValue().getPbcProdId();
	    	String pbcProdIdShow=dto.getValue().getPbcProdIdShow();
	    	//模板名
	    	String name=dto.getValue().getPbcCode();
	    	if(StringUtils.isEmpty(name)){
				name = pbcProdId;
	    	}
	    	String operateMode = "";
	    	List<String> operateModeList = pbcProdRaiseSubMapper.selectOperateMode(pbcProdIdShow);
	    	if(null!=operateModeList&&operateModeList.size()>0){
	    		operateMode = operateModeList.get(0);
	    	}
	    	//根据产品币种，判断使用人民币/外币模板
	    	if(StringUtil.isNotEmpty(dto.getValue().getCcy())&&dto.getValue().getCcy().equals("CNY")){
	    		// 根据运作模式选择不同模板
	    		if("2".equals(operateMode)){
	    			template = "PbcReport_PbcRaisProd_CNY_Mode2";
				}else if ("3".equals(operateMode)){
	    			template = "PbcReport_PbcRaisProd_CNY_Mode3";
				}else if("4".equals(operateMode)) {
					template = "PbcReport_PbcRaisProd_CNY_Mode4";
				}else {
					template = "PbcReport_PbcRaisProd_CNY_Mode5";
				}
//	    		template="PbcReport_PbcRaisProd_CNY";
	    	}else{
	    		// 根据运作模式选择不同模板
				if("2".equals(operateMode)){
					template = "PbcReport_PbcRaisProd_Oth_Mode2";
				}else if ("3".equals(operateMode)){
					template = "PbcReport_PbcRaisProd_Oth_Mode3";
				}else if("4".equals(operateMode)) {
					template = "PbcReport_PbcRaisProd_Oth_Mode4";
				}else {
					template = "PbcReport_PbcRaisProd_Oth_Mode5";
				}
//	    		template="PbcReport_PbcRaisProd_Oth";
	    	}
            // add by zcw  TISHKS-244 新人行代码关联不上老人行表。改为老人行代码
            //param.put("pbcProdId", dto.getValue().getPbcProdId());
	    	List<PbcInitProdRaisingSubVo> listDetailSheet1 = pbcProdRaiseSubMapper.searchListDetailSheet1(param);
	    	if (!CollectionUtils.isEmpty(listDetailSheet1)) {
	            for (PbcInitProdRaisingSubVo detailVo1 : listDetailSheet1) {
	                // 处理数据
	                dealCashRaiseDateListForExport(detailVo1);
	                /*dealProdData(detail, isShowZero);*/
	            	String code="";
	            	if(detailVo1.getCode() != null){
	            		code=detailVo1.getCode();
	            	}else{
	            		code="buyplace";
	            	}
	                data.put(code, detailVo1);
	            }
	        }
	    	
	    	List<PbcInitProdRaisingSubVo> listDetailSheet2 = pbcProdRaiseSubMapper.searchListDetailSheet2(param);
	    	if (!CollectionUtils.isEmpty(listDetailSheet2)) {
	            for (PbcInitProdRaisingSubVo detailVo2 : listDetailSheet2) {
	                // 处理数据
	                dealCashRaiseDateListForExport(detailVo2);
	                /*dealProdData(detail, isShowZero);*/
	            	String code="";
	            	if(detailVo2.getCode() != null){
	            		code=detailVo2.getCode();
	            	}else{
	            		code="buyplace";
	            	}
	                data.put(code, detailVo2);
	            }
	        }
	    	
	    	List<PbcInitProdRaisingSubVo> listDetailSheet3 = pbcProdRaiseSubMapper.searchListDetailSheet3(param);
	    	if (!CollectionUtils.isEmpty(listDetailSheet3)) {
	            for (PbcInitProdRaisingSubVo detailVo3 : listDetailSheet3) {
	                // 处理数据
	                dealCashRaiseDateListForExport(detailVo3);
	                /*dealProdData(detail, isShowZero);*/
	            	String code="";
	            	if(detailVo3.getCode() != null){
	            		code=detailVo3.getCode();
	            	}else{
	            		code="buyplace";
	            	}
	                data.put(code, detailVo3);
	            }
	        }
			List<PbcProdRaiseSub> listDetailSheet4 = null;
	    	if("2".equals(operateMode)||"4".equals(operateMode)){
				listDetailSheet4 = pbcProdRaiseSubMapper.searchListDetailSheet4(param);
			}else if("3".equals(operateMode)){
	    		listDetailSheet4 = pbcProdRaiseSubMapper.searchListDetailSheet4Yield(param);
			}

	    	if (!CollectionUtils.isEmpty(listDetailSheet4)) {
	            for (PbcProdRaiseSub detailVo4 : listDetailSheet4) {
	                // 处理数据
	            	/*dealCashRaiseDateList4ForExport(detailVo4);
	                dealProdData(detail, isShowZero);*/
	            	String code="";
	            	if(detailVo4.getCode() != null){
	            		code=detailVo4.getCode();
	            	}else{
	            		code="code";
	            	}
	                data.put(code, detailVo4);
	            }
	        }
	    	if (data.size() == 0) {
	    		continue;
	    	}
	    	//String fileName = "PCD_"+pbcProdId+"_"+DateUtil.format(new Date(), "yyyyMMdd")+"-"+f2.format(count)+".xls";
	    	if (nameMap.get(name) != null) {
	    		nameMap.put(name, nameMap.get(name) + 1);
	    		name = name  + "(" + nameMap.get(name) + ")";
	    	} else {
	    		nameMap.put(name, 1);
	    	}
			Calendar cale = Calendar.getInstance();
	    	cale.setTime(dto.getValue().getRaisingDate());
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			String  lastday = DateUtil.format(cale.getTime(),"yyyyMMdd");
			String fileName = "资金募集数据-"+name+"-"+lastday+".xls";
			String fileName1 = name+"-"+lastday+".xls";

			File depositfile = TisUtil.createFile(data, "pbc/"+template, fileName);
			File depositfile1 = TisUtil.createFile(data, "pbc/"+template, fileName1);
			sourceFiles0.add(depositfile);
			sourceFiles.add(depositfile1);
			count++;
		}
		
		if (sourceFiles.isEmpty()){
			//String fileName = "PCD_"+DateUtil.format(new Date(), "yyyyMMdd")+"-"+f2.format(count)+".xls";
			String fileName = "资金募集数据-"+DateUtil.format(new Date(), "yyyy-MM-dd")+".xls";
    		Map<String,Object> data  = new HashMap<String,Object>();
			File depositfile = TisUtil.createFile(data, "pbc/PbcReport_PbcRaisProd_CNY", fileName);
			sourceFiles.add(depositfile);
		}
		 /*add by zhoubin 20220325 start*/
		if (sourceFiles0.isEmpty()){
			String fileName = "资金募集数据-"+DateUtil.format(new Date(), "yyyy-MM-dd")+".xls";
			Map<String,Object> data  = new HashMap<String,Object>();
			File depositfile = TisUtil.createFile(data, "pbc/PbcReport_PbcRaisProd_CNY", fileName);
			sourceFiles0.add(depositfile);
		}
		/*add by zhoubin 20220325 end*/
        // 如果生成excel文件个数大于1，生成压缩文件
        File outFile = null;
        if (sourceFiles.size() > 1) {
            String exportFilePath = SvrDataUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//            String zipFileName = FileNameUtil.createFileName("zip");
//            String zipFileName = "PbocPrdRaise("+DateUtil.format(new Date(), "yyyy-MM-dd")+").zip";
			String zipFileName = "资金募集数据"+".zip";
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
			outFile = sourceFiles0.get(0);
        }
		return ExportUtil.export(outFile.getName(), outFile, true);
    }

	
	
	
}
