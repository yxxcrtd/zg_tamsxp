package com.joyintech.tams.svreport.interf.chb.prodinfo.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.base.entity.ChbPrdPeriodYield;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.prodinfo.dao.ChbPrdPeriodYieldInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
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

/**
 * @Description 报价信息文件接口
 * @author jiangxiao
 * @version 1.7 2019年5月10日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrProdPeriodyieldFileImpService extends FileImpService {

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
	private ChbPrdPeriodYieldInterfMapper chbPrdPeriodYieldInterfMapper;

	@Autowired
	private BatchInsertService batchInsertService;
	
	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile,String date) {
		String result = "";
		logger.debug("报价信息检查----开始-----:" +  df.format(new Date()));
		checkData(listData, interfFile.getFilename(), InterfDefine.产品收益率净值接口数据.getCode(),InterfDefine.产品收益率净值接口数据.getCode(), null,"FPROD_ID","PROD_ID", "CHANGE_DATE", "CHANG_TYPE");
		logger.debug("报价信息检查----结束-----:" +  df.format(new Date()));
		if(CollectionUtils.isEmpty(listData)){
			return "success";
		}
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.产品收益率净值接口数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        String saveFlag = interfConfig.getContentType();
        logger.debug("批量入库报价信息----开始-----:" +  df.format(new Date()));
		result = saveData(listData,saveFlag,interfFile.getDataSource());
		logger.debug("批量入库报价信息----开始-----:" +  df.format(new Date()));
		// 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.产品收益率净值接口数据.getCode());
		return result;
	}
	
	@Override
	public String saveData(List<Map<String, String>> periodyieldList, String saveFlag,String dataSource) {
        String tmpPath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 解析成功或失败的标识
		boolean success = true;
		if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
			logger.debug("报价信息map转dto----开始-----:" +  df.format(new Date()));
			// modify by convertMapToEntity yangxianjiu 2022-01-19 start
//			List<ChbPrdPeriodYield> listNE = convertMapToEntity(periodyieldList,dateSource);
			List<ChbPrdPeriodYield> listNE = (List<ChbPrdPeriodYield>) convertMapToEntity(periodyieldList,ChbPrdPeriodYield.class, dataSource);
			listNE.stream().forEach(dto->{
				dto.setSeqno(IDGenerator.generate(36));
			});
			// modify by convertMapToEntity yangxianjiu 2022-01-19 end
			logger.debug("报价信息map转dto----开始-----:" +  df.format(new Date()));
			if(!CollectionUtils.isEmpty(listNE)){
				mergeIntoData(listNE);
			}
		}else if (periodyieldList.size() <= 1000) {
			// 数据小于1000直接入库
			success = excuteProdPeriodyieldByStep(dataSource,saveFlag,periodyieldList, null, null, null, null, null);
		} else {
			// 大于1000使用sqlloader入库
			try {
				ImpTable table = new ImpTable("CHB_PRD_PERIODYIELD");
				table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
				// 临时文件存放目录
				String path = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

				batchInsertService.exeBatchldr(path, periodyieldList, table);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "fail";
			}
		}
		return "success";
	}
	
	/**
	 * @Description 收益率入库方式：新增，日全量（根据updatetime，createtime删除数据）
	 * @version 1.7 2019年5月20日
	 */
	private boolean excuteProdPeriodyieldByStep(String dataSource,String saveFlag, List<Map<String, String>> periodyieldList, String type, String[] result,
			String tmpPath, ImpTable table, ImpTable subTable) {
		// 定义新增数据list
		logger.debug("报价信息map转dto----开始-----:" +  df.format(new Date()));
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
//		List<ChbPrdPeriodYield> listNE = convertMapToEntity(periodyieldList,dateSource);
		List<ChbPrdPeriodYield> listNE = (List<ChbPrdPeriodYield>) convertMapToEntity(periodyieldList,ChbPrdPeriodYield.class, dataSource);
		listNE.stream().forEach(dto->{
			dto.setSeqno(IDGenerator.generate(36));
			// 创建部门
			dto.setCreateDept(CommonUtil.getDeptCode());
			// 部门代码
			dto.setDeptCode(CommonUtil.getDeptCode());
			// 机构代码
			dto.setOrgCode(CommonUtil.getOrgCode());
			// 数据来源
			dto.setDataSource(SysDataUtil.getNameFromDataDict(DictCode.数据来源, dataSource));
		});
		// modify by convertMapToEntity yangxianjiu 2022-01-19 end
		logger.debug("报价信息map转dto----开始-----:" +  df.format(new Date()));
		if (listNE != null && listNE.size() > 0) {
			deleteAllData();
//			if (tmpPath == null) {
				// 插入报价数据
				insertBatch(listNE);
//			} else {
//				createDataFile(tmpPath + "/CHB_PRD_PERIODYIELD/CHB_PRD_PERIODYIELD", listNE, table.getColumns());
//			}
		}
		return (CollectionUtils.isNotEmpty(listNE));
	}
	
	@SuppressWarnings("unused")
	private ChbPrdPeriodYield dataConversionEntity(Map<String, String> map) {
		ChbPrdPeriodYield dto = new ChbPrdPeriodYield();
		dto.setSeqno(IDGenerator.generate(36));//主键
		dto.setFprodId(map.get("FPROD_ID"));//父产品id
		dto.setProdId(map.get("PROD_ID"));//产品id
		dto.setChangeDate(DateUtil.parse(map.get("CHANGE_DATE")));//变动日期
		dto.setChangType(map.get("CHANG_TYPE"));//变动类型 01:净值变动 02:收益率变动
		if(map.get("PROD_VALUE") != null){
			dto.setProdValue(new BigDecimal(map.get("PROD_VALUE")));//变动值
		}
		dto.setStatus(map.get("STATUS"));//数据状态
		dto.setRevDate(DateUtil.parse(map.get("REV_DATE")));//撤销日
		dto.setDataSource(map.get("DATA_SOURCE"));//数据来源
		dto.setCreateTime(DateUtil.parse(map.get("CREATE_USER")));//创建人
		dto.setCreateUser(map.get("CREATE_TIME"));//创建时间
		dto.setCcy(map.get("CCY"));//币种
		if(map.get("UNIT_VALUE_TOTAL") != null){
			dto.setUnitValueTotal(new BigDecimal(map.get("UNIT_VALUE_TOTAL")));//单位累计净值
		}
		return dto;
	}
	
	private void deleteAllData() {
		chbPrdPeriodYieldInterfMapper.deleteTodayData();
	}
    
	@SuppressWarnings("unused")
	private void deleteBatch(List<ChbPrdPeriodYield> periodyieldlist) {
		int singalCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?
				                           "100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));
		List<List<ChbPrdPeriodYield>> bigList = createList(periodyieldlist,singalCount);
		for(int i=0;i < bigList.size();i++){
			chbPrdPeriodYieldInterfMapper.deleteBatch2(bigList.get(i));
		}
	}
	
	/**
	 * 将集合均分成几个大小一致的子集合
	 * @param 集合
	 * @param 子集合大小
	 * @return
	 */
	public static List<List<ChbPrdPeriodYield>>  createList(List<ChbPrdPeriodYield> targe,int size) {
		List<List<ChbPrdPeriodYield>> listArr = new ArrayList<List<ChbPrdPeriodYield>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<ChbPrdPeriodYield> sub = new ArrayList<ChbPrdPeriodYield>();
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

	private void insertBatch(List<ChbPrdPeriodYield> periodyieldlist) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    	//批量插入mapper
		ChbPrdPeriodYieldInterfMapper chbPrdPeriodYieldInterfMapper =  session.getMapper(ChbPrdPeriodYieldInterfMapper.class);
		try{
			int count = 0;
			for(ChbPrdPeriodYield sch : periodyieldlist){
				chbPrdPeriodYieldInterfMapper.insert(sch);
        		count++;
        		if(count % 1000==0){
            		session.commit();
            		session.clearCache();
        		}
        	}
			session.commit();
    		session.clearCache();
		} finally {
    		session.close();
    	}
	}
	
	private void mergeIntoData(List<ChbPrdPeriodYield> periodyieldlist) {
		logger.debug("批量插入报价信息----开始-----:" +  df.format(new Date()));
		int singalCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?
                "100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));
		List<List<ChbPrdPeriodYield>> bigList = createList(periodyieldlist,singalCount);
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    	//批量插入mapper
		ChbPrdPeriodYieldInterfMapper chbPrdPeriodYieldInterfMapper =  session.getMapper(ChbPrdPeriodYieldInterfMapper.class);
		try{
			int count = 0;
			for(List<ChbPrdPeriodYield> sch : bigList){
				chbPrdPeriodYieldInterfMapper.mergeChbPrdPeriodYieldData(sch);
        		count++;
        		if(count % 10==0){
            		session.commit();
            		session.clearCache();
        		}
        	}	
			session.commit();
    		session.clearCache();
		} finally {
    		session.close();
    	}
		logger.debug("批量插入报价信息----结束-----:" +  df.format(new Date()));		
	}
	
	/**
     * 将map转换为entity格式
     *
     * @param infoList 传入的listMap
     * @param entities 转换后的lis
     * @param listCloumn 表列名
     * @param interType 数据来源
     * @see
     * @since 1.0
     */
    private List<ChbPrdPeriodYield> convertMapToEntity(List<Map<String, String>> dataList, String dateSource) {
        // 获取产品表的列
    	List<ChbPrdPeriodYield> rs = new ArrayList<ChbPrdPeriodYield>();
        for (Map<String, String> map : dataList) {
        	ChbPrdPeriodYield t = new ChbPrdPeriodYield();
        	t.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            for (String key : map.keySet()) {
            	if(key==null||key.equals("ERRMSG")){//待处理的数据
            		t.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
            		continue;
            	}
                String value = map.get(key);
                Field field = ReflectionUtils.findField(ChbPrdPeriodYield.class,
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
                    } else if ("java.util.Date".equals(type.getTypeName())) {
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
            t.setSeqno(IDGenerator.generate(36));//主键
            // 创建人
            t.setCreateUser(CommonUtil.getUserName());
            t.setUpdateUser(CommonUtil.getUserName());
            // 创建部门
            t.setCreateDept(CommonUtil.getDeptCode());
            // 部门代码
            t.setDeptCode(CommonUtil.getDeptCode());
            // 机构代码
            t.setOrgCode(CommonUtil.getOrgCode());
            // 数据来源
            t.setDataSource(SysDataUtil.getNameFromDataDict(DictCode.数据来源, dateSource));
            rs.add(t);
        }
        return rs;
    }
}
