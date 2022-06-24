package com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.LongAdder;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto.PbcPrdAccountSub;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.handler.JoyinBookConfig;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.service.PbcPrdAccountSubService001;

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
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.dao.PbcPrdAccountEndSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdaccountendsub.dto.PbcPrdAccountEndSub;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.dto.PbcPrdBaseinfoEndSub;
/**
 * 人行报表查询-资产池终止信息controller
 * @author liuyu
 *
 */
@Service
@Transactional
public class PbcPrdAccountEndSubService001 extends CommonExportService<PbcPrdAccountEndSub> {
	/**
	 * 资产池终止信息mapper
	 */
	@Autowired
	private PbcPrdAccountEndSubMapper pbcPrdAccountEndSubMapper;
	
	/**
	 * 资产池基本信息service
	 */
	@Autowired
	public PbcPrdAccountSubService001 pbcPrdAccountSubService001;
	
	/**
	 * 日志.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 页面查询
	 * 
	 * @param searchParam
	 * @return
	 */
	public Page<PbcPrdAccountEndSub> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcPrdAccountEndSub> page = queryForPage(pagenum,length,()->pbcPrdAccountEndSubMapper.searchPage(searchParam));
		dealResult(page);
		return page;
	}

	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcPrdAccountEndSub> page) {
		List<PbcPrdAccountEndSub> resultList = page.getRoot();
		for(PbcPrdAccountEndSub result : resultList){
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
		if (StringUtil.isNotEmpty(searchParam.get("accountId"))){
			searchParam.put("accountIdList",
					StringUtil.splitToList(",",(String) searchParam.get("accountId")));
		} else {
			searchParam.put("accountIdList",null);
		}
	}
	
	/**
	 * 设置导出文件参数
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config(
	            "/com/joyintech/tams/svreport/pbc/classic/prdaccountendsub/conf/export_conf.xml")
	            .dataSource(DataSourceType.MASERT);
	}
	
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcPrdAccountEndSub> selectPageData(Map<String, Object> selectParam) {
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
		List<PbcPrdAccountEndSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			try {
				infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcPrdAccountEndSub.class);
			} catch (IOException e) {
				logger.debug("数据转换异常",e.getMessage(),e);
				return ;
			}
		}else{
			Map<String,Object> pageParam;
			if("dat".equals(flag)){
				infoList = pbcPrdAccountEndSubMapper.searchPage(map);
			}else{
				pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
				infoList = pbcPrdAccountEndSubMapper.searchPage(pageParam);
			}
			
		}
		// His插入
		Iterator<PbcPrdAccountEndSub> it = infoList.iterator();
		while (it.hasNext()) {
			PbcPrdAccountEndSub info = it.next();
			if (SysDataUtil.getNameFromDataDict(DictCode.报送状态, "03").equals(info.getSendStatusStr())) {
				it.remove();
				continue;
			}
			// 只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和人行退回的数据进行手工处理
			if (!((SysDataUtil.getNameFromDataDict(DictCode.报送状态, "01").equals(info.getSendStatusStr()))
					|| ((SysDataUtil.getNameFromDataDict(DictCode.报送状态, "02").equals(info.getSendStatusStr()))
							&& ((SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, "02")
									.equals(info.getSubmitFeedbackStatusStr()))
									|| (SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, "04")
											.equals(info.getSubmitFeedbackStatusStr())))))) {
				it.remove();
			}
		}
		if(infoList.isEmpty())
			return ;
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdAccountEndSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				pbcPrdAccountEndSubMapper.insertHisBatchBySendStatus(list1);
				// 原始表删除
				pbcPrdAccountEndSubMapper.deleteBatchBySendStatus(list1);		
				// 原始表更新状态
				pbcPrdAccountEndSubMapper.doManul(list1,map.get("submitadd")==null?null:map.get("submitadd").toString());
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdAccountEndSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			pbcPrdAccountEndSubMapper.insertHisBatchBySendStatus(list2);
			// 原始表删除
			pbcPrdAccountEndSubMapper.deleteBatchBySendStatus(list2);		
			// 原始表更新状态
			pbcPrdAccountEndSubMapper.doManul(list2,map.get("submitadd")==null?null:map.get("submitadd").toString());
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
		List<PbcPrdAccountEndSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			infoList  = (List<PbcPrdAccountEndSub>) map.get("infoList");
		}else{
			// 查询方式
//			Map<String,Object> pageParam = RequestUtil.getPageParam();
//			infoList = searchPage(pageParam).getRoot();
			deaSearchParam(map);
			infoList = pbcPrdAccountEndSubMapper.searchPage(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcPrdAccountEndSubMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdAccountEndSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 更新状态
				pbcPrdAccountEndSubMapper.cancelManul(list1);
				// His表数据插入sub表
				pbcPrdAccountEndSubMapper.insetBatchSubFromHisByUpdateTime(list1);
				// 删除his表中移走的数据
				pbcPrdAccountEndSubMapper.deleteBatchSubFromHisByUpdateTime(list1);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdAccountEndSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 更新状态
			pbcPrdAccountEndSubMapper.cancelManul(list2);
			// His表数据插入sub表
			pbcPrdAccountEndSubMapper.insetBatchSubFromHisByUpdateTime(list2);
			// 删除his表中移走的数据
			pbcPrdAccountEndSubMapper.deleteBatchSubFromHisByUpdateTime(list2);
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 end */
		logger.debug("取消手工处理结束:------------------------------"+new Date());
		return "success";
	}
	
	/**
	 * 资产池终止信息人行导出Excel
	 */
    public ResponseEntity<byte[]> export2(String pageId, Map<String, Object> selectParam) {
//    	// 判断当前画面的导出进度是否正在处理
//        if(progressBarService.isProcessing(pageId, PROCESS_KEY)) {
//            throw new BusinessException("当前画面正在执行导出操作，请耐心等待！");
//        }
//        // 设置当前画面进度为0
//        progressBarService.updatePercent(pageId, PROCESS_KEY, 0D, false);
    	// 首次进来不勾选报送状态默认为只导出未报送的
    	if(selectParam.get("sendStatus")==null && selectParam.get("keyList") == null) {
    		selectParam.put("sendStatus", "01");
    	}
    	//查出数据
    	deaSearchParam(selectParam);
        List<PbcPrdAccountEndSub> dataList = pbcPrdAccountEndSubMapper.searchPage(selectParam);
        //先默认一个名词，避免出现名字为NULL
        String exportFileName = "资产池终止信息";
        // 计数器，记载有多少资产池
		int count = 0;
		// 去重：若勾选了记录，按照勾选的进行导出,注意若同一个资产池若有2条记录，则导出修改未报送那条
        List<PbcPrdAccountEndSub> list1= new ArrayList<PbcPrdAccountEndSub>();
    	if(CollectionUtils.isNotEmpty(dataList)) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (PbcPrdAccountEndSub pbcPrdAccountEndSub : dataList) {
                if (pbcPrdAccountEndSub == null) {
                    continue;
                }
                String pbcAccountId  = pbcPrdAccountEndSub.getPbcAccountId();
                if(StringUtils.isBlank(pbcAccountId)) {
                	continue;
                }
                if (pbcAccountId != null) {
                	String value = hashMap.get(pbcAccountId);
                    if (StringUtils.isBlank(value)) { 
                    	//如果value是空的  说明是第一次取到
                        hashMap.put(pbcAccountId, pbcAccountId);
                        list1.add(pbcPrdAccountEndSub);
                        count ++;
                    } else {
                    		if(StringUtils.equalsIgnoreCase(pbcPrdAccountEndSub.getSendStatus(), DictCodeListExt2.报送状态_未报送.getValue())) {
								Iterator<PbcPrdAccountEndSub> iterator = list1.iterator();
								while (iterator.hasNext()) {
									PbcPrdAccountEndSub prdAccountEndSub = iterator.next();
									if (pbcAccountId.equals(prdAccountEndSub.getPbcAccountId())) {
										iterator.remove();//使用迭代器的删除方法删除
									}
								}
								list1.add(pbcPrdAccountEndSub);
								continue;
                    	}
                    }
                }
            }
         }
        // 当超过一个资产池的情况
        if(count > 1) {
           	// 导出文件
            List<File> sourceFiles = new ArrayList<>();
            for(PbcPrdAccountEndSub pbcPrdAccountEndSub : list1) {
            	List<PbcPrdAccountEndSub> pbcPrdAccountEndSubList = new ArrayList<PbcPrdAccountEndSub>();
            	if(StringUtils.isNotBlank(pbcPrdAccountEndSub.getPbcAccountId())) {
               	 exportFileName = "PPE-"+pbcPrdAccountEndSub.getPbcAccountId();
            	}
            	pbcPrdAccountEndSubList.add(pbcPrdAccountEndSub);
            	File outFile = makeFile2(
            			"/com/joyintech/tams/svreport/pbc/classic/prdaccountendsub/conf/export_conf2.xml", pbcPrdAccountEndSubList,exportFileName,"xls",true);
            	sourceFiles.add(outFile);
            }
          	// 生成压缩包
            File outFile = null;
            String exportFilePath = SvrDataUtil.getSysParam(
                    GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//                String zipFileName = FileNameUtil.createFileName("zip");
                String zipFileName = "PbocAssetPoolEnd("+DateUtil.format(new Date())+").zip";
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
             return ExportUtil.export(outFile.getName(), outFile, true);
         }
        // 当只有一个资产池，只有Excel导出
        for(PbcPrdAccountEndSub pbcPrdAccountEndSub : list1) {
        	if(StringUtils.isNotBlank(pbcPrdAccountEndSub.getPbcAccountId())) {
            	 exportFileName = "PPE-"+pbcPrdAccountEndSub.getPbcAccountId();
        	}
          }
		File outFile = makeFile2(
				"/com/joyintech/tams/svreport/pbc/classic/prdaccountendsub/conf/export_conf2.xml", list1,exportFileName,"xls",true);
		return ExportUtil.export(
            						 exportFileName +"("
                                     + DateUtil.format(new Date()) + ")"
                                     + "." + "xls",
                outFile, true);
    }
    
    
    /**
     * 根据配置文件生成报表文件
     *
     * @param configFile 配置文件
     * @param contents 需要被生成的报表内容
     * @return 报表文件
     */
    private File makeFile2(String configFile, List<PbcPrdAccountEndSub> contents,String name, String fileType,Boolean isHasName){
        if (null == contents) {
            contents = Collections.emptyList();
        }
        if(StringUtils.isBlank(name)) {
        	isHasName = false;
        	// 模板文件后缀名
            fileType = GlobalConstant.POINT + fileType;
        }else {
        	fileType = name + GlobalConstant.POINT + fileType;
        }
        beforeExprot();
		Map<String, String> cacheValue = CacheUtil.getAppCache(GlobalConstant.SYS_PARA_KEY, Map.class);
		cacheValue.put(GlobalConstant.EXPORT_EXCEL_TYPE, DictCodeList.是否_中债_否.getValue());
		CacheUtil.putAppCache(GlobalConstant.SYS_PARA_KEY, cacheValue);
		// 读取配置文件
        JoyinBookConfig reportConfig = pbcPrdAccountSubService001.getConfig(configFile);
        // 获取工作簿
        Workbook wb = null;
        try{
            wb = pbcPrdAccountSubService001.getWorkbook(reportConfig);
        }
        catch (IOException e){
        	logger.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
      
        // 如果导出数据大于系统参数设置的excel行数
        int count = StringUtil.integerValueOf(
            CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_ROW_COUNT), 50000);
        File targetFile = null;
        List<File> fileList = new ArrayList<>(contents.size() / count + 1);
        beforeCreateExcel(contents);
        try {
            while (contents.size() > count) {
                List<?> subList = contents.subList(0, count);
                contents = contents.subList(count, contents.size());
                fileList.add(pbcPrdAccountSubService001.makeSingleFile(wb, reportConfig, null, fileType, subList, new LongAdder(), 0L, count,isHasName));
            }
            fileList.add(pbcPrdAccountSubService001.makeSingleFile(wb, reportConfig, null, fileType, contents, new LongAdder(), 0L, count,isHasName));
        }
        catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        finally {
            try {
                if (null != wb) {
                    wb.close();
                }
            }
            catch (Exception e) {
            	logger.error(e.getMessage(),e);
            }
        }
        // 如果只有一个excel文件，不需要生成压缩文件
        if (fileList.size() == 1) {
            targetFile = fileList.get(0);
        }
        else {
            String exportFilePath = CacheUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
            String zipFileName = FileNameUtil.createFileName("zip");
            targetFile = new SafelyFile(exportFilePath + File.separator + zipFileName);
            try {
                ZipFileUtil.compressZip(targetFile, true, fileList.toArray(new SafelyFile[] {}));
            }
            catch (Exception e) {
                // 压缩文件失败后，删除所有临时文件
                for(File file : fileList) {
                    if(file.exists()) {
                        file.delete();
                    }
                }
                logger.error(e.getMessage(),e);
            }
        }
        afterExprot(null, contents.size());
		Map<String, String> cacheValue2 = CacheUtil.getAppCache(GlobalConstant.SYS_PARA_KEY, Map.class);
		cacheValue2.put(GlobalConstant.EXPORT_EXCEL_TYPE, DictCodeList.是否_中债_是.getValue());
		CacheUtil.putAppCache(GlobalConstant.SYS_PARA_KEY, cacheValue2);

		return targetFile;
    }
	
}
