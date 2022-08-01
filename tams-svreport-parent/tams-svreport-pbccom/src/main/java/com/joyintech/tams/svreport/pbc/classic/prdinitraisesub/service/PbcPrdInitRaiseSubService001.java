package com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dao.PbcInitProdRaisingSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dto.PbcInitProdRaisingSub;
/**
 * 	产品起始募集信息service
 * @author liuyu
 *
 */
@Service
@Transactional
public class PbcPrdInitRaiseSubService001 extends CommonExportService<PbcInitProdRaisingSub> {
	/**
	 * 产品起始募集信息mapper
	 */
	@Autowired
	private PbcInitProdRaisingSubMapper pbcInitProdRaisingSubMapper;
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
	public Page<PbcInitProdRaisingSub> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcInitProdRaisingSub> page = queryForPage(pagenum,length,()->pbcInitProdRaisingSubMapper.searchPage(searchParam));
		dealResult(page);
		return page;
		
	}
	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcInitProdRaisingSub> page) {
		List<PbcInitProdRaisingSub> resultList = page.getRoot();
		for(PbcInitProdRaisingSub result : resultList){
//			result.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, result.getStatus()));
//			result.setSendStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态, result.getSendStatus()));
//			result.setSubmitFeedbackStatus(SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态,result.getSubmitFeedbackStatus()));
            if (StringUtil.isNotEmpty(result.getBuyPlace())){
				result.setBuyPlace( result.getBuyPlace()+" "+SysDataUtil.getNameFromDataDict(DictCode.行政区划_省,result.getBuyPlace()));
            }
            if (StringUtil.isNotEmpty(result.getCustomerType1())){
                result.setCustomerType1( SysDataUtil.getNameFromDataDict(DictCode.人行客户类型,result.getCustomerType1()));
            }
//			result.setCcy(SysDataUtil.getNameFromDataDict(DictCode.币种, result.getCcy()));
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
	            "/com/joyintech/tams/svreport/pbc/classic/prdinitraisesub/conf/export_conf.xml")
	            .dataSource(DataSourceType.MASERT);
	}
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcInitProdRaisingSub> selectPageData(Map<String, Object> selectParam) {
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
		List<PbcInitProdRaisingSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			try {
				 infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcInitProdRaisingSub.class);
			} catch (IOException e) {
				logger.debug("数据转换异常",e.getMessage(),e);
				logger.error(e.getMessage(),e);
				return ;
			}
			
		}else{
			// 查询方式
			if("dat".equals(flag)){
				infoList = pbcInitProdRaisingSubMapper.searchPage(map);
			}else{
				Map<String,Object> pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
				infoList = pbcInitProdRaisingSubMapper.searchPage(pageParam);
			}
			
		}
		// His插入
		Iterator<PbcInitProdRaisingSub> it = infoList.iterator();
		Map<String,String> subMap = new HashMap<String,String>();
		while(it.hasNext()){
			PbcInitProdRaisingSub info = it.next();
			if(subMap.get(info.getPbcProdId())!=null){
				it.remove();
				continue;
			}else{
				subMap.put(info.getPbcProdId(), "1");
			}
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
			List<PbcInitProdRaisingSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 历史表插入
				pbcInitProdRaisingSubMapper.insertHisBatchBySendStatus(list1);
				// 原始表删除
				pbcInitProdRaisingSubMapper.deleteBatchBySendStatus(list1);		
				// 原始表更新状态
				pbcInitProdRaisingSubMapper.doManul(list1,map.get("submitadd")==null?null:map.get("submitadd").toString());
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcInitProdRaisingSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 历史表插入
			pbcInitProdRaisingSubMapper.insertHisBatchBySendStatus(list2);
			// 原始表删除
			pbcInitProdRaisingSubMapper.deleteBatchBySendStatus(list2);		
			// 原始表更新状态
			pbcInitProdRaisingSubMapper.doManul(list2,map.get("submitadd")==null?null:map.get("submitadd").toString());
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
		List<PbcInitProdRaisingSub> infoList = new ArrayList<PbcInitProdRaisingSub>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			try {
				infoList  = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcInitProdRaisingSub.class);
			} catch (IOException e) {
				logger.debug("数据转换异常",e.getMessage(),e);
				logger.error(e.getMessage(),e);
				return "fail";
			}
		}else{
			// 查询方式
//			infoList = searchPage(map).getRoot();
			deaSearchParam(map);
			infoList = pbcInitProdRaisingSubMapper.searchPage(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcInitProdRaisingSubMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		Iterator<PbcInitProdRaisingSub> it = infoList.iterator();
		Map<String,String> subMap = new HashMap<String,String>();
		while(it.hasNext()){
			PbcInitProdRaisingSub info = it.next();
			if(subMap.get(info.getPbcProdId())!=null){
				it.remove();
			}else{
				subMap.put(info.getPbcProdId(), "1");
			}
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcInitProdRaisingSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 更新状态
				pbcInitProdRaisingSubMapper.cancelManul(list1);
				// His表数据插入sub表
				pbcInitProdRaisingSubMapper.insetBatchSubFromHisByUpdateTime(list1);
				// 删除his表中移走的数据
				pbcInitProdRaisingSubMapper.deleteBatchSubFromHisByUpdateTime(list1);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcInitProdRaisingSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 更新状态
			pbcInitProdRaisingSubMapper.cancelManul(list2);
			// His表数据插入sub表
			pbcInitProdRaisingSubMapper.insetBatchSubFromHisByUpdateTime(list2);
			// 删除his表中移走的数据
			pbcInitProdRaisingSubMapper.deleteBatchSubFromHisByUpdateTime(list2);
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 end */
		logger.debug("取消手工处理结束:------------------------------"+new Date());
		return "success";
	}
	
	
	
	
}
