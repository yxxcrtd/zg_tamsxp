package com.joyintech.tams.svreport.interf.chb.baseinfo.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.MstExchargeQuoteErrodataWithBLOBs;
import com.joyintech.tams.common.base.entity.MstHoliday;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.MstExchargeQuote;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstExchangeImpMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * @Description 汇率信息文件接口
 * @author jiangxiao
 * @version 1.7 2019年5月10日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrExchangFileImpService extends FileImpService {
	
	 /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
	 * 上游接口文件编码格式
	 */
	@Value("${application.reportInterfFileEncoding}")
	public String reportInterfFileEncoding;
	/**
	 * 上游接口解析分割符
	 */
	@Value("${application.reportInterfSplitCharacter}")
	private String reportInterfSplitCharacter;
	
	/**
	 * 上游接口解析读取文件处理最大线程数
	 */
	@Value("${application.interfMaxCorePoolSize}")
	private int interfMaxCorePoolSize;
	 /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrDataCount}")
    private int reportInterfOneTimeSqlLdrDataCount;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;
    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;
    
    @Autowired
	private SqlSessionTemplate template;
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private SvrMstExchangeImpMapper chbExchangeImpMapper;

	@Autowired
	private BaseCommonCheckService baseCommonCheckService;

	@Autowired
	private BatchInsertService batchInsertService;
	@Autowired
	private CommonCodeCheckBatchService commonCodeCheckBatchService;
	
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		// 解析成功或失败的标识
		boolean success = true;
        String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		logger.debug("汇率数据转dto---开始-----:" +  df.format(new Date()));
		// modify by convertMapToEntity yangnxianjiu 2022-01-19 start
//		listNE = convertMapToEntity(exchangeList);
		List<MstExchargeQuote> listNE = (List<MstExchargeQuote>) convertMapToEntity(listData, MstExchargeQuote.class, dataSource);
		// modify by convertMapToEntity yangnxianjiu 2022-01-19 end
		logger.debug("汇率数据转dto---结束-----:" +  df.format(new Date()));
		if(!CollectionUtils.isEmpty(listNE)){
			// 增量模式根据业务主键更新
			if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
				List<MstExchargeQuoteErrodataWithBLOBs> mstExchargeQuoteErrodataList = Lists.newArrayList();
				listNE.forEach(e->{
					MstExchargeQuoteErrodataWithBLOBs mstExchargeQuoteErrodataWithBLOBs = new MstExchargeQuoteErrodataWithBLOBs();
					mstExchargeQuoteErrodataWithBLOBs.setExchargeCode(e.getExchargeCode());
					mstExchargeQuoteErrodataWithBLOBs.setTradeDate(e.getTradeDate());
					mstExchargeQuoteErrodataList.add(mstExchargeQuoteErrodataWithBLOBs);
				});
				commonCodeCheckBatchService.deleteMstExchargeQuoteErrodata(mstExchargeQuoteErrodataList);
				chbExchangeImpMapper.deleteExchangeBatch(listNE);
				mergeExchangeInfo(listNE);
			}
			// 全量模式。根据日期全删全插
			else{
				Map<Date, List<MstExchargeQuote>> mstExchargeQuoteMap = listNE.stream().collect(JoyinCollectors.groupingBy(MstExchargeQuote::getTradeDate));
				Set<Date> dateSet = mstExchargeQuoteMap.keySet();
				deleteAllData(dateSet);
				List<MstExchargeQuoteErrodataWithBLOBs> mstExchargeQuoteErrodataList = Lists.newArrayList();
				listNE.forEach(e->{
					MstExchargeQuoteErrodataWithBLOBs mstExchargeQuoteErrodataWithBLOBs = new MstExchargeQuoteErrodataWithBLOBs();
					mstExchargeQuoteErrodataWithBLOBs.setExchargeCode(e.getExchargeCode());
					mstExchargeQuoteErrodataWithBLOBs.setTradeDate(e.getTradeDate());
					mstExchargeQuoteErrodataList.add(mstExchargeQuoteErrodataWithBLOBs);
				});
				commonCodeCheckBatchService.deleteMstExchargeQuoteErrodata(mstExchargeQuoteErrodataList);
				chbExchangeImpMapper.deleteExchangeBatch(listNE);
				if (listNE.size() <= 1000) {
					insertExchangeInfo(listNE);
				} else {// 大于1000使用sqlloader入库
					try {
						//sqlloader插入
						ImpTable table = new ImpTable("MST_EXCHARGE_QUOTE");
						table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
						// 临时文件存放目录
						String path = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

						batchInsertService.exeBatchldr(path, listNE, table);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}

			}
		}
		return "success";
	}

	@Override
	public String importDB(List<Map<String, String>> listData,InterfFile fileList, String date) {
		String remoteDir = "";
		String result = "";
		
		logger.debug("汇率数据检查---开始-----:" +  df.format(new Date()));
	    //对数据进行校验
		baseCommonCheckService.checkDataAll("I", listData, "MST_EXCHARGE_QUOTE", fileList.getFilename() ,InterfDefine.汇率信息数据.getCode(), "EXCHARGE_CODE","TRADE_DATE","DATASOURCE_TYPE");
		logger.debug("汇率数据检查---结束-----:" +  df.format(new Date()));
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.汇率信息数据.getCode());
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        logger.debug("汇率数据入库---开始-----:" +  df.format(new Date()));
		result = saveData(listData, saveFlag,null);
		logger.debug("汇率数据入库---结束-----:" +  df.format(new Date()));
		// 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.汇率信息数据.getCode());
		if("fail".equals(result)||"fail".equals(remoteDir)){
			return "fail";
		}else{
			return "success";
		}
	}
	@Override
	public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> listData, InterfFile interfFile, String date) {

		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.汇率信息数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		checkImportData(listExcel,listData, interfFile.getFilename(), ChbCheckTableInfo.汇率行情信息,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");

		String saveFlag = interfConfig.getContentType();
		String result = "";
		if(listData.size()>0){
			result=saveData(listData, listExcel == null?saveFlag: InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
		}
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.汇率信息数据.getCode());
		return result;
	}
	private void mergeExchangeInfo(List<MstExchargeQuote> listNE) {
		chbExchangeImpMapper.mergeExchangeData(listNE);
	}
	
	private void deleteAllData(Set<Date> dateSet) {
		chbExchangeImpMapper.deleteTodayData(dateSet);
	}

	@SuppressWarnings("unused")
	private void deleteExchangeInfo(List<MstExchargeQuote> exchangeInfolist) {
		int batchCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT) ? "100"
				: SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
		int batchLastIndex = batchCount;// 每批最后一个的下标
		logger.debug("汇率删除开始-------------" + df.format(new Date()));
		for (int index = 0; index < exchangeInfolist.size();) {
			if (batchLastIndex > exchangeInfolist.size() - 1) {
				batchLastIndex = exchangeInfolist.size();
				chbExchangeImpMapper.deleteExchangeBatch(exchangeInfolist.subList(index, batchLastIndex));
				break;// 数据插入完毕,退出循环
			} else {
				chbExchangeImpMapper.deleteExchangeBatch(exchangeInfolist.subList(index, batchLastIndex));
				index = batchLastIndex;// 设置下一批下标
				batchLastIndex = index + batchCount;
			}
		}
		logger.debug("汇率删除结束-------------" + df.format(new Date()));
	}
	
	private void insertExchangeInfo(List<MstExchargeQuote> exchangeInfolist) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int count = 0;
			SvrMstExchangeImpMapper batchMapper = session.getMapper(SvrMstExchangeImpMapper.class);
			logger.debug("汇率插入开始-------------" + df.format(new Date()));
			for (MstExchargeQuote exchangeInfo : exchangeInfolist) {
				batchMapper.insert(exchangeInfo);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
					logger.debug("汇率已插入条数--------" + count + "-----" + df.format(new Date()));
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
			logger.debug("汇率已插入条数--------" + count + "-----" + df.format(new Date()));
		} finally {
			session.close();
		}
		logger.debug("汇率插入结束-------------" + df.format(new Date()));
	}
	
	/**
     * 将map转换为entity格式
     * @param dataList 传入的listMap
     * @see
     * @since 1.0
     */
    private List<MstExchargeQuote> convertMapToEntity(List<Map<String, String>> dataList) {
        // 获取产品表的列
    	List<MstExchargeQuote> rs = new ArrayList<MstExchargeQuote>();
        for (Map<String, String> map : dataList) {
        	MstExchargeQuote t = new MstExchargeQuote();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            for (String key : map.keySet()) {
            	if(key==null||key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(MstExchargeQuote.class,
                			subBlank(key.toLowerCase()));
                if(field==null)
                	continue;
                field.setAccessible(true);
                Type type = field.getGenericType();
                if("java.lang.String".equals(type.getTypeName())){
                    try {
                        field.set(t, value);
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                    }
                }else if ("java.math.BigDecimal".equals(type.getTypeName())) {
                        try {
                            field.set(t,(value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
                        } catch ( IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    else if ("java.lang.Integer".equals(type.getTypeName())) {
                        try {
                            field.set(t,(value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    else if ("java.lang.Short".equals(type.getTypeName())) {
                        try {
                            field.set(t, (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                        } catch ( IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    } 
                    else if ("java.lang.Long".equals(type.getTypeName())) {
                        try {
                            field.set(t, (value == null || "".equals(value)) ? 0 : Long.valueOf(value));
                        } catch ( IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    else if ("java.util.Date".equals(type.getTypeName())) {
                        if (value != null&&""!=value) {
                            try {
                                field.set(t, DateUtil.parse(value));
                            } catch ( IllegalAccessException e) {
                                logger.error(e.getMessage(),e);
                            }
                        }
                    } else{
                	try {
                        field.set(t, value);
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            }
            if(t.getCreateUser()==null){
            	t.setCreateUser("system");
            }
            // 创建人
            t.setCreateUser(CommonUtil.getUserName());
            // 创建部门
            t.setCreateDept(CommonUtil.getDeptCode());
            // 更新人
            t.setUpdateUser(CommonUtil.getUserName());
            rs.add(t);
        }
        return rs;
    }


	/**
	 * 增强check list转换为Bean
	 *
	 * @return 返回检查结果
	 */
	private List<MstExchargeQuote> convertToBean(List<Map<String, String>> mapList) {


		List<MstExchargeQuote> returnList = new LinkedList<>();
		MstExchargeQuote dto = null;
		for (Map<String, String> map : mapList) {
			dto = new MstExchargeQuote();
			if (map.containsKey("EXCHARGE_CODE")) {
				String exchargeCode = map.get("EXCHARGE_CODE");
				dto.setExchargeCode(exchargeCode);
				dto.setBasisCyy(exchargeCode.split("/")[0]);
				dto.setQuotCyy(exchargeCode.split("/")[1]);
			}
			if (map.containsKey("TRADE_DATE")) {
				dto.setTradeDate(com.joyintech.framework.util.DateUtil.parse(map.get("TRADE_DATE")));
			}
			if (map.containsKey("DATASOURCE_TYPE")) {
				dto.setDatasourceType(map.get("DATASOURCE_TYPE"));
			}
			if (map.containsKey("OPEN_PRICE")) {
				String openPrice = map.get("OPEN_PRICE");
				dto.setOpenPrice(StringUtil.isBlank(openPrice) ? null : new BigDecimal(openPrice));
			}
			if (map.containsKey("CLOSE_PRICE")) {
				String closePrice = map.get("CLOSE_PRICE");
				dto.setClosePrice(StringUtil.isBlank(closePrice) ? null: new BigDecimal(closePrice));
			}
			if (map.containsKey("MID_PRICE")) {
				String midPrice = map.get("MID_PRICE");
				dto.setMidPrice(StringUtil.isBlank(midPrice)? null : new BigDecimal(midPrice));
			}
			if (map.containsKey("HIGH_PRICE")) {
				String highPrice = map.get("HIGH_PRICE");
				dto.setHighPrice(StringUtil.isBlank(highPrice)? null : new BigDecimal(highPrice));
			}
			if (map.containsKey("LOW_PRICE")) {
				String lowPrice = map.get("LOW_PRICE");
				dto.setLowPrice(StringUtil.isBlank(lowPrice)? null : new BigDecimal(lowPrice));
			}
			if (map.containsKey("QUOT_UNIT")) {
				dto.setQuotUnit(Long.parseLong(map.get("QUOT_UNIT")));
			}
			if (Objects.equals("A", map.get("STATUS"))) {
				dto.setStatus(DictCodeListExt2.数据状态_待处理.getValue());
				dto.setErrMsg(map.get("ERRMSG"));
			} else {
				dto.setStatus(DictCodeListExt2.数据状态_有效.getValue());
			}
			dto.setDataSource(DictCodeListExt2.数据来源_接口.getValue());
			returnList.add(dto);
		}
		return returnList;
	}
}
