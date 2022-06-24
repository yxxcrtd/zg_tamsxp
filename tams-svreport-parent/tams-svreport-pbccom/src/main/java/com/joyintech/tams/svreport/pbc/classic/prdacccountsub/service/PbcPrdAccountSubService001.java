package com.joyintech.tams.svreport.pbc.classic.prdacccountsub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.LongAdder;

import com.joyintech.framework.file.SafelyFile;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.handler.JoyinBookConfig;
import com.joyintech.framework.export.handler.JoyinCellConfig;
import com.joyintech.framework.export.handler.JoyinSheetConfig;
import com.joyintech.framework.export.handler.ReportCfgHandler;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.progressbar.ProgressBarService;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileHelper;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dao.PbcPrdAccountSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto.PbcPrdAccountSub;
import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dto.PbcProdRaiseSub;
/**
 * 人行报表查询-资产池基本信息controller
 * @author liuyu
 *
 */
@Service
@Transactional
public class PbcPrdAccountSubService001 extends CommonExportService<PbcPrdAccountSub> {
	/**
	 * 资产池基本信息mapper
	 */
	@Autowired
	private PbcPrdAccountSubMapper pbcPrdAccountSubMapper;
	/**
	 * 日志.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

    /**
     * 进度条ID
     */
    private static final String PROCESS_KEY = "exportProcessBar";
    
    /**
    *
    */
   private static final String TMP_DIR = "/tpl/";

    /**
     * 进度条接口
     */
    @Autowired
    private ProgressBarService progressBarService;
    
    /**
     * 导出总条数
     */
    private ThreadLocal<Long> exportSize = new ThreadLocal<>();
	

	/**
	 * 页面查询
	 * 
	 * @param searchParam
	 * @return
	 */
	public Page<PbcPrdAccountSub> searchPage(Map<String, Object> searchParam) {
		deaSearchParam(searchParam);
		int pagenum = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+searchParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
		Page<PbcPrdAccountSub> page = queryForPage(pagenum,length,()->pbcPrdAccountSubMapper.searchPage(searchParam));
//		dealResult(page);
		return page;
	}

	/**
	 * 查询结果处理
	 * @param page
	 */
	private void dealResult(Page<PbcPrdAccountSub> page) {
		List<PbcPrdAccountSub> resultList = page.getRoot();
		for(PbcPrdAccountSub result : resultList){
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
	}
	
	/**
	 * 设置导出文件参数
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config(
	            "/com/joyintech/tams/svreport/pbc/classic/prdacccountsub/conf/export_conf.xml")
	            .dataSource(DataSourceType.MASERT);
	}
	
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<PbcPrdAccountSub> selectPageData(Map<String, Object> selectParam) {
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
		List<PbcPrdAccountSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的手工处理
			try {
				infoList = JsonUtils.json2List(JsonUtils.object2Json(map.get("infoList")),PbcPrdAccountSub.class);
			} catch (IOException e) {
				logger.debug("数据转换异常",e.getMessage(),e);
				return ;
			}
		}else{
			Map<String,Object> pageParam;
			if("dat".equals(flag)){
				infoList = pbcPrdAccountSubMapper.searchPage(map);
			}else{
				pageParam = RequestUtil.getPageParam();
				deaSearchParam(pageParam);
				infoList = pbcPrdAccountSubMapper.searchPage(pageParam);
			}
			
		}
		// His插入
		Iterator<PbcPrdAccountSub> it = infoList.iterator();
		while(it.hasNext()){
			PbcPrdAccountSub info = it.next();
			if(SysDataUtil.getNameFromDataDict(DictCode.报送状态, "03").equals(info.getSendStatusStr())){
				it.remove();
				continue;
			}
			// 只能选择报送状态为未报送、或报送状态为接口报送且反馈状态为发送失败和人行退回的数据进行手工处理
	        if(!(
	        		(SysDataUtil.getNameFromDataDict(DictCode.报送状态, "01").equals(info.getSendStatusStr()))
						||
						((SysDataUtil.getNameFromDataDict(DictCode.报送状态, "02").equals(info.getSendStatusStr()))
					    && ((SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, "02").equals(info.getSubmitFeedbackStatusStr()))
					    ||  (SysDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, "04").equals(info.getSubmitFeedbackStatusStr()))))
	        		))
	        	{
			   it.remove();
	        	}
			}
		if(infoList.isEmpty())
			return ;
		/* 手工处理数据批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdAccountSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				pbcPrdAccountSubMapper.insertHisBatchBySendStatus(list1);
				// 原始表删除
				pbcPrdAccountSubMapper.deleteBatchBySendStatus(list1);		
				// 原始表更新状态
				pbcPrdAccountSubMapper.doManul(list1,map.get("submitadd")==null?null:map.get("submitadd").toString());
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdAccountSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			pbcPrdAccountSubMapper.insertHisBatchBySendStatus(list2);
			// 原始表删除
			pbcPrdAccountSubMapper.deleteBatchBySendStatus(list2);		
			// 原始表更新状态
			pbcPrdAccountSubMapper.doManul(list2,map.get("submitadd")==null?null:map.get("submitadd").toString());
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
		List<PbcPrdAccountSub> infoList = new ArrayList<>();
		if("01".equals(flag)){
			// 选中的取消手工处理
			infoList  = (List<PbcPrdAccountSub>) map.get("infoList");
		}else{
			// 查询方式
//			Map<String,Object> pageParam = RequestUtil.getPageParam();
//			infoList = searchPage(pageParam).getRoot();
			deaSearchParam(map);
			infoList = pbcPrdAccountSubMapper.searchPage(map);
		}
		if(infoList.isEmpty())
			return "success";
		//找sub表有没有未报送的数据
		List<String> unregList = pbcPrdAccountSubMapper.selectRegistSeqnoList(infoList,"01");
		if(!CollectionUtils.isEmpty(unregList)){
			return "fail";
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 begin */
		int grps = infoList.size()/PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		int idx = 0;
		for ( int i=0;i<grps;i++ ) {
			List<PbcPrdAccountSub> list1 = infoList.subList(idx,idx + PbcClassicsDataFileHelper.COMMIT_CONSTANTS);
			if ( list1.size()>0 ) {
				// 更新状态
				pbcPrdAccountSubMapper.cancelManul(list1);
				// His表数据插入sub表
				pbcPrdAccountSubMapper.insetBatchSubFromHisByUpdateTime(list1);
				// 删除his表中移走的数据
				pbcPrdAccountSubMapper.deleteBatchSubFromHisByUpdateTime(list1);
			}
			idx += 	PbcClassicsDataFileHelper.COMMIT_CONSTANTS;
		}
		List<PbcPrdAccountSub> list2 = infoList.subList(idx, infoList.size());
		if ( list2.size()>0 ) {
			// 更新状态
			pbcPrdAccountSubMapper.cancelManul(list2);
			// His表数据插入sub表
			pbcPrdAccountSubMapper.insetBatchSubFromHisByUpdateTime(list2);
			// 删除his表中移走的数据
			pbcPrdAccountSubMapper.deleteBatchSubFromHisByUpdateTime(list2);
		}
		/* 取消手工处理批量进行 update by gaojian 2019-06-22 end */
		logger.debug("取消手工处理结束:------------------------------"+new Date());
		return "success";
	}

	/**
	 * 资产池基本信息人行导出Excel
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
        List<PbcPrdAccountSub> dataList = pbcPrdAccountSubMapper.searchPage(selectParam);
        //先默认一个名词，避免出现名字为NULL
        String exportFileName = "资产池基本信息";
        // 计数器，记载有多少资产池
		int count = 0;
		// 去重：若勾选了记录，按照勾选的进行导出,注意若同一个资产池若有2条记录，则导出修改未报送那条
        List<PbcPrdAccountSub> list1= new ArrayList<PbcPrdAccountSub>();
    	if(CollectionUtils.isNotEmpty(dataList)) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (PbcPrdAccountSub pbcPrdAccountSub : dataList) {
                if (pbcPrdAccountSub == null) {
                    continue;
                }
                String pbcAccountId  = pbcPrdAccountSub.getPbcAccountId();
                if(StringUtils.isBlank(pbcAccountId)) {
                	continue;
                }
                if (pbcAccountId != null) {
                	String value = hashMap.get(pbcAccountId);
                    if (StringUtils.isBlank(value)) { 
                    	//如果value是空的  说明是第一次取到
                        hashMap.put(pbcAccountId, pbcAccountId);
                        list1.add(pbcPrdAccountSub);
                        count ++;
                    } else {
                    		if(StringUtils.equalsIgnoreCase(pbcPrdAccountSub.getSendStatus(), DictCodeListExt2.报送状态_未报送.getValue())) {
                    			Iterator<PbcPrdAccountSub> iterator = list1.iterator();
								while (iterator.hasNext()) {
									PbcPrdAccountSub prdAccountSub = iterator.next();
									if (pbcAccountId.equals(prdAccountSub.getPbcAccountId())) {
										iterator.remove();//使用迭代器的删除方法删除
									}
								}
                    			list1.add(pbcPrdAccountSub);
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
            for(PbcPrdAccountSub pbcPrdAccountSub : list1) {
            	List<PbcPrdAccountSub> pbcPrdAccountSubList = new ArrayList<PbcPrdAccountSub>();
            	if(StringUtils.isNotBlank(pbcPrdAccountSub.getPbcAccountId())) {
            		if(StringUtils.isNotEmpty(pbcPrdAccountSub.getPbcCode())) {
						exportFileName = "PPI-"+pbcPrdAccountSub.getPbcCode();
					} else {
						exportFileName = "PPI-"+pbcPrdAccountSub.getPbcAccountId();
					}

            	}
            	pbcPrdAccountSubList.add(pbcPrdAccountSub);
            	File outFile = makeFile2(
            			"/com/joyintech/tams/svreport/pbc/classic/prdacccountsub/conf/export_conf2.xml", pbcPrdAccountSubList,exportFileName,"xls",true);
            	sourceFiles.add(outFile);
            }
          	// 生成压缩包
            File outFile = null;
            String exportFilePath = SvrDataUtil.getSysParam(
                    GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//                String zipFileName = FileNameUtil.createFileName("zip");
                String zipFileName = "PPB1资产池基本信息("+DateUtil.format(new Date())+").zip";
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
                }
             return ExportUtil.export(outFile.getName(), outFile, true);
         }
        // 当只有一个资产池，只有Excel导出
        for(PbcPrdAccountSub pbcPrdAccountSub : list1) {
        	if(StringUtils.isNotBlank(pbcPrdAccountSub.getPbcAccountId())) {
				if(StringUtils.isNotEmpty(pbcPrdAccountSub.getPbcCode())) {
					exportFileName = "PPI-"+pbcPrdAccountSub.getPbcCode();
				} else {
					exportFileName = "PPI-"+pbcPrdAccountSub.getPbcAccountId();
				}
        	}
          }
		File outFile = makeFile2(
				"/com/joyintech/tams/svreport/pbc/classic/prdacccountsub/conf/export_conf2.xml", list1,exportFileName,"xls",true);
		return ExportUtil.export(
            						 exportFileName +"("
                                     + DateUtil.format(new Date()) + ")"
                                     + "." + "xls",
                outFile, true);
    }
    
    /**
     * 获取配置文件指向的工作簿
     *
     * @param reportConfig 配置文件
     * @return Workbook对象
     * @throws IOException 异常
     */
    public Workbook getWorkbook(JoyinBookConfig reportConfig)
        throws IOException{
        // 工作表对象
        Workbook wb = null;
        String EXPORT_EXCEL_TYPE = CacheUtil.getSysParam(GlobalConstant.EXPORT_EXCEL_TYPE);
        // 模板文件后缀名
        String fileType = GlobalConstant.FILE_POSTFIX_XLSX;
        String fileName = StringUtil.splitToList(GlobalConstant.POINT, reportConfig.getTempleteFile()).get(0);
        if(StringUtil.equals(EXPORT_EXCEL_TYPE, DictCodeList.是否_中债_否.getValue())){
        	fileType = GlobalConstant.FILE_POSTFIX_XLS;
        }
        // 从配置文件中获取模板文件  以及文件类型
        String tempFilePath = fileName + fileType;
        File templeteFile = new SafelyFile(buildTempleteFilePath(tempFilePath));
        reportConfig.setTempleteFile(fileName + fileType);
        // 模板输入流
        InputStream stream = null;
        try{
			if (templeteFile.exists()) {
				stream = new FileInputStream(templeteFile);
			}
			else {
				stream = CommonExportService.class.getResourceAsStream(
						TMP_DIR + tempFilePath);
			}
			// 工作表对象初始化
			if (StringUtil.equals(fileType, GlobalConstant.FILE_POSTFIX_XLS)) {
				wb = new HSSFWorkbook(stream);
			}
			else if (StringUtil.equals(fileType, GlobalConstant.FILE_POSTFIX_XLSX)) {
				wb = new XSSFWorkbook(stream);
			}
			else {
				throw new BusinessException("不是一个有效的Excel文件");
			}
		}finally {
        	if(stream != null){
				stream.close();
			}
		}

        return wb;
    }
    
    /**
     * 解析配置文件
     *
     * @param configFile 配置文件
     * @return JoyinBookConfig
     */
    public JoyinBookConfig getConfig(String configFile){
        ReportCfgHandler handler = new ReportCfgHandler();
        // 读取配置文件
        JoyinBookConfig reportConfig = null;
        try {
            reportConfig = handler.readConfig(configFile);
        }
        catch (Exception e){
        	logger.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return reportConfig;
    }
    
    /**
     * 根据配置文件生成报表文件
     *
     * @param wb 工作簿对象
     * @param reportConfig 配置文件
     * @param pageId 画面ID
     * @param fileType 文件类型
     * @param contents 需要被生成的报表内容
     * @param current 已导出条数
     * @param total 需要被导出的总条数
     * @param sheetMaxRowCount 一个Sheet中最大行
     * @return 报表文件
     * @throws IOException 异常
     */
    public File makeSingleFile(Workbook wb, JoyinBookConfig reportConfig, String pageId, String fileType,
                                List<?> contents, LongAdder current, long total, int sheetMaxRowCount,Boolean isHasName)
        throws IOException {
        ReportCfgHandler handler = new ReportCfgHandler();
        wb.close();
        wb = getWorkbook(reportConfig);
        // 配置文件中的工作表列表
        List<JoyinSheetConfig> sheetList = reportConfig.getSheetConfList();
        // 循环处理每个Sheet
        for (int i = 0; i < sheetList.size(); i++ ) {
            // 获取配置文件中的工作表配置
            JoyinSheetConfig sheetCfg = sheetList.get(i);
            // javaBean
            String beanName = sheetCfg.getJavaBean();
            // 获取配置文件中每行对应的单元格配置
            List<JoyinCellConfig> cellList = sheetCfg.getCellConfList();
            // 获取模板文件中的sheet页
            Sheet sheet = wb.getSheetAt(i);
            // 模板文件填充的开始行，默认从第二行开始。第一行预留为标题行
            int index = sheetCfg.getRowStart();
			
            //TISHKBS-976 产品终止信息，资产池基本信息，资产池终止信息导出人行excel,导出内容标题栏与数据栏中间多了一行空白 modify by lizhi 20200825 start
			index ++;
            //TISHKBS-976 产品终止信息，资产池基本信息，资产池终止信息导出人行excel,导出内容标题栏与数据栏中间多了一行空白 modify by lizhi 20200825 end
			
            // // 模板文件当前sheet中的最大行
            // int maxRowNum = sheet.getLastRowNum() - sheet.getFirstRowNum();
            // // 模板文件当前sheet中的最大列
            // int maxColNum = sheet.getRow(sheet.getFirstRowNum()).getPhysicalNumberOfCells();
            // 模板文件最后一行
            Row lastRow = sheet.getRow(sheet.getLastRowNum());
            for (Object dto : contents) {
                Map<String, Object> map = BeanUtil.object2Map(dto);
                Row row = sheet.getRow(index++ );
                if (row == null) {
                    handler.copyRow(wb, lastRow, sheet.createRow(index - 1), false);
                    row = sheet.getRow(index - 1);
                }

                for (int ci = 0; ci < cellList.size(); ci++ ) {
                    JoyinCellConfig cfg = cellList.get(ci);
                    // 获取单元格
                    Cell cell = row.getCell(ci, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String propName = cfg.getBeanPropName();
                    if (StringUtil.isNotEmpty(propName)) {
                        Cell hCell = lastRow.getCell(ci);
                        if (null == cell && null != hCell) {
                            cell = row.createCell(ci);
                            handler.copyCell(wb, hCell, cell, false);
                            setCellStyle(wb, cell.getCellStyle());
                        }
                        Object value = map.get(propName);
                        if (cell!=null && StringUtil.isNotEmpty(value)) {
                            if (value instanceof Date) {
                                cell.setCellValue(DateUtil.formatDateTime((Date)value));
                            }
                            else if (value instanceof String) {
                                if (StringUtil.isNotEmpty(value)) {
                                    value = StringUtil.getLimitLengthString(value.toString(),
                                        16383, StringUtil.EMPTY);
                                }
                                cell.setCellValue((String)value);
                            }
                            else if (value instanceof Boolean) {
                                cell.setCellValue((Boolean)value);
                            }
                            else if (value instanceof Calendar) {
                                cell.setCellValue((Calendar)value);
                            }
                            else if (value instanceof RichTextString) {
                                cell.setCellValue((RichTextString)value);
                            }
                            else if (value instanceof Double) {
                                cell.setCellValue((Double)value);
                            }
                            else if (value instanceof BigDecimal) {
                                BigDecimal cellValue = (BigDecimal)value;
                                cell.setCellValue(cellValue.doubleValue());
                            }
                            else if (value instanceof Long) {
                                Long cellValue = (Long)value;
                                cell.setCellValue(cellValue.doubleValue());
                            }
                            else if (value instanceof Integer) {
                                Integer cellValue = (Integer)value;
                                cell.setCellValue(cellValue.doubleValue());
                            }
                            // 自定义的特殊处理
                            setCellValue(beanName, cell, propName, value);
                        }
                    }
                }
                if(null != pageId && total > 0) {
                    current.increment();
                    progressBarService.updatePercent(pageId, PROCESS_KEY, current.doubleValue() / total, false);
                }
            }
        }

        // 生成后的文件名
        String outFileName = FileNameUtil.createFileName(fileType);
        if(isHasName) {
            if(StringUtil.isNotEmpty(fileType)) {
            	 outFileName = fileType;
        } 
        }
        // 从系统配置参数中获取文件存放路径
        String exportFilePath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
        File file = new SafelyFile(exportFilePath + File.separator + outFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try{
			fileOutputStream = new FileOutputStream(file);
			wb.write(fileOutputStream);
			return file;
		}finally {
        	if (fileOutputStream != null){
        		fileOutputStream.close();
			}
		}

    }
    
    /**
     * 根据配置文件生成报表文件
     *
     * @param configFile 配置文件
     * @param contents 需要被生成的报表内容
     * @return 报表文件
     */
    private File makeFile2(String configFile, List<PbcPrdAccountSub> contents,String name, String fileType,Boolean isHasName){
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
        JoyinBookConfig reportConfig = getConfig(configFile);
        // 获取工作簿
        Workbook wb = null;
        try{
            wb = getWorkbook(reportConfig);
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
                fileList.add(makeSingleFile(wb, reportConfig, null, fileType, subList, new LongAdder(), 0L, count,isHasName));
            }
            fileList.add(makeSingleFile(wb, reportConfig, null, fileType, contents, new LongAdder(), 0L, count,isHasName));
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
                logger.error(e.getMessage(), e);
            }
        }
        afterExprot(null, contents.size());
		Map<String, String> cacheValue2 = CacheUtil.getAppCache(GlobalConstant.SYS_PARA_KEY, Map.class);
		cacheValue2.put(GlobalConstant.EXPORT_EXCEL_TYPE, DictCodeList.是否_中债_是.getValue());
		CacheUtil.putAppCache(GlobalConstant.SYS_PARA_KEY, cacheValue2);
		return targetFile;
    }

}
