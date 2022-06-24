package com.joyintech.tams.svreport.interf.chb.loandetailinfo.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.tams.common.base.dao.ChbLoanDetailContractDtoMapper;
import com.joyintech.tams.common.base.dao.MstBondCreditRatingMapper;
import com.joyintech.tams.common.base.entity.MstBondCreditRating;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.ChbLoanDetailContractDto;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.loandetailinfo.dao.SvrLoanDetailContractMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * @Description 贷款明细原始合同信息接口
 * @author yuluan
 * @version 1.0 2020年2月13日
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrLoanDetailContractFileImpService extends FileImpService {
	
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
	private SvrLoanDetailContractMapper svrLoanDetailContractMapper;

	
	@Autowired
	private SqlSessionTemplate template;
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dateSource) {
		// 解析成功或失败的标识
		boolean success = true;
        String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 数据小于1000直接入库
		if (listData.size() <= 1000) {
			success = excuteSecRatingByStep(saveFlag,listData, null, null, null, null,dateSource);
		} else {// 大于1000使用sqlloader入库
			// 创建表
			ImpTable table = new ImpTable(InterfDefine.贷款明细原始合同信息接口.getCode());
			table.setColumns(ImportGenerator.createImpTableColumns(InterfDefine.贷款明细原始合同信息接口.getCode()));
			// 创建一个线程池
			if (interfMaxCorePoolSize < 1) {
				interfMaxCorePoolSize = 20;
			}
			ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
			List<Callable<Boolean>> tasks = new ArrayList<>();
			if (null != listData) {
				int start = 0;
				int end = 0;
				int size = listData.size();
				int max = (int) Math.ceil((double) size / 1000);
				for (int i = 0; i < max; i++) {
					start = end;
					end = Math.min(size, 1000 * (i + 1));
					List<Map<String, String>> subList = listData.subList(start, end);
					logger.debug("生成债券评级qlloader文件"+i);
					tasks.add(new Callable<Boolean>() {
						@Override
						public Boolean call() {
							return excuteSecRatingByStep(saveFlag,subList,null, tmpPath, table, null,dateSource);
						}
					});
				}
			}
			// 为线程池添加任务
			List<Future<Boolean>> taskResult;
			try {
				taskResult = service.invokeAll(tasks);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				return "fail";
			}
			for (Future<Boolean> res : taskResult) {
				try {
					success = success && res.get();
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage(), e);
					return "fail";
				}
			}
			// 任务结束，停止线程池的所有线程
			service.shutdown();
			Collection<File> files = FileUtils.listFiles(new SafelyFile(tmpPath+"/CHB_LOAN_DETAIL_CONTRACT/"), new String[] { "tmp" }, false);
			logger.debug("获取债券评级sqlloder文件个数"+files.size());
			List<String> content = new ArrayList<>(listData.size());
			String result = "";
			for (File f : files) {
				if (f.getName().startsWith(InterfDefine.债券评级数据.getCode())) {
					try {
						content.addAll(FileUtils.readLines(f, Charset.forName("UTF-8")));
						logger.debug("查找sqlloder文件" + f);
					} catch (IOException e) {
						logger.debug("查找sqlloder文件异常");
						logger.error(e.getMessage(), e);
						result = "fail";
					} finally {
						f.delete();
					}
				}
			}
			logger.debug("读取债券评级sqlloder文件共"+content.size()+"条");
			if ("fail".equals(result)) {
				return result;
			}
			// 系统是否使用的db2数据库
			boolean isDB2DataBase = CommonUtil.isDB2DataBase();
			if (isDB2DataBase) {
				// db2数据库处理
				if (CollectionUtils.isNotEmpty(content)) {
					try {
						success = success && ImportGenerator.importToDBByPart(table, tmpPath+"/CHB_LOAN_DETAIL_CONTRACT/", content,
								reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "del",
								reportSqlldrDatabaseName);
					} catch (IOException | InterruptedException e) {
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}
			} else {
				// oracle原逻辑
				if (CollectionUtils.isNotEmpty(content)) {
					try {
						logger.debug("sqlloader入库开始");
						success = success && ImportGenerator.importToDBByPart(table, tmpPath+"/CHB_LOAN_DETAIL_CONTRACT/", content,
								reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "txt",
								reportSqlldrDatabaseName);
						logger.debug("sqlloader入库结束");
					} catch (IOException | InterruptedException e) {
						logger.debug("sqlloader入库报错");
						logger.error(e.getMessage(), e);
						return "fail";
					}
				}
			}
		}
		return "success";
	}

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
		String remoteDir = "";
		String result = "";
		logger.debug("数据检查---开始-----:" +  df.format(new Date()));
		@SuppressWarnings("unused")
		List<Map<String, String>> listError = checkData(listData, interfFile.getFilename(), InterfDefine.贷款明细原始合同信息接口.getCode(), InterfDefine.贷款明细原始合同信息接口.getCode(), null,"PROD_ID","ASSET_CODE","ORIG_CCY");
		logger.debug("数据检查---结束-----:" +  df.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.贷款明细原始合同信息接口.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        logger.debug("评级数据入库---开始-----:" +  df.format(new Date()));
		result = saveData(listData,saveFlag,interfFile.getDataSource());
		logger.debug("评级数据入库---结束-----:" +  df.format(new Date()));
		// 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.贷款明细原始合同信息接口.getCode());
		if("fail".equals(result)||"fail".equals(remoteDir)){
			return "fail";
		}else{
			return "success";
		}
	}

	private boolean excuteSecRatingByStep(String saveFlag, List<Map<String, String>> datalist, String[] result,
			String tmpPath, ImpTable table, ImpTable subTable, String dataSource) {
		// 定义新增数据list-验证通过数据
		List<ChbLoanDetailContractDto> listNE = new ArrayList<ChbLoanDetailContractDto>();
		logger.debug("评级数据转换dto---开始-----:" +  df.format(new Date()));
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
//		listNE = convertMapToEntity(datalist,dateSource);
		listNE = (List<ChbLoanDetailContractDto>) convertMapToEntity(datalist,ChbLoanDetailContractDto.class, dataSource);
		// modify by convertMapToEntity yangxianjiu 2022-01-19 end
		logger.debug("评级数据转换dto---结束-----:" +  df.format(new Date()));
		if (listNE != null && listNE.size() > 0) {
			// 删除评级(当日)
			if (tmpPath == null) {
				insertDataMerge(listNE);
			} else {
				createDataFile(tmpPath + "/CHB_LOAN_DETAIL_CONTRACT/CHB_LOAN_DETAIL_CONTRACT", listNE, table.getColumns());
				logger.debug("创建sqlloder文件");
			}
		}
		return (CollectionUtils.isNotEmpty(listNE));
	}
	
//	private void insertLoanDetailContract(List<ChbLoanDetailContractDto> listNE) {
//		logger.debug("批量插入贷款明细原始合同信息----开始-----:" +  df.format(new Date()));
//		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//		ChbLoanDetailContractDtoMapper mstBondCreditRatingMapper =  session.getMapper(ChbLoanDetailContractDtoMapper.class);
//		try{
//			int count = 0;
//			for(ChbLoanDetailContractDto sch : listNE){
//				mstBondCreditRatingMapper.insert(sch);
//        		count++;
//        		if(count % 1000==0){
//            		session.commit();
//            		session.clearCache();
//        		}
//        	}	
//			session.commit();
//    		session.clearCache();
//		} finally {
//    		session.close();
//    	}
//		logger.debug("批量插入贷款明细原始合同信息----结束-----:" +  df.format(new Date()));	
//	}
	
	 /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param investTradeList 投资交易信息
     * @param saveFlag:F－全量，I－增量
     */
    public void insertDataMerge(List<ChbLoanDetailContractDto> investTradeList) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("投资交易插入开始--------------" + new Date());
            SvrLoanDetailContractMapper batchMapper = session.getMapper(SvrLoanDetailContractMapper.class);
            int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < investTradeList.size();){
                if(batchLastIndex > investTradeList.size()-1){
                    batchLastIndex = investTradeList.size();
                    batchMapper.mergeLoanDetailContract(investTradeList.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeLoanDetailContract(investTradeList.subList(index, batchLastIndex));
                    index = batchLastIndex;//设置下一批下标
                    batchLastIndex = index + batchCount;                     
                }
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            logger.debug("投资交易插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("投资交易插入结束--------------" + new Date());
    }

	private void deleteAllData(){
		svrLoanDetailContractMapper.deleteAllData();
	}
	
	@SuppressWarnings("unused")
	private void mergeLoanDetailContract(List<ChbLoanDetailContractDto> list){
		svrLoanDetailContractMapper.mergeLoanDetailContract(list);
	}

	/**
     * 将map转换为entity格式
     * @param dataList 传入的listMap
     * @param entities 转换后的lis
     * @param listCloumn 表列名
     * @param interType 数据来源
     * @see
     * @since 1.0
     */
    @SuppressWarnings("unused")
	private List<ChbLoanDetailContractDto> convertMapToEntity(List<Map<String, String>> dataList, String dateSource) {
    	List<ChbLoanDetailContractDto> rs = new ArrayList<ChbLoanDetailContractDto>();
        for (Map<String, String> map : dataList) {
        	ChbLoanDetailContractDto t = new ChbLoanDetailContractDto();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            for (String key : map.keySet()) {
            	if(key==null||key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(ChbLoanDetailContractDto.class,
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
            t.setDataSource(dateSource);
            rs.add(t);
        }
        return rs;
    }
    
//    private List<MstBondCreditRating> dataConversionEntity(List<Map<String, String>> datamapList) {
//    	List<MstBondCreditRating> rs = new ArrayList<MstBondCreditRating>();
//    	for(Map<String, String> map : datamapList){
//			MstBondCreditRating dto = new MstBondCreditRating();
//    		dto.setoCode(map.get("SECID"));//债券代码（市场代码）
//        	dto.setGradeDate(DateUtil.parse(map.get("PUBDATE")));//评级日期
//        	dto.setGradeOrgId(map.get("RATINGORG"));//评级机构
//        	dto.setoType(map.get("RATINGTYPE"));//评级类型,01:主体评级、02:债项评级
//        	dto.setShortLongTerm(map.get("RATINGSUBTYPE"));//长短期,01:短期 02:长期
//        	dto.setGradeResult(map.get("RATING"));//评级结果
////        	dto.setSecIssueId(map.get("CREATE_DEPT"));//债券发行人
////        	dto.setInputType(map.get("CREATE_DEPT"));//录入方式
//        	dto.setIsRecommand(map.get("IS_RECOMMAND"));//是否推荐,01是02否
//			if(StringUtils.isNotBlank(map.get("CREATE_DEPT"))){
//				dto.setCreateDept(map.get("CREATE_DEPT"));//创建部门
//			}
//			if(StringUtils.isNotBlank(map.get("CREATE_TIME"))) {
//				dto.setCreateTime(DateUtil.parse(map.get("CREATE_TIME")));//创建时间
//			}
//			if(StringUtils.isNotBlank(map.get("CREATE_USER"))){
//				dto.setCreateUser(map.get("CREATE_USER"));//创建人
//			}
//			if(StringUtils.isNotBlank(map.get("UPDATE_TIME"))) {
//				dto.setUpdateTime(DateUtil.parse(map.get("UPDATE_TIME")));//更新时间
//			}
//			if(StringUtils.isNotBlank(map.get("UPDATE_USER"))) {
//				dto.setUpdateUser(map.get("UPDATE_USER"));//更新人
//			}
//    		rs.add(dto);
//    	}
//		return rs;
//	}
}
