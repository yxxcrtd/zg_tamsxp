package com.joyintech.tams.svreport.interf.chb.proddeal.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.xml.parsers.ParserConfigurationException;

import com.joyintech.framework.file.SafelyFile;
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
import org.xml.sax.SAXException;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbFileReadMonitorInfo;
import com.joyintech.tams.common.base.entity.ChbPrdFeeDeal;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbInterfErrodataMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbInterfErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.common.TxtParseUtil;
import com.joyintech.tams.report.common.XmlParseUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.proddeal.dao.ChbPrdDealInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 产品费用交易接口文件解析并入库
 * @author yuluan
 *
 */
@Service
public class SvrProdFeeDealFileImpService extends FileImpService{
	/**
	 * CHB_PRD_DEAL_NAME
	 */
	private static final String CHB_PRD_FEEDEAL = "CHB_PRD_FEEDEAL";
	/**
	 * 业务主键
	 */
	private static final String BUSINESSKEY_CHB_PRD_FEEDEAL = "TRADE_ID";
	
	 /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
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
    
    @Autowired
   	private DataCheckService dataCheckService;
    @Autowired
   	private  ChbInterfErrodataMapper chbInterfErrodataMapper;
    
	private boolean excuteProdBaseInfoByStep(List<Map<String, String>> baseinfoList, 
			String tmpPath, ImpTable table, String saveFlag,String dataSource) {

		// 定义新增数据list-验证不通过数据
//		List<ChbPrdFeeDeal> listNA = new ArrayList<ChbPrdFeeDeal>();
		// 定义新增数据list-验证通过数据
		List<ChbPrdFeeDeal> listNE = new ArrayList<ChbPrdFeeDeal>();
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
		listNE = (List<ChbPrdFeeDeal>) convertMapToEntity(baseinfoList,ChbPrdFeeDeal.class, dataSource);
		listNE.stream().forEach(dto->{
			dto.setSeqno(IDGenerator.generate(36));
		});
//		Date paydate=null;
//		for (Map<String, String> map : baseinfoList) {
//			ChbPrdFeeDeal feedeal =  dataConversionEntity(map);
//			if(paydate==null&&feedeal.getPayDate()!=null){
//				paydate=feedeal.getPayDate();
//			}
//			feedeal.setDataSource(dateSource);
//
//			listNE.add(feedeal);
//		}
		// modify by convertMapToEntity yangxianjiu 2022-01-19 end
		if (listNE != null && listNE.size() > 0) {
			// 如果是增量文件则不需要删除
//			if (tmpPath == null) {
			insertPrdFeeDealDataMerge(listNE);
			
			logger.debug("产品费用交易插入结束--------------" + new Date());
//			}else{
//					chbPrdDealInterfMapper.deletePrdFeeDealByDate(paydate);
//				createDataFile(tmpPath + "/CHB_PRD_FEEDEAL", listNE, table.getColumns());
//			}
			return CollectionUtils.isNotEmpty(listNE);
		}
		
	  return false;
		
	}
	private void insertPrdFeeDealDataMerge(List<ChbPrdFeeDeal> listNE) {
		 SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
					 	int count = 0;
			            logger.debug("产品费用交易插入开始--------------" + new Date());
			            ChbPrdDealInterfMapper batchMapper = session.getMapper(ChbPrdDealInterfMapper.class);
			            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
			            int batchLastIndex = batchCount;//每批最后一个的下标
			            for(int index = 0; index < listNE.size();){
			                if(batchLastIndex > listNE.size()-1){
			                    batchLastIndex = listNE.size();
			                    batchMapper.insertChbPrdFeeDealData(listNE.subList(index, batchLastIndex));
			                    break;//数据插入完毕,退出循环
			                }else{
			                    batchMapper.insertChbPrdFeeDealData(listNE.subList(index, batchLastIndex));
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
			            session.commit();
			            // 清理缓存，防止溢出
			            session.clearCache();
			} finally {
				session.close();
			}
	 }
	/**
	 * map转换成实体类
	 * @param map
	 * @return
	 */
	ChbPrdFeeDeal dataConversionEntity(Map<String, String> map){
		ChbPrdFeeDeal feeDeal= new ChbPrdFeeDeal();
		feeDeal.setSeqno(IDGenerator.generate(36));
		feeDeal.setTradeId(map.get("TRADE_ID"));
		
		feeDeal.setFprodId(map.get("FPROD_ID"));//父产品代码
		feeDeal.setProdId(map.get("PROD_ID"));//产品代码
		feeDeal.setProdName(map.get("PROD_NAME"));//产品名称
//		feeDeal.setRegCode(map.get("REG_CODE"));//产品登记编码
//		feeDeal.setFeeId(map.get("FEE_ID"));
		feeDeal.setFeeName(map.get("FEE_NAME"));
		feeDeal.setFeeType(map.get("FEE_TYPE"));
		feeDeal.setFeeSectype(map.get("FEE_SECTYPE"));
		feeDeal.setCcy(map.get("CCY"));
		if(map.get("AMT")!=null&&!"".equals(map.get("AMT"))){
			feeDeal.setAmt(new BigDecimal(map.get("AMT")));
		}
		if(map.get("PAY_DATE")!=null&&!"".equals(map.get("PAY_DATE"))){
			feeDeal.setPayDate(DateUtil.parse(map.get("PAY_DATE")));
		}
		feeDeal.setOrgCode(map.get("ORG_CODE"));
		feeDeal.setDeptCode(map.get("DEPT_CODE"));
		feeDeal.setStatus(map.get("STATUS"));
		if(map.get("ERRMSG")!=null&&!"".equals(map.get("ERRMSG"))){
			feeDeal.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
		}
		feeDeal.setDataSource("12");
		if(map.get("REV_DATE")!=null&&!"".equals(map.get("REV_DATE"))){
			feeDeal.setRevDate(DateUtil.parse(map.get("REV_DATE")));
		}
		feeDeal.setUpdateUser("system");
		feeDeal.setUpdateTime(DateUtil.parse(map.get("UPDATE_TIME")));
		feeDeal.setCreateUser("system");
		feeDeal.setCreateTime(DateUtil.parse(map.get("CREATE_TIME")));
		feeDeal.setRemark(map.get("REMARK"));
		feeDeal.setRivalPartyType(map.get("RIVAL_PARTY_TYPE"));
		feeDeal.setTradeCounter(map.get("TRADE_COUNTER"));
		//新增交易员和交易审批人身份证号和姓名字段 add by  tuqirong 2021-12-23 start
		feeDeal.setInvestApproverId(map.get("INVEST_APPROVER_ID"));
		feeDeal.setInvestApproverName(map.get("INVEST_APPROVER_NAME"));
		feeDeal.setInvestTraderId(map.get("INVEST_TRADER_ID"));
		feeDeal.setInvestTraderName(map.get("INVEST_TRADER_NAME"));
		//新增交易员和交易审批人身份证号和姓名字段 add by  tuqirong 2021-12-23 start
		return feeDeal;
		
	}
    
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag,String dateSource) {
		String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		boolean success = true;
		if (listData.size() <= 1000) {
			success = excuteProdBaseInfoByStep(listData,  null, null, saveFlag,dateSource);
		} else {
//			listData= Collections.synchronizedList(listData);
			// 创建表
			ImpTable table = new ImpTable(CHB_PRD_FEEDEAL);
			table.setColumns(ImportGenerator.createImpTableColumns(CHB_PRD_FEEDEAL));
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
					tasks.add(new Callable<Boolean>() {

						@Override
						public Boolean call() {
							return excuteProdBaseInfoByStep(subList,  tmpPath, table, saveFlag,dateSource);
						}
					});
				}
			}

			// 为线程池添加任务
			List<Future<Boolean>> taskResult;
            try {
                taskResult = service.invokeAll(tasks);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
                return "fail";
            }
			for (Future<Boolean> res : taskResult) {
				try {
                    success = success && res.get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage(),e);
                    return "fail";
                }
			}
			// 任务结束，停止线程池的所有线程
			service.shutdown();

			Collection<File> files = FileUtils.listFiles(new SafelyFile(tmpPath), new String[] { "tmp" }, false);
			List<String> content = new ArrayList<>(listData.size());
			String result = "";
			for (File f : files) {
				if (f.getName().startsWith(CHB_PRD_FEEDEAL)) {
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
			if ("fail".equals(result)) {
				return result;
			}
			// 系统是否使用的db2数据库
            boolean isDB2DataBase = CommonUtil.isDB2DataBase();
            if (isDB2DataBase) {
                // db2数据库处理
                if (CollectionUtils.isNotEmpty(content)) {
                    try {
                        success = success && ImportGenerator.importToDBByPart(table, tmpPath,
                            content, reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "del", reportSqlldrDatabaseName);
                    } catch (IOException | InterruptedException e) {
                        logger.error(e.getMessage(),e);
                        return "fail";
                    }
                }
            }
            else {
            	// oracle原逻辑
				if (CollectionUtils.isNotEmpty(content)) {
	               try {
                    success = success && ImportGenerator.importToDBByPart(table, tmpPath, content,
                               reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount,
                               "txt", reportSqlldrDatabaseName);
                } catch (IOException | InterruptedException e) {
                    logger.error(e.getMessage(),e);
                    return "fail";
                }
		        }
            }
		}
//	}
	// 数据解析完成，并导入数据库成功后，将ftp文件修改名称
	if (success) {
		return "success";
	}
	//add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
//    monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
    //add by liubin end
	return "fail";
	}
	@Override
	public String importDB(List<Map<String, String>> listData,InterfFile interfFile, String date) {
		String result="success";
//		 	List<Map<String, String>> listEerro = 
		 			checkData(listData,interfFile.getFilename(),InterfDefine.产品费用交易接口数据.getCode(),InterfDefine.产品费用交易接口数据.getCode(),BUSINESSKEY_CHB_PRD_FEEDEAL,BUSINESSKEY_CHB_PRD_FEEDEAL);
//		 	listData.addAll(listEerro);
		 	ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品费用交易接口数据.getCode());
	        if (interfConfig == null){
	            logger.info("CHB_INTERF_CONFIG表中无配置项！");
	            return "fail";
	        }
	        String saveFlag = interfConfig.getContentType();
	        if(!listData.isEmpty()){
	        	result=saveData(listData, saveFlag, interfFile.getDataSource());
	        }
	        // 更新系统配置时间
	        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.产品费用交易接口数据.getCode());
			return result;
	}
}
